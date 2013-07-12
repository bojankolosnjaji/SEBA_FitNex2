package controllers;

import models.Tutorial;
import models.User;
import models.UserTutorial;
import play.mvc.Controller;

public class UserTutorials extends Controller {

	public static int addUserTutorials(long id, int interestLevel) {
		// String email = Security.connected();
		User signedUser = User.convertToUser(Security.session.get("user"));
		String email = null;
		if (signedUser != null)
			email = signedUser.email;

		System.out.println("Looking for email... " + email);
		User user = (User) User.find("byEmail", email).first();
		if (user == null) {
			Application.index(null);
		}

		Tutorial tutorial = Tutorial.findById(id);

		

		UserTutorial uT = new UserTutorial(tutorial, user,
				interestLevel);

		int doesExist = uT.doesExist();
		
		System.out.println("doesExist: " + doesExist);
		
		if (doesExist == 0) {
			
			if(interestLevel==1)
			LogMaker.log("UserTutorialActivity", signedUser, "now prefer "
					+ tutorial.title);
			
			else {
				LogMaker.log("UserTutorialActivity", signedUser,
						"now do not prefer " + tutorial.title);
			}
			
			uT.add();
			return 1;
			
		} else {

			if (doesExist == 1 && interestLevel == 1) {
				System.out.println("User Tutorial already exists");
				return -1;

			} else if ((doesExist == 1 && interestLevel == -1)) {

				LogMaker.log("UserTutorialActivity", signedUser,
						"now is nuetral after liking " + tutorial.title);
				uT.deleteUserTutorial();
				return 0;

			} else if ((doesExist == -1 && interestLevel == 1)) {

				LogMaker.log("UserTutorialActivity", signedUser, "now is nuetral after disliking "
						+ tutorial.title);
				uT.deleteUserTutorial();
				return 0;

			} else if ((doesExist == -1 && interestLevel == -1)) {

				System.out.println("User Preference already exists");
				return -1;

			}
		}
		
		
		return -1;

	}


}
