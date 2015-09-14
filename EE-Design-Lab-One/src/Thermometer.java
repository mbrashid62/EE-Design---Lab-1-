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
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

public class Thermometer {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
    private String targetNumber;
	private int minTemp;
	private int maxTemp;
	private int userNumber;
	private boolean onGoing;
	private boolean isCelsius;
	private TwoWaySerialComm communication;
	private Queue<Integer> Tdata;

	
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
		frame.getContentPane().setBackground(new Color(102, 204, 204));
		frame.getContentPane().setForeground(new Color(0, 0, 0));
		frame.setBounds(100, 100, 387, 468);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JLabel lblTextMessageAlert = new JLabel("Text Message Alert");
		lblTextMessageAlert.setBounds(0, 0, 347, 22);
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
		textField.setBounds(107, 33, 133, 22);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBackground(new Color(153, 255, 255));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textField.getText().matches("[0-9]+") && textField.getText().length() > 2) {
					targetNumber = textField.getText();
				} else {
					JOptionPane.showMessageDialog(null,"Tartget number must contains integers only !");
				}
			}
		});
		btnSave.setBounds(270, 33, 77, 23);
		frame.getContentPane().add(btnSave);
		
		JLabel lblNewLabel = new JLabel("Real-Time Temperature");
		lblNewLabel.setBounds(84, 130, 186, 14);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Temperature Settings");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(101, 62, 159, 22);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblMax = new JLabel("Max:");
		lblMax.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMax.setBounds(30, 99, 34, 14);
		frame.getContentPane().add(lblMax);
		
		textField_1 = new JTextField();
		textField_1.setBounds(62, 95, 60, 24);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Min:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(143, 99, 34, 15);
		frame.getContentPane().add(lblNewLabel_2);
		
		textField_2 = new JTextField();
		textField_2.setBounds(177, 95, 60, 24);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		Tdata = new LinkedList<Integer>();
		
		communication = new TwoWaySerialComm();
        try
        {
            communication.connect("COM1");//???????????
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.setBackground(new Color(153, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int a = Integer.parseInt(textField_1.getText());
				int b = Integer.parseInt(textField_2.getText());
				if(a <= b) {
					JOptionPane.showMessageDialog(null,"Max Temperature must be bigger than Min Temperature!");
				} else {
					if(a>63||b<-10) {
						JOptionPane.showMessageDialog(null,"Proper temperature limit should be within -10 ~ 63 degree Cesius!");
					} else {
						maxTemp = a;
						minTemp = b;
					}	
				}
				
			}
		});
		System.out.print(targetNumber);
		btnNewButton.setBounds(270, 96, 77, 23);
		frame.getContentPane().add(btnNewButton);
		
		ButtonGroup group = new ButtonGroup();
		
		JRadioButton rdbtnC = new JRadioButton("C \u00BA");
		rdbtnC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isCelsius = true;
			}
		});
		rdbtnC.setBackground(new Color(102, 204, 204));
		rdbtnC.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnC.setBounds(301, 170, 46, 23);
		rdbtnC.setSelected(true);
		frame.getContentPane().add(rdbtnC);
		
		JRadioButton rdbtnF = new JRadioButton("F \u00BA");
		rdbtnF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isCelsius = false;
			}
		});
		rdbtnF.setBackground(new Color(102, 204, 204));
		rdbtnF.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnF.setBounds(301, 196, 46, 23);
		frame.getContentPane().add(rdbtnF);
		
		group.add(rdbtnC);
		group.add(rdbtnF);
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("LED");
		tglbtnNewToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				communication.StartWriting();
				//send LED on commands
                communication.WriterStop();
			}
		});
		tglbtnNewToggleButton.setBackground(new Color(255, 255, 255));
		tglbtnNewToggleButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		tglbtnNewToggleButton.setForeground(new Color(102, 0, 102));
		tglbtnNewToggleButton.setBounds(301, 313, 60, 31);
		frame.getContentPane().add(tglbtnNewToggleButton);		
        
       
		
        JPanel panel = new JPanel();
		panel.setBounds(10, 155, 285, 217);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBackground(new Color(204, 255, 153));
		textArea.setForeground(new Color(51, 102, 204));
		textArea.setBounds(0, 0, 285, 217);
		panel.add(textArea);
		textArea.setFont(new Font("Comic Sans MS", textArea.getFont().getStyle() | Font.ITALIC, textArea.getFont().getSize() + 140));
        
		JButton btnNewButton_1 = new JButton("Go");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				communication.StartReading();
				onGoing = true;
				while(onGoing) {
					Integer currentTemperature = 0;
					try {
						currentTemperature = communication.getTemperature();
						communication.PauseReading(500);
					} catch (IOException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(Tdata.size()==600) {
						Tdata.remove();
					}	
					Tdata.add(currentTemperature);
					textArea.setText(""+currentTemperature);
				}
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setBackground(new Color(153, 255, 255));
		btnNewButton_1.setForeground(new Color(0, 153, 51));
		btnNewButton_1.setBounds(30, 387, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Stop");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onGoing = false;
				communication.AllStop();
				textArea.setText("");
			}
		});
		btnNewButton_2.setBackground(new Color(153, 255, 255));
		btnNewButton_2.setForeground(new Color(255, 51, 51));
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_2.setBounds(193, 387, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		
	}
}
