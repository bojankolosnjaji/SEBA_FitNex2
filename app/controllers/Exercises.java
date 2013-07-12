package controllers;

import play.*;
import play.db.jpa.GenericModel.JPAQuery;
import play.db.jpa.JPA;
import play.mvc.*;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import oauth.signpost.signature.SigningStrategy;

import org.hibernate.validator.constraints.impl.SizeValidatorForString;

import models.*;

public class Exercises extends Controller {

	public static void loadExercise(long id) {
		
		User signedUser = User.convertToUser(Security.session.get("user"));
		Exercise exercise = Exercise.findById(id);
		int haveVoted=-2;
		if (signedUser!=null) {
			haveVoted = exercise.haveUserVoted(signedUser.id);
		}

		TreeSet<Category> relCat = exercise.getRelatedCategories();
		LinkedHashSet<RelatedEquipment> relEquip = exercise.getRelatedEquipments();
		
		renderTemplate("Exercises/exercise.html", exercise, signedUser,haveVoted,relCat,relEquip);
	}

	public static void exercises() {

		User signedUser = null;
		signedUser = User.convertToUser(Security.session.get("user"));

//		 models.UserExercisePreference.deleteAll();
//		 models.RelatedEquipment.deleteAll();
//		 models.ExerciseRelatedCategory.deleteAll();
//		 models.ExerciseComment.deleteAll();
//		 models.Exercise.deleteAll();
//		 models.Category.deleteAll();
		// models.User.deleteAll();

		List<Exercise> exerciseList = models.Exercise
				.find("order by date desc").fetch();

		if (exerciseList == null || exerciseList.isEmpty()) {

			Category cat1 = new models.Category("Cardio", "").save();
			Category cat2 = new models.Category("Strength", "").save();
			Category cat3 = new models.Category("Endurance", "").save();

			Exercise ex1 = new models.Exercise("Exercise1", "Content1", "/public/images/banner_exercises.png",
					new Date(), ExerciseCategory.MUSCLE,
					ExerciseLevel.BEGINNER, 2, 3).save();
			Exercise ex2 = new models.Exercise("Exercise2", "Content2", "/public/images/banner_exercises.png",
					new Date(), ExerciseCategory.NUTRITION,
					ExerciseLevel.EXPERT, 3, 2).save();
			Exercise ex3 = new models.Exercise("Exercise3", "Content3", "/public/images/banner_exercises.png",
					new Date(), ExerciseCategory.RESEARCH,
					ExerciseLevel.BEGINNER, 1, 1).save();
			Exercise ex4 = new models.Exercise("Exercise4", "Content4", "/public/images/banner_exercises.png",
					new Date(), ExerciseCategory.RESEARCH,
					ExerciseLevel.BEGINNER, 5, 1).save();
			Exercise ex5 = new models.Exercise("Exercise5", "Content5", "/public/images/banner_exercises.png",
					new Date(), ExerciseCategory.MUSCLE,
					ExerciseLevel.BEGINNER, 4, 2).save();

			new ExerciseRelatedCategory(ex1, cat1).save();
			new ExerciseRelatedCategory(ex1, cat2).save();
			new ExerciseRelatedCategory(ex2, cat1).save();
			new ExerciseRelatedCategory(ex2, cat3).save();
			new ExerciseRelatedCategory(ex3, cat2).save();
			new ExerciseRelatedCategory(ex4, cat3).save();
			new ExerciseRelatedCategory(ex5, cat1).save();
			new ExerciseRelatedCategory(ex5, cat2).save();

			new RelatedEquipment(cat2, "Dumbbell", "/public/images/eq1.jpg", "")
					.save();
			new RelatedEquipment(cat1, "Treadmills", "/public/images/eq1.jpg",
					"").save();
			new RelatedEquipment(cat1, "Exercise Bike",
					"/public/images/eq2.jpg", "").save();
			new RelatedEquipment(cat3, " Horizon Elliptical",
					"/public/images/eq3.jpg", "").save();

			String email = null;
			
			if (signedUser != null)
				email = signedUser.email;

			User user = User.find("byEmail", email).first();

			System.out.println("ex1.id  " + ex1.id);
			System.out.println("user.id   " + user.id);
			new UserExercisePreference(ex1, user, 1).save();
			ex1.rank++;
			ex1.numberOfVotes++;
			ex1.save();
			new UserExercisePreference(ex2, user, -1).save();
			ex2.rank--;
			ex2.numberOfVotes++;
			ex2.save();

			System.out.println("Exercises1 fetched  " + ex1.numberOfVotes
					+ "   " + ex1.rank);

			exerciseList = models.Exercise.find("order by date desc").fetch();
			System.out.println(exerciseList.size() + " Exercise inserted");
		}
		
		System.out.println("Exercises fetched");
		List<Category> categoryList = models.Category.all().fetch();
		render(exerciseList, signedUser,categoryList);

		
	}
	
