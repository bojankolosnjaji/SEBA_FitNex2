package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Exercises extends Controller {

	public static void loadExercise(long id) {
		Exercise exercise = Exercise.findById(id);
		User signedUser=User.convertToUser(Security.session.get("user"));
        renderTemplate("Exercises/exercise.html", exercise,signedUser);
    }

    public static void exercises() {
    	List<Exercise> exerciseList= models.Exercise.find("order by date desc").fetch();
    	
    	if (exerciseList==null || exerciseList.isEmpty())
    	{
    		
    		new models.Exercise("Exercise1", "Content1", null, new Date(), ExerciseCategory.MUSCLE, ExerciseLevel.BEGINNER, 2,3).save();
        	new models.Exercise("Exercise2", "Content2", null, new Date(), ExerciseCategory.NUTRITION, ExerciseLevel.EXPERT, 3,2).save();
        	new models.Exercise("Exercise3", "Content3", null, new Date(), ExerciseCategory.RESEARCH, ExerciseLevel.BEGINNER,1,1).save();
        	new models.Exercise("Exercise4", "Content4", null, new Date(), ExerciseCategory.RESEARCH, ExerciseLevel.BEGINNER,5,1).save();
        	new models.Exercise("Exercise5", "Content5", null, new Date(), ExerciseCategory.MUSCLE, ExerciseLevel.BEGINNER,4,2).save();
        	exerciseList= models.Exercise.find("order by date desc").fetch();
        	System.out.println(exerciseList.size() + " Exercise inserted");
    	}
    	System.out.println("Exercises fetched");
    	User signedUser=User.convertToUser(Security.session.get("user"));
    	render(exerciseList,signedUser);
    }
    
    
    public static void search(String txtSearch,String selCategory,String selLevel) {
    	List<Exercise> exerciseList= models.Exercise.find("order by date desc").fetch();
    	
    	List<Exercise> exerciseSearchList= models.Exercise.find("level=? and category=? and UPPER(content) LIKE ?   order by date desc", (ExerciseLevel.getValue(selLevel)),ExerciseCategory.getValue(selCategory),"%"+txtSearch.toUpperCase()+"%").fetch();
    	
    	User signedUser=User.convertToUser(Security.session.get("user"));
    	renderTemplate("Exercises/exercises.html",exerciseList,exerciseSearchList,signedUser);
        
    }

    public static void exercises_beginner() {
    	List<Exercise> exerciseList= models.Exercise.find("level=? order by date desc", ExerciseLevel.BEGINNER).fetch();
    	User signedUser=User.convertToUser(Security.session.get("user"));
    	render(exerciseList,signedUser);
        
    }

    public static void exercises_intermediate() {
    	List<Exercise> exerciseList= models.Exercise.find("level=? order by date desc", ExerciseLevel.INTERMEDIATE).fetch();
    	User signedUser=User.convertToUser(Security.session.get("user"));
    	render(exerciseList,signedUser);
    }

    public static void exercises_expert() {
    	List<Exercise> exerciseList= models.Exercise.find("level=? order by date desc", ExerciseLevel.EXPERT).fetch();
    	User signedUser=User.convertToUser(Security.session.get("user"));
    	render(exerciseList,signedUser);
    }

}
