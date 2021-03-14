import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.DefaultListModel;

public class courseData {
	
	private static Connection conn = null;
	
	static boolean connectMySql() {

		// MySql

		try {
			// Load the current class and check rather it exists or not
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Use the current class and connect with the database
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/course","root","mysql");
			return true;
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: No Driver detected");
		} catch (Exception e) {
			System.out.println("ERROR: Failed to connect");
		}
		return false;
	}
	
	static void getCourseList(DefaultListModel<String> listModel) {
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
	}
	
	static void addCourseData(String courseName, String zoomLink, DefaultListModel<String> listModel) {
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
	
	static void deleteCourseData(String courseName, int index, DefaultListModel<String> listModel) {
		try {
			java.sql.Statement deleteStmt = conn.createStatement();
			deleteStmt.executeUpdate("delete from course where courseName = '" 
					+ courseName + "'");
			listModel.remove(index);
			deleteStmt.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
