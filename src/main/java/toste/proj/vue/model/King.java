package toste.proj.vue.model;

import java.util.ArrayList;
import java.util.Arrays;

public class King extends Piece {
    public King(Board board, int[] position, boolean isWhite) {
        super(board, position, isWhite);
    }

    @Override
    public boolean move(int[] to, boolean isWhite){
        if(this.isWhite != isWhite){
            return false;
        }
        int x = to[0] - this.position[0];
        int y = to[1] - this.position[1];
        if(x > 1 || x < -1 || y > 1 || y < -1)
            return false;
        return true;
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
            if (board.getPosition(aux) != null)
                break;
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
}