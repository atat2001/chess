package toste.proj.vue.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Bishop extends Piece{
    public Bishop(Board board, int[] position, boolean isWhite){
        super(board, position, isWhite);
    }


    @Override
    public boolean move(int[] to, boolean isWhite){
        if(this.isWhite != isWhite){
            return false;
        }
        // tells if a move is possible
        boolean debug = false;
        /*System.out.println(Arrays.toString(to));
        System.out.println(isWhite);
        System.out.println(Arrays.toString(this.position));*/
        int[] from = {this.position[0], this.position[1]};

        // verifica se esta na diagonal
        if(!(to[0] - from[0] ==  to[1] - from[1] || to[0] - from[0] == from[1] - to[1])) {
            if(debug)
                System.out.println("entrei Bishop0");
            return false;
        }

        // podia ter pequena otimisacao pondo nos returns mais a frente mas tenho medo de bugs
        if(!this.emptyDiag(from, to))
            return false;
        // ao chegar a to ve se e um move ou capture
        // captura
        if(board.checkPosition(to)){
            if(debug)
                System.out.println("entrei Bishop2");
            if(board.getPosition(to).isWhite != this.isWhite){
                //this.position = to;
                return true;
            }
            return false;
        }
        // move
        //this.position = to;
        return true;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "position=" + Arrays.toString(position) +
                ", isWhite=" + isWhite +
                " b " +
                '}';
    }
    @Override
    public char type(){
        return 'B';
    }

}
