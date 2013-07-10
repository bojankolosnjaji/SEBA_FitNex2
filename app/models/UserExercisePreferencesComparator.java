package models;

import java.util.Comparator;

import models.UserExercisePreference;

public class UserExercisePreferencesComparator implements Comparator<UserExercisePreference> {
	 @Override
	    public int compare(UserExercisePreference o1, UserExercisePreference o2) {
	          Exercise ex1=o1.exercise;
	          Exercise ex2=o2.exercise;

	          if(ex1.rank>ex2.rank)
	              return -1;
	          else if(ex1.rank<ex2.rank)
	              return +1;
	          else
	              return 0;
	    }           
}
