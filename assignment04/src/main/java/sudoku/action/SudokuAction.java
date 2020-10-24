package sudoku.action;

import gridgames.data.action.Action;

public class SudokuAction implements Action {
	
	private int row;
	private int col;
	private int digit;
	
	public SudokuAction(int row, int col, int digit) {
		this.row = row;
		this.col = col;
		this.digit = digit;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public int getDigit() {
		return this.digit;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public Action getActionFromDescription(String description) {
		return null;
	}

}
