package models;

public enum TutorialCategory {
	MUSCLE(0), RESEARCH(1), NUTRITION(2);

	private final int id;

	TutorialCategory(int id) {
		this.id = id;
	}

	public int getValue() {
		return id;
	}

	public static TutorialCategory getValue(String id) {
		if (id.equalsIgnoreCase("muscle"))
			return MUSCLE;
		if (id.equalsIgnoreCase("research"))
			return RESEARCH;
		else
			return NUTRITION;
	}
}