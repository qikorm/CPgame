import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class showhistory extends JFrame{//display game history, storing it in path of C:\Record.txt

	JTextArea jta=new JTextArea(23,20);
	JScrollPane jsp=new JScrollPane(jta);
	
	public showhistory() throws IOException{// constructor functions
		this.add(jsp);
		this.setTitle("GameHistory");
		this.setSize(500,500);
		this.setVisible(true);
		this.setvalue();
	}
	
	private void setvalue() throws IOException{// obtain and display data in the text.
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
				bufferedReader.close();
			}
			if (fileReader != null) {
				fileReader.close();
			}
		}
		jta.setText(result);
	}
	
}
