package toste.proj.vue.model;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import toste.proj.vue.controller.ChessController;
import toste.proj.vue.dto.Fen;
import toste.proj.vue.dto.MoveResponse;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.print.DocFlavor;
import java.io.IOException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;

@Entity
@Table
public class Game {
    @Transient
    private ArrayList<Fen> positionsList;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ElementCollection
    public List<Integer> movelist = new ArrayList<>();
    @OneToOne(cascade = {CascadeType.ALL})
    public Fen startingPos;
    @Transient
    public Board board;
    @Transient
    private King kingW;
    @Transient
    private King kingB;
    @Transient
    private int savedMove = 0;
    // turn
    @Transient
    private boolean isWhite = true;
    public Game(List<Integer> movelist, Fen startingPos){
        this.movelist = movelist;
        this.startingPos = startingPos;
        this.loadGame();
    }
    public Game(){
        board = new Board();
        positionsList = new ArrayList<>();
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
        positionsList.add(this.toFen());
        this.startingPos = this.toFen();
    }

    public Game(Fen aux){
        this.loadFen(aux);
    }
    
    public Game(String fen){
        this(new Fen(fen));
    }

    public boolean move(String move){
        int[][] aux = lanToPos(move);
        int[] from = aux[0];
        int[] to = aux[1];
        boolean debug = false;
        if(debug){
        System.out.println(from[0]);
        System.out.println(from[1]);
        System.out.println(to[0]);
        System.out.println(to[1]);}

        if(board.move(from, to, isWhite)) {
            isWhite = !isWhite;
            positionsList.add(this.toFen());
            movelist.add(from[0]);
            movelist.add(from[1]);
            movelist.add(to[0]);
            movelist.add(to[1]);
            movelist.add(0);
            return true;
        }
        return false;
    }

    public boolean move(ArrayList<String[]> move){
        for(String[] e: move){
            if(e[1].equals("")){
                return this.move(anToPos(e[0]));
            }
            boolean debug = false;
            if(debug){
            System.out.println("moving " + e[0] + ", " + e[1]);}
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
                positionsList.add(this.toFen());
                movelist.add(from[0]);
                movelist.add(from[1]);
                movelist.add(to[0]);
                movelist.add(to[1]);
                movelist.add(0);
                isWhite = !isWhite;
                return true;
            }
            return false;
        }
        boolean debug = false;
        if(debug){System.out.println(from[0]);
        System.out.println(from[1]);
        System.out.println(to[0]);
        System.out.println(to[1]);}

