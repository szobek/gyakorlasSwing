package orabolt;

public class Ora {
	private String megnevezes;
	private OraTipusok tipus;
	private int ar;
	private int id;
	private boolean vizallo;
	
	public Ora(String megnevezes, OraTipusok tipus, int ar, boolean vizallo) {
		this.megnevezes = megnevezes;
		this.tipus = tipus;
		this.ar = ar;
		this.vizallo = vizallo;
	}
	
	public Ora(String[] csvSor) {
		this.megnevezes = csvSor[0];
		this.tipus = OraTipusok.convertToEnum(csvSor[3]) ;
		this.ar = Integer.parseInt(csvSor[1]) ;
		this.vizallo = Boolean.parseBoolean(csvSor[2]) ;
	}
	

	public String getMegnevezes() {
		return megnevezes;
	}

	public OraTipusok getTipus() {
		return tipus;
	}

	public int getAr() {
		return ar;
	}

	public int getId() {
		return id;
	}

	public boolean isVizallo() {
		return vizallo;
	}

	@Override
	public String toString() {
		return  megnevezes + " "+ ar + " " +tipus.toString() ;
	}
	
	
	
}
