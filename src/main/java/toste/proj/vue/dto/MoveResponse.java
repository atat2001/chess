package toste.proj.vue.dto;

public class MoveResponse{
    public int selected;
    public int[] possibleMoves;
    public boolean checkmateWhite = false;
    public boolean checkmateBlack = false;

    public MoveResponse(){
    }
}