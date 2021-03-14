import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class zoomLaunchView {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					zoomLaunchView window = new zoomLaunchView();
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
	public zoomLaunchView() {
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

		frame = new JFrame();
		frame.setBounds(100, 100, 743, 852);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel courseListLabel = new JLabel("Course List");
		courseListLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		courseListLabel.setBounds(290, 70, 150, 30);
		frame.getContentPane().add(courseListLabel);

		JList<String> list = new JList<String>(listModel);
		list.setFont(new Font("Tahoma", Font.PLAIN, 20));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(190, 150, 350, 500);
		frame.getContentPane().add(list);

		JButton launchBtn = new JButton("Launch");
		launchBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String courseName = list.getSelectedValue();
				try {
					java.sql.Statement inputStmt = conn.createStatement();
					java.sql.ResultSet rs = inputStmt.executeQuery("select * from course where courseName = '"
							+ courseName + "'");
					rs.next();
					Desktop desktop = java.awt.Desktop.getDesktop();
					URI url = null;
					try {
						url = new URI(rs.getString("zoomLink"));
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					desktop.browse(url);

					inputStmt.close();
					rs.close();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		launchBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		launchBtn.setBounds(590, 150, 100, 30);
		frame.getContentPane().add(launchBtn);

		JButton editBtn = new JButton("Edit");
		editBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
				editCourseView.main(null);
			}
		});
		editBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		editBtn.setBounds(590, 200, 100, 30);
		frame.getContentPane().add(editBtn);
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
