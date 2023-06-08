package orabolt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;



public class DbHandle {
	private static String dbUser = "root";
	private static String dbPassword = "";
	
	private static Connection connect() {
		Connection con=null;
		try {
		
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/orabolt",dbUser, dbPassword);
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "nem sikerült kapcsolódni a db-hez: "+e.getMessage());
			System.exit(0);
		}
		return con;
	}
	
	public static void all() {
		Connection conn = connect();
		Statement stmt;
		try {
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("select * from orak");
			System.out.println("Csatlakozva");
			while(rs.next()) {
				System.out.println(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
