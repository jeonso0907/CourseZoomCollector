import java.awt.Component;
import java.awt.Container;
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
	
	private void setBoundary(int x, int y, int width, int height, Object obj, Panel panel) {
		
		((Component) obj).setFont(new Font("Tahoma", Font.PLAIN, 20));
		((Component) obj).setBounds(x, y, width, height);
		panel.add((Component) obj);
		
		if (obj instanceof JTextField) {
			((JTextField) obj).setColumns(40);
		} else if (obj instanceof JList) {
			((JList<String>) obj).setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		// MySql Connection
		boolean isConnected = courseData.connectMySql();

		// Initialize the program if the database connection is successful
		if (isConnected) {

			// Initialize the default list model to store the courses
			DefaultListModel<String> listModel = new DefaultListModel<String>();
			
			// Get the course list and display it as a list
			courseData.getCourseList(listModel);

			// Program Frame
			frame = new JFrame();
			frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
			frame.getContentPane().setLayout(null);
			
			Panel courseEditPanel = new Panel();
			courseEditPanel.setBounds(0, 0, 750, 680);
			frame.getContentPane().add(courseEditPanel);
			courseEditPanel.setLayout(null);

			JLabel courseNameLabel = new JLabel("Course Name");
			setBoundary(30, 30, 150, 20, courseNameLabel, courseEditPanel);

			courseNameText = new JTextField();
			setBoundary(200, 30, 350, 25, courseNameText, courseEditPanel);

			JLabel zoomLinkLabel = new JLabel("Zoom Link");
			setBoundary(30, 70, 150, 20, zoomLinkLabel, courseEditPanel);

			zoomLinkText = new JTextField();
			setBoundary(200, 70, 350, 25, zoomLinkText, courseEditPanel);

			JButton addButn = new JButton("Add");
			addButn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String courseName = courseNameText.getText();
					String zoomLink = zoomLinkText.getText();
					
					courseData.addCourseData(courseName, zoomLink, listModel);
					
					courseNameText.setText("");
					zoomLinkText.setText("");
				}
			});

			setBoundary(325, 115, 100, 25, addButn, courseEditPanel);

			JList<String> list = new JList<String>(listModel);
			setBoundary(200, 160, 350, 500, list, courseEditPanel);

			JButton deleteBtn_1 = new JButton("Delete");
			deleteBtn_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					courseData.deleteCourseData(list.getSelectedValue(), list.getSelectedIndex(), listModel);
				}
			});
			setBoundary(600, 160, 100, 25, deleteBtn_1, courseEditPanel);

			JButton doneBtn = new JButton("Done");
			doneBtn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					frame.setVisible(false);
					zoomLaunchView.main(null);
				}
			});
			setBoundary(600, 70, 100, 25, doneBtn, courseEditPanel);
			
			frame.setBounds(100, 100, 763, 717);

			// Close the frame when exits
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
}
