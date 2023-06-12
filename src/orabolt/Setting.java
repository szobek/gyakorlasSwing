package orabolt;

import java.util.HashMap;
import java.util.Map;

public class Setting {
	private Map<String, String> beallitasok = new HashMap<String, String>();

	public Setting() {
		this.beallitasok.put("DbUrl", "localhost");
	}

	public Map<String, String> getBeallitasok() {
		return beallitasok;
	}
	
	
	
	

}
