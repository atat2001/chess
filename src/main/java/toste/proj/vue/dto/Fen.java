package toste.proj.vue.dto;

import java.util.Arrays;

public class Fen {
    private String position;
    private boolean isWhite;
    private boolean[] castle;
    private int[] enPassant;
    // ignoring this 2 becouse they are useless for my app
    private int halfMove;
    private int fullMove;

    private String fenString = "";

    public Fen(String position, boolean isWhite, boolean[] castle, int[] enPassant) {
        this.position = position;
        this.isWhite = isWhite;
        this.castle = castle;
        this.enPassant = enPassant;
    }
    public Fen(String fen){
        this.fenString = fen;
        // System.out.println(fen);
        String[] parsed = fen.split(" ");
        position = parsed[0];
        isWhite = parsed[1].equals("w");
        castle = new boolean[]{false,false,false,false};
        // in fen the order is KQkq
        // capital letters are white
        if(parsed[2].contains("Q")){
            castle[0] = true;
        }
        if(parsed[2].contains("K")){
            castle[1] = true;
        }
        if(parsed[2].contains("q")){
            castle[2] = true;
        }
        if(parsed[2].contains("k")){
            castle[3] = true;
        }
        if(parsed[3].equals("-")){
            enPassant = null;
        }
        else{
            enPassant = new int[]{(int)(parsed[3].charAt(0) - 'a') + 1, Character.getNumericValue(parsed[3].charAt(1))};
        }
    }

    @Override
    public String toString() {
        return "Fen{" +
                "position='" + position + '\'' +
                ", isWhite=" + isWhite +
                ", castle=" + Arrays.toString(castle) +
                ", enPassant=" + Arrays.toString(enPassant) +
                ", fenString='" + fenString + '\'' +
                '}';
    }
}
