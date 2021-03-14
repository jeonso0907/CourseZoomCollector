import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import java.awt.Panel;
import javax.swing.ListModel;
import java.awt.FlowLayout;
import javax.swing.ListSelectionModel;

public class editCourseView {

	private JFrame frame;
	private JTextField courseNameText;
	private JTextField zoomLinkText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		// UI
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					editCourseView window = new editCourseView();
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
	public editCourseView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		Connection conn = mySql();
		try {

			java.sql.Statement stmt = conn.createStatement();
			java.sql.ResultSet rs = stmt.executeQuery("select * from course");
			while (rs.next()) {
				listModel.addElement(rs.getString("courseName"));
			}
			stmt.close();
			rs.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println("ERROR: Failed to load the statement");
		}

		// Program Frame
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));

		// COURSE ADD UI
		
		JButton deleteBtn = null;
		frame.getContentPane().setLayout(null);
		
		Panel courseEditPanel = new Panel();
		courseEditPanel.setBounds(0, 0, 750, 680);
		frame.getContentPane().add(courseEditPanel);
		courseEditPanel.setLayout(null);
		
		JLabel courseNameLabel = new JLabel("Course Name");
		courseNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		courseNameLabel.setBounds(30, 30, 150, 20);
		courseEditPanel.add(courseNameLabel);
		
		courseNameText = new JTextField();
		courseNameText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		courseNameText.setColumns(40);
		courseNameText.setBounds(200, 30, 350, 25);
		courseEditPanel.add(courseNameText);
		
		JLabel zoomLinkLabel = new JLabel("Zoom Link");
		zoomLinkLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		zoomLinkLabel.setBounds(30, 70, 150, 20);
		courseEditPanel.add(zoomLinkLabel);
		
		zoomLinkText = new JTextField();
		zoomLinkText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		zoomLinkText.setColumns(40);
		zoomLinkText.setBounds(200, 70, 350, 25);
		courseEditPanel.add(zoomLinkText);
		
		JButton addButn = new JButton("Add");
		addButn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String courseName = courseNameText.getText();
				String zoomLink = zoomLinkText.getText();
				String newData = "insert into course values('" + courseName + "','" + zoomLink + "')";
				
				try {
					java.sql.Statement inputStmt = conn.createStatement();
					inputStmt.executeUpdate(newData);
					listModel.addElement(courseName);
					inputStmt.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		addButn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		addButn.setBounds(325, 115, 100, 25);
		courseEditPanel.add(addButn);
		
		JList<String> list = new JList<String>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFont(new Font("Tahoma", Font.PLAIN, 20));
		list.setBounds(200, 160, 350, 500);
		courseEditPanel.add(list);
		
		JButton deleteBtn_1 = new JButton("Delete");
		deleteBtn_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					java.sql.Statement deleteStmt = conn.createStatement();
					String courseName = list.getSelectedValue();
					int index = list.getSelectedIndex();
					deleteStmt.executeUpdate("delete from course where courseName = '" 
												+ courseName + "'");
					listModel.remove(index);
					deleteStmt.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		deleteBtn_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		deleteBtn_1.setBounds(600, 160, 100, 25);
		courseEditPanel.add(deleteBtn_1);
		
		JButton doneBtn = new JButton("Done");
		doneBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
				zoomLaunchView.main(null);
			}
		});
		doneBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		doneBtn.setBounds(600, 70, 100, 25);
		courseEditPanel.add(doneBtn);
		frame.setBounds(100, 100, 763, 717);

		// Close the frame when exits
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private Connection mySql() {

		// MySql
		Connection conn = null;

		try {
			// Load the current class and check rather it exists or not
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Use the current class and connect with the database
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/course","root","mysql");

		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: No Driver detected");
		} catch (Exception e) {
			System.out.println("ERROR: Failed to connect");
		}
		return conn;
	}
}
