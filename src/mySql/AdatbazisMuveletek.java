package mySql;

import java.sql.SQLException;
import java.util.List;

/**
 * Alap szerkezet az Adatbázis müveletekhez.
 * implementálva egyéni Adatbázis kezelõt lehet létrehozni.
 * @param <T> megadásával példányosítható más osztályokkal.
 */
public interface AdatbazisMuveletek<T> {

	/**
	 * MySql elérési címe és portja.
	 */
	public static final String sqlConnection = "jdbc:mysql://127.0.0.1:3306/";
	
	/**
	 * Automatikus újracsatlakozás lehetõség beállítása.
	 */
	public static final String sqlAutoReconnect = "?autoReconnect=true&useSSL=false";
	
	/**
	 * 
	 * @throws SQLException
	 */
	public void sqlKapcsolat() 
	throws SQLException;
	
	public void sqlKapcsolatBontas()
	throws SQLException;
	
	public void sqlKeszites(T generic)
	throws SQLException;
	
	public List<T> sqlBeolvasas()
	throws SQLException;
	
	public void sqlModositas()
	throws SQLException;
	
	public void sqlTorles()
	throws SQLException;
}