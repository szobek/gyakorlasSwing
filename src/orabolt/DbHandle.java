package orabolt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

public class DbHandle {

	private static Connection connect(List<Ora> lista) {
		Connection con = null;
		Setting datas = new Setting();
		Map<String, String> dbData = datas.getBeallitasok();
		try {

			String connectionString = "jdbc:mysql://" + dbData.get("DbUrl") + ":" + dbData.get("DbPort") + "/"
					+ dbData.get("DbName");
			con = DriverManager.getConnection(connectionString, dbData.get("DbUser"), dbData.get("DbPsw"));
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			FileHandle.readFile(lista);
			JOptionPane.showMessageDialog(null,
					"nem sikerült kapcsolódni a db-hez,ezért fájl olvasva: " + e.getMessage());
		}
		return con;
	}

	private static Connection connect() {
		Connection con = null;
		Setting datas = new Setting();
		Map<String, String> dbData = datas.getBeallitasok();
		try {
			String connectionString = "jdbc:mysql://" + dbData.get("DbUrl") + ":" + dbData.get("DbPort") + "/"
					+ dbData.get("DbName");
			con = DriverManager.getConnection(connectionString, dbData.get("DbUser"), dbData.get("DbPsw"));
			Class.forName("com.mysql.jdbc.Driver");
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "nem sikerült kapcsolódni a db-hez: " + e.getMessage());
			System.exit(0);
		}
		return con;
	}

	public static void all(List<Ora> lista) {
		Connection conn = connect(lista);

		Statement stmt;
		try {
			if (conn == null) {
				return;
			}
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("select * from orak");
			System.out.println("Csatlakozva");
			while (rs.next()) {
				lista.add(new Ora(rs.getString("name"),  OraTipusok.convertToEnum(rs.getString("tipus")), rs.getInt("ar"), rs.getBoolean("vizallo")));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Lekér olcsó óákat,amik olcsóbbak mint a paraméterben átadott
	 * 
	 * @param ar
	 */
	public static void justCheap(int ar) {
		Connection conn = connect();
		try {
			PreparedStatement query = conn.prepareStatement("select * from orak where ar<?");
			query.setInt(1, ar);
			ResultSet res = query.executeQuery();
			while (res.next()) {
				System.out.println(res.getString("name"));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void getByString(String sql) {
		Connection conn = connect();
		try {
			PreparedStatement query = conn.prepareStatement(sql);
			ResultSet res = query.executeQuery();
			while (res.next()) {
				System.out.println(res.getString("name"));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void getDataFromWatch(int id) {
		Connection conn = connect();
		try {
			PreparedStatement query = conn.prepareStatement("select * from orak where id=?");
			query.setInt(1, id);
			ResultSet res = query.executeQuery();
			while (res.next()) {
				System.out.println(res.getString("name") + " | " + res.getInt("ar") + "Ft | "
						+ (res.getBoolean("vizallo") ? "Vízálló" : "Nem vízálló"));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<Ora> synchronAfterSave(List<Ora> lista) {
		Connection conn = connect();
		try {
			PreparedStatement query = conn.prepareStatement("select * from orak");
			ResultSet rs = query.executeQuery();
			lista.clear();
			while (rs.next()) {
				lista.add(new Ora(rs.getString("name"), OraTipusok.convertToEnum(rs.getString("tipus")),
						rs.getInt("ar"), rs.getBoolean("vizallo")));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public static void ujOra(Ora ora) {

		try (Connection kapcsolat = connect()) {

			String sql = "INSERT INTO orak (name, tipus, ar, vizallo) VALUES (?,?,?,?)";
			PreparedStatement ps = kapcsolat.prepareStatement(sql);
			ps.setString(1, ora.getMegnevezes());
			ps.setString(2, ora.getTipus().toString());
			ps.setInt(3, ora.getAr());
			ps.setBoolean(4, ora.isVizallo());

			ps.executeUpdate();

		} catch (SQLException e) {

			System.err.println("DB hiba! " + e.getMessage());

		}

	}
}
