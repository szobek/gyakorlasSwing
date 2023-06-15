package orabolt;

public enum OraTipusok {
	KARORA("kar�ra"), FALIORA("fali�ra"), EBRESZTOORA("�breszt��ra"), STOPPERORA("stopper�ra");

	private String type;

	private OraTipusok(String type) {
		this.type=type;
	}

	public static OraTipusok convertToEnum(String tipus) {
		for (OraTipusok elem : OraTipusok.values()) {
			if (elem.type.equals(tipus)) {
				return elem;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return type;
	}
}
