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
				System.out.println(res.getString("name")+" | "+res.getInt("ar")+"Ft | "+(res.getBoolean("vizallo")?"Vízálló":"Nem vízálló"));
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
	 * Adatbázishoz való kapcsolódás.
	 * @return kapcsolat létrejött vagy, új kapcsolatott próbál.
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
	 * Adatbázis kapcsolat bontása.
	 * @throws SQLException
	 */
	public static void sqlKapcsolatBontas()
	throws SQLException {
		if (connect() != null) {
			try {
				connect().close();
			} catch (Exception e) {
				throw new SQLException("Adatbázis kapcsolat bontása sikertelen!");
			}
		}
	}

	/**
	 * Új elemek beszúrás kezdése.
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
				throw new SQLException("Adatbázisban már létezik az elem, mentés sikertelen!");
			} else {
				throw new SQLException("Adatbázisba mentés sikertelen!");
			}
		}
	}

	/**
	 * Adatbázisból táblázatból beolvasott rész elemek.
	 * @return List adattípusban tárólt példányosított objektum.
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
			throw new SQLException("Adatbázisból beolvasás sikertelen!");
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
	 * ha elsõ meghivásra a kapcsolat nem jött létre akkor megprobálja
	 * létrehozni a még nem létezõ adatbázis és táblázatott.
	 * @return MySql új kapcsolatott, vagy egy hiba üzenetett ad, madj kilép a program leáll.
	 */
	private static Connection sqlAdatbazis() {
		try {
			sqlAdatbazisKeszites();
			return sqlTablazatKeszites();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "nem sikerült kapcsolódni a db-hez: "+e.getMessage());
			System.exit(0);
		}
		return null;
	}
	
	/**
	 * Ha nem létezik még az adatbázis a Adatbázis-ben akkor létrehozza.
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
			throw new SQLException("Adatbázis kapcsolat sikertelen!");
		}
	}
	
	/**
	 * ha még nem létezik a táblázat a Adatbázis-ban akkor létrehozza a táblázatott.
	 * @return Adatbázis új kapcsolatott adja vissza.
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
			throw new SQLException("Adatbázis kapcsolat sikertelen!");
		}
	}
}