	public static void loadExercises(List<Exercise> exerciseList){
		System.out.println("Exercises fetched");
		User signedUser = User.convertToUser(Security.session.get("user"));
		List<Category> categoryList = models.Category.all().fetch();
		render(exerciseList, signedUser,categoryList);
	}

	public static void rankUp(long id) {
		int addResult = UserExercisePreferences
				.addExerciseToPreferences(id,1);
		
		if (addResult==1) {
			Exercise exercise = Exercise.findById(id);
			exercise.rank++;
			exercise.numberOfVotes++;
			exercise.save();

			loadExercise(id);

		}else if(addResult==0)
		{
			Exercise exercise = Exercise.findById(id);
			exercise.rank++;
			exercise.numberOfVotes--;
			exercise.save();
			
			loadExercise(id);
		}
		else {
			System.out.println("User did not rank up");

		}

	}

	public static void rankDown(long id) {
		
		int addResult = UserExercisePreferences
				.addExerciseToPreferences(id,-1);
		
		if (addResult==1) {
			Exercise exercise = Exercise.findById(id);
			exercise.rank--;
			exercise.numberOfVotes++;
			exercise.save();

			loadExercise(id);

		} else if(addResult==0)
		{
			Exercise exercise = Exercise.findById(id);
			exercise.rank--;
			exercise.numberOfVotes--;
			exercise.save();
			
			loadExercise(id);
		}else {
			System.out.println("User did not rank down");
		}
	}

	public static void search(String txtSearch, String selCategory,
			String selLevel) {
		List<Exercise> exerciseList = models.Exercise
				.find("order by date desc").fetch();

		
		
		String queryString="select distinct e from Exercise e join e.category r join r.category c where UPPER(e.content) LIKE ?";
		
					
		if(!selCategory.equals("none"))
			queryString+="and c.name= '"+selCategory+"'";
		
		
		
		JPAQuery query=models.Exercise
				.find(queryString,
						"%" + txtSearch.toUpperCase() + "%");
		
		if(!selLevel.equals("none"))
		{
			queryString+="and e.level=?";
			
			query=models.Exercise
					.find(queryString,
							"%" + txtSearch.toUpperCase() + "%",ExerciseLevel.getValue(selLevel));
		}
		
		
		
		List<Exercise> exerciseSearchList = query.fetch();

		int selLevelInt=ExerciseLevel.getValue(selLevel).getValue()+1;//To take into account the none option
		List<Category> categoryList = models.Category.all().fetch();
		User signedUser = User.convertToUser(Security.session.get("user"));
		renderTemplate("Exercises/exercises.html", exerciseList,
				exerciseSearchList, signedUser,categoryList,selCategory,selLevelInt);

	}

	public static void exercises_beginner() {
		List<Exercise> exerciseList = models.Exercise.find(
				"level=? order by date desc", ExerciseLevel.BEGINNER).fetch();
		loadExercises(exerciseList);

	}

	public static void exercises_intermediate() {
		List<Exercise> exerciseList = models.Exercise.find(
				"level=? order by date desc", ExerciseLevel.INTERMEDIATE)
				.fetch();
		loadExercises(exerciseList);
	}

	public static void exercises_expert() {
		List<Exercise> exerciseList = models.Exercise.find(
				"level=? order by date desc", ExerciseLevel.EXPERT).fetch();
		loadExercises(exerciseList);
	}

}
