package models;

public enum ArticleCategory {
	
	MUSCLE(0), RESEARCH(1), NUTRITION(2),NONE(-1);

	private final int id;

	ArticleCategory(int id) {
		this.id = id;
	}

	public int getValue() {
		return id;
	}

	public static ArticleCategory getValue(String id) {
		if (id.equalsIgnoreCase("muscle"))
			return MUSCLE;
		if (id.equalsIgnoreCase("research"))
			return RESEARCH;
		else if (id.equalsIgnoreCase("nutrition"))
			return NUTRITION;
		else
			return NONE;
	}
}