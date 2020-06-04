import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.TitledBorder;


import javax.swing.JRadioButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import java.awt.Button;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChatBox {

	private JFrame frmChat;
	private JTextField txtMessage;
	private Client client;
	private static JTextArea txtChat;
	private JButton btnSend;
	private JButton btnExit;
	private JTextArea txtStatus;
	private Socket socket;
	private static List<String> onlineList;
	private String clientName;
	

	public ChatBox(String hostName, String clientName) {
		initialize();
		execute(hostName);
		this.clientName = clientName;
		client =  new Client(clientName);
		onlineList = new ArrayList<>();
		onlineList.add(clientName);
	}
	
	 private void execute(String hostName) {
		 try {
			socket = new Socket(hostName, 9999);
			
			ReadClient read = new ReadClient(socket);
			read.start();				
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChat = new JFrame();
		frmChat.setIconImage(Toolkit.getDefaultToolkit().getImage("/Users/nguyendinhlam/Downloads/[Sharecode.vn] Code chat client server trong java/[Sharecode.vn] Code chat client server trong java/lol.png"));
		frmChat.setFont(new Font("Chalkboard", Font.PLAIN, 15));
		frmChat.setTitle("CHAT");
		frmChat.setBounds(100, 100, 794, 544);
		frmChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChat.getContentPane().setLayout(null);
		frmChat.setVisible(true);
		
		JLabel lblNewLabel = new JLabel("LIST ONLINE");
		lblNewLabel.setBounds(6, 6, 135, 26);
		lblNewLabel.setFont(new Font("Chalkboard SE", Font.PLAIN, 20));
		frmChat.getContentPane().add(lblNewLabel);
		
		txtStatus = new JTextArea();
		txtStatus.setBounds(6, 44, 206, 379);
		txtStatus.setEditable(false);
		txtStatus.setBackground(UIManager.getColor("TextPane.selectionBackground"));
		frmChat.getContentPane().add(txtStatus);
		txtStatus.append(clientName);
		
		txtChat = new JTextArea();
		txtChat.setBounds(224, 44, 550, 379);
		txtChat.setEditable(false);
		txtChat.setBackground(new Color(248, 248, 255));
		frmChat.getContentPane().add(txtChat);
		
		JLabel lblNewLabel_1 = new JLabel("CHAT BOX");
		lblNewLabel_1.setBounds(464, 6, 115, 26);
		lblNewLabel_1.setFont(new Font("Chalkboard SE", Font.PLAIN, 20));
		frmChat.getContentPane().add(lblNewLabel_1);
		
		txtMessage = new JTextField();
		txtMessage.setBounds(255, 463, 421, 33);
		txtMessage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtMessage.setText("");
			}
		});
		txtMessage.setText("  @ Enter message to send");
		frmChat.getContentPane().add(txtMessage);
		txtMessage.setColumns(10);
		
		btnSend = new JButton("SEND");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!txtChat.getText().equals("")) {
					txtChat.append("Me :" + txtChat.getText());
					WriteClient write = new WriteClient(socket, client.getClientName(), txtChat.getText());
					write.start();
				}
			}
		});
		btnSend.setBounds(688, 448, 100, 68);
		btnSend.setFont(new Font("Chalkboard SE", Font.PLAIN, 20));
		btnSend.setForeground(new Color(220, 20, 60));
		frmChat.getContentPane().add(btnSend);
		
		btnExit = new JButton("EXIT");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WriteClient write = new WriteClient(socket, client.getClientName(), "Exit@@"); // cho @@ sau thang client nhan tin chua an exit thi van okay
				// update lai danh sach online
				txtChat.setText("");
				updateOnline();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} finally {
					System.exit(0);
				}
			}
		});
		btnExit.setBounds(6, 448, 124, 68);
		btnExit.setFont(new Font("Chalkboard SE", Font.PLAIN, 20));
		btnExit.setForeground(new Color(220, 20, 60));
		frmChat.getContentPane().add(btnExit);
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("windowBorder"));
		panel.setBounds(6, 6, 782, 510);
		frmChat.getContentPane().add(panel);
	}
	
	private void updateOnline() {
		for(String temp : onlineList) {
			if(temp != client.getClientName()) {
			txtStatus.append(temp);
			}
		}
	}
	
	public static void appendSMS(String sms) {
		txtChat.append("\n" + sms);
	}
}
