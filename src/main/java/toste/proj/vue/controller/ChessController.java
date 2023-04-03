package toste.proj.vue.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toste.proj.vue.dto.Fen;
import toste.proj.vue.model.Game;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;


@RestController
@RequestMapping("/api/chess")
public class ChessController{
    private Game game;

    public ChessController() {
        game = new Game();
    }
/*    @CrossOrigin(origins = {"http://localhost:8081", "http://localhost:8080"})
    @PutMapping("/move/{move}")
    public int move(@PathVariable int move){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        if(game.saveMove((int)moveInt))
            return new ResponseEntity<Integer>(1,headers,HttpStatus.OK);
         return 0;
    }

*/
    public static class Move{
        public int move;
        Move(){
        }
    }
    public static class MoveResponse{
        public int selected;
        public int[] possibleMoves;
        MoveResponse(){
        }
    }
    @CrossOrigin("http://localhost:8081")

    @PutMapping("/move")
    public MoveResponse move(@RequestBody Move move) {
        //System.out.println(move.move);
        MoveResponse returner = new MoveResponse();
        switch(game.saveMove((int)move.move)){
            case 1:
                returner.selected = move.move;
                return returner;
            case 2:
                returner.selected = move.move;
                int[][] aux =game.getPossibleMoves(move.move);
                if(aux == null){
                    return null;
                }
                int[] possibleMovesList = new int[aux.length];
                int index = 0;
                for(int[] e: aux){
                    possibleMovesList[index++] = e[0] * 10 + e[1];
                }
                returner.possibleMoves = possibleMovesList;
                return returner;
            case 0:
                return null;
        }
        return null;
    }
    @CrossOrigin("http://localhost:8081")
    @GetMapping("/position")
    public Fen getPosition(){
        return game.toFen();
    }

    @CrossOrigin("http://localhost:8081")
    @GetMapping("/getMoves")
    public int[] getMoves(@RequestBody Move move){
        return null;
    }


}
