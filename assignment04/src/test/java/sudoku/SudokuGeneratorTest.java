package sudoku;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import gridgames.data.item.Item;
import gridgames.grid.Board;
import gridgames.grid.Cell;
import sudoku.SudokuGenerator;
import sudoku.data.SudokuItem;
import sudoku.grid.SudokuCell;

public class SudokuGeneratorTest {
	
	private SudokuGenerator puzzleGenerator;

	@Test
	public void testPuzzleGeneration() {
		try {
			String sudokuBoardStr;
			Board board;
			List<Item> items;
			puzzleGenerator = new SudokuGenerator();
			
			for(int i=0; i<5; i++) {
				sudokuBoardStr = puzzleGenerator.generateSudokuPuzzle();
				board = new Board(9,9);
				parseSudokuGeneratorBoard(sudokuBoardStr, board);
				for(int row=0; row<9; row++) {
					for(int col=0; col<9; col++) {
						items = board.getCell(row, col).getItems();
						if(items == null || items.isEmpty() || items.contains(SudokuItem.EMPTY)) {
							fail("generated puzzle should not contain empty cells");
						}
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			fail("check the console for the exception stack trace");
		}
	}
	
	@Test
	public void testValidPuzzleGeneration() {
		String sudokuBoardStr;
		Board board;
		puzzleGenerator = new SudokuGenerator();
		
		for(int i=0; i<5; i++) {
			sudokuBoardStr = puzzleGenerator.generateSudokuPuzzle();
			board = new Board(9,9);
			parseSudokuGeneratorBoard(sudokuBoardStr, board);
			assertTrue("generated puzzle is not valid", isPuzzleValid(board));
		}
	}
	
	@Test
	public void testRandomPuzzleGeneration() {
		String sudokuBoardStr;
		Board board;
		ArrayList<Board> boards = new ArrayList<Board>();
		puzzleGenerator = new SudokuGenerator();
		
		for(int i=0; i<3; i++) {	
			sudokuBoardStr = puzzleGenerator.generateSudokuPuzzle();
			board = new Board(9,9);
			parseSudokuGeneratorBoard(sudokuBoardStr, board);
			assertTrue("generated puzzle is not valid", isPuzzleValid(board));
			boards.add(board);
		}
		for(int i=0; i<3; i++) {
			for(int j=i+1; j<3; j++) {
				assertTrue("generated puzzles are not sufficiently random", numCellsDifferent(boards.get(i), boards.get(j)) > 27);
			}
		}
	}
	
	private void parseSudokuGeneratorBoard(String completedSudokuBoard, Board board) {
		SudokuCell cell;
		char cellDigit;
		int currentIndex = 21;
		for(int row=0; row<9; row++) {
			for(int col=0; col<9; col++) {
				cell = new SudokuCell(row, col, false);
				cellDigit = completedSudokuBoard.charAt(currentIndex);
				cell.add(SudokuItem.convertCharToItem(cellDigit));
				board.setCell(row, col, cell);
				currentIndex += 14;
			}
		}
	}
	
	private boolean isPuzzleValid(Board board) {
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
	
	private int numCellsDifferent(Board board1, Board board2) {
		int numCellsDifferent = 0;
		for(int row=0; row<9; row++) {
			for(int col=0; col<9; col++) {
				if(board1.getCell(row, col).getItems().get(0) != board2.getCell(row, col).getItems().get(0)) {
					numCellsDifferent++;
				}
			}
		}
		return numCellsDifferent;
	}

}
