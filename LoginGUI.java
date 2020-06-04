import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JDialog;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;

public class LoginGUI extends JFrame{

	private JFrame frame;
	private JTextField txtIP;
	private JTextField txtName;
	private JCheckBox check;
	private JButton btnStart;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI window = new LoginGUI();
					window.frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("/Users/nguyendinhlam/Desktop/235-2350682_new-svg-image-small-user-login-icon-hd.jpg"));
		frame.setBounds(100, 100, 648, 548);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 291, 514);
		panel.setBackground(new Color(0, 255, 255));
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel Title = new JLabel("CHAT CLIENT - SERVER");
		Title.setBackground(new Color(255, 255, 0));
		Title.setFont(new Font("Chalkboard SE", Font.PLAIN, 13));
		Title.setHorizontalAlignment(SwingConstants.CENTER);
		Title.setBounds(29, 289, 218, 53);
		panel.add(Title);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(229, 261, -187, 16);
		panel.add(separator);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(29, 277, 223, 1);
		panel.add(separator_2);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("/Users/nguyendinhlam/Desktop/235-2350682_new-svg-image-small-user-login-icon-hd.jpg"));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(29, 37, 223, 215);
		panel.add(lblNewLabel);
		
		JSeparator separator_1_1_1 = new JSeparator();
		separator_1_1_1.setForeground(Color.DARK_GRAY);
		separator_1_1_1.setBounds(41, 261, 206, 12);
		panel.add(separator_1_1_1);
		
		JLabel lblNewLabel_1 = new JLabel("IP Address");
		lblNewLabel_1.setBounds(361, 101, 84, 56);
		frame.getContentPane().add(lblNewLabel_1);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(361, 173, 264, 12);
		frame.getContentPane().add(separator_1);
		
		txtIP = new JTextField();
		txtIP.setBounds(440, 115, 195, 26);
		txtIP.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtIP.setText("");
			}
		});
		txtIP.setText("Enter IP Address");
		txtIP.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 13));
		txtIP.setBackground(SystemColor.window);
		frame.getContentPane().add(txtIP);
		txtIP.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Nick Name");
		lblNewLabel_1_1.setBounds(361, 197, 84, 56);
		lblNewLabel_1_1.setFont(new Font("Chalkboard SE", Font.PLAIN, 13));
		frame.getContentPane().add(lblNewLabel_1_1);
		
		txtName = new JTextField();
		txtName.setBounds(440, 212, 195, 26);
		txtName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtName.setText("");
			}
		});
		txtName.setText("Enter nick name");
		txtName.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 13));
		txtName.setColumns(10);
		txtName.setBackground(SystemColor.window);
		frame.getContentPane().add(txtName);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(361, 277, 264, 12);
		frame.getContentPane().add(separator_1_1);
		
		btnStart = new JButton("START");
		btnStart.setBounds(361, 358, 264, 70);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isValid1()) {
					String hostName = txtIP.getText();
					String clientName = txtName.getText();
					btnStart.setEnabled(false);
					new ChatBox(hostName, clientName);
				}
				
			}

		});
		btnStart.setForeground(new Color(220, 20, 60));
		btnStart.setBackground(new Color(255, 140, 0));
		btnStart.setFont(new Font("Chalkboard", Font.PLAIN, 13));
		frame.getContentPane().add(btnStart);
		
		check = new JCheckBox("Agree");
		check.setBounds(361, 306, 128, 23);
		check.setEnabled(false);
		check.setSelected(true);
		frame.getContentPane().add(check);
	}
	
	private boolean isValid1() {
		if(txtIP.getText().equals("") || txtName.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Enter full field to start !");
			return false;
		} else if(txtIP.getText().equals("localhost") && txtName.getText().length() > 6) {
			return true;
		}
		JOptionPane.showMessageDialog(null, "Enter 6 characters for nick name !");
		txtName.setText("");
		return false;
	}
}
