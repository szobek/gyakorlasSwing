package mySql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Alap szerkezet az Adatbázis müveletekhez.
 * implementálva egyéni Adatbázis kezelõt lehet létrehozni.
 * @param 		<T> megadásával példányosítható más osztályokkal.
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
	public Connection sqlKapcsolat() 
	throws SQLException;
	
	public void sqlKapcsolatBontas()
	throws SQLException;
	
	/**
	 * CRUD egyik müvelete a Készítés megvalósítása.
	 * @param 		generic példányosított Osztály változó megadása paraméterkén.
	 * @throws 		SQLException
	 */
	public void sqlKeszites(T generic)
	throws SQLException;
	
	/**
	 * CRUD egyik müvelete a Beolvasas megvalósítása.
	 * @return 		List példányosított adatszerkezetben visszaadja azokat az elemeket amik,
	 * 				példányosítva lettek mint genricus Osztály.
	 * @throws 		SQLException
	 */
	public List<T> sqlBeolvasas()
	throws SQLException;
	
	/**
	 * CRUD egyik müvelete a Módosítás megvalósítása.
	 * @throws SQLException
	 */
	public void sqlModositas()
	throws SQLException;
	
	/**
	 * CRUD egyik müvelete a Törlés megvalósítása.
	 * @throws SQLException
	 */
	public void sqlTorles()
	throws SQLException;
}