package toste.proj.vue.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Rook extends Piece{
    public Rook(Board board, int[] position, boolean isWhite){
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

        if(from[1] == to[1] || from[0] == to[0]){
            // check if square is empty
            if(board.checkPosition(to)){
                if(debug)
                    System.out.println("entrei rook2");

                if(board.getPosition(to).isWhite != this.isWhite) {
                    if(!emptyHV(to)){
                        if(debug)
                            System.out.println("entrei rook3");
                        return false;
                    }
                    //this.position = to;
                    return true;
                }
                if(debug)
                    System.out.println("entrei rook4");
                return false;
            }
            if(!emptyHV(to)){
                if(debug)
                    System.out.println("entrei rook5");
                return false;
            }
            //this.position = to;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "position=" + Arrays.toString(position) +
                ", isWhite=" + isWhite +
                " r " +
                '}';
    }

}
