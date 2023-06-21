package orabolt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Setting {
	private static Map<String, String> beallitasok = new HashMap<>();

	public static void putDData() {

		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String appConfigPath = rootPath + "appSettings.prop";

		Properties appProps = new Properties();
		try {
			appProps.load(new FileInputStream(appConfigPath));


			beallitasok.put("DbUrl", appProps.getProperty("DbUrl"));
			beallitasok.put("DbPort",appProps.getProperty("DbPort"));
			beallitasok.put("DbName",appProps.getProperty("DbName"));
			beallitasok.put("DbUser",appProps.getProperty("DbUser"));
			beallitasok.put("DbPsw",appProps.getProperty("DbPsw"));


		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public Map<String, String> getBeallitasok() {
		return beallitasok;
	}





}
