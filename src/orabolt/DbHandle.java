package orabolt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
	
	/**
	 * Lekér olcsó óákat,amik olcsóbbak mint a paraméterben átadott
	 * @param ar
	 */
	public static void justCheap(int ar) {
		Connection conn = connect();
		try {
			PreparedStatement query = conn.prepareStatement("select * from orak where ar<?");
			query.setInt(1, ar);
			ResultSet res=query.executeQuery();
			while (res.next()) {
				System.out.println(res.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void getByString(String sql) {
		Connection conn = connect();
		try {
			PreparedStatement query = conn.prepareStatement(sql);
			ResultSet res=query.executeQuery();
			while (res.next()) {
				System.out.println(res.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void getDataFromWatch(int id) {
		Connection conn = connect();
		try {
			PreparedStatement query = conn.prepareStatement("select * from orak where id=?");
			query.setInt(1, id);
			ResultSet res=query.executeQuery();
			while (res.next()) {
				System.out.println(res.getString("name")+" | "+res.getInt("ar")+"Ft | "+(res.getBoolean("vizallo")?"Vízálló":"Nem vízálló"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Ora> synchronAfterSave(List<Ora> lista) {
		Connection conn = connect();
		Statement stmt;
		try {
			PreparedStatement query = conn.prepareStatement("select * from orak");
			ResultSet rs=query.executeQuery();
			lista.clear();
			while(rs.next()) {
				lista.add(new Ora(rs.getString("name"),OraTipusok.convertToEnum(rs.getString("tipus")),rs.getInt("ar"),rs.getBoolean("vizallo")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
}
