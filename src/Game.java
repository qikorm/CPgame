
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.MediaException;
import javax.swing.*;

import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URL;


/* This class is the main System level class which creates all the objects 
 * representing the game logic (model) and the panel for user interaction. 
 * It also implements the main game loop 
 */

public class Game extends JFrame implements Runnable, ControllerListener{

	private final int TIMEALLOWED = 30;
	public int timeduration = 1000;
	private JButton GameHistory = new JButton("GameHistory");
	private JButton setting = new JButton("Setting");
	private JButton pause = new JButton("Pause");
	private JButton up = new JButton("up");
	private JButton down = new JButton("down");
	private JButton left = new JButton("left");
	private JButton right = new JButton("right");
	private JButton start = new JButton("start");
	private JButton reStart = new JButton("reStart"); 
	private JLabel mLabel = new JLabel("Time Remaining : " + TIMEALLOWED);
	private JLabel labelpoints = new JLabel("Energy remains:");

	Image image3=new ImageIcon("src/plane.jpg").getImage();  
	
	private Grid grid;
	public Player player;
	public Monster monster;
	public BoardPanel bp;
	String username;
	JFrame jf=this;
	javax.media.Player play; // -1音频播放对象
	
	public boolean end = false; // game over signal

	/*
	 * This constructor creates the main model objects and the panel used for
	 * UI. It throws an exception if an attempt is made to place the player or
	 * the monster in an invalid location.
	 */
	public Game(String username) throws Exception {
		// listening to keyboard control（-1 按键监听重新更换了监听方式）
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
		    public void eventDispatched(AWTEvent event) {
		    	KeyEvent e = (KeyEvent) event;
		        if (e.getID() == KeyEvent.KEY_PRESSED) {
		        	if (e.getID() == KeyEvent.KEY_PRESSED) {
		            	if(e.getKeyChar()=='w'){
		        			player.setDirection('U');
		        			//quick move. more steps in one move, increasing the moving ability
		        			if(player.getMoves()<2){
		        				player.setMoves(player.getMoves()+1);
		        				playOnce("file:src/sound/moveM.wav");
		        			}
		        		}else if(e.getKeyChar()=='s'){
		        			player.setDirection('D');
		        			if(player.getMoves()<2){
		        				player.setMoves(player.getMoves()+1);
		        				playOnce("file:src/sound/moveM.wav");
		        				
		        			}
		        		}else if(e.getKeyChar()=='a'){
		        			player.setDirection('L');
		        			if(player.getMoves()<2){
		        				player.setMoves(player.getMoves()+1);
		        				playOnce("file:src/sound/moveM.wav");
		        			}
		        		}else if(e.getKeyChar()=='d'){
		        			player.setDirection('R');
		        			if(player.getMoves()<2){
		        				player.setMoves(player.getMoves()+1);
		        				playOnce("file:src/sound/moveM.wav");
		        			}
		        		}
		            }
		        }
		    }
		}, AWTEvent.KEY_EVENT_MASK);
	    
		grid = new Grid();
		player = new Player(grid, 0, 0, 1000);
		monster = new Monster(grid, player, 5, 5);
		bp = new BoardPanel(grid, player, monster, this, image3);
		this.username=username;
		// Create a separate panel and add all the buttons
		JPanel panel = new JPanel();
		panel.add(up);
		panel.add(down);
		panel.add(left);
		panel.add(right);
		panel.add(start);
		panel.add(reStart);
		panel.add(pause);
		panel.add(setting);
		panel.add(GameHistory);
		panel.add(mLabel);
		panel.add(labelpoints);
		
		// add Action listeners to all button events
		up.addActionListener(bp);
		down.addActionListener(bp);
		left.addActionListener(bp);
		right.addActionListener(bp);
		start.addActionListener(bp);
		reStart.addActionListener(bp); 
		pause.addActionListener(bp);
		setting.addActionListener(bp);
		GameHistory.addActionListener(bp);
		labelpoints.setText("Energy remains:" + player.getPoints());
		// add panels to frame
		
		
		add(bp, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
		


	}

	// method to delay by specified time in ms
	public void delay(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// the method will run since the game is start until end.
	public String play() throws Exception {
		int time = 0;
		String message;
//		int i = 0;
		player.setDirection(' '); // set to no direction
		while (!player.isReady()) {
			delay(100);
		}
		do {
			
			if(player.getCell().col == 3 && player.getCell().row == 0)
			 {
				 player.addCellList(grid.getCell(0, 3));
				 
			 }
			 
			 if(player.getCell().col == 5 && player.getCell().row == 0 )
			 {
				 player.addCellList(grid.getCell(0, 5));
				 
			 }

			while (bp.isPause) {

			}
			Cell newPlayerCell = player.currentCell;
			// To check if have enough energy to move
			if (player.getPoints() > 0) {
				newPlayerCell = player.move();
			}
			if (newPlayerCell == monster.getCell()) {
				break;
			}
			player.setMoves(0);// reset to no direction
			player.setDirection(' ');
			player.setMoves(1);
			player.setDirection(' ');
			player.setMoves(2);
			player.setDirection(' ');
			player.setMoves(0);
			Cell newMonsterCell = monster.move();
			// -1 如果是贪吃蛇模式，那么魔王碰到了勇者头部或身体任意一节，就算输掉啦
			if(bp.getSnakeMode()){
				boolean hitFlag = false; //碰撞标记
				for(Cell c : player.getCellList()){
					if (newMonsterCell == c) {
						hitFlag = true;
						break;
					}
				}
				if(hitFlag){
					playOnce("file:src/sound/eatM.wav");
					break;
				}
			} else {
				if (newMonsterCell == player.getCell()) {
					playOnce("file:src/sound/eatM.wav");
					break;
				}
			}
			
			// if monster's distance is less than 5, then hide
			if (monster.ifCanHide()
					&& grid.distance(newPlayerCell, newMonsterCell) <= 5) {
				monster.setcanView(false);
			} else {
				monster.setcanView(true);
			}
			

			
			if (monster.ifCanJump() && newPlayerCell.row == newMonsterCell.row
					&& newMonsterCell.row % 5 == 0) {// monster can jump
				break;
			}
			if (monster.ifCanJump() && newPlayerCell.col == newMonsterCell.col
					&& newMonsterCell.col % 5 == 0) {
				break;
			}
			// update time and repaint
			time++;
			mLabel.setText("Time Remaining : " + (TIMEALLOWED - time));
			labelpoints.setText("Energy remains:" + player.getPoints());//  display the amount of energy.
			delay(timeduration);
			bp.repaint();
		} while (time < TIMEALLOWED);

		if (time < TIMEALLOWED) { // players has been eaten up
			playOnce("file:src/sound/monsterM.wav");
			message = "Player Lost";
			JOptionPane.showMessageDialog(null, "Player Lost", "",
					JOptionPane.INFORMATION_MESSAGE);
			save("lost");
		} else {
			playOnce("file:src/sound/plsyerM.wav");
			message = "Player won";
			JOptionPane.showMessageDialog(null, "Player won", "",
					JOptionPane.INFORMATION_MESSAGE);
			save("won");
		}
		mLabel.setText(message);
		this.end = true;// end of game
		return message;
	}

	public static void main(String args[]) throws Exception {

		Login lg = new Login();
		
	}
	public void save(String s){
		File fileName=new File("C:\\Record.txt");
		try {
			if (!fileName.exists()) {
				fileName.createNewFile();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = "";
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(new File("C:\\Record.txt"));
			bufferedReader = new BufferedReader(fileReader);
			try {
				String read = null;
				while ((read = bufferedReader.readLine()) != null) {
					result = result + read + "\r\n";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		FileOutputStream o = null;
		try {
			o = new FileOutputStream(new File("C:\\Record.txt"),true);
			PrintWriter pw = new PrintWriter(o);
			pw.write(("Palyer:"+username+" Score:"+player.getScore()+" Statu:"+s+" \n").toCharArray());
			pw.flush();
			o.close();
			pw.close();
			// mm=new RandomAccessFile(fileName,"rw");
			// mm.writeBytes(content);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	@Override
	public void run() {
		playLoop("file:src/sound/backgroundM.mid");
		//in UI thread, if there are two user interfaces with a dead loop in one of them, then another UI will break down, so here 
		// an extra thread are required to run the game.
		try {
			play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* -1 播放游戏音频文件(使用jmf.jar扩展包)start */
	public void playLoop(String path){
		try {
			//创建播放对象
			play = Manager.createPlayer(new URL(path));
			//对player对象注册监听器，在相关事件发生的时候执行相关的动作
			play.addControllerListener(this);
			play.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MediaException e) {
			e.printStackTrace();
		}
	}
	
	public void playOnce(String path){
		try {
			javax.media.Player play = Manager.createPlayer(new URL(path));
			play.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MediaException e) {
			e.printStackTrace();
		}
	}

	// -1 音频文件播放监听
	public void controllerUpdate(ControllerEvent ce) {
		// 背景音乐播放结束，重新开始播放
		if (ce instanceof EndOfMediaEvent) {
			play.start();
		}
		
	}
	
	/* -1 播放游戏音频文件end */
}
