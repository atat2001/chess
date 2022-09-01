package toste.proj.vue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import toste.proj.vue.model.Board;
import toste.proj.vue.model.Game;

@SpringBootApplication
public class VueApplication {

	public static void main(String[] args) {
		Game game = new Game();
		SpringApplication.run(VueApplication.class, args);
		System.out.println("olaaaaaaa\n\na");
		String move = "";
		Character a = 'a';
		int w = 5;
		while(w != 0){
			try{
				a = (Character) (char) System.in.read();
			}catch(Exception b){
				w = 1;
			}
			if(a == 's')
				w = 0;
			else if(!(a == '\n' || a == '\0')) {
				/*System.out.println(move);
				System.out.println(w);*/
				w = w - 1;
				move = move.concat(a.toString());
			}
			else if(w == 1){
				w = 5;
				game.move(move);
				//System.out.println(game.toString());
				move = "";
			}
		}

	}

}
