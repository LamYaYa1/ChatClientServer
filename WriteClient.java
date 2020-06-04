import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

public class WriteClient extends Thread {
	private DataOutputStream os;
	private Socket socket;
	private String sms;
	private String clientName;
	SimpleDateFormat sdf = new SimpleDateFormat();	
	
	public WriteClient() {
		
	}
	public WriteClient(Socket socket, String clientName, String sms) {
		this.socket = socket;
		this.sms = sms;
		this.clientName = clientName;
		
		try {
			os = new DataOutputStream(socket.getOutputStream());
		} catch(IOException e) {
			JOptionPane.showMessageDialog(null, "IO Erors!");
		}
	}

	@Override
	public void run() {
		try {
			while(true) {
				os.writeUTF("[" + sdf.format(new Date(System.currentTimeMillis())) + "] [" + clientName + "] " + sms);
			}
		} catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Errors occur while send message !");
		}
	}
}
