package toste.proj.vue.controller;

import org.springframework.web.bind.annotation.*;
import toste.proj.vue.model.Game;


@RestController
@RequestMapping("/api/chess")
@CrossOrigin("http://localhost:8081/")
public class ChessController{
    private Game game;

    public ChessController() {
        game = new Game();
    }

    @PutMapping("/move/{move}")
    public int move(@PathVariable String move){
        int moveInt = Integer.parseInt(move);
        if(game.saveMove((int)moveInt))
            return 1;

        /*
        int diferenceX = Math.abs((moveInt%10) - (game.getObjective()%10));
        int diferenceY = Math.abs(((moveInt/10) - (game.getObjective()/10)));
        System.out.println(diferenceX);
        System.out.println(diferenceY);
        if(diferenceY+diferenceX == 0){
            System.out.println(game.getObjective());
            return 100;
        }
        return diferenceY+diferenceX;*/
        return 0;
    }
    @GetMapping("/position")
    public String getPosition(){
        return game.toFen().toString();
    }
}