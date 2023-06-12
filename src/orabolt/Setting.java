package orabolt;

import java.util.HashMap;
import java.util.Map;

public class Setting {
	private Map<String, String> beallitasok = new HashMap<String, String>();

	public Setting() {
		beallitasok.put("DbUrl", "localhost");
		beallitasok.put("DbPort","3306");
		beallitasok.put("DbName","orabolt");
		beallitasok.put("DbUser","root");
		beallitasok.put("DbPsw","");
		
	}

	public Map<String, String> getBeallitasok() {
		return beallitasok;
	}
	
	
	
	

}
