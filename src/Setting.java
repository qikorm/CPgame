import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class Setting extends JFrame implements ActionListener{//set the windows

	JLabel jl1=new JLabel("Monster:");
	JRadioButton r1 = new JRadioButton("Jump");
	JRadioButton r2 = new JRadioButton("Hide");
	JRadioButton r3 = new JRadioButton("SnakeMode");

	JLabel jl2=new JLabel("Player:");
	JLabel jl21=new JLabel("Initial energy:");
	JTextField jtf21=new JTextField(5);
	JLabel jl3=new JLabel("Others:");
	JLabel jl31=new JLabel("Gold appears rate(permillage):");
	JTextField jtf31=new JTextField(5);
	JLabel jl32=new JLabel("Time duration(ms):");
	JTextField jtf32=new JTextField(5);
	JPanel jp1,jp2,jp3,jp4,jp5,jp6;
	JButton jb=new JButton("submit");
	private Game game;
	public Setting(Game game){//registration windows
		this.game=game;
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		jp4=new JPanel();
		jp5=new JPanel();
		jp6=new JPanel();
		jp1.add(jl1,BorderLayout.WEST);
		jp1.add(r1,BorderLayout.CENTER);
		jp1.add(r2,BorderLayout.EAST);
		jp2.add(jl2,BorderLayout.WEST);
		jp2.add(jl21,BorderLayout.CENTER);
		jp2.add(jtf21,BorderLayout.EAST);
		jp6.add(r3, BorderLayout.WEST);
		jp3.setLayout(new BorderLayout());
		jp3.add(jl3,BorderLayout.NORTH);
		jp4.add(jl31,BorderLayout.CENTER);
		jp4.add(jtf31,BorderLayout.EAST);
		jp5.add(jl32,BorderLayout.CENTER);
		jp5.add(jtf32,BorderLayout.EAST);
		jp5.add(jb,BorderLayout.EAST);
		jp3.add(jp4,BorderLayout.CENTER);
		jp3.add(jp5,BorderLayout.SOUTH);
		this.add(jp1,BorderLayout.NORTH);		
		this.add(jp2,BorderLayout.CENTER);		
		this.add(jp3,BorderLayout.SOUTH);	
		this.add(jp6,BorderLayout.EAST);	

		jb.addActionListener(this);
		jb.setActionCommand("submit");
		this.setVisible(true);
		this.setSize(300, 200);
		
	}
	public static void main(String args[]){
		//new Setting();
	}
	@Override
	public void actionPerformed(ActionEvent e) {//change parameters of the game according to varied preferences.
		if(e.getActionCommand().equals("submit")){
			if(r1.isSelected()){
				game.monster.setCanJump(true);
			}
			if(r2.isSelected()){
				game.monster.setCanHide(true);
			}
			if(r3.isSelected()){
				game.bp.setSnakeMode(true);
			}
			if(!jtf21.getText().equals("")){
				try{
					int k=Integer.parseInt(jtf21.getText());
					game.player.setPoints(k);
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null,
							"Please check your input!", "",
							JOptionPane.INFORMATION_MESSAGE);
				}
			
			}
			if(!jtf32.getText().equals("")){
				try{
					int k=Integer.parseInt(jtf32.getText());
					game.timeduration=k;
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null,
							"Please check your input!", "",
							JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
			if(!jtf31.getText().equals("")){
				try{
					int k=Integer.parseInt(jtf31.getText());
					game.bp.goldrate=k;
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null,
							"Please check your input!", "",
							JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
			
			this.setVisible(false);
		}
	}
}
