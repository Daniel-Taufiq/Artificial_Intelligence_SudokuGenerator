package sudoku;

import java.util.Random;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

import gridgames.grid.Cell;

public class SudokuGenerator {
	
	public String generateSudokuPuzzle() 
	{
		Model model = new Model("sudoku");
		Solver solver = model.getSolver();
		Solution solution = new Solution(model);
		// TODO: complete
		IntVar[][] vars = new IntVar[9][9];

		// create variables and domains
		for (int row = 0; row < vars.length; row++)
		{
			for (int col = 0; col < vars[row].length; col++) 
			{
				// domain: 1 - 9 (we add row+1 because cell starts at cell(1,1))
				vars[row][col] = model.intVar("cell (" + (row + 1) + "," + (col + 1) + ")", 1, 9);
			}
		}

		IntVar[] varColumns = new IntVar[9];
		IntVar[] varRows = new IntVar[9];
		IntVar[] squareList = new IntVar[9];
		int rowOffset = 0;
		
		/*
		 * I'm going to use an outer loop for rows
		 * and then for the inner loop, compare each
		 * column to that row.
		 */
		
		// this outer loop represents rows 1-9
		for (int i = 0; i < 9; i++) 
		{
			// Fill the array with row values at row i
			for (int k = 0; k < 9; k++) 
			{
				varRows[k] = vars[i][k]; // this indicates only to get i
			}
			
			// this inner loop represents each column 1 - 9
			for(int p = 0; p < 9; p++)
			{				
				// Fill the array with column values at column p
				for (int j = 0; j < 9; j++) 
				{
					varColumns[j] = vars[j][p]; // this indicates only to get j column
				}

				
				if(p % 3 == 0)
				{
					// squares
					int addCol = (p / 3) * 3;
					int addRow = (p % 3) * 3;
					if(i == 3)
                    {
                        rowOffset = 3;
                    }
                    else if(i == 6)
                    {
                        rowOffset = 6;
                    }
					int index = 0; // used to keep track of where we are in array while filling it out
					for (int row = 0; row < 3; row++) 
					{
						for (int indexCol = 0; indexCol < 3; indexCol++) 
						{
							squareList[index++] = vars[row + rowOffset][indexCol + addCol];
						}
					}
				}
				
				model.allDifferent(varRows).post();
				model.allDifferent(varColumns).post();
				model.allDifferent(squareList).post();
			}	
		}
		generateRandomSolution(solver, solution);
		return solution.toString();
	}

	
	private void generateRandomSolution(Solver solver, Solution solution) {
		Random r = new Random();
		int solutionNum = r.nextInt(1000);
		solver.setRestartOnSolutions();
		
		while (solver.solve() && solutionNum > 0) {
		    solutionNum--;
		}
		solution.record();
	}
}
