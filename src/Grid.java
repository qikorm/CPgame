import java.util.ArrayList;


// The class use to represent the grids game need. 
public class Grid {

	Cell cells[] = new Cell[57];
	Cell cells2D[][] = new Cell[11][11];

	public Grid() {
		int k = 0;
		for (int i = 0; i < 11; i++)
			for (int j = 0; j < 11; j++)
				if ((i % 5 == 0) || (j % 5 == 0 && i % 5 != 0)) {
					cells2D[i][j] = new Cell(i, j);
					cells[k++] = cells2D[i][j];
				}
	}


	// the position of row % 5 and col % 5 = 0 is specified cell
	public Cell getCell(int row, int col) throws Exception {
		if ((row % 5 != 0 && col % 5 != 0) || row < 0 || row > 10 || col < 0
				|| col > 10)
			throw new Exception("Invalid Coordiantes row = " + row + " column "
					+ col);
		return cells2D[row][col];
	}


	//Get the cell in after press move button. 
	public Cell getCell(Cell cell, char direction) {

		if (direction == ' ')
			return cell;
		if (direction == 'U') {
			if (cell.col % 5 == 0 && cell.row > 0)
				return cells2D[cell.row - 1][cell.col];
			return cell;
		} else if (direction == 'D') {
			if (cell.col % 5 == 0 && cell.row < 10)
				return cells2D[cell.row + 1][cell.col];
			return cell;
		} else if (direction == 'L') {
			if (cell.row % 5 == 0 && cell.col > 0)
				return cells2D[cell.row][cell.col - 1];
			return cell;
		} else if (direction == 'R') {
			if (cell.row % 5 == 0 && cell.col < 10)
				return cells2D[cell.row][cell.col + 1];
			return cell;
		}
		return null;
	}

	// use direction and last direction to check if the player want to move to other direction.
	// if player want to move to forward other direction, will check if the last part of body can move, if the
	// last part can not move to that direction then the player can not move.


	public ArrayList<Cell> getCell(ArrayList<Cell> cell, char direction, char lastDirection) {
		if (direction == ' ')
			return cell;
		else if (direction == lastDirection) {
			if (direction == 'U') {
				if (cell.get(0).col % 5 == 0 && cell.get(0).row > 0) {
					for (int i = cell.size() - 1; i > 0; i--) {
						cell.set(i, cell.get(i - 1));
					}
					cell.set(0, cells2D[cell.get(0).row - 1][cell.get(0).col]);
				}
			} else if (direction == 'D') {
				if (cell.get(0).col % 5 == 0 && cell.get(0).row < 10) {
					for (int i = cell.size() - 1; i > 0; i--) {
						cell.set(i, cell.get(i - 1));
					}
					cell.set(0, cells2D[cell.get(0).row + 1][cell.get(0).col]);
				}
			} else if (direction == 'L') {
				if (cell.get(0).row % 5 == 0 && cell.get(0).col > 0) {
					for (int i = cell.size() - 1; i > 0; i--) {
						cell.set(i, cell.get(i - 1));
					}
					cell.set(0, cells2D[cell.get(0).row][cell.get(0).col - 1]);
				}
			} else if (direction == 'R') {
				if (cell.get(0).row % 5 == 0 && cell.get(0).col < 10) {
					for (int i = cell.size() - 1; i > 0; i--) {
						cell.set(i, cell.get(i - 1));
					}
					cell.set(0, cells2D[cell.get(0).row][cell.get(0).col + 1]);
				}
			}
		} else {
			if (direction == 'U') {
				if (cell.get(cell.size()-1).col % 5 == 0 && cell.get(cell.size()-1).row > 0) {
					for (int i=0; i<cell.size()-1; i++) {
						cell.set(i, cell.get(i + 1));
					}
					cell.set(cell.size()-1, cells2D[cell.get(cell.size()-1).row - 1][cell.get(cell.size()-1).col]);
				}
			} else if (direction == 'D') {
				if (cell.get(cell.size()-1).col % 5 == 0 && cell.get(cell.size()-1).row < 10) {
					for (int i=0; i<cell.size()-1; i++) {
						cell.set(i, cell.get(i + 1));
					}
					cell.set(cell.size()-1, cells2D[cell.get(cell.size()-1).row + 1][cell.get(cell.size()-1).col]);
				}
			} else if (direction == 'L') {
				if (cell.get(cell.size()-1).row % 5 == 0 && cell.get(cell.size()-1).col > 0) {
					for (int i=0; i<cell.size()-1; i++) {
						cell.set(i, cell.get(i + 1));
					}
					cell.set(cell.size()-1, cells2D[cell.get(cell.size()-1).row][cell.get(cell.size()-1).col - 1]);
				}
			} else if (direction == 'R') {
				if (cell.get(cell.size()-1).row % 5 == 0 && cell.get(cell.size()-1).col < 10) {
					for (int i=0; i<cell.size()-1; i++) {
						cell.set(i, cell.get(i + 1));
					}
					cell.set(cell.size()-1, cells2D[cell.get(cell.size()-1).row][cell.get(cell.size()-1).col + 1]);
				}
			}
		}
//	    for(int i=0; i<cell.size();i++)
//	    	System.out.print(i+"."+cell.get(i).col+""+cell.get(i).row+"\n");
		return cell;
	}
	public Cell[] getAllCells() {
		return cells;
	}

	/*
	 * helper method to check whether val is in the range a to b
	 */
	private boolean inBetween(int val, int a, int b) {
		if (val >= a && val <= b)
			return true;
		else
			return false;

	}

	

	// return the best direction from source cell to the target cell.
	public char getBestDirection(Cell from, Cell to) {

		if (from.row == to.row) {
			if (from.col < to.col)
				return 'R';
			else if (from.col > to.col)
				return 'L';
		} else if (from.col == to.col) {
			if (from.row < to.row)
				return 'D';
			else if (from.row > to.row)
				return 'U';
		}

		int row = to.row;
		int col = to.col;

		if (inBetween(to.row % 5, 1, 2))
			row = to.row / 5 * 5;
		else if (inBetween(to.row % 5, 3, 4))
			row = to.row / 5 * 5 + 5;
		if (inBetween(to.col % 5, 1, 2))
			col = to.col / 5 * 5;
		else if (inBetween(to.col % 5, 3, 4))
			col = to.col / 5 * 5 + 5;

		if (from.row % 5 == 0)
			if (from.col < col)
				return 'R';
			else if (from.col > col)
				return 'L';
		if (from.col % 5 == 0)
			if (from.row < row)
				return 'D';
			else if (from.row > row)
				return 'U';
		return ' ';
	}

	// A helper method to get the absolute value 
	private int abs(int x) {
		if (x >= 0)
			return x;
		else
			return -x;
	}

	//A helper method to get the minimum of three values 
	private int min(int x, int y, int z) {
		if (x <= y && x <= z)
			return x;
		if (y <= z && y <= x)
			return y;
		return z;
	}


	// get the shortest distance between 2 cell
	public int distance(Cell from, Cell to) {
		int d = 0;
		// compute minimum horizontal distance:
		if (from.row == to.row)
			d += abs(to.col - from.col);
		else
			d += min(from.col + to.col, abs(from.col - 5) + abs(to.col - 5),
					abs(from.col - 10) + abs(to.col - 10));

		// compute minimum vertical distance as follows:
		if (from.col == to.col)
			d += abs(to.row - from.row);
		else
			d += min(from.row + to.row, abs(from.row - 5) + abs(to.row - 5),
					abs(from.row - 10) + abs(to.row - 10));
		return d;
	}

}