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
	
}
