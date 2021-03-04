
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

class CourseView extends JPanel {

	private JButton editButton;
	private View win;

	public CourseView(View win) {

		this.win = win;
		setLayout(null);

		editButton = new JButton("Edit Course");
		editButton.setSize(150, 30);
		editButton.setLocation(175, 600);
		add(editButton);

		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				win.change("editView");
			}

		});
	}
}

class CourseEditView extends JPanel {

	private static int X = 100;
	private static int Y = 20;
	private static int UX = 160;
	private static int UY = 150;
	JList<String> courseList;
	DefaultListModel<String> courseListModel = new DefaultListModel<String>();

	private JTextField nameText;
	private JTextField linkText;
	private JButton updateButton;
	private JButton doneButton;
	private JButton deleteButton;
	private View win;


	private void setDataInput(String labelName) {
		JLabel lb = new JLabel(labelName);
		if (labelName.equals("Course Name")) {
			lb.setBounds(X, Y, 100, 15);
			nameText = new JTextField();
			nameText.setBounds(X + 90, Y, 200, 21);
			add(lb);
			add(nameText);
			nameText.setColumns(10);
		} else {
			lb.setBounds(X, Y + 44, 100, 15);
			linkText = new JTextField();
			linkText.setBounds(X + 90, Y + 44, 200, 21);
			add(lb);
			add(linkText);
			linkText.setColumns(10);
		}
	}

	CourseEditView(View win) {
		setLayout(null);
		this.win = win;
		setDataInput("Course Name");
		setDataInput("Course Link");

		// Add Course Button
		updateButton = new JButton("Add Course");
		updateButton.setSize(150, 30);
		updateButton.setLocation(X + 75, Y + 80);
		add(updateButton);

		updateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String courseString = nameText.getText().toString();
				if (!courseString.isEmpty() && courseString.length() <= 30) {
					courseListModel.addElement(courseString);
					courseList = new JList<String>(courseListModel);
					courseList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
					courseList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
					courseList.setBounds(100, 100, 10, 10);
					add(new JScrollPane(courseList));
					
					// Delete Course Button
					deleteButton = new JButton("X");
					deleteButton.setFont(new Font("Arial", Font.PLAIN, 6));
					deleteButton.setBounds(UX + 220, UY, 38, 15);
					add(deleteButton);

					UY += 20;
				} else if (courseString.length() > 30) {
					JOptionPane.showMessageDialog(null, "Please type less then 30 characters", "InforBox:", JOptionPane.INFORMATION_MESSAGE);
				} else if (courseString.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Course Name is Missing!", "InforBox:", JOptionPane.INFORMATION_MESSAGE);
				}
				win.change("editPanel");
			}

		});

		// Done Edit Button
		doneButton = new JButton("Done");
		doneButton.setSize(80, 40);
		doneButton.setLocation(400, 10);
		add(doneButton);

	}
}

class View extends JFrame {

	public CourseView coursePanel = null;
	public CourseEditView editPanel = null;

	public void change(String panelName) {
		if (panelName.equals("coursePanel")) {
			getContentPane().removeAll();
			getContentPane().add(coursePanel);
			revalidate();
			repaint();
		} else {
			getContentPane().removeAll();
			getContentPane().add(editPanel);
			revalidate();
			repaint();
		}
	}

	public static void main(String[] args) {
		View win = new View();
		win.setTitle("Zoom Link Linker");
		win.coursePanel = new CourseView(win);
		win.editPanel = new CourseEditView(win);

		win.add(win.coursePanel);
		win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		win.setSize(500, 700);
		win.setVisible(true);
	}

}
