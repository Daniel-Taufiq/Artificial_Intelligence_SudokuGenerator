import java.util.Scanner;


import gridgames.display.ConsoleDisplay;
import gridgames.display.Display;
import gridgames.player.Player;
import sudoku.Sudoku;
import sudoku.player.HumanSudokuPlayer;

public class Main {

	public static void main(String[] args) {
    	runOnConsole();
    }
    
    public static void runOnConsole() {
    	Scanner scanner = new Scanner(System.in);
    	Display display = new ConsoleDisplay();
        String choice;
        Player player = new HumanSudokuPlayer(display, scanner);
        Sudoku game = null;
        
        do {
        	do {
        		System.out.print("Enter difficulty level [1-5]: ");
        		choice = scanner.next();
        	} while(choice.compareTo("1") < 0 || choice.compareTo("5") > 0);
        	game = new Sudoku(display, Integer.parseInt(choice));
        	player = new HumanSudokuPlayer(display, scanner);
            do {
                game.play(player);
				System.out.print("Play again? [YES, NO]: ");
                choice = scanner.next().toLowerCase();
            } while(!choice.equals("yes") && !choice.equals("no"));
        } while(choice.equals("yes"));
        scanner.close();
    }
}
