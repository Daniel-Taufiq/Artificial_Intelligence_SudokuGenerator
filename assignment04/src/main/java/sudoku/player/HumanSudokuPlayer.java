package sudoku.player;

import java.util.InputMismatchException;
import java.util.Scanner;

import gridgames.data.action.Action;
import gridgames.display.Display;
import gridgames.player.Player;
import sudoku.action.SudokuAction;

public class HumanSudokuPlayer extends Player {
	
	private Scanner scanner;

	public HumanSudokuPlayer(Display display, Scanner scanner) {
		super(display);
		this.scanner = scanner;
	}

	@Override
	public Action getAction() {		
		int row = getRowCol("row");
		int col = getRowCol("column");
		int digit = getDigit();
		
		return new SudokuAction(row, col, digit);
	}
	
	private int getRowCol(String desiredInput) {
		int input = -1;
		do {
			System.out.print("Enter a " + desiredInput + " (1-9): ");
			try {
				input = scanner.nextInt();
				if(input < 1 || input > 9) {
					input = -1;
					System.out.println("Invalid input.");
				}
			} catch(InputMismatchException e) {
				System.out.println("Invalid input.");
			}
		} while(input == -1);
		return input;
	}
	
	private int getDigit() {
		int input = -1;
		do {
			System.out.print("Enter a digit (1-9, 0 to clear): ");
			try {
				input = scanner.nextInt();
				if(input < 0 || input > 9) {
					input = -1;
					System.out.println("Invalid input.");
				}
			} catch(InputMismatchException e) {
				System.out.println("Invalid input.");
			}
		} while(input == -1);
		return input;
	}
}
