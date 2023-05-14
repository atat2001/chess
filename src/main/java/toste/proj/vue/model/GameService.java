package toste.proj.vue.model;

import org.springframework.stereotype.Service;
import toste.proj.vue.dto.Fen;
import toste.proj.vue.dto.MoveResponse;
import toste.proj.vue.model.Game;
import toste.proj.vue.model.GameRepository;
import toste.proj.vue.model.FenRepository;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final FenRepository fenRepository;
    private Game game;
    public GameService(GameRepository gameRepository, FenRepository fenRepository) {
        this.fenRepository = fenRepository;
        this.gameRepository = gameRepository;
        this.game = new Game();
    }
    public void saveGame(){
        if(gameRepository.findById(game.getId()).isPresent()){
            if(game.compareMoves(gameRepository.findById(game.getId()).get().movelist)){
                return;
            }
            else{
            }
        }
        gameRepository.save(game);
        fenRepository.save(game.startingPos);
    }
    public Fen undo(int val){
        game.undo(val);
        return game.toFen();
    }

    public Fen getPosition(){
        return game.toFen();
    }
    public MoveResponse move(int move, int promotionType){
        return game.moveResponse(move, promotionType);
    }
    public Fen load(int id){
        if(gameRepository.findById(id).isPresent()){
            this.game = new Game(gameRepository.findById(id).get().movelist,gameRepository.findById(id).get().startingPos);
        }
        return this.game.toFen();
    }

}
