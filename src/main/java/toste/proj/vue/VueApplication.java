package toste.proj.vue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import toste.proj.vue.model.Board;
import toste.proj.vue.model.Game;
import toste.proj.vue.util.Parser;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;   // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

import java.io.Console;

@SpringBootApplication
public class VueApplication {

	public static void main(String[] args) {
		Game game = new Game();

		System.out.println(game.toString());
		System.out.println(game.toFen());
		Parser parser = new Parser();
		try{
		SpringApplication.run(VueApplication.class, args);

		} catch (Exception e) {
		e.printStackTrace();
		}/*
		Character a = 'a';
		String input;
		int w = 5;
		System.out.println("To play: press 1");
		System.out.println("To load fen: press 2");
		System.out.println("To load pgn: press 3");
		try{
			a = (Character) (char) System.in.read();
		}catch(Exception b){
			;
		}


		if(Character.getNumericValue(a) == 2){
			while(myReader.hasNextLine()){
				input = myReader.nextLine();
				if(parser.parsePgn(input.concat("\0")))
					continue;
				break;
			}
			System.out.println(parser.toString());
		}
		System.out.println(game.move(parser.getMoves()));
		System.out.println(game.toString());
	}*/
}
}
/*String move = "";
		Character a = 'a';
		boolean an = false;
		int w = 5;
		while(w != 0){
			try{
				a = (Character) (char) System.in.read();
			}catch(Exception b){
				w = 1;
			}
			if(a == 's')
				w = 0;
			else if(a == 'o') {
				an = true;
				while (an == true) {
					try {
						a = (Character) (char) System.in.read();
					} catch (Exception b) {
						w = 1;
					}
					if (a == 's') {
						System.out.println(move);
						System.out.println(game.anToPos(move) != null ? game.move(game.anToPos(move)) + "\n\n\n" : "false------ \n");
						System.out.println("ended " + move);
						move = "";
					}
					else if (!(a == '\n' || a == '\0'))
						move = move.concat(a.toString());
				}
			}
			else if(!(a == '\n' || a == '\0')) {
				*System.out.println(move);
				System.out.println(w);/*
				w = w - 1;
						move = move.concat(a.toString());
						}
						else if(w == 1){
						w = 5;
						game.move(move);
						if(game.board.getEnPassant() != null)
						System.out.println(game.board.getEnPassant().getPosition()[0] + 100 * game.board.getEnPassant().getPosition()[1]);
						move = "";
						}
						}
						*/