package sudoku;

import java.util.HashSet;
import java.util.Random;

import gridgames.data.action.Action;
import gridgames.data.item.Item;
import gridgames.display.Display;
import gridgames.game.Game;
import gridgames.grid.Board;
import gridgames.grid.Cell;
import gridgames.player.Player;
import sudoku.action.SudokuAction;
import sudoku.data.SudokuItem;
import sudoku.grid.SudokuCell;

public class Sudoku extends Game {
	
	private int difficultyLevel;
	
	public Sudoku(Display display, int difficultyLevel) {
		this.board = new Board(9, 9);
		this.difficultyLevel = difficultyLevel;
        this.display = display;
        this.display.setBoard(board);
        initializeBoard();
	}

	@Override
	public void play(Player player) {
		Action move;
		do {
			display.printState(true);
			move = player.getAction();
			if(!updateCell(move)) {
				display.addMessage("That cell is fixed");
			}
		} while(!isGameOver());
	}

	@Override
	public void initializeBoard() {
		SudokuGenerator puzzleGenerator = new SudokuGenerator();
		String completedSudokuBoard = puzzleGenerator.generateSudokuPuzzle();
		parseSudokuGeneratorBoard(completedSudokuBoard);
		removeDigits();
	}
	
	public boolean updateCell(Action action) {
		SudokuAction sudokuAction = (SudokuAction) action;
		int row = sudokuAction.getRow()-1;
		int col = sudokuAction.getCol()-1;
		int digit = sudokuAction.getDigit();
		SudokuCell c = (SudokuCell) board.getCell(row, col);
		
		if(c.isModifiable()) {
			c.removeAll();
			c.add(SudokuItem.convertCharToItem(Character.forDigit(digit, 10)));
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isGameOver() {
		HashSet<Item> digits;
		Cell c;
		
		//check all rows
		for(int row=0; row<9; row++) {
			digits = new HashSet<Item>();
			for(int col=0; col<9; col++) {
				c = board.getCell(row, col);
				if(c.getItems().isEmpty() || c.getItems().contains(SudokuItem.EMPTY)) {
					return false;
				}
				digits.add(c.getItems().get(0));
			}
			if(digits.size() != 9) {
				return false;
			}
		}
		
		//check all columns
		for(int col=0; col<9; col++) {
			digits = new HashSet<Item>();
			for(int row=0; row<9; row++) {
				c = board.getCell(row, col);
				if(c.getItems().isEmpty() || c.getItems().contains(SudokuItem.EMPTY)) {
					return false;
				}
				digits.add(c.getItems().get(0));
			}
			if(digits.size() != 9) {
				return false;
			}
		}
		
		//check all blocks
		for(int rowStart=0; rowStart<9; rowStart+=3) {
			for(int colStart=0; colStart<9; colStart+=3) {
				digits = new HashSet<Item>();
				for(int row=rowStart; row<rowStart+3; row++) {
					for(int col=colStart; col<colStart+3; col++) {
						c = board.getCell(row, col);
						if(c.getItems().isEmpty() || c.getItems().contains(SudokuItem.EMPTY)) {
							return false;
						}
						digits.add(c.getItems().get(0));
					}
				}
				if(digits.size() != 9) {
					return false;
				}
			}
		}
		return true;
	}
	
	private void parseSudokuGeneratorBoard(String completedSudokuBoard) {
		SudokuCell cell;
		char cellDigit;
		int currentIndex = 21;
		for(int row=0; row<9; row++) {
			for(int col=0; col<9; col++) {
				cell = new SudokuCell(row, col, false);
				cellDigit = completedSudokuBoard.charAt(currentIndex);
				cell.add(SudokuItem.convertCharToItem(cellDigit));
				this.board.setCell(row, col, cell);
				currentIndex += 14;
			}
		}
	}

	private void removeDigits() {
		int numDigitsToRemove = 27;
		Random r = new Random();
		SudokuCell c;
		int row;
		int col;
		
		numDigitsToRemove += ((this.difficultyLevel-1)*9);
		
		while(numDigitsToRemove > 0) {
			do {
				row = r.nextInt(9);
				col = r.nextInt(9);
				c = (SudokuCell) board.getCell(row, col);
			} while(c.getItems().isEmpty());
			
			c.removeAll();
			c.setIsModifiable(true);
			numDigitsToRemove--;
		}
	}
}
