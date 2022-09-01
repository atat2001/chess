package toste.proj.vue.controller;

import org.springframework.web.bind.annotation.*;
import toste.proj.vue.model.Game;


@RestController
@RequestMapping("/api/chess")
public class ChessController{
    private Game game;

    public ChessController() {
        game = new Game();
    }

    @PutMapping("/move/{move}")
    public int move(@PathVariable String move){
        int moveInt = Integer.parseInt(move);
        game.saveMove((int)moveInt);
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
        return moveInt;
    }
}