        if(board.move(from, to, isWhite)) {
            positionsList.add(this.toFen());
            movelist.add(from[0]);
            movelist.add(from[1]);
            movelist.add(to[0]);
            movelist.add(to[1]);
            movelist.add(0);
            isWhite = !isWhite;
            return true;
        }
        return false;
    }
    public boolean move(int x1, int y1, int x2, int y2,  char... promotionType){
        int[] from = new int[]{x1,y1};
        int[] to = new int[]{x2,y2};
        boolean debug = false;
        if(debug){
            System.out.println(from[0]);
            System.out.println(from[1]);
            System.out.println(to[0]);
            System.out.println(to[1]);
        }

        if(board.move(from, to, isWhite, promotionType)){
            positionsList.add(this.toFen());
            movelist.add(from[0]);
            movelist.add(from[1]);
            movelist.add(to[0]);
            movelist.add(to[1]);
            // asign a random char to move on with the code
            if(promotionType == null){
                promotionType = new char[]{'a'};
            }
            switch (promotionType[0]){
                case 'r':
                    movelist.add(1);
                    break;
                case 'h':
                    movelist.add(2);
                    break;
                case 'b':
                    movelist.add(3);
                    break;
                case 'q':
                    movelist.add(4);
                    break;
                default:
                    movelist.add(0);
            }
            isWhite = !isWhite;
            return true;
        }
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

    public int saveMove(int move,  char... promotionType){
        if(savedMove == 0) {
            if(this.getPossibleMoves(move) == null){
                return 0;
            }
            savedMove = move;
            return 2;
        }
        else {
            int moveSaved = savedMove;
            savedMove = 0;
            int aux = move(moveSaved / 10, moveSaved % 10, move / 10, move % 10, promotionType)? 1:0;
            if(this.checkmate()){
                return this.isWhite? 4:3;
            }
            return aux;
        }
    }

    // algebric notation(default notation on chess) to Pos
    public int[][] anToPos(String an){
        boolean debug = false;
        Piece[] pieces;
        int length = an.length();
        if(an.charAt(length-1) == '+' || an.charAt(length-1) == '#') {
            an = an.substring(0,length-1);
            length = length-1;
        }
        if(an.charAt(0) == 'O'){
            // long castle2
            if(debug){
            System.out.println("castle: " + an);
            }
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
                if(debug){
                System.out.println("de:");
                System.out.println( (aux2 - (isWhite? 1:-1)));
                System.out.println(to[0]);
                System.out.println("para:");
                System.out.println(aux2);
                System.out.println(to[0]);}
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
    public Fen toFen(){
        String fenPosition = board.toFen().split(" ")[0].concat(" ");
        String fenCurrentMove;
        // current move
        if(isWhite){
            fenCurrentMove = "w ";
        }
         else{
            fenCurrentMove = "b ";
        }
        String fenCastle = "";
        if(board.castleRights(true)[1]){
            fenCastle = fenCastle.concat("K");
        }
        if(board.castleRights(true)[0]){
            fenCastle = fenCastle.concat("Q");
        }
        if(board.castleRights(false)[1]){
            fenCastle = fenCastle.concat("k");
        }
        if(board.castleRights(false)[0]){
            fenCastle = fenCastle.concat("q");
        }
        if(fenCastle.equals("")){
            fenCastle = "-";
        }
        fenCastle = fenCastle.concat(" ");
        String fenEnPassant = board.toFen().split(" ")[1];

        Fen returner = new Fen(fenPosition.concat(fenCurrentMove.concat(fenCastle.concat(fenEnPassant))));
        if(this.checkmate()){
            if(this.isWhite){
                returner.checkmateBlack = true;
            }
            else
                returner.checkmateWhite = true;
        }

        return returner;
        // castle rights
    }

    public int[][] getPossibleMoves(int move){
        int[] pos = new int[]{move / 10,move % 10};
        if(board.getPosition(pos) != null && board.getPosition(pos).isWhite != isWhite){
            return null;
        }
        return board.getPossibleMoves(pos);
    }
    public boolean checkmate(){
        return board.checkmate(this.isWhite);
    }

    // loads fen as starting position
    private void loadFen(Fen aux){
        board = new Board();
        positionsList = new ArrayList<>();
        CharacterIterator it = new StringCharacterIterator(aux.position);
        this.isWhite = aux.isWhite;
        int row = 8;
        int col = 1;
        // Iterate and print current character
        while (it.current() != CharacterIterator.DONE) {
            switch(it.current()){
                case 'p':
                    board.addPiece(new int[]{col, row}, 'p',false);
                    col++;
                    it.next();
                    break;
                case 'P':
                    board.addPiece(new int[]{col, row}, 'p',true);
                    col++;
                    it.next();
                    break;
                case 'k':
                    board.addPiece(new int[]{col, row}, 'k',false);
                    kingB = (King)board.getPosition(new int[]{5, 8});
                    col++;
                    it.next();
                    break;
                case 'K':
                    board.addPiece(new int[]{col, row}, 'k',true);
                    kingW = (King)board.getPosition(new int[]{col, row});
                    col++;
                    it.next();
                    break;
                case 'n':
                    board.addPiece(new int[]{col, row}, 'h',false);
                    col++;
                    it.next();
                    break;
                case 'N':
                    board.addPiece(new int[]{col, row}, 'h',true);
                    col++;
                    it.next();
                    break;
                case 'r':
                    board.addPiece(new int[]{col, row}, 'r',false);
                    col++;
                    it.next();
                    break;
                case 'R':
                    board.addPiece(new int[]{col, row}, 'r',true);
                    col++;
                    it.next();
                    break;
                case 'b':
                    board.addPiece(new int[]{col, row}, 'b',false);
                    col++;
                    it.next();
                    break;
                case 'B':
                    board.addPiece(new int[]{col, row}, 'b',true);
                    col++;
                    it.next();
                    break;
                case 'q':
                    board.addPiece(new int[]{col, row}, 'q',false);
                    col++;
                    it.next();
                    break;
                case 'Q':
                    board.addPiece(new int[]{col, row}, 'q',true);
                    col++;
                    it.next();
                    break;
                case '/':
                    col = 1;
                    row +=-1;
                    it.next();
                    break;
                default:
                    col = col + Character.getNumericValue(it.current());
                    it.next();
                    break;
            }
        }
        // board.setCastle(boolean isWhite, boolean a,int index)
        if(aux.castle.get(0)){
            board.setCastle(true, true,0);

            board.setCastle(true, true,1);
        }
        if(aux.castle.get(1)){
            board.setCastle(true, true,1);

            board.setCastle(true, true,2);
        }
        if(aux.castle.get(2)){
            board.setCastle(false, true,0);

            board.setCastle(false, true,1);
        }
        if(aux.castle.get(3)){
            board.setCastle(false, true,1);

            board.setCastle(false, true,2);
        }
        if(aux.enPassant != null && aux.enPassant.size() == 2){
            int wasWhite = aux.enPassant.get(1) == 2?1:-1;
            board.setEnPassant(aux.enPassant.get(0), aux.enPassant.get(1) + wasWhite);
        }
        positionsList.add(this.toFen());
        this.startingPos = this.toFen();
    }

    public void undo(int undos){
        if(positionsList.size() < undos){
            return;
        }
        while(undos > 0){
            undos += -1;
            positionsList.remove(positionsList.size()-1);
            movelist = movelist.subList(0, movelist.size()-5);
            System.out.println("changed to size: " + movelist.size());
        }
        this.loadGame();
    }

    public MoveResponse moveResponse(int move, int promotionType) {

        MoveResponse returner = new MoveResponse();
        char charPromotionType;
        switch (promotionType) {
            case 2:
                charPromotionType = 'r';
                break;
            case 3:
                charPromotionType = 'h';
                break;
            case 4:
                charPromotionType = 'b';
                break;
            case 5:
                charPromotionType = 'q';
                break;
            default:
                charPromotionType = 'q';
                break;
        }
        switch (this.saveMove(move, charPromotionType)) {
            case 1:
                returner.selected = move;
                return returner;
            case 2:
                returner.selected = move;
                int[][] aux = this.getPossibleMoves(move);
                if (aux == null) {
                    return null;
                }
                int[] possibleMovesList = new int[aux.length];
                int index = 0;
                for (int[] e : aux) {
                    possibleMovesList[index++] = e[0] * 10 + e[1];
                }
                returner.possibleMoves = possibleMovesList;
                return returner;
            case 3:
                returner.checkmateBlack = true;
                return returner;
            // white won
            case 4:
                // black won
                returner.checkmateWhite = true;
                return returner;
            case 0:
                return null;
        }
        return null;
    }
    public int getId(){
        return this.id;
    }
    public void setId(int newId){
        this.id = newId;
    }
    public boolean compareMoves(List<Integer> movelist ){
        Iterator<Integer> aux = this.movelist.iterator();
        for(Integer e: movelist){
            if(aux.hasNext()){
                if(e != aux.next()){
                    return false;
                }
            }
            else{
                return false;
            }
        }
        return !aux.hasNext();
    }
    // used to generate data that isnt stored in the database
    public void loadGame(){
        // load fen(starting position)
        this.loadFen(this.startingPos);
        this.loadMoves();
    }
    public void loadMoves(){
        boolean debug = false;
        List<Integer> list = new ArrayList<>(movelist);
        positionsList = new ArrayList<>();
        movelist = new ArrayList<>();
        Iterator<Integer> iter = list.iterator();
        int x1, x2;
        int y1, y2;
        int promotion;
        char aux;
        while(iter.hasNext()){
            x1 = iter.next();
            y1 = iter.next();
            x2 = iter.next();
            y2 = iter.next();
            promotion = iter.next();
            switch (promotion){
                case 1:
                    aux = 'r';
                    break;
                case 2:
                    aux = 'h';
                    break;
                case 3:
                    aux = 'b';
                    break;
                case 4:
                    aux = 'q';
                    break;
                default:
                    aux = 'q';
            }
            if(debug)
                System.out.println("loadMoves:" + " " + x1 +" " + y1 +" " + x2 + " " + y2 + " " + aux);
            if(!this.move(x1,y1,x2,y2,new char[]{aux})){
                if(debug){
                System.out.println("Erro loadmoves");
                return;
                }
            }
        }
        if(debug)
            System.out.println("loadMoves: SUCCESS");
    }

}
