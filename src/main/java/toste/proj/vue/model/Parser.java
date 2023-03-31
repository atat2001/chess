package toste.proj.vue.model;
import java.util.ArrayList;

public class Parser {
    ArrayList<String[]> moves = new ArrayList<>();
    int move_nr = 0;
    int current_move_colour = 0;
    String current_move = "";

    public Parser() {
    }

    public boolean parsePgn(String input){
        if(input.length() == 0 || input.charAt(0) == '['){
            System.out.println("[");
            return true;
        }
        while(true){
            // se for um numero:
            if(input.charAt(0) == ' '){
                System.out.println("skipping space");
                input = input.substring(1);
            }
            else if(input.charAt(0) == '\n' || input.charAt(0) == '\0'){

                System.out.println("end of file or line");
                return true;
            }
            else if(input.charAt(0) < 58){
                System.out.println("skipping move count");
                while(input.charAt(0) != '.' && input.charAt(0) != '\0' && input.charAt(0) != '\n'){
                    input = input.substring(1);
                }
                if(input.charAt(0) == '.'){
                    if(input.length() == 1){
                        return true;
                    }
                    input = input.substring(1);
                }
                else{
                    System.out.println("bug:parser parsepgn 1");
                    return false;
                }
            }
            else{
                System.out.println("adding move");
                while(input.charAt(0) != ' ' && input.charAt(0) != '\n' && input.charAt(0) != '\0'){
                    current_move = current_move.concat(input.substring(0,1));
                    input = input.substring(1);
                    System.out.println(input);
                }
                add_move(current_move);
                current_move = "";
            }
        }
    }

    private void add_move(String move){
        if(move.equals("")){
            return;
        }
        if(current_move_colour == 0){
            moves.add(move_nr, new String[]{"", ""});
            moves.get(move_nr)[current_move_colour++] = move;
        }
        else{
            moves.get(move_nr++)[current_move_colour--] = move;
        }

    }
    private String printMoves(){
        String returner = "";
        for(String[] e: moves){
            returner = returner.concat(e[0]);
            returner = returner.concat(",");
            returner = returner.concat(e[1]);
            returner = returner.concat(" ");
        }
        return returner;
    }
    public ArrayList<String[]> getMoves(){
        return moves;
    }

    @Override
    public String toString() {
        return "Parser{" +
                "moves=" + printMoves() +
                '}';
    }
}