package mySql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Alap szerkezet az Adatb�zis m�veletekhez.
 * implement�lva egy�ni Adatb�zis kezel�t lehet l�trehozni.
 * @param 		<T> megad�s�val p�ld�nyos�that� m�s oszt�lyokkal.
 */
public interface AdatbazisMuveletek<T> {

	/**
	 * MySql el�r�si c�me �s portja.
	 */
	public static final String sqlConnection = "jdbc:mysql://127.0.0.1:3306/";
	
	/**
	 * Automatikus �jracsatlakoz�s lehet�s�g be�ll�t�sa.
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
	 * CRUD egyik m�velete a K�sz�t�s megval�s�t�sa.
	 * @param 		generic p�ld�nyos�tott Oszt�ly v�ltoz� megad�sa param�terk�n.
	 * @throws 		SQLException
	 */
	public void sqlKeszites(T generic)
	throws SQLException;
	
	/**
	 * CRUD egyik m�velete a Beolvasas megval�s�t�sa.
	 * @return 		List p�ld�nyos�tott adatszerkezetben visszaadja azokat az elemeket amik,
	 * 				p�ld�nyos�tva lettek mint genricus Oszt�ly.
	 * @throws 		SQLException
	 */
	public List<T> sqlBeolvasas()
	throws SQLException;
	
	/**
	 * CRUD egyik m�velete a M�dos�t�s megval�s�t�sa.
	 * @throws SQLException
	 */
	public void sqlModositas()
	throws SQLException;
	
	/**
	 * CRUD egyik m�velete a T�rl�s megval�s�t�sa.
	 * @throws SQLException
	 */
	public void sqlTorles()
	throws SQLException;
}