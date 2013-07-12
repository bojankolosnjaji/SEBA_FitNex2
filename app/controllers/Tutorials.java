package controllers;

import java.util.Date;
import java.util.List;

import models.Step;
import models.Tutorial;
import models.TutorialCategory;
import models.TutorialLevel;
import models.User;
import models.UserTutorial;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.Controller;

public class Tutorials extends Controller {

	public static void loadTutorial(long id) {
		User signedUser = User.convertToUser(Security.session.get("user"));
		Tutorial tutorial = Tutorial.findById(id);
		int haveVoted = -2;
		if (signedUser != null) {
			haveVoted = tutorial.haveUserVoted(signedUser.id);
		}

		renderTemplate("Tutorials/tutorial.html", tutorial, signedUser,
				haveVoted);
	}

	public static void tutorials() {
		List<Tutorial> tutorialList = models.Tutorial
				.find("order by date desc").fetch();

		User signedUser = User.convertToUser(Security.session.get("user"));

		// models.UserTutorial.deleteAll();
		// models.Step.deleteAll();
		// models.TutorialComment.deleteAll();
		// models.Tutorial.deleteAll();

		if (tutorialList == null || tutorialList.isEmpty()) {

			Tutorial tutorial1 = new models.Tutorial("Tutorial5", "Content5",
					null, new Date(), TutorialCategory.MUSCLE,
					TutorialLevel.BEGINNER).save();
			new Step(tutorial1, 1, "Tutorial5", "Step1Content", null).save();
			new Step(tutorial1, 2, "Tutorial5", "Step2Content", null).save();
			new Step(tutorial1, 3, "Tutorial5", "Step3Content", null).save();
			Tutorial tutorial2 = new models.Tutorial("Tutorial1", "Content1",
					null, new Date(), TutorialCategory.MUSCLE,
					TutorialLevel.BEGINNER).save();
			new models.Tutorial("Tutorial2", "Content2", null, new Date(),
					TutorialCategory.NUTRITION, TutorialLevel.EXPERT).save();
			new models.Tutorial("Tutorial3", "Content3", null, new Date(),
					TutorialCategory.RESEARCH, TutorialLevel.BEGINNER).save();
			new models.Tutorial("Tutorial4", "Content4", null, new Date(),
					TutorialCategory.RESEARCH, TutorialLevel.BEGINNER).save();

			String email = null;
			if (signedUser != null)
				email = signedUser.email;
			User user = User.find("byEmail", email).first();

			if (user != null) {
				System.out.println("user.id   " + user.id);
				new UserTutorial(tutorial1, user, 1).save();
				tutorial1.rank++;
				tutorial1.numberOfVotes++;
				tutorial1.save();
				new UserTutorial(tutorial2, user, -1).save();
				tutorial2.rank--;
				tutorial2.numberOfVotes++;
				tutorial2.save();
			}

			tutorialList = models.Tutorial.find("order by date desc").fetch(10);
			System.out.println(tutorialList.size() + " Tutorial inserted");
		}
		System.out.println("Tutorials fetched");

		render(tutorialList, signedUser);
	}

	public static void rankUp(long id) {
		int addResult = UserTutorials.addUserTutorials(id, 1);

		if (addResult == 1) {
			Tutorial tutorial = Tutorial.findById(id);
			tutorial.rank++;
			tutorial.numberOfVotes++;
			tutorial.save();

			loadTutorial(id);

		} else if (addResult == 0) {
			Tutorial tutorial = Tutorial.findById(id);
			tutorial.rank++;
			tutorial.numberOfVotes--;
			tutorial.save();

			loadTutorial(id);
		} else {
			System.out.println("User did not rank up");

		}

	}

	public static void rankDown(long id) {

		int addResult = UserTutorials.addUserTutorials(id, -1);

		if (addResult == 1) {
			Tutorial tutorial = Tutorial.findById(id);
			tutorial.rank--;
			tutorial.numberOfVotes++;
			tutorial.save();

			loadTutorial(id);

		} else if (addResult == 0) {
			Tutorial tutorial = Tutorial.findById(id);
			tutorial.rank--;
			tutorial.numberOfVotes--;
			tutorial.save();

			loadTutorial(id);
		} else {
			System.out.println("User did not rank down");
		}
	}

	public static void search(String txtSearch, String selCategory,
			String selLevel) {
		List<Tutorial> tutorialList = models.Tutorial
				.find("order by date desc").fetch(10);

		String txtSearchWithoutWhiteSp = txtSearch.replaceAll("\\s", "%");

		String queryString = "UPPER(content) LIKE ?   ";

		if (!selCategory.equals("none"))
			queryString += "and category= '"
					+ (TutorialCategory.getValue(selCategory)).getValue() + "'";

		if (!selLevel.equals("none"))
			queryString += "and level='"
					+ (TutorialLevel.getValue(selLevel)).getValue() + "'";

		JPAQuery query = models.Tutorial.find(queryString
				+ "order by date desc",
				"%" + txtSearchWithoutWhiteSp.toUpperCase() + "%");

		List<Tutorial> tutorialSearchList = query.fetch();

		int selLevelInt = TutorialLevel.getValue(selLevel).getValue() + 1;
		int selCategoryInt = TutorialCategory.getValue(selCategory).getValue() + 1;
		User signedUser = User.convertToUser(Security.session.get("user"));
		renderTemplate("Tutorials/tutorials.html", tutorialList,
				tutorialSearchList, signedUser, selLevelInt, selCategoryInt,
				txtSearch);

	}

	public static void tutorials_beginner() {
		List<Tutorial> tutorialList = models.Tutorial.find(
				"level=? order by date desc", TutorialLevel.BEGINNER).fetch(30);

		User signedUser = User.convertToUser(Security.session.get("user"));
		render(tutorialList, signedUser);

	}

	public static void tutorials_intermediate() {
		List<Tutorial> tutorialList = models.Tutorial.find(
				"level=? order by date desc", TutorialLevel.INTERMEDIATE)
				.fetch(30);
		User signedUser = User.convertToUser(Security.session.get("user"));
		render(tutorialList, signedUser);
	}

	public static void tutorials_expert() {
		List<Tutorial> tutorialList = models.Tutorial.find(
				"level=? order by date desc", TutorialLevel.EXPERT).fetch(30);

		User signedUser = User.convertToUser(Security.session.get("user"));
		render(tutorialList, signedUser);
	}

}
