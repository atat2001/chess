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
    @Override
    public int[][] getPossibleMoves(){
        int[][] returner = new int[8][2];
        int current_moves = 0;
        int[] from = {this.position[0], this.position[1]};
        for (int[] e: new int[][]{{-2,-1}, {-2,1}, {-1,-2}, {-1,2},{1,2},{1,-2},{2,1},{2,-1}}){
            int[] possible_move = new int[]{this.position[0] + e[0], this.position[1] + e[1]};
            if(board.checkMove(this.position, possible_move, this.isWhite))
                returner[current_moves++] = possible_move;
        }
        int[][] aux = returner;
        returner = new int[current_moves][2];
        while(current_moves != 0){
            current_moves--;
            returner[current_moves] = aux[current_moves];
        }
        System.out.println("Horse from " + this.position[0] + "," + this.position[1] + "moves: ");
        for(int[] e: returner){
            System.out.print(e[0] + "," + e[1] + " - ");

        }
        return returner;
    }
}
