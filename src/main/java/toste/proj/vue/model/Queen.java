package toste.proj.vue.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Queen extends Piece{
    public Queen(Board board, int[] position, boolean isWhite){
        super(board, position, isWhite);
    }


    @Override
    public boolean move(int[] to, boolean isWhite){
        if(this.isWhite != isWhite){
            return false;
        }
        // tells if a move is possible
        boolean debug = false;
        boolean isBishop = true;
        /*System.out.println(Arrays.toString(to));
        System.out.println(isWhite);
        System.out.println(Arrays.toString(this.position));*/
        int[] from = {this.position[0], this.position[1]};

        // verifica se esta na diagonal
        if(!(to[0] - from[0] ==  to[1] - from[1] || to[0] - from[0] == from[1] - to[1])) {
            if(debug)
                System.out.println("entrei Queen0");
            isBishop = false;
        }
        if(isBishop) {
            // podia ter pequena otimisacao pondo nos returns mais a frente mas tenho medo de bugs
            if (!this.emptyDiag(from, to))
                return false;
            // ao chegar a to ve se e um move ou capture
            // captura
            if (board.checkPosition(to)) {
                if (debug)
                    System.out.println("entrei Queen2");
                if (board.getPosition(to).isWhite != this.isWhite) {
                    //this.position = to;
                    return true;
                }
                return false;
            }
            // move
            //this.position = to;
            return true;
        }
        else{
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
    }
    @Override
    public String toString() {
        return "Piece{" +
                "position=" + Arrays.toString(position) +
                ", isWhite=" + isWhite +
                " q " +
                '}';
    }

    @Override
    public char type(){
        return 'Q';
    }
    @Override
    public int[][] getPossibleMoves(){
        int[][] bishop = getPossibleBishopMoves();
        int[][] rook = getPossibleRookMoves();
        int[][] queen = new int[bishop.length + rook.length][2];
        int index = 0;
        for(int[] e: bishop){
            queen[index++] = e;
        }
        for(int[] e: rook){
            queen[index++] = e;
        }
        System.out.println("queen from " + this.position[0] + "," + this.position[1] + "moves: ");
        for(int[] e: queen){
            System.out.print(e[0] + "," + e[1] + " - ");
        }
        return queen;
    }
    public int[][] getPossibleRookMoves(){
        int[][] returner = new int[16][2];
        int current_moves = 0;
        int[] from = {this.position[0], this.position[1]};
        int v = 1;
        while(this.position[0] + v != 9){
            int[] aux = new int[]{from[0] + v, from[1]};
            if (board.getPosition(aux) != null){
                if(board.getPosition(aux).isWhite() != this.isWhite && board.checkMove(this.position, aux, this.isWhite))
                    returner[current_moves++] = aux;
                break;
            }
            if (board.getPosition(aux) == null && board.checkMove(this.position, aux, this.isWhite)){
                returner[current_moves++] = aux;
            }
            v++;
        }
        v = -1;
        while(this.position[0] + v != 0){
            int[] aux = new int[]{from[0] + v, from[1]};
            if (board.getPosition(aux) != null){
                if(board.getPosition(aux).isWhite() != this.isWhite && board.checkMove(this.position, aux, this.isWhite))
                    returner[current_moves++] = aux;
                break;
            }
            if (board.getPosition(aux) == null && board.checkMove(this.position, aux, this.isWhite)){
                returner[current_moves++] = aux;
            }
            v--;
        }
        v = 1;

        while(this.position[1] + v != 9){
            int[] aux = new int[]{from[0], v + from[1]};
            if (board.getPosition(aux) != null){
                if(board.getPosition(aux).isWhite() != this.isWhite && board.checkMove(this.position, aux, this.isWhite))
                    returner[current_moves++] = aux;
                break;
            }
            if (board.getPosition(aux) == null && board.checkMove(this.position, aux, this.isWhite)){
                returner[current_moves++] = aux;
            }
            v++;
        }
        v = -1;
        while(this.position[1] + v != 0){
            int[] aux = new int[]{from[0], v + from[1]};
            if (board.getPosition(aux) != null){
                if(board.getPosition(aux).isWhite() != this.isWhite && board.checkMove(this.position, aux, this.isWhite))
                    returner[current_moves++] = aux;
                break;
            }
            if (board.getPosition(aux) == null && board.checkMove(this.position, aux, this.isWhite)){
                returner[current_moves++] = aux;
            }
            v--;
        }
        // removes rest of list(was easier if i used arraylist but too late)
        int[][] aux = returner;
        returner = new int[current_moves][2];
        while(current_moves != 0){
            current_moves--;
            returner[current_moves] = aux[current_moves];
        }
        return returner;
    }
    public int[][] getPossibleBishopMoves(){
        int[][] returner = new int[13][2];
        int current_moves = 0;
        int[] from = {this.position[0], this.position[1]};
        int v = 1;
        while(this.position[0] + v != 9 && this.position[1] + v != 9){
            int[] aux = new int[]{from[0] + v, from[1] + v};
            if (board.getPosition(aux) != null){
                if(board.getPosition(aux).isWhite() != this.isWhite && board.checkMove(this.position, aux, this.isWhite))
                    returner[current_moves++] = aux;
                break;
            }
            if (board.getPosition(aux) == null && board.checkMove(this.position, aux, this.isWhite)){
                returner[current_moves++] = aux;
            }
            v++;
        }
        v = -1;
        while((this.position[0] + v)*(this.position[1] + v) != 0){
            int[] aux = new int[]{from[0] + v, from[1] + v};
            if (board.getPosition(aux) != null){
                if(board.getPosition(aux).isWhite() != this.isWhite && board.checkMove(this.position, aux, this.isWhite))
                    returner[current_moves++] = aux;
                break;
            }
            if (board.getPosition(aux) == null && board.checkMove(this.position, aux, this.isWhite)){
                returner[current_moves++] = aux;
            }
            v--;
        }
        v = 1;
        while(this.position[0] + v != 9 && this.position[1] - v != 0){
            int[] aux = new int[]{from[0] + v, from[1] - v};
            if (board.getPosition(aux) != null){
                if(board.getPosition(aux).isWhite() != this.isWhite && board.checkMove(this.position, aux, this.isWhite))
                    returner[current_moves++] = aux;
                break;
            }
            if (board.getPosition(aux) == null && board.checkMove(this.position, aux, this.isWhite)){
                returner[current_moves++] = aux;
            }
            v++;
        }
        v = -1;
        while((this.position[0] + v) != 0 && (this.position[1] - v) != 9){
            int[] aux = new int[]{from[0] + v, from[1] - v};
            if (board.getPosition(aux) != null){
                if(board.getPosition(aux).isWhite() != this.isWhite && board.checkMove(this.position, aux, this.isWhite))
                    returner[current_moves++] = aux;
                break;
            }
            if (board.getPosition(aux) == null && board.checkMove(this.position, aux, this.isWhite)){
                returner[current_moves++] = aux;
            }
            v--;
        }
        // removes rest of list(was easier if i used arraylist but too late)
        int[][] aux = returner;
        returner = new int[current_moves][2];
        while(current_moves != 0){
            current_moves--;
            returner[current_moves] = aux[current_moves];
        }
        return returner;
    }

}
