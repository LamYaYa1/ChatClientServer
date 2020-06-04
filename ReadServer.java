import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ReadServer extends Thread {
	private BufferedReader br;
	private DataOutputStream os;
	private Socket socket;
	
	public ReadServer(Socket socket) throws IOException {
		this.socket = socket;
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			os = new DataOutputStream(socket.getOutputStream());
		} catch(IOException e) {
			JOptionPane.showMessageDialog(null, "IO errors");
			if(br != null) br.close();
			if(socket != null) socket.close();
		}
	}
	public void run() {
		String sms;
		
		try {
			while(true) {
				sms = br.readLine();
				if(sms != null) {
					if(sms.contains("Exit@@")) {
						ServerGUI.getSocketList().remove(socket);
						notifyToAllClients(sms.replaceAll("Exit@@", "has quitted"));
					}else {
						notifyToAllClients(sms);
					}
				}
			}
		} catch(IOException e) {
			JOptionPane.showMessageDialog(null, "Fail occurs while reading Info");
		}
	}
	
	private void notifyToAllClients(String sms) throws IOException {
		for(Socket items : ServerGUI.getSocketList()) {
			if(items.getPort() != socket.getPort()) {
				DataOutputStream os1 = new DataOutputStream(items.getOutputStream());
				os1.writeUTF(sms);
			}
		}
	}

}
