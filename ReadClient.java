import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ReadClient extends Thread {
	private BufferedReader br;
	private Socket socket;
	
	public ReadClient(Socket socket) {
		
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Errors occured while reading from server!");
		}
		
	}

	@Override
	public void run() {
		String sms;
		
		try {
			while(true) {
				sms = br.readLine();
				ChatBox.appendSMS(sms);
			} 
		} catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Fail to read from server!");
		}
	}
	
	

}
