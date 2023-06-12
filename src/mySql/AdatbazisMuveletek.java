package mySql;

import java.sql.SQLException;
import java.util.List;

/**
 * Alap szerkezet az Adatb�zis m�veletekhez.
 * implement�lva egy�ni Adatb�zis kezel�t lehet l�trehozni.
 * @param <T> megad�s�val p�ld�nyos�that� m�s oszt�lyokkal.
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