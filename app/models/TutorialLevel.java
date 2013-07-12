package models;

public enum TutorialLevel {
	BEGINNER(0), INTERMEDIATE(1), EXPERT(2),NONE(-1);

	private final int id;

	TutorialLevel(int id) {
		this.id = id;
	}

	public int getValue() {
		return id;
	}

	public static TutorialLevel getValue(String id) {
		if (id.equalsIgnoreCase("beginner"))
			return BEGINNER;
		if (id.equalsIgnoreCase("intermediate"))
			return INTERMEDIATE;
		else if (id.equalsIgnoreCase("expert"))
			 return EXPERT;
		else
			return NONE;
	}
}
