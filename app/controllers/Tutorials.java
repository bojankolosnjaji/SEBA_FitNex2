package controllers;

import play.*;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.*;

import java.util.*;

import models.*;

public class Tutorials extends Controller {

	public static void loadTutorial(long id) {
		Tutorial tutorial = Tutorial.findById(id);
		User signedUser = User.convertToUser(Security.session.get("user"));
		renderTemplate("Tutorials/tutorial.html", tutorial, signedUser);
	}

	public static void tutorials() {
		List<Tutorial> tutorialList = models.Tutorial
				.find("order by date desc").fetch();

		if (tutorialList == null || tutorialList.isEmpty()) {

			Tutorial tutorial = new models.Tutorial("Tutorial5", "Content5",
					null, new Date(), TutorialCategory.MUSCLE,
					TutorialLevel.BEGINNER).save();
			new Step(tutorial, 1, "Tutorial5", "Step1Content", null).save();
			new Step(tutorial, 2, "Tutorial5", "Step2Content", null).save();
			new Step(tutorial, 3, "Tutorial5", "Step3Content", null).save();
			new models.Tutorial("Tutorial1", "Content1", null, new Date(),
					TutorialCategory.MUSCLE, TutorialLevel.BEGINNER).save();
			new models.Tutorial("Tutorial2", "Content2", null, new Date(),
					TutorialCategory.NUTRITION, TutorialLevel.EXPERT).save();
			new models.Tutorial("Tutorial3", "Content3", null, new Date(),
					TutorialCategory.RESEARCH, TutorialLevel.BEGINNER).save();
			new models.Tutorial("Tutorial4", "Content4", null, new Date(),
					TutorialCategory.RESEARCH, TutorialLevel.BEGINNER).save();
			tutorialList = models.Tutorial.find("order by date desc").fetch();
			System.out.println(tutorialList.size() + " Tutorial inserted");
		}
		System.out.println("Tutorials fetched");
		User signedUser = User.convertToUser(Security.session.get("user"));
		render(tutorialList, signedUser);
	}

	public static void rankUp(long id) {
		// int addResult = UserExercisePreferences
		// .addExerciseToPreferences(id,1);

		// if (addResult==1) {
		Tutorial tutorial = Tutorial.findById(id);
		tutorial.rank++;
		tutorial.numberOfVotes++;
		tutorial.save();

		loadTutorial(id);

		// }else if(addResult==0)
		// {
		// Exercise exercise = Exercise.findById(id);
		// exercise.rank++;
		// exercise.numberOfVotes--;
		// exercise.save();
		//
		// loadExercise(id);
		// }
		// else {
		// System.out.println("User did not rank up");
		//
		// }

	}

	public static void rankDown(long id) {

		// int addResult = UserExercisePreferences
		// .addExerciseToPreferences(id,-1);

		// if (addResult==1) {
		Tutorial tutorial = Tutorial.findById(id);
		tutorial.rank--;
		tutorial.numberOfVotes++;
		tutorial.save();

		loadTutorial(id);

		// } else if(addResult==0)
		// {
		// Exercise exercise = Exercise.findById(id);
		// exercise.rank--;
		// exercise.numberOfVotes--;
		// exercise.save();
		//
		// loadExercise(id);
		// }else {
		// System.out.println("User did not rank down");
		// }
	}

	public static void search(String txtSearch, String selCategory,
			String selLevel) {
		List<Tutorial> tutorialList = models.Tutorial
				.find("order by date desc").fetch();

		
		String queryString="UPPER(content) LIKE ?   ";
		
		
		if(!selCategory.equals("none"))
			queryString+="and category= '"+(TutorialCategory.getValue(selCategory)).getValue()+"'";
		
		if(!selLevel.equals("none"))
		{
			queryString+="and level='"+(TutorialLevel.getValue(selLevel)).getValue()+"'";
			
			
		}
		
		
		
		JPAQuery query=models.Tutorial
				.find(queryString+"order by date desc",
						"%" + txtSearch.toUpperCase() + "%");
		
		
//		List<Tutorial> tutorialSearchList = models.Tutorial
//				.find("level=? and category=? and UPPER(content) LIKE ?   order by date desc",
//						(TutorialLevel.getValue(selLevel)),
//						TutorialCategory.getValue(selCategory),
//						"%" + txtSearch.toUpperCase() + "%").fetch();
		
		List<Tutorial> tutorialSearchList = query.fetch();

		int selLevelInt = TutorialLevel.getValue(selLevel).getValue()+1;
		int selCategoryInt = TutorialCategory.getValue(selCategory).getValue()+1;
		User signedUser = User.convertToUser(Security.session.get("user"));
		renderTemplate("Tutorials/tutorials.html", tutorialList,
				tutorialSearchList, signedUser, selLevelInt, selCategoryInt);

	}



	public static void tutorials_beginner() {
		List<Tutorial> tutorialList = models.Tutorial.find(
				"level=? order by date desc", TutorialLevel.BEGINNER).fetch();
		
		User signedUser = User.convertToUser(Security.session.get("user"));
		render(tutorialList, signedUser);

	}

	public static void tutorials_intermediate() {
		List<Tutorial> tutorialList = models.Tutorial.find(
				"level=? order by date desc", TutorialLevel.INTERMEDIATE)
				.fetch();
		User signedUser = User.convertToUser(Security.session.get("user"));
		render(tutorialList, signedUser);
	}

	public static void tutorials_expert() {
		List<Tutorial> tutorialList = models.Tutorial.find(
				"level=? order by date desc", TutorialLevel.EXPERT).fetch();
		
		User signedUser = User.convertToUser(Security.session.get("user"));
		render(tutorialList, signedUser);
	}

}
