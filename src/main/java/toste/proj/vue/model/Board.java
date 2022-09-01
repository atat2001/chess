package toste.proj.vue.model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Board {
    //private Hashtable<int[], Piece> pieceList = new Hashtable<>();
    private List<Piece> pieceList = new ArrayList<Piece>(64);
    private King kingW;
    private King kingB;
    public Board() {
        for(int i = 0;i<64; i++)
            pieceList.add(null);

    }

    @Override
    public String toString() {
        String aux = "";
        for(int i = 0;i<64; i++) {
            if (pieceList.get(i) != null) {
                aux = aux.concat("\n");
                aux = aux.concat(pieceList.get(i).toString());
            }
        }
        return "Board{" +
                "pieceList=" + aux +
                '}';
    }

    public boolean checkMove(int[] from, int[] to, boolean isWhite){
        if(convertIndex(from) < 0 || convertIndex(from) > 63 || pieceList.get(convertIndex(from)) == null || convertIndex(to) < 0 || convertIndex(to) > 63){
            System.out.println(convertIndex(from));
            System.out.println(convertIndex(to));
            System.out.println("move not possible");
            return false;
        }
        if((pieceList.get(convertIndex(from))).move(to, isWhite)){
            Piece fromBackup = pieceList.get(convertIndex(from));
            Piece toBackup = pieceList.get(convertIndex(to));
            pieceList.set(convertIndex(to), pieceList.get(convertIndex(from)));
            pieceList.get(convertIndex(to)).setPosition(to);
            if(checkCheck(isWhite)){
                pieceList.set(convertIndex(to), toBackup);
                pieceList.get(convertIndex(to)).setPosition(to);
                pieceList.set(convertIndex(from), fromBackup);
                pieceList.get(convertIndex(from)).setPosition(from);
                System.out.println("move not possible2");
                return false;
            }
            pieceList.set(convertIndex(from), fromBackup);
            pieceList.set(convertIndex(to), toBackup);
            return true;
        }
        else{
            System.out.println(convertIndex(from));
            System.out.println(convertIndex(to));
            System.out.println("move not possible3");
            return false;
        }
    }
    public boolean move(int[] from, int[] to, boolean isWhite) {
        if(!checkMove(from, to, isWhite))
            return false;
        pieceList.set(convertIndex(to), pieceList.get(convertIndex(from)));
        pieceList.get(convertIndex(to)).setPosition(to);
        pieceList.set(convertIndex(from), null);
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

    public boolean checkCheck(boolean isWhite){
        if(isWhite)
            return kingW.isCheck();
        return kingB.isCheck();
    }


}
