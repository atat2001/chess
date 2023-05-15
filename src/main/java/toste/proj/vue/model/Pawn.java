package toste.proj.vue.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Pawn extends Piece{
    public Pawn(Board board, int[] position, boolean isWhite){
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


        if(this.isWhite() && from[1] == 2){
            if(to[0] == from[0] && 4 == to[1]){
                // check if square is empty
                if(board.checkPosition(to) || board.checkPosition(new int[]{to[0],to[1]-1})){
                    return false;
                }
                //this.position = to;
                return true;
            }
        }
        else if(!this.isWhite() && from[1] == 7) {
            if (to[0] == from[0] && 5 == to[1]) {
                // check if square is empty
                if(board.checkPosition(to) || board.checkPosition(new int[]{to[0],to[1]+1})){
                    return false;
                }
                //this.position = to;
                return true;
            }
        }
        // white move and capture
        if(this.isWhite()){
            // regular move
            if(to[0] == from[0] && from[1] + 1 == to[1]){
                // check if square is empty
                if(board.checkPosition(to)){
                    return false;
                }
                //this.position = to;
                return true;
            }
            // capture
            if(from[1] + 1 == to[1] && (from[0] + 1 == to[0] || from[0] == to[0] + 1)){
                // check if square is empty
                if(board.checkPosition(to))
                    if(!board.getPosition(to).isWhite()){
                        //this.position = to;
                        return true;
                    }
                if(board.getEnPassant() != null && board.getEnPassant().position[0] == to[0] && board.getEnPassant().position[1] == to[1] - 1){
                    return true;
                }
                return false;
            }
        }
        // black move and capture
        else{
            // regular move
            if(to[0] == from[0] && from[1] - 1 == to[1]){
                // check if square is empty
                if(board.checkPosition(to)){
                    return false;
                }
                //this.position = to;
                return true;
            }
            // capture
            if(from[1] == to[1] + 1 && (from[0] + 1 == to[0] || from[0] == to[0] + 1)){
                // check if square is empty
                if(board.checkPosition(to))
                    if(board.getPosition(to).isWhite()){
                        //this.position = to;
                        return true;
                    }

                if(board.getEnPassant() != null && board.getEnPassant().position[0] == to[0] && board.getEnPassant().position[1] == to[1] + 1){
                    return true;
                }
                return false;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return "Piece{" +
                "position=" + Arrays.toString(position) +
                ", isWhite=" + isWhite +
                " p " +
                '}';
    }

    @Override
    public int[][] getPossibleMoves(){
        int[][] returner = new int[4][2];
        int current_moves = 0;
        int[][] possible_moves;
        if(isWhite)
            possible_moves = new int[][]{{this.position[0] + 1, this.position[1] + 1}, {this.position[0] - 1, this.position[1] + 1}, {this.position[0], this.position[1] + 1}, {this.position[0], this.position[1] + 2}};
        else
            possible_moves = new int[][]{{this.position[0] + 1, this.position[1] - 1}, {this.position[0] - 1, this.position[1] - 1}, {this.position[0], this.position[1] - 1}, {this.position[0], this.position[1] - 2}};
        for(int[] possible_move: possible_moves){
            if(board.checkMove(this.position, possible_move, this.isWhite)){
                returner[current_moves++] = possible_move;
            }
        }
        int[][] aux = returner;
        returner = new int[current_moves][2];
        while(current_moves != 0){
            current_moves--;
            returner[current_moves] = aux[current_moves];
        }
        boolean debug = false;
        if(debug){
        System.out.println("Pawn from " + this.position[0] + "," + this.position[1] + "moves: ");
        for(int[] e: returner){
            System.out.print(e[0] + "," + e[1] + " - ");

        }}
        return returner;
    }

}
