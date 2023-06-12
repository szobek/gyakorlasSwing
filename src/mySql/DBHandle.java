package mySql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import orabolt.Ora;
import orabolt.OraTipusok;

public class DBHandle {
	
	private static final String dbConnection = "jdbc:mysql://127.0.0.1:3306/";
	private static final String dbAutoReconnect = "?autoReconnect=true&useSSL=false";
	private static String dbDatabase = "oraBolt";
	private static String dbTable = "orak";
	private static String dbUser = "root";
	private static String dbPassword = "";
	
	private static Connection connect() {
		Connection con=null;
		try {
			con = sqlKapcsolat();
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			sqlAdatbazis();
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
				System.out.println(res.getString("name")+" | "+res.getInt("ar")+"Ft | "+(res.getBoolean("vizallo")?"V�z�ll�":"Nem v�z�ll�"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Ora> synchronAfterSave(List<Ora> lista) {
		try {
			lista.clear();
			return sqlBeolvasas();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	/**
	 * Adatb�zishoz val� kapcsol�d�s.
	 * @return kapcsolat l�trej�tt vagy, �j kapcsolatott pr�b�l.
	 * @throws SQLException
	 */
	public static Connection sqlKapcsolat() 
	throws SQLException {
		try {
			return DriverManager.getConnection((dbConnection+dbDatabase+dbAutoReconnect), dbUser, dbPassword);
		} catch (Exception e) {
			return sqlAdatbazis();
		}
	}

	/**
	 * Adatb�zis kapcsolat bont�sa.
	 * @throws SQLException
	 */
	public static void sqlKapcsolatBontas()
	throws SQLException {
		if (connect() != null) {
			try {
				connect().close();
			} catch (Exception e) {
				throw new SQLException("Adatb�zis kapcsolat bont�sa sikertelen!");
			}
		}
	}

	/**
	 * �j elemek besz�r�s kezd�se.
	 * @param generic
	 * @throws SQLException
	 */
	public static void sqlKeszites(Ora generic) 
	throws SQLException {
		Connection conn = connect();
		try {
			PreparedStatement update = conn.prepareStatement(
				"INSERT INTO " + dbTable + "(Megnevezes_ora, Tipus_ora, Ar_ora, Vizallo_ora) "	+
				"VALUES (?, ?, ?, ?)"
			);
			update.setString(1, generic.getMegnevezes());
			update.setString(2, generic.getTipus().toString());
			update.setInt(3, generic.getAr());
			update.setBoolean(4, generic.isVizallo());
			update.executeUpdate();
			update.clearParameters();
		} catch (Exception e) {
			if (e.getMessage().toLowerCase().contains("duplicate")) {
				throw new SQLException("Adatb�zisban m�r l�tezik az elem, ment�s sikertelen!");
			} else {
				throw new SQLException("Adatb�zisba ment�s sikertelen!");
			}
		}
	}

	/**
	 * Adatb�zisb�l t�bl�zatb�l beolvasott r�sz elemek.
	 * @return List adatt�pusban t�r�lt p�ld�nyos�tott objektum.
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Ora> sqlBeolvasas() 
	throws SQLException {
		Connection conn = connect();
		try {
			List<Ora> orak = new ArrayList();
			PreparedStatement query = conn.prepareStatement(
				"SELECT name, tipus, ar, vizallo FROM " + dbTable
			);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				orak.add(new Ora(
					resultSet.getString("name"),
					OraTipusok.convertToEnum(resultSet.getString("tipus")),
					resultSet.getInt("ar"),	
					resultSet.getBoolean("vizallo")
				));
			}
			resultSet.close();
			query.clearParameters();
			return orak;
		} catch (Exception e) {
			throw new SQLException("Adatb�zisb�l beolvas�s sikertelen!");
		}
	}

	public static void sqlModositas() 
	throws SQLException {
		// TODO Auto-generated method stub
	}

	public static void sqlTorles() 
	throws SQLException {
		// TODO Auto-generated method stub
	}
	
	/**
	 * ha els� meghiv�sra a kapcsolat nem j�tt l�tre akkor megprob�lja
	 * l�trehozni a m�g nem l�tez� adatb�zis �s t�bl�zatott.
	 * @return MySql �j kapcsolatott, vagy egy hiba �zenetett ad, madj kil�p a program le�ll.
	 */
	private static Connection sqlAdatbazis() {
		try {
			sqlAdatbazisKeszites();
			return sqlTablazatKeszites();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "nem siker�lt kapcsol�dni a db-hez: "+e.getMessage());
			System.exit(0);
		}
		return null;
	}
	
	/**
	 * Ha nem l�tezik m�g az adatb�zis a Adatb�zis-ben akkor l�trehozza.
	 * @throws SQLException
	 */
	private static void sqlAdatbazisKeszites() 
	throws SQLException {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection((dbConnection+dbAutoReconnect), dbUser, dbPassword);
			PreparedStatement update = conn.prepareStatement(
				"CREATE DATABASE IF NOT EXISTS " + dbDatabase	
			);
			update.executeUpdate();
			update.clearParameters();
			update.close();
		} catch (Exception e) {
			throw new SQLException("Adatb�zis kapcsolat sikertelen!");
		}
	}
	
	/**
	 * ha m�g nem l�tezik a t�bl�zat a Adatb�zis-ban akkor l�trehozza a t�bl�zatott.
	 * @return Adatb�zis �j kapcsolatott adja vissza.
	 * @throws SQLException
	 */
	private static Connection sqlTablazatKeszites()
	throws SQLException {
		try {
			Connection con = DriverManager.getConnection((dbConnection+dbDatabase+dbAutoReconnect), dbUser, dbPassword);
			PreparedStatement update = con.prepareStatement(
				"CREATE TABLE IF NOT EXISTS " + dbTable + " ( " +
				"ID int auto_increment unique,  " + 
				"name varchar(50) not null, " +
				"tipus varchar(10) not null, " +
				"ar int, " +
				"vizallo BOOLEAN default false"	+	 
				")"		
			);
			update.executeUpdate();
			update.clearParameters();
			return con; 
		} catch (Exception e) {
			throw new SQLException("Adatb�zis kapcsolat sikertelen!");
		}
	}
}