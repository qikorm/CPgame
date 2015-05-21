import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {

	JTextField jtf1, jtf2, jtf3;
	JButton jb;
	JLabel jl1, jl2, jl3;
	JPanel j1, j2, j3;
	JRadioButton rb1, rb2;
	JPanel jp1, jp2;
	// login user interface
	public Login() {
		jtf1 = new JTextField(10);
		jtf2 = new JTextField(10);
		jtf3 = new JTextField(10);
		jl1 = new JLabel("UserName");
		jl2 = new JLabel("Password");
		jl3 = new JLabel("Confirm Password");
		JPanel jp1 = new JPanel();
		JPanel jp2 = new JPanel();
		JPanel j1 = new JPanel();
		JPanel j2 = new JPanel();
		JPanel j3 = new JPanel();
		jb = new JButton("Submit");
		j1.add(jl1, BorderLayout.WEST);
		j1.add(jtf1, BorderLayout.EAST);
		j2.add(jl2, BorderLayout.WEST);
		j2.add(jtf2, BorderLayout.EAST);
		j3.add(jl3, BorderLayout.WEST);
		j3.add(jtf3, BorderLayout.EAST);
		rb1 = new JRadioButton("Login");
		rb2 = new JRadioButton("Register");
		ButtonGroup group = new ButtonGroup();
		group.add(rb1);
		group.add(rb2);
		rb1.setSelected(true);
		jp1.setLayout(new BorderLayout());
		jp1.add(j1, BorderLayout.NORTH);
		jp1.add(j2, BorderLayout.CENTER);
		jp1.add(j3, BorderLayout.SOUTH);
		jp2.add(jb);
		jp2.add(rb1);
		jp2.add(rb2);
		jb.addActionListener(this);
		jb.setActionCommand("submit");
		this.add(jp1, BorderLayout.NORTH);
		this.add(jp2, BorderLayout.SOUTH);
		this.setSize(300, 200);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setTitle("Monster Game Login");

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals("submit")&&!jtf1.getText().equals("")&&!jtf2.getText().equals("")&&!jtf3.getText().equals("")) {
			if (rb1.isSelected()) {
				try {
					if (Lg()) {// login 
						//this.setVisible(false);
						
						Game game = new Game(jtf1.getText());
					
						game.setTitle("Monster Game");
						Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
						game.setSize(screenSize.width, screenSize.height - 100);
						game.setLocationRelativeTo(null); // center the frame
						game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						game.setVisible(true);
						new Thread(game).start();
						this.setVisible(false);
						
						
					} else {//login failed
						JOptionPane.showMessageDialog(null, "Login failed, please check your Id",
								"ERROR!", JOptionPane.ERROR_MESSAGE);
					}
				} catch (HeadlessException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					if (Rg()) {//registration 
						JOptionPane.showMessageDialog(null,
								"Register Success!", "",
								JOptionPane.INFORMATION_MESSAGE);
					} else {//registration failed
						JOptionPane.showMessageDialog(null, "Register failed!",
								"ERROR!", JOptionPane.ERROR_MESSAGE);
					}
				} catch (HeadlessException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private boolean Lg() throws IOException {//functions of login
		String result = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(new File("C:\\Login.txt"));
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
				bufferedReader.close();
			}
			if (fileReader != null) {
				fileReader.close();
			}
		}
		if(result!=null&&result.contains("("+jtf1.getText()+"+"+jtf2.getText()+")")){
			return true;
		}
		return false;

	}

	private boolean Rg() throws Exception {//functions of registration 
		createFile(new File("C:\\Login.txt"));
		String result = "";
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(new File("C:\\Login.txt"));
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
				bufferedReader.close();
			}
			if (fileReader != null) {
				fileReader.close();
			}
		}
		if (result!=null&&result.contains(jtf1.getText()+"+")) {
			return false;
		}
		if (!(jtf3.getText().equals(jtf2.getText()))) {
			return false;
		}

		FileOutputStream o = null;
		try {
			o = new FileOutputStream(new File("C:\\Login.txt"));
			PrintWriter pw = new PrintWriter(o);
			if(result!=null){
				pw.write((result+"("+jtf1.getText()+"+"+jtf2.getText()+")\n").toCharArray());
			}else{
				pw.write((jtf1.getText()+"+"+jtf2.getText()+"\n").toCharArray());
			}
			pw.flush();
			o.close();
			pw.close();
			// mm=new RandomAccessFile(fileName,"rw");
			// mm.writeBytes(content);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return true;
	}
	//if the file does not exist, then create the file.
	public static boolean createFile(File fileName) throws Exception {
		boolean flag = false;
		try {
			if (!fileName.exists()) {
				fileName.createNewFile();
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
