package orabolt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;




public class DbHandle {
	private static List<Ora> oraList;
	private static Ora oraPeldany;

	private static String dbUser = "root";
	private static String dbPassword = "Ruander2023";
	
	private static Connection connect(List<Ora> lista) {
		Connection con=null;
		try {
		
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/orabolt?autoReconnect=true&useSSL=false",dbUser, dbPassword);
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			FileHandle.readFile(lista);
			JOptionPane.showMessageDialog(null, "nem siker�lt kapcsol�dni a db-hez,ez�rt f�jl olvasva: "+e.getMessage());
		}
		return con;
	}
	
	private static Connection connect() {
		Connection con=null;
		try {
		
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/orabolt?autoReconnect=true&useSSL=false",dbUser, dbPassword);
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "nem siker�lt kapcsol�dni a db-hez: "+e.getMessage());
			System.exit(0);
		}
		return con;
	}
	
	public static List<Ora> all(List<Ora> lista) {
		Connection conn = connect(lista);

		oraList = lista;
		Statement stmt;
		try {
			if(conn==null) {return null;}
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("select * from orak");
			System.out.println("Csatlakozva");
			while(rs.next()) {
				System.out.println(rs.getString("name"));
				oraPeldany = new Ora(rs.getString("name"), OraTipusok.convertToEnum(rs.getString("tipus")), rs.getInt("ar"), rs.getBoolean("vizallo"));		
				oraList.add(oraPeldany);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return oraList;
	}
	
	/**
	 * Lek�r olcs� ��kat,amik olcs�bbak mint a param�terben �tadott
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
				System.out.println(res.getString("name")+" | "+res.getInt("ar")+"Ft | "+(res.getBoolean("vizallo")?"V�z�ll�":"Nem v�z�ll�"));
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
	
	
	public static void ujOra(Ora ora) {
		
				
			try (Connection kapcsolat = connect()){
				
				String sql = "INSERT INTO orak (name, tipus, ar, vizallo) VALUES (?,?,?,?)";
				PreparedStatement ps = kapcsolat.prepareStatement(sql);
				ps.setString(1, ora.getMegnevezes());
				ps.setString(2, ora.getTipus().toString());
				ps.setInt(3, ora.getAr());
				ps.setBoolean(4, ora.isVizallo());
						
				ps.executeUpdate();
	
			} catch (SQLException e) {
				
				System.err.println("DB hiba! "+e.getMessage());
				
			} 
			
			
			
		
	}
}
