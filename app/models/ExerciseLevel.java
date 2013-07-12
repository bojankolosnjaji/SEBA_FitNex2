package models;

public enum ExerciseLevel {
	BEGINNER(0),INTERMEDIATE(1), EXPERT(2),NONE(-1);
	
	private final int id;
	ExerciseLevel(int id) { this.id = id; }
	 public int getValue() { return id; }
	 public static ExerciseLevel getValue(String id) { 
		 if(id.equalsIgnoreCase("beginner"))
			 return BEGINNER;
		 if(id.equalsIgnoreCase("intermediate"))
			 return INTERMEDIATE;
		else if (id.equalsIgnoreCase("expert"))
			 return EXPERT;
		else
			return NONE;
	 }
	}
