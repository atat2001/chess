package toste.proj.vue.model;

import java.util.ArrayList;
import java.util.Arrays;

public class King extends Piece {
    public King(Board board, int[] position, boolean isWhite) {
        super(board, position, isWhite);
    }

    @Override
    public boolean move(int[] to, boolean isWhite){
        boolean debug = false;
        if(this.isWhite != isWhite){
            return false;
        }
        int x = to[0] - this.position[0];
        int y = to[1] - this.position[1];
        if(!(x > 1 || x < -1 || y > 1 || y < -1))
            return true;
        boolean[] aux = board.castleRights(isWhite);
        if(to[0] == this.position[0] + 2 && aux[1]){
            if(debug)
                System.out.println("king move 1");
            return board.move(new int[]{8, this.position[1]}, new int[]{this.position[0]+1, this.position[1]},this.isWhite);
        }
        if(to[0] == this.position[0] - 2 && aux[0]){
            if(debug)
                System.out.println("king move 2");
            return board.move(new int[]{1, this.position[1]}, new int[]{this.position[0]-1, this.position[1]},this.isWhite);
        }
        return false;
    }

    public boolean horseCheck(){
        int[] from = {this.position[0], this.position[1]};
        Horse a = new Horse();
        for (int[] e: new int[][]{{-2,-1}, {-2,1}, {-1,-2}, {-1,2},{1,2},{1,-2},{2,1},{2,-1}}){
            int[] aux = new int[]{from[0] + e[0], from[1] + e[1]};
            if(aux[0] < 1 || aux[0] > 8 || aux[1] < 1 || aux[1] > 8)
                continue;
            if (board.getPosition(aux) != null && board.getPosition(aux) instanceof Horse && board.getPosition(aux).isWhite() != this.isWhite)
                return true;
        }
        return false;
    }

    public boolean rookCheck(){
        int[] from = {this.position[0], this.position[1]};
        int v = 1;
        while(this.position[0] + v != 9){
            int[] aux = new int[]{from[0] + v, from[1]};
            if (board.getPosition(aux) != null &&  (board.getPosition(aux) instanceof Rook || board.getPosition(aux) instanceof Queen)  && board.getPosition(aux).isWhite() != this.isWhite)
                return true;
            if (board.getPosition(aux) != null)
                break;
            v++;
        }
        v = -1;
        while(this.position[0] + v != 0){
            int[] aux = new int[]{from[0] + v, from[1]};
            if (board.getPosition(aux) != null &&  (board.getPosition(aux) instanceof Rook || board.getPosition(aux) instanceof Queen)  && board.getPosition(aux).isWhite() != this.isWhite)
                return true;
            if (board.getPosition(aux) != null)
                break;
            v--;
        }
        v = 1;

        while(this.position[1] + v != 9){
            int[] aux = new int[]{from[0], v + from[1]};
            if (board.getPosition(aux) != null && (board.getPosition(aux) instanceof Rook || board.getPosition(aux) instanceof Queen) && board.getPosition(aux).isWhite() != this.isWhite)
                return true;
            if (board.getPosition(aux) != null)
                break;
            v++;
        }
        v = -1;
        while(this.position[1] + v != 0){
            int[] aux = new int[]{from[0], v + from[1]};
            if (board.getPosition(aux) != null && (board.getPosition(aux) instanceof Rook || board.getPosition(aux) instanceof Queen) && board.getPosition(aux).isWhite() != this.isWhite)
                return true;
            if (board.getPosition(aux) != null)
                break;
            v--;
        }
        return false;
    }

    public boolean bishopCheck(){
        int[] from = {this.position[0], this.position[1]};
        int v = 1;
        while(this.position[0] + v != 9 && this.position[1] + v != 9){
            int[] aux = new int[]{from[0] + v, from[1] + v};
            if (board.getPosition(aux) != null &&  (board.getPosition(aux) instanceof Bishop || board.getPosition(aux) instanceof Queen)  && board.getPosition(aux).isWhite() != this.isWhite)
                return true;
            if (board.getPosition(aux) != null)
                break;
            v++;
        }
        v = -1;
        while((this.position[0] + v)*(this.position[1] + v) != 0){
            int[] aux = new int[]{from[0] + v, from[1] + v};
            if (board.getPosition(aux) != null && (board.getPosition(aux) instanceof Bishop || board.getPosition(aux) instanceof Queen)  && board.getPosition(aux).isWhite() != this.isWhite)
                return true;
            if (board.getPosition(aux) != null)
                break;
            v--;
        }
        v = 1;
        while(this.position[0] + v != 9 && this.position[1] - v != 0){
            int[] aux = new int[]{from[0] + v, from[1] - v};
            if (board.getPosition(aux) != null &&  (board.getPosition(aux) instanceof Bishop || board.getPosition(aux) instanceof Queen)  && board.getPosition(aux).isWhite() != this.isWhite)
                return true;
            if (board.getPosition(aux) != null)
                break;
            v++;
        }
        v = -1;
        while((this.position[0] + v) != 0 && (this.position[1] - v) != 9){
            int[] aux = new int[]{from[0] + v, from[1] - v};
            if (board.getPosition(aux) != null &&  (board.getPosition(aux) instanceof Bishop || board.getPosition(aux) instanceof Queen)  && board.getPosition(aux).isWhite() != this.isWhite)
                return true;

            if (board.getPosition(aux) != null){
                // System.out.println(board.getPosition(aux));
                break;
            }
            v--;
        }
        return false;
    }

    public boolean pawnCheck(){
        int v = -1;
        if (this.isWhite()){
            v = 1;
        }
        if( this.position[1] + v == 0 || this.position[1] + v == 9)
            return false;
        int[] aux = new int[]{this.position[0] + 1, this.position[1] + v};
        if (board.getPosition(aux) != null && board.getPosition(aux) instanceof Pawn && board.getPosition(aux).isWhite() != this.isWhite)
            return true;
        aux[0] = aux[0] - 2;
        if (board.getPosition(aux) != null && board.getPosition(aux) instanceof Pawn && board.getPosition(aux).isWhite() != this.isWhite)
            return true;
        return false;
    }

    public boolean isCheck(){
//if(obj instanceof Horse)
        if(horseCheck()){
            return true;
        }
        if(rookCheck()){
            return true;
        }
        if(bishopCheck())
            return true;
        if(pawnCheck())
            return true;
        return false;
    }




    @Override
    public String toString() {
        return "Piece{" +
                "position=" + Arrays.toString(position) +
                ", isWhite=" + isWhite +
                " k " +
                '}';
    }

    @Override
    public char type(){
        return 'K';
    }
    @Override
    public int[][] getPossibleMoves(){
        int[][] returner = new int[8][2];
        int current_moves = 0;
        for(int x: new int[]{0,1,-1}){
            for(int y: new int[]{0,1,-1}){
                if(y == 0 && x == 0){
                    continue;
                }
                int[] possible_move = new int[]{this.position[0] + x, this.position[1] + y};
                if(board.checkMove(this.position, possible_move, this.isWhite))
                    returner[current_moves++] = possible_move;
            }
        }
        int[][] aux = returner;
        returner = new int[current_moves][2];
        while(current_moves != 0){
            current_moves--;
            returner[current_moves] = aux[current_moves];
        }
        System.out.println("King from " + this.position[0] + "," + this.position[1] + "moves: ");
        for(int[] e: returner){
            System.out.print(e[0] + "," + e[1] + " - ");

        }
        return returner;
    }


}