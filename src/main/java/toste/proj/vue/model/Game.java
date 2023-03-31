package toste.proj.vue.model;

import javax.print.DocFlavor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private String boardPgn;
    public Board board;
    private int w = 1;
    private King kingW;
    private King kingB;


    private int savedMove = 0;

    private boolean isWhite = true;
    public Game(){
        board = new Board();
        for(int e = 1; e < 9; e++) {
            board.addPiece(new int[]{e, 2}, 'p',true);
            board.addPiece(new int[]{e, 7}, 'p',false);
        }
        board.addPiece(new int[]{1, 1}, 'r',true);
        board.addPiece(new int[]{2, 1}, 'h',true);
        board.addPiece(new int[]{3, 1}, 'b',true);
        board.addPiece(new int[]{4, 1}, 'q',true);
        board.addPiece(new int[]{5, 1}, 'k',true);
        board.addPiece(new int[]{6, 1}, 'b',true);
        board.addPiece(new int[]{7, 1}, 'h',true);
        board.addPiece(new int[]{8, 1}, 'r',true);

        board.addPiece(new int[]{1, 8}, 'r',false);
        board.addPiece(new int[]{2, 8}, 'h',false);
        board.addPiece(new int[]{3, 8}, 'b',false);
        board.addPiece(new int[]{4, 8}, 'q',false);
        board.addPiece(new int[]{5, 8}, 'k',false);
        board.addPiece(new int[]{6, 8}, 'b',false);
        board.addPiece(new int[]{7, 8}, 'h',false);
        board.addPiece(new int[]{8, 8}, 'r',false);
        kingW = (King)board.getPosition(new int[]{5, 1});
        kingB = (King)board.getPosition(new int[]{5, 8});
    }

    public boolean move(String move){
        int[][] aux = lanToPos(move);
        int[] from = aux[0];
        int[] to = aux[1];
        /*System.out.println(from[0]);
        System.out.println(from[1]);
        System.out.println(to[0]);
        System.out.println(to[1]);*/

        if(board.move(from, to, isWhite)) {
            isWhite = !isWhite;
            return true;
        }
        return false;
        /*System.out.println("is king black in check?");
        System.out.println(kingB.isCheck());
        System.out.println("is king white in check?");
        System.out.println(kingW.isCheck());
        System.out.println("\n");*/
    }

    public boolean move(ArrayList<String[]> move){
        for(String[] e: move){
            if(e[1].equals("")){
                return this.move(anToPos(e[0]));
            }
            System.out.println("moving " + e[0] + ", " + e[1]);
            if((!this.move(anToPos(e[0]))) || (!this.move(anToPos(e[1])))){
                return false;
            }
        }
        return true;
    }
    public boolean move(int[][] aux){
        int[] from = aux[0];
        int[] to = aux[1];
        if(aux.length == 3){
            if(board.move(from, to, isWhite, (char)aux[2][0])) {
                isWhite = !isWhite;
                return true;
            }
            return false;
        }
        /*System.out.println(from[0]);
        System.out.println(from[1]);
        System.out.println(to[0]);
        System.out.println(to[1]);*/

        if(board.move(from, to, isWhite)) {
            isWhite = !isWhite;
            return true;
        }
        return false;
        /*System.out.println("is king black in check?");
        System.out.println(kingB.isCheck());
        System.out.println("is king white in check?");
        System.out.println(kingW.isCheck());
        System.out.println("\n");*/
    }
    public boolean move(int x1, int y1, int x2, int y2){
        int[] from = new int[]{x1,y1};
        int[] to = new int[]{x2,y2};
        /*System.out.println(from[0]);
        System.out.println(from[1]);
        System.out.println(to[0]);
        System.out.println(to[1]);*/

        if(board.move(from, to, isWhite)){
            isWhite = !isWhite;
            //System.out.println(this.toString());
            return true;
        }
/*
        System.out.println(x1);
        System.out.println(y1);
        System.out.println(x2);
        System.out.println(y2);
        System.out.println("\n");

        System.out.println("is king black2 in check?");
        System.out.println(kingB.isCheck());
        System.out.println("is king white in check?");
        System.out.println(kingW.isCheck());
        System.out.println("\n");*/
        return false;
    }

    // long algebric notation to pos=(from,to), from=(x,y), to= (x,y)
    public int[][] lanToPos(String move){
        int[][] aux = new int[2][2];
        switch (move.charAt(0)){
            case 'a':
                aux[0][0] = 1;
                break;
            case 'b':
                aux[0][0] = 2;
                break;
            case 'c':
                aux[0][0] = 3;
                break;
            case 'd':
                aux[0][0] = 4;
                break;
            case 'e':
                aux[0][0] = 5;
                break;
            case 'f':
                aux[0][0] = 6;
                break;
            case 'g':
                aux[0][0] = 7;
                break;
            case 'h':
                aux[0][0] = 8;
                break;

        }

        switch (move.charAt(2)){
            case 'a':
                aux[1][0] = 1;
                break;
            case 'b':
                aux[1][0] = 2;
                break;
            case 'c':
                aux[1][0] = 3;
                break;
            case 'd':
                aux[1][0] = 4;
                break;
            case 'e':
                aux[1][0] = 5;
                break;
            case 'f':
                aux[1][0] = 6;
                break;
            case 'g':
                aux[1][0] = 7;
                break;
            case 'h':
                aux[1][0] = 8;
                break;

        }
        switch (move.charAt(1)){
            case '1':
                aux[0][1] = 1;
                break;
            case '2':
                aux[0][1] = 2;
                break;
            case '3':
                aux[0][1] = 3;
                break;
            case '4':
                aux[0][1] = 4;
                break;
            case '5':
                aux[0][1] = 5;
                break;
            case '6':
                aux[0][1] = 6;
                break;
            case '7':
                aux[0][1] = 7;
                break;
            case '8':
                aux[0][1] = 8;
                break;
        }
        switch (move.charAt(3)){
            case '1':
                aux[1][1] = 1;
                break;
            case '2':
                aux[1][1] = 2;
                break;
            case '3':
                aux[1][1] = 3;
                break;
            case '4':
                aux[1][1] = 4;
                break;
            case '5':
                aux[1][1] = 5;
                break;
            case '6':
                aux[1][1] = 6;
                break;
            case '7':
                aux[1][1] = 7;
                break;
            case '8':
                aux[1][1] = 8;
                break;
        }
        return aux;

    }

    public boolean saveMove(int move){
        if(savedMove == 0) {
            savedMove = move;
            return true;
        }
        else {
            int moveSaved = savedMove;
            savedMove = 0;
            return move(moveSaved / 10, moveSaved % 10, move / 10, move % 10);
        }
    }

    // algebric notation(default notation on chess) to Pos
    public int[][] anToPos(String an){
        boolean debug = true;
        Piece[] pieces;
        int length = an.length();
        if(an.charAt(length-1) == '+' || an.charAt(length-1) == '#') {
            an = an.substring(0,length-1);
            length = length-1;
        }
        if(an.charAt(0) == 'O'){
            // long castle2
            System.out.println("castle: " + an);
            if(an.length() > 4 && an.charAt(3) == '-'){
                if(board.castleRights(isWhite)[0]){
                    if(isWhite)
                        return new int[][]{kingW.position,{kingW.position[0]-2, kingW.position[1]}};
                    else
                        return new int[][]{kingB.position,{kingB.position[0]-2, kingB.position[1]}};
                }
            }
            // short castle
            else{
                if(board.castleRights(isWhite)[1]){
                    if(isWhite)
                        return new int[][]{kingW.position,{kingW.position[0]+2, kingW.position[1]}};
                    else
                        return new int[][]{kingB.position,{kingB.position[0]+2, kingB.position[1]}};
                }
            }
        }
        int[] to;
        if(an.charAt(length-2) == '='){
            to = new int[]{an.charAt(length-4) -96, Integer.parseInt(an.substring(length-3, length-3+1))};
        }
        else {
            to = new int[]{an.charAt(length - 1 - 1) - 96, Integer.parseInt(an.substring(length - 1, length - 1 + 1))};
        }
        Character filter = null;
        switch (an.charAt(0)) {
            case 'N':
                if(length > 3 && an.charAt(1) != 'x'){
                    filter = an.charAt(1);
                }
                pieces = board.possibleH(to, isWhite);
                if(debug)
                    System.out.println(length + "pos:" + to[0] + "," + to[1]);
                for(Piece e: pieces){
                    if(e == null)
                        continue;
                    if(debug) {
                        System.out.println(e == null ? "null" : e.toString());
                        //System.out.println((e != null ? e.getPosition()[0] + " " + e.getPosition()[1]: "null") + " filter: " + (filter == null ? "null": (filter - 96) + " " + (char)filter));
                        //System.out.println(e != null? board.checkMove(e.getPosition(), to, isWhite) : "e == null");
                        //System.out.println(filter == null);
                        if(e == null && e.getPosition() != null && filter != null) {
                            System.out.println(e.getPosition()[0]);
                            System.out.println(e.getPosition()[1]);
                            System.out.println((int) filter - 96);
                            System.out.println((int) (char) filter);
                        }
                    }
                    if(board.checkMove(e.getPosition(), to, isWhite) && (filter == null || e.getPosition()[0] == (int)filter - 96 || e.getPosition()[1] == (int)(char)filter - 48)){
                        return new int[][]{e.getPosition(),to};
                    }
                }
                break;

            case 'B':
                if(length > 3 && an.charAt(1) != 'x'){
                    filter = an.charAt(1);
                }
                pieces = board.possibleBorQ(to, isWhite);
                for(Piece e: pieces){
                    if(e == null)
                        continue;
                    if(e instanceof Bishop && board.checkMove(e.getPosition(), to, isWhite) && (filter == null || e.getPosition()[0] == (int)filter - 96 || e.getPosition()[1] == (int)(char)filter - 48)){
                        return new int[][]{e.getPosition(),to};
                    }
                }
                break;
            case 'R':
                if(length > 3 && an.charAt(1) != 'x'){
                    filter = an.charAt(1);
                }
                pieces = board.possibleRorQ(to, isWhite);
                for(Piece e: pieces){
                    if(e == null)
                        continue;
                    if(e instanceof Rook && board.checkMove(e.getPosition(), to, isWhite) && (filter == null || e.getPosition()[0] == (int)filter - 96 || e.getPosition()[1] == (int)(char)filter - 48)){
                        return new int[][]{e.getPosition(),to};
                    }
                }
                break;
            case 'K':
                if(board.checkMove(board.getKing(isWhite).getPosition(),to, isWhite))
                    return new int[][]{board.getKing(isWhite).getPosition(),to};
                break;
            case 'Q':
                if(length > 3 && an.charAt(1) != 'x'){
                    filter = an.charAt(1);
                }
                pieces = board.possibleRorQ(to, isWhite);
                Piece[] pieces2 = board.possibleBorQ(to, isWhite);
                for(Piece e: pieces){
                    if(e == null)
                        continue;
                    if(e instanceof Queen && board.checkMove(e.getPosition(), to, isWhite) && (filter == null || e.getPosition()[0] == (int)filter - 96 || e.getPosition()[1] == (int)(char)filter - 48)){
                        return new int[][]{e.getPosition(),to};
                    }
                }
                for(Piece e: pieces2){
                    if(e == null)
                        continue;
                    if(e instanceof Queen && board.checkMove(e.getPosition(), to, isWhite) && (filter == null || e.getPosition()[0] == (int)filter - 96 || e.getPosition()[1] == (int)(char)filter - 48)){
                        return new int[][]{e.getPosition(),to};
                    }
                }
                break;

            default:
                int aux;
                if(length > 2) {
                    aux = an.charAt(0) - 96;
                    if(board.checkMove(new int[]{aux, to[1] - (isWhite? 1:-1)}, new int[]{to[0], to[1]}, isWhite)){
                        if(an.charAt(length-2) == '='){
                            return new int[][]{{aux, to[1] - (isWhite? 1:-1)},{to[0], to[1]},{an.charAt(length-1)}};
                        }
                        return new int[][]{{aux, to[1] - (isWhite? 1:-1)},{to[0], to[1]}};
                    }
                }
                int aux2 = to[1];
                System.out.println("de:");
                System.out.println( (aux2 - (isWhite? 1:-1)));
                System.out.println(to[0]);
                System.out.println("para:");
                System.out.println(aux2);
                System.out.println(to[0]);
                if(board.checkMove(new int[]{to[0], (aux2 - (isWhite? 1:-1))}, new int[]{to[0], aux2}, isWhite)){
                    if(an.charAt(length-2) == '='){
                        return new int[][]{{to[0],aux2-(isWhite? 1:-1)},{to[0], aux2},{an.charAt(length-1)}};
                    }
                    return new int[][]{{to[0],aux2-(isWhite? 1:-1)},{to[0], aux2}};
                }
                if(board.checkMove(new int[]{to[0], (aux2 - (isWhite? 2:-2))}, new int[]{to[0], aux2}, isWhite)){
                    if(an.charAt(length-2) == '='){
                        return new int[][]{{to[0],aux2-(isWhite? 2:-2)},{to[0], aux2},{an.charAt(length-1)}};
                    }
                    return new int[][]{{to[0],aux2-(isWhite? 2:-2)},{to[0], aux2}};
                }
                return null;
        }
        return null;
    }

    public String toString(){
        return board.toString();
    }
}
