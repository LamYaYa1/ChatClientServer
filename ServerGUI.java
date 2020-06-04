import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class ServerGUI extends JFrame implements Runnable{

	private JFrame frame;
	private JTextArea txtShow;
	private ServerSocket serverSocket;
	private JButton btnStop;
	private JButton btnStart;
	private ReadServer read;
	private static ServerGUI instance = null;
	private static List<Socket> socketList;
	SimpleDateFormat sdf = new SimpleDateFormat("hh : mm : ss");

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGUI window = new ServerGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ServerGUI() {
		socketList = new ArrayList<>();
		initialize();
	}
	
	private void startServer() {
		try {
			serverSocket = new ServerSocket(9999);
			txtShow.append("[" + sdf.format(new Date(System.currentTimeMillis())) + "]" + "Server is listening ..." + "\nPlease connect to be served");
			while(true) {
				Socket socket = serverSocket.accept();
				socketList.add(socket);				
				txtShow.append("\n[" + sdf.format(new Date(System.currentTimeMillis())) + "]" + socket + " is connecting ..." );
				
				read = new ReadServer(socket);
				read.start();
				
			} 
			
		} catch(IOException ex) {
			if(serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			JOptionPane.showMessageDialog(null, "SERVER IS OCCUPIED!");
		}
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 804, 539);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 792, 505);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("SERVER'S STATUS");
		lblNewLabel.setBounds(17, 6, 197, 29);
		lblNewLabel.setFont(new Font("Chalkboard SE", Font.PLAIN, 20));
		panel.add(lblNewLabel);
		
		txtShow = new JTextArea();
		txtShow.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		txtShow.setEditable(false);
		txtShow.setBounds(27, 47, 740, 377);
		txtShow.setForeground(Color.DARK_GRAY);
		txtShow.setBackground(Color.LIGHT_GRAY);
		panel.add(txtShow);
		
		btnStart = new JButton("START");
		btnStart.setBounds(55, 444, 117, 44);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStartEvent(e);
			}
		});
		btnStart.setForeground(new Color(220, 20, 60));
		panel.add(btnStart);
		
		btnStop = new JButton("STOP");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					btnStopEvent(e);
			}
		});
		btnStop.setBounds(611, 444, 117, 44);
		btnStop.setForeground(new Color(220, 20, 60));
		panel.add(btnStop);
	}
	
	private void btnStartEvent(ActionEvent e) {
		new Thread(this).start();
		btnStart.setEnabled(false);
		btnStop.setEnabled(true);
	}
	
	private void btnStopEvent(ActionEvent e) {
		int kq = JOptionPane.showConfirmDialog(null, "Do you sure to exit?", "COMFIRM", JOptionPane.YES_NO_OPTION);
		if(kq == JOptionPane.YES_OPTION) {
			txtShow.append("\nServer has equitted !");
			btnStop.setEnabled(false);
		}
	}

	public static List<Socket> getSocketList() {
		return socketList;
	}
	
	

	public static ServerGUI getInstance() {
		if(instance == null) {
			instance = new ServerGUI();
		}
		return instance;
	}

	@Override
	public void run() {
		this.startServer();
	}

}
