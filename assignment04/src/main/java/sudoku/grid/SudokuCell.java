package sudoku.grid;

import gridgames.grid.Cell;

public class SudokuCell extends Cell {
	
	private boolean isModifiable;

	public SudokuCell(int row, int col, boolean isModifiable) {
		super(row, col);
		this.isModifiable = isModifiable;
	}
	
	public boolean isModifiable() {
		return this.isModifiable;
	}
	
	public void setIsModifiable(boolean isModifiable) {
		this.isModifiable = isModifiable;
	}

}
