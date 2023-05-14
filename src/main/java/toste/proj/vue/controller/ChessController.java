package toste.proj.vue.controller;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toste.proj.vue.dto.Fen;
import toste.proj.vue.dto.Move;
import toste.proj.vue.dto.MoveResponse;
import toste.proj.vue.model.FenRepository;
import toste.proj.vue.model.Game;
import toste.proj.vue.model.GameRepository;
import toste.proj.vue.model.GameService;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;


@RestController
@RequestMapping("/api/chess")
public class ChessController{
    private static boolean lock = false;
    private final GameService gameService;

    public ChessController(GameService gameService) {
        this.gameService = gameService;
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
    /*
    * const PieceType = {
        None: 0,
        WPawn: 1,
        WRook: 2,
        WKnight: 3,
        WBishop: 4,
        WQueen: 5,
        WKing: 6,
        BPawn: 11,
        BRook: 12,
        BKnight: 13,
        BBishop: 14,
        BQueen: 15,
        BKing: 16,

    }*/
    @CrossOrigin("http://localhost:8081")

    @PutMapping("/move")
    public MoveResponse move(@RequestBody Move move) {
        return gameService.move(move.move, move.promotionType);
    }
    @CrossOrigin("http://localhost:8081")
    @GetMapping("/position")
    public Fen getPosition(){
        return gameService.getPosition();
    }

    @CrossOrigin("http://localhost:8081")
    @GetMapping("/getMoves")
    public int[] getMoves(@RequestBody Move move){
        return null;
    }

    @CrossOrigin("http://localhost:8081")
    @PutMapping("/undo/{val}")
    public Fen undo(@PathVariable int val){
        return gameService.undo(val);
    }
    @CrossOrigin("http://localhost:8081")
    @GetMapping("/save")
    public void saveGame(){
        gameService.saveGame();
    }

    @CrossOrigin("http://localhost:8081")
    @PutMapping("/load/{id}")
    public Fen load(@PathVariable int id){
        return gameService.load(id);
    }


}
