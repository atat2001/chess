package toste.proj.vue.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private String boardPgn;
    private Board board;
    private int w = 1;
    private King kingW;
    private King kingB;

    private int savedMove = 0;

    private boolean whiteTurn = true;
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

    public void move(String move){
        int[][] aux = toPos(move);
        int[] from = aux[0];
        int[] to = aux[1];
        /*System.out.println(from[0]);
        System.out.println(from[1]);
        System.out.println(to[0]);
        System.out.println(to[1]);*/

        if(board.move(from, to, whiteTurn))
            whiteTurn = !whiteTurn;
        System.out.println("is king black in check?");
        System.out.println(kingB.isCheck());
        System.out.println("is king white in check?");
        System.out.println(kingW.isCheck());
        System.out.println("\n");
    }
    public void move(int x1, int y1, int x2, int y2){
        int[] from = new int[]{x1,y1};
        int[] to = new int[]{x2,y2};
        /*System.out.println(from[0]);
        System.out.println(from[1]);
        System.out.println(to[0]);
        System.out.println(to[1]);*/

        if(board.move(from, to, whiteTurn))
            whiteTurn = !whiteTurn;
        System.out.println("is king black in check?");
        System.out.println(kingB.isCheck());
        System.out.println("is king white in check?");
        System.out.println(kingW.isCheck());
        System.out.println("\n");
    }

    public int[][] toPos(String move){
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

    public void saveMove(int move){
        if(savedMove == 0)
            savedMove = move;
        else {
            move(savedMove / 10, savedMove % 10, move / 10, move % 10);
            savedMove = 0;
        }
    }

    public String toString(){
        return board.toString();
    }
}
