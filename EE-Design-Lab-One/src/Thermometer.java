import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import java.awt.Insets;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Thermometer {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
    private String targetNumber;
	private int minTemp;
	private int maxTemp;
	private int userNumber;

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Thermometer window = new Thermometer();
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
	public Thermometer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 468);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(52, 155, 261, 221);
		frame.getContentPane().add(textArea);
		
		
		JLabel lblTextMessageAlert = new JLabel("Text Message Alert");
		lblTextMessageAlert.setBounds(0, 0, 358, 22);
		lblTextMessageAlert.setHorizontalAlignment(SwingConstants.CENTER);
		lblTextMessageAlert.setFont(new Font("Tahoma", Font.BOLD, 15));
		frame.getContentPane().add(lblTextMessageAlert);
		
		JLabel label = new JLabel("Target Number:");
		label.setBounds(10, 36, 101, 15);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("Tahoma", Font.ITALIC, 12));
		label.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(label);
		
		textField = new JTextField();
		textField.setBounds(107, 33, 175, 22);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textField.getText().matches("[0-9]+") && textField.getText().length() > 2) {
					targetNumber = textField.getText();
				} else {
					JOptionPane.showMessageDialog(null,"Tartget number must contains integers only !");
				}
			}
		});
		btnSave.setBounds(296, 33, 77, 23);
		frame.getContentPane().add(btnSave);
		
		JLabel lblNewLabel = new JLabel("Real-Time Temperature");
		lblNewLabel.setBounds(83, 130, 203, 14);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Temperature Settings");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(96, 62, 159, 22);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblMax = new JLabel("Max:");
		lblMax.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMax.setBounds(52, 99, 34, 14);
		frame.getContentPane().add(lblMax);
		
		textField_1 = new JTextField();
		textField_1.setBounds(88, 95, 60, 24);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Min:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(168, 100, 34, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		textField_2 = new JTextField();
		textField_2.setBounds(201, 95, 60, 22);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int a = Integer.parseInt(textField_1.getText());
				int b = Integer.parseInt(textField_2.getText());
				if(a <= b) {
					JOptionPane.showMessageDialog(null,"Max Temperature must be bigger than Min Temperature!");
				} else {
					maxTemp = a;
					minTemp = b;
				}
				
			}
		});
		System.out.print(targetNumber);
		btnNewButton.setBounds(296, 96, 77, 23);
		frame.getContentPane().add(btnNewButton);
		
		ButtonGroup group = new ButtonGroup();
		
		JRadioButton rdbtnC = new JRadioButton("C \u00BA");
		rdbtnC.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnC.setBounds(319, 173, 46, 23);
		frame.getContentPane().add(rdbtnC);
		
		JRadioButton rdbtnF = new JRadioButton("F \u00BA");
		rdbtnF.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnF.setBounds(319, 199, 46, 23);
		frame.getContentPane().add(rdbtnF);
		
		group.add(rdbtnC);
		group.add(rdbtnF);
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("LED");
		tglbtnNewToggleButton.setBounds(309, 295, 67, 23);
		frame.getContentPane().add(tglbtnNewToggleButton);
		
		JButton btnNewButton_1 = new JButton("Go");
		btnNewButton_1.setBounds(48, 387, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Stop");
		btnNewButton_2.setBounds(193, 387, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
	}
}
