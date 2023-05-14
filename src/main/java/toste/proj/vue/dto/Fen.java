package toste.proj.vue.dto;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table
public class Fen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    public String position;
    @Column
    public boolean isWhite;
    @Column
    @ElementCollection
    public List<Boolean> castle = new ArrayList<>();
    @Column
    @ElementCollection
    public List<Integer> enPassant = new ArrayList<>();
    @Transient
    // ignoring this 2 because they are useless for my app
    private int halfMove;
    @Transient
    private int fullMove;
    @Column
    public boolean checkmateWhite = false;
    @Column
    public boolean checkmateBlack = false;

    @Transient
    private String fenString = "";
    public Fen(){

    }
    public Fen(String position, boolean isWhite, boolean[] castle, int[] enPassant) {
        this.position = position;
        this.isWhite = isWhite;
        for (int i = 0; i < 4; i++){
            this.castle.add(castle[i]);
        }
        this.enPassant = Arrays.asList(enPassant[0], enPassant[1]);
    }
    public Fen(String fen){
        this.fenString = fen;
        // System.out.println(fen);
        String[] parsed = fen.split(" ");
        position = parsed[0];
        isWhite = parsed[1].equals("w");
        castle.add(false);
        castle.add(false);
        castle.add(false);
        castle.add(false);
        // in fen the order is KQkq
        // capital letters are white
        if(parsed[2].contains("Q")){
            castle.set(0,true);
        }
        if(parsed[2].contains("K")){
            castle.set(1,true);
        }
        if(parsed[2].contains("q")){
            castle.set(2,true);
        }
        if(parsed[2].contains("k")){
            castle.set(3,true);
        }
        if(parsed[3].equals("-")){
            enPassant = null;
        }
        else{
            enPassant.add((int)(parsed[3].charAt(0) - 'a') + 1);
            enPassant.add(Character.getNumericValue(parsed[3].charAt(1)));
        }
    }
    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString() {
        if(enPassant == null){
            return "Fen{" +
                    "position='" + position + '\'' +
                    ", isWhite=" + isWhite +
                    ", castle=" + castle.toString() +
                    ", enPassant= null" +
                    ", fenString='" + fenString + '\'' +
                    '}';
        }
        return "Fen{" +
                "position='" + position + '\'' +
                ", isWhite=" + isWhite +
                ", castle=" + castle.toString() +
                ", enPassant=" + enPassant.toString() +
                ", fenString='" + fenString + '\'' +
                '}';
    }
}
