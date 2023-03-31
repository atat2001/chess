package toste.proj.vue.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Horse extends Piece{
    public Horse(){
    }
    public Horse(Board board, int[] position, boolean isWhite){
        super(board, position, isWhite);
    }

    @Override
    public boolean move(int[] to, boolean isWhite){
        if(this.isWhite != isWhite){
            return false;
        }
        // tells if a move is possible
        /*
        System.out.println(Arrays.toString(to));
        System.out.println(isWhite);
        System.out.println(Arrays.toString(this.position));*/
        int[] from = {this.position[0], this.position[1]};
        if(from[0] - to[0] == 2 || to[0] - from[0] == 2 )
            return to[1] - from[1] == 1 || from[1] - to[1] == 1;
        if(from[1] - to[1] == 2 || to[1] - from[1] == 2 )
            return to[0] - from[0] == 1 || from[0] - to[0] == 1;
        return false;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "position=" + Arrays.toString(position) +
                ", isWhite=" + isWhite +
                " h " +
                '}';
    }
    @Override
    public char type(){
        return 'N';
    }
}
