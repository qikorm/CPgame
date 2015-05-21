import java.awt.event.*;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/* This panel represents the game board (grid) 
 * It also responds to game related events
 * The overridden paintcompnent() is called whenever the board
 * or the pieces needs to be updated 
 */
public class BoardPanel extends JPanel implements ActionListener {

	private Player player;
	private Monster monster;
	private Grid grid;
	private Graphics gr;
	private Game game;
	private Image image=new ImageIcon("src/player.png").getImage();
	private Image image2=new ImageIcon("src/monster.png").getImage();
	private Image nut=new ImageIcon("src/nut.jpg").getImage();

	private Image image3 = null;
	private boolean isSnakeMode = false;
	// random to get the color
	private Color c[] = { Color.red, Color.blue, Color.green, Color.gray,
			Color.CYAN, Color.pink };
	Color c1 = c[(int) (Math.random() * 6)];
	public int goldrate = 2;
	private final int CELLWIDTH = 40;
	private final int CELLHEIGHT = 40;
	private final int LMARGIN = 500;
	private final int TMARGIN = 100;
	public boolean isPause = false;

	public BoardPanel(Grid g, Player p, Monster m, Game gm, Image image3) {
		this.requestFocus();
		player = p;
		grid = g;
		monster = m;
		game = gm;
		gr = this.getGraphics();
		this.image3 = image3;

	}

	public void processParentEvent(AWTEvent e) {
		this.processEvent(e);
	}

	/* responds to various button clicked messages */
	public void actionPerformed(ActionEvent e) {
		if (((JButton) e.getSource()).getText().compareTo("up") == 0) {
			player.setDirection('U');
			if (player.getMoves() < 2) {// more than one steps in one move,
										// increasing moving ability

				player.setMoves(player.getMoves() + 1);
				game.playOnce("file:src/sound/moveM.wav");
			}
		} else if (((JButton) e.getSource()).getText().compareTo("down") == 0) {
			player.setDirection('D');
			if (player.getMoves() < 2) {

				player.setMoves(player.getMoves() + 1);
				game.playOnce("file:src/sound/moveM.wav");
			}
		} else if (((JButton) e.getSource()).getText().compareTo("left") == 0) {
			player.setDirection('L');
			if (player.getMoves() < 2) {

				player.setMoves(player.getMoves() + 1);
				game.playOnce("file:src/sound/moveM.wav");
			}
		} else if (((JButton) e.getSource()).getText().compareTo("right") == 0) {
			player.setDirection('R');
			if (player.getMoves() < 2) {
				player.setMoves(player.getMoves() + 1);
				game.playOnce("file:src/sound/moveM.wav");
			}
		} else if (((JButton) e.getSource()).getText().compareTo("start") == 0) {
			// check if pause
			if (isPause) {
				isPause = !isPause;
			} else {
				player.setReady(true);
			}
		}
		// restart
		else if (((JButton) e.getSource()).getText().compareTo("reStart") == 0) {
			if (this.game.end) {
				try {
					this.game.dispose(); // -1 销毁旧窗口
					Game newGame = new Game("Game");
					newGame.setFocusable(true); // -1 焦点设为新的窗口
					newGame.setTitle("Monster Game");
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					newGame.setSize(screenSize.width, screenSize.height - 100);
					newGame.setLocationRelativeTo(null); // center the frame
					newGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					newGame.setVisible(true);
					new Thread(newGame).start();
					//newGame.player.setReady(true); // -1 重新开始，不自动追赶
					this.game.end = false;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		} else if (((JButton) e.getSource()).getText().compareTo("Pause") == 0) {
			isPause = !isPause;
		} else if (((JButton) e.getSource()).getText().compareTo("Setting") == 0) {
			new Setting(game);
		} else if (((JButton) e.getSource()).getText().compareTo("GameHistory") == 0) {
			try {
				new showhistory();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/* returns the x coordinate based on left margin and cell width */
	private int xCor(int col) {
		return LMARGIN + col * CELLWIDTH;
	}

	/* returns the y coordinate based on top margin and cell height */
	private int yCor(int row) {
		return TMARGIN + row * CELLHEIGHT;
	}

	/*
	 * Redraws the board and the pieces Called initially and in response to
	 * repaint()
	 */
	public void setSnakeMode(boolean isSnakeMode){
		this.isSnakeMode = isSnakeMode;
	}
	
	public boolean getSnakeMode(){
		return isSnakeMode;
	}
	
	protected void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		Cell cells[] = grid.getAllCells();
		Cell cell;
		ArrayList<Cell> cellList;
		gr.drawImage(image3, 0, 0, this.getWidth(), this.getHeight(), this);

		for (int i = 0; i < cells.length; i++) {
			cell = cells[i];
			if (cell.col % 5 == 0 && cell.row % 5 == 0)
				gr.setColor(Color.pink);
			else
				gr.setColor(Color.gray);
			// gold can appear with certain possibility, increasing energy.
			if (!cell.gotGold && (new Random()).nextInt(1000) < goldrate) {
				cell.gotGold = true;
			}
			if (cell.gotGold) {
				gr.setColor(Color.red);
				
			}
			gr.fillRect(xCor(cell.col), yCor(cell.row), CELLWIDTH, CELLHEIGHT);
			gr.setColor(Color.black);
			gr.drawRect(xCor(cell.col), yCor(cell.row), CELLWIDTH, CELLHEIGHT);
		}
		cellList = player.getCellList();
		int i = 0;

			//gr.setColor(c1);
			// gr.fillOval(xCor(cellList.get(i).col) + CELLWIDTH / 8,
			// yCor(cellList.get(i).row) + CELLWIDTH / 8,
			// CELLWIDTH * 3 / 4, CELLHEIGHT * 3 / 4);
		if(isSnakeMode==false){
			gr.drawImage(image, xCor(cellList.get(0).col) + CELLWIDTH / 8,
					yCor(cellList.get(0).row) + CELLWIDTH / 8,
					CELLWIDTH * 3 / 4, CELLHEIGHT * 3 / 4, this);
		}else{
			gr.drawImage(image, xCor(cellList.get(0).col) + CELLWIDTH / 8,
					yCor(cellList.get(0).row) + CELLWIDTH / 8,
					CELLWIDTH * 3 / 4, CELLHEIGHT * 3 / 4, this);
			
			for (i = 1; i < cellList.size(); i++) {
				//gr.setColor(c1);
				gr.drawImage(nut, xCor(cellList.get(i).col) + CELLWIDTH / 8,
						yCor(cellList.get(i).row) + CELLWIDTH / 8,
						CELLWIDTH * 3 / 4, CELLHEIGHT * 3 / 4, this);
			}
			
		}

		if (monster.viewable()) {
			cell = monster.getCell();
			gr.drawImage(image2, xCor(cell.col) + CELLWIDTH / 8, yCor(cell.row)
					+ CELLWIDTH / 8, CELLWIDTH * 3 / 4, CELLHEIGHT * 3 / 4,
					this);
			// gr.setColor(Color.black);
			// gr.fillRect(xCor(cell.col), yCor(cell.row), CELLWIDTH,
			// CELLHEIGHT);

		}
	}
}