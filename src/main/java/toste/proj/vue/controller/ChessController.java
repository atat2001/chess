package toste.proj.vue.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toste.proj.vue.dto.Fen;
import toste.proj.vue.model.Game;

import javax.servlet.http.HttpServletResponse;


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
    @CrossOrigin("http://localhost:8081")

    @PostMapping("/move")
    public int move(@RequestBody Move move) {
        System.out.println(move.move);
        if(game.saveMove((int)move.move)){
            return 1;
        }
        return 0;
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
