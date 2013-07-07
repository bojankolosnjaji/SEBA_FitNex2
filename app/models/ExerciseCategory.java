package models;

public enum ExerciseCategory {
	MUSCLE(0), RESEARCH(1), NUTRITION(2);
	
	private final int id;
	ExerciseCategory(int id) { this.id = id; }
	 public int getValue() { return id; }
	 public static ExerciseCategory getValue(String id) { 
		 if(id.equalsIgnoreCase("muscle"))
			 return MUSCLE;
		 if(id.equalsIgnoreCase("research"))
			 return RESEARCH;
		 else
			 return NUTRITION;
	 }
	}