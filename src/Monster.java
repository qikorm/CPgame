/* This class encapsulates Monster position and direction
 * It also keeps a reference to the player it is tracking
 * The canView attribute can be used to limit monster visibility
 */

public class Monster extends Moveable {
	private boolean canView = true; // allows
	private Player player;
	private boolean canJump=false;
	private boolean canHide=false;

	public Monster(Grid g, Player p, int row, int col) throws Exception {
		super(g);
		player = p;
		setCell(grid.getCell(row, col));
	}

	public Cell move() {
		currentDirection = grid.getBestDirection(currentCell, player.getCell());
		currentCell = (grid.getCell(getCell(), getDirection()));
		return currentCell;
	}

	public boolean viewable() // can be used for hiding
	{
		return canView;
	}
	public void setcanView(boolean s){
		this.canView=s;
	}

	public boolean ifCanJump() {
		return canJump;
	}

	public void setCanJump(boolean canJump) {
		this.canJump = canJump;
	}

	public boolean ifCanHide() {
		return canHide;
	}

	public void setCanHide(boolean canHide) {
		this.canHide = canHide;
	}
}