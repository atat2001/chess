package toste.proj.vue.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class Board {
    //private Hashtable<int[], Piece> pieceList = new Hashtable<>();
    private List<Piece> pieceList = new ArrayList<Piece>(64);
    private King kingW;
    private King kingB;

    private int checkMate = 0;

    private Pawn enPassant;
    private boolean[] castleW = {true, true, true};
    private boolean[] castleB = {true, true, true}; // rook, king, rook

    public Board() {
        for(int i = 0;i<64; i++)
            pieceList.add(null);

    }

    @Override
    public String toString() {
        String aux = "\n";
        int i = 0;
        for(Piece e: pieceList) {
            if(e == null){
                aux = aux.concat("#");
            }
            else
                aux = aux.concat(((Character)e.type()).toString());
            if(i++ == 7){
                i = 0;
                aux = aux.concat("\n");
            }
        }
        return "Board{" +
                "pieceList=" + aux +
                '}';
    }

    public boolean checkMove(int[] from, int[] to, boolean isWhite){
        boolean debug = false;
        if(from[0] < 1 || to[0] < 1 || from[0] > 9 || to[0] > 9 || from[1] < 1 || to[1] < 1 || from[1] > 9 || to[1] > 9 ){
            if(debug){
            System.out.println("move not possible0");}
            return false;
        }

        if(convertIndex(from) < 0 || convertIndex(from) > 63 || pieceList.get(convertIndex(from)) == null || convertIndex(to) < 0 || convertIndex(to) > 63){
            if(debug){
            System.out.println("move not possible");}
            return false;
        }
        if((pieceList.get(convertIndex(from))).move(to, isWhite)){
            Piece fromBackup = pieceList.get(convertIndex(from));
            Piece toBackup = pieceList.get(convertIndex(to));
            pieceList.set(convertIndex(to), pieceList.get(convertIndex(from)));
            pieceList.get(convertIndex(to)).setPosition(to);
            pieceList.set(convertIndex(from), null);
            if(checkCheck(isWhite)){
                pieceList.set(convertIndex(from), fromBackup);
                fromBackup.setPosition(from);
                pieceList.set(convertIndex(to), toBackup);
                if(debug){
                System.out.println("move not possible2");}
                return false;
            }
            pieceList.set(convertIndex(from), fromBackup);
            pieceList.set(convertIndex(to), toBackup);
            fromBackup.setPosition(from);
            if(debug){
            System.out.println("Move possible");}
            return true;
        }
        else{
            if(debug){
            System.out.println("move not possible3");}
            return false;
        }
    }
    public boolean move(int[] from, int[] to, boolean isWhite, char... promotionType) {
        boolean debug = false;
        if(debug){
            System.out.println(this.toString());
            System.out.println(from[0] + " " + from[1]);
            System.out.println(to[0] + " " + to[1]);
            System.out.println(pieceList.get(convertIndex(from)));
        }
        // pieceList.get(convertIndex(from)).getPossibleMoves();
        if(!checkMove(from, to, isWhite)) {
            if(debug){
            System.out.println("debug board move 0");}
            return false;
        }
        // depois fazer com for
        if((convertIndex(from) < 8 || convertIndex(from) > 55)){
            if(debug)
                System.out.println("debug board move 1");
            if(convertIndex(from) < 8) {
                if(debug){
                    System.out.println("debug board move 2");
                    System.out.println(convertIndex(from));
                }

                if (convertIndex(from) == 0)
                    setCastle(true, false, 0);
                else if (convertIndex(from) == 4) {
                    if(debug)
                        System.out.println("debug board move 2.1");
                    setCastle(true, false, 1);
                }
                else if (convertIndex(from) == 7) {
                    setCastle(true, false, 2);
                }
            }
            else if (convertIndex(from) % 8 == 0)
                    setCastle(false, false,0);
            else if (convertIndex(from) == 60) {
                if(debug)
                    System.out.println("debug board move 3");
                setCastle(false, false, 1);
            }
            else if (convertIndex(from) % 8 == 7) {
                if(debug)
                    System.out.println("debug board move 4");
                setCastle(false, false,2);
            }
        }
        else if ((convertIndex(to) < 9 || convertIndex(to) > 55) && (convertIndex(to) % 8 == 0 || convertIndex(to) % 8 == 7)) {
            if(debug)
                System.out.println("debug board move 5");
            if(convertIndex(to) < 8) {
                if(debug)
                    System.out.println("debug board move 6");
                if (convertIndex(to) == 0)
                    setCastle(true, false, 0);
                else if (convertIndex(to) == 7) {
                    setCastle(true, false, 2);
                }
            }
            else if (convertIndex(to) % 8 == 0)
                setCastle(false, false,0);
            else if (convertIndex(to) % 8 == 7) {
                if(debug)
                    System.out.println("debug board move 7");
                setCastle(false, false,2);
            }
        }
        //
        if(pieceList.get(convertIndex(from)) instanceof Pawn && ((to[1] == 1 && !pieceList.get(convertIndex(from)).isWhite()) || (to[1] == 8 && pieceList.get(convertIndex(from)).isWhite()))){
            if(promotionType == null){
                return false;
            }
            this.addPiece(to,promotionType[0], pieceList.get(convertIndex(from)).isWhite());
        }
        else{
            pieceList.set(convertIndex(to), pieceList.get(convertIndex(from)));
        }
        pieceList.get(convertIndex(to)).setPosition(to);
        pieceList.set(convertIndex(from), null);
        if (Math.abs(from[1] - to[1]) == 2 && pieceList.get(convertIndex(to)) instanceof Pawn) {
            enPassant = (Pawn)pieceList.get(convertIndex(to));
        }
        else if (pieceList.get(convertIndex(to)) instanceof Pawn && enPassant != null && to[0] == enPassant.position[0] && to[1] + (isWhite? -1:+1) == enPassant.position[1]) {
            pieceList.set(convertIndex(enPassant.getPosition()), null);
            enPassant = null;
        } else
            enPassant = null;
        return true;
    }

    // true-something on the position
    public boolean checkPosition(int[] pos){
        if(convertIndex(pos) > 63 || convertIndex(pos) < 0)
            return false;
        return pieceList.get(convertIndex(pos)) != null;
    }
    public Piece getPosition(int[] pos){
        return pieceList.get(convertIndex(pos));
    }
    public int convertIndex(int[] pos){
        return 8*pos[1] + pos[0] - 9;
    }

    public void addPiece(int[] pos, char piece, boolean isWhite){
        // switch to case
        if(piece == 'r')
            pieceList.set(convertIndex(pos), new Rook(this, pos, isWhite));
        if(piece == 'b')
            pieceList.set(convertIndex(pos), new Bishop(this, pos, isWhite));
        if(piece == 'h')
            pieceList.set(convertIndex(pos), new Horse(this, pos, isWhite));
        if(piece == 'q')
            pieceList.set(convertIndex(pos), new Queen(this, pos, isWhite));
        if(piece == 'k'){
            pieceList.set(convertIndex(pos), new King(this, pos, isWhite));
            if(isWhite)
                kingW = (King)pieceList.get(convertIndex(pos));
            else
                kingB = (King)pieceList.get(convertIndex(pos));
        }
        if(piece == 'p')
            pieceList.set(convertIndex(pos), new Pawn(this, pos, isWhite));
    }

    public Pawn getEnPassant() {
        return this.enPassant;
    }

    public King getKing(boolean isWhite) {
        return isWhite?kingW:kingB;
    }

    // da set ao castle right index index ao boolean a
    public void setCastle(boolean isWhite, boolean a,int index){
        if(isWhite) {
            if(index ==0)
                castleW[0] = castleW[0] && a;
            else if (index == 1)
                castleW[1] = castleW[1] && a;
            else
                castleW[2] = castleW[2] && a;
            return;
        }
        if(index == 0)
            castleB[0] = castleB[0] && a;
        else if (index == 1)
            castleB[1] = castleB[1] && a;
        else
            castleB[2] = castleB[2] && a;
    }

    public void printCastleRights(){
        int aux = 9;
        for(int i = 0; i < 6; i++){
            aux = aux * 10;
            if(i < 3) {
                if (castleW[i])
                    aux += 1;
            }
            else {
                if (castleB[i - 3])
                    aux += 1;
            }
        }
    }
    // returns {long castle?, short castle?}
    public boolean[] castleRights(boolean isWhite){
        return new boolean[]{(isWhite && castleW[0] && castleW[1]) || (!isWhite && castleB[0] && castleB[1]) , (isWhite && castleW[2] && castleW[1]) || (!isWhite && castleB[2] && castleB[1])};
    }

    public boolean checkCheck(boolean isWhite){
        if(isWhite)
            return kingW.isCheck();
        return kingB.isCheck();
    }
    public Horse[] possibleH(int[] position, boolean isWhite){
        Horse[] returner = new Horse[8];
        int[] from = {position[0], position[1]};
        int i = 0;
        for (int[] e: new int[][]{{-2,-1}, {-2,1}, {-1,-2}, {-1,2},{1,2},{1,-2},{2,1},{2,-1}}){
            int[] aux = new int[]{from[0] + e[0], from[1] + e[1]};
            if(aux[0] < 1 || aux[0] > 8 || aux[1] < 1 || aux[1] > 8)
                continue;
            if (this.getPosition(aux) != null && this.getPosition(aux) instanceof Horse && this.getPosition(aux).isWhite() == isWhite)
                returner[i++] =  (Horse)this.getPosition(aux);
        }
        return returner;
    }
    public Piece[] possibleRorQ(int[] position, boolean isWhite){
        isWhite = !isWhite;
        Piece[] returner = new Piece[4];
        int[] from = {position[0], position[1]};
        int v = 1;
        while(position[0] + v != 9){
            int[] aux = new int[]{from[0] + v, from[1]};
            if (this.getPosition(aux) != null &&  (this.getPosition(aux) instanceof Rook || this.getPosition(aux) instanceof Queen)  && this.getPosition(aux).isWhite() != isWhite)
                returner[0] = this.getPosition(aux);
            if (this.getPosition(aux) != null)
                break;
            v++;
        }
        v = -1;
        while(position[0] + v != 0){
            int[] aux = new int[]{from[0] + v, from[1]};
            if (this.getPosition(aux) != null &&  (this.getPosition(aux) instanceof Rook || this.getPosition(aux) instanceof Queen)  && this.getPosition(aux).isWhite() != isWhite)
                returner[1] = this.getPosition(aux);
            if (this.getPosition(aux) != null)
                break;
            v--;
        }
        v = 1;

        while(position[1] + v != 9){
            int[] aux = new int[]{from[0], v + from[1]};
            if (this.getPosition(aux) != null && (this.getPosition(aux) instanceof Rook || this.getPosition(aux) instanceof Queen) && this.getPosition(aux).isWhite() != isWhite)
                returner[2] = this.getPosition(aux);
            if (this.getPosition(aux) != null)
                break;
            v++;
        }
        v = -1;
        while(position[1] + v != 0){
            int[] aux = new int[]{from[0], v + from[1]};
            if (this.getPosition(aux) != null && (this.getPosition(aux) instanceof Rook || this.getPosition(aux) instanceof Queen) && this.getPosition(aux).isWhite() != isWhite)
                returner[3] = this.getPosition(aux);
            if (this.getPosition(aux) != null)
                break;
            v--;
        }
        isWhite = !isWhite;
        return returner;
    }

    public Piece[] possibleBorQ(int[] position, boolean isWhite){
        isWhite = !isWhite;
        Piece[] returner = new Piece[4];
        int[] from = {position[0], position[1]};
        int v = 1;
        while(position[0] + v != 9 && position[1] + v != 9){
            int[] aux = new int[]{from[0] + v, from[1] + v};
            if (this.getPosition(aux) != null &&  (this.getPosition(aux) instanceof Bishop || this.getPosition(aux) instanceof Queen)  && this.getPosition(aux).isWhite() != isWhite)
                returner[0] = this.getPosition(aux);
            if (this.getPosition(aux) != null)
                break;
            v++;
        }
        v = -1;
        while((position[0] + v)*(position[1] + v) != 0){
            int[] aux = new int[]{from[0] + v, from[1] + v};
            if (this.getPosition(aux) != null && (this.getPosition(aux) instanceof Bishop || this.getPosition(aux) instanceof Queen)  && this.getPosition(aux).isWhite() != isWhite)
                returner[1] = this.getPosition(aux);
            if (this.getPosition(aux) != null)
                break;
            v--;
        }
        v = 1;
        while(position[0] + v != 9 && position[1] - v != 0){
            int[] aux = new int[]{from[0] + v, from[1] - v};
            if (this.getPosition(aux) != null &&  (this.getPosition(aux) instanceof Bishop || this.getPosition(aux) instanceof Queen)  && this.getPosition(aux).isWhite() != isWhite)
                returner[2] = this.getPosition(aux);
            if (this.getPosition(aux) != null)
                break;
            v++;
        }
        v = -1;
        while((position[0] + v) != 0 && (position[1] - v) != 9){
            int[] aux = new int[]{from[0] + v, from[1] - v};
            if (this.getPosition(aux) != null &&  (this.getPosition(aux) instanceof Bishop || this.getPosition(aux) instanceof Queen)  && this.getPosition(aux).isWhite() != isWhite)
                returner[3] = this.getPosition(aux);
            if (this.getPosition(aux) != null)
                break;
            v--;
        }
        isWhite = !isWhite;
        return returner;
    }

    private Piece getPromotionPiece(int[] pos, boolean isWhite, char promotionType){
        switch (promotionType){
            case 'R':
                return new Rook(this,pos,isWhite);
            case 'N':
                return new Horse(this,pos,isWhite);
            case 'B':
                return new Bishop(this,pos,isWhite);
            case 'Q':
                return new Queen(this,pos,isWhite);
            default:
                return new Queen(this,pos,isWhite);
        }
    }
    public String toFen(){
        Integer empty = 0;
        int i;
        String returner = "";
        for(int z = 0;z<64; z++){
            i = (8-(z / 8))*8 - 8 + (z % 8);
            if(z % 8 == 0 && z != 0){
                if(empty != 0){
                    returner = returner.concat(empty.toString());
                    empty = 0;
                }
                returner = returner.concat("/");
            }
            if(pieceList.get(i) != null){
                if(empty != 0){
                    returner = returner.concat(empty.toString());
                    empty = 0;
                }
                if(pieceList.get(i).isWhite()){
                    returner = returner.concat(Character.valueOf(Character.toUpperCase(pieceList.get(i).type())).toString());
                }
                else{
                    returner = returner.concat(Character.valueOf(Character.toLowerCase(pieceList.get(i).type())).toString());
                }
            }
            else{
                empty += 1;
            }
        }
        if(enPassant == null){
            returner = returner.concat(" -");
        }
        else{
            returner = returner.concat(" ");
            String pos = enPassant.posToAn();
            // en passant is the square behind the pawn that moved 2 pieces
            pos = pos.replace(pos.charAt(1),(char)(pos.charAt(1) - (enPassant.isWhite()?1:-1)));
            returner = returner.concat(pos);
        }
        return returner;
    }
    public int[][] getPossibleMoves(int[] pos){
        if(pieceList.get(convertIndex(pos)) != null)
            return pieceList.get(convertIndex(pos)).getPossibleMoves();
        return null;
    }
    public boolean checkmate(boolean isWhite){
        for(Piece e: pieceList){
            if(e == null)
                continue;
            if(e.getPossibleMoves() != null && e.isWhite() == isWhite && e.getPossibleMoves().length != 0)
                return false;
        }
        return true;
    }
    public void setEnPassant(int x, int y){
        if(this.getPosition(new int[]{x,y}) != null && this.getPosition(new int[]{x,y}) instanceof Pawn){
            this.enPassant = (Pawn)this.getPosition(new int[]{x,y});
        }
        else{
            System.out.println("error setenpassant");
        }
    }
}
