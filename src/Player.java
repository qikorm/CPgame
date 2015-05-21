import java.util.ArrayList;

/*  This class encapsulates player position and direction  
 */
public class Player extends Moveable {
	private boolean readyToStart = false;
	private int points = 40;
	private int moves=0;
	private int score=0;
	private boolean body = true;
	private char lastDirection[] = new char[3];
    private ArrayList<Cell> cellList = new ArrayList<Cell>();

	private char currentDirection[]=new char[3];//store quick move
	public Player(Grid g, int row, int col, int points) throws Exception {
		super(g);
		currentCell = grid.getCell(row, col);
		this.setPoints(points);
		currentDirection[0] = ' ';
		currentDirection[1] = ' ';
		currentDirection[2] = ' ';
		cellList.add(currentCell);

		lastDirection[0] = ' ';
		lastDirection[1] = ' ';
		lastDirection[2] = ' ';
	}

//	public Player(Grid g, int row, int col) throws Exception {
//		super(g);
//		currentCell = grid.getCell(row, col);
//		currentDirection[0] = ' ';
//		currentDirection[1] = ' ';
//		currentDirection[2] = ' ';
//		cellList.add(currentCell);
//		lastDirection[0] = ' ';
//		lastDirection[1] = ' ';
//		lastDirection[2] = ' ';	}
	
	 //  get the celllist and get the last direction 
	   
	  // to add the cell list
	   public void addCellList(Cell cell)
	   {
		   cellList.add(cellList.get(cellList.size()-1));
		   for(int i=cellList.size()-2; i > 0; i--)
			   cellList.set(i, cellList.get(i-1));
		   cellList.set(0, cell);
	   }
	   public ArrayList<Cell> getCellList()
	   {
		   return cellList;
	   }
	   
	public void setDirection(char d) {
		currentDirection [moves] = d;
	}

	public char getDirection(int i) {
		return currentDirection[i];
	}
	
	public Cell move() {
		
		cellList = grid.getCell(cellList,currentDirection[0],lastDirection[0]);
		 if(currentDirection[0] != ' ')
	    	   this.lastDirection = currentDirection;
		 int countpoints=0;//recording energy consumed by moving
		for(int i=0;i<=moves;i++){ //a loop to deal with every step of move
			Cell lastcell=currentCell;
			currentCell = grid.getCell(currentCell, currentDirection[i]);
			if(!(lastcell.col==currentCell.col)||!(lastcell.row==currentCell.row)){//moved
				countpoints++;
			}
			if(currentCell.gotGold){//get the gold and increasing energy amount by 6
				cellList.add(cellList.get(cellList.size()-1));
				score++;
				this.points+=6;
				getBody();
				currentCell.gotGold=false;
			}
		}
		if(countpoints==1){// reduce points according to how many steps moves
			points-=2;
		}else if(countpoints==2){
			points-=6;
		}else if(countpoints==3){
			points-=14;
		}
		return cellList.get(0);
	}

	public int maxCellsPerMove() {
		return 1;
	}
	
	public boolean getBody(){
		return body;
	}

	public void setReady(boolean val) {
		readyToStart = val;
	}

	public boolean isReady() {
		return readyToStart;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	 public Cell getCell()
	   {
		   return cellList.get(0);
	   }
}