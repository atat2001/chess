package toste.proj.vue.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Piece {
    protected Board board;
    protected int[] position;
    protected boolean isWhite;

    public Piece(){

    }
    public Piece(Board board, int[] position, boolean isWhite){
        if(position[0] > 8 || position[0] < 1 || position[1] > 8 || position[1] < 1) {
            System.out.println("bad format");
            return;
        }
        this.board = board;
        this.position = position;
        this.isWhite = isWhite;

    }


    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean white) {
        isWhite = white;
    }

    public boolean move(int[] to, boolean isWhite){
        return false;
    }

    public boolean emptyHV(int[] to){
        int aux;
        int aux2;
        boolean debug = false;
        // verifica se o destino e a posicao atual
        if(to[0] == this.position[0] && to[1] == this.position[1])
            return false;
        if(debug)
            System.out.println("emptyHV 1");
        // verifica se estao no mesmo x
        if(to[0] == this.position[0]){
            // ve o lado para ir
            int incr;
            if(position[1]>to[1]) {
                incr = -1;
            }
            else{
                incr = 1;
            }
            aux = position[1] + incr;
            aux2 = to[1];
            while(aux != aux2){
                if(board.checkPosition(new int[]{to[0], aux})){
                    // System.out.println(to[0]);
                    // System.out.println(aux);
                    if(debug)
                        System.out.println("emptyHV 2");
                    return false;
                }
                aux += incr;

            }
        } else if (to[1] == this.position[1]) {
            int incr;
            if(position[0]>to[0]) {
                incr = -1;
            }
            else{
                incr = 1;
            }
            aux = position[0] + incr;
            aux2 = to[0];
            while(aux != aux2){
                if(board.checkPosition(new int[]{aux, to[1]})){
                    //System.out.println(aux);
                    //System.out.println(to[1]);
                    if(debug)
                        System.out.println("emptyHV 3");
                    System.out.println(board.getPosition(new int[]{aux, to[1]}).toString());
                    return false;
                }
                aux += incr;
            }
        }
        else
            return false;
        if(debug)
            System.out.println("emptyHV 4");
        return true;
    }

    public boolean emptyDiag(int[] from, int[] to){
        boolean debug = false;
        // cria vetor diagonal certo
        int[] incr = {-1,-1};
        if(to[0] > from[0])
            incr[0] = 1;
        if(to[1] > from[1])
            incr[1] = 1;
        // percorre diagonal certa
        from[0] += incr[0];
        from[1] += incr[1];
        while(from[0] != to[0]){
            // ve se tem algo entre a from e a to
            if(board.checkPosition(from)){
                if(debug) {
                    System.out.println("entrei Bishop1");
                    System.out.println(Arrays.toString(from));
                }
                return false;
            }
            from[0] += incr[0];
            from[1] += incr[1];
        }
        return true;
    }

    public char type(){
        return 'p';
    }
    public String posToAn(){
        char col = 'a';
        col = (char)(col + position[0] - 1);
        return new String(Character.toString(col).concat(Integer.toString(position[1])));
    }

    @Override
    public String toString() {
        return "Piece{" +
                "position=" + Arrays.toString(position) +
                ", isWhite=" + isWhite +
                '}';
    }
}
