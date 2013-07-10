package controllers;

import java.util.Date;

import models.Exercise;
import models.ExerciseComment;
import models.User;
import models.UserExercisePreference;
import play.mvc.Controller;

public class UserExercisePreferences extends Controller {

	public static int addExerciseToPreferences(long id, int interestLevel) {
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

		Exercise exercise = Exercise.findById(id);

		

		UserExercisePreference uep = new UserExercisePreference(exercise, user,
				interestLevel);

		int doesExist = uep.doesExist();
		
		System.out.println("doesExist: " + doesExist);
		
		if (doesExist == 0) {
			
			if(interestLevel==1)
			LogMaker.log("ExercisePreferenceActivity", signedUser, "now prefer "
					+ exercise.title);
			
			else {
				LogMaker.log("ExercisePreferenceActivity", signedUser,
						"now do not prefer " + exercise.title);
			}
			
			uep.add();
			return 1;
			
		} else {

			if (doesExist == 1 && interestLevel == 1) {
				System.out.println("User Preference already exists");
				return -1;

			} else if ((doesExist == 1 && interestLevel == -1)) {

				LogMaker.log("ExercisePreferenceActivity", signedUser,
						"now is nuetral after liking " + exercise.title);
				uep.deleteUserExercisePreference();
				return 0;

			} else if ((doesExist == -1 && interestLevel == 1)) {

				LogMaker.log("ExercisePreferenceActivity", signedUser, "now is nuetral after disliking "
						+ exercise.title);
				uep.deleteUserExercisePreference();
				return 0;

			} else if ((doesExist == -1 && interestLevel == -1)) {

				System.out.println("User Preference already exists");
				return -1;

			}
		}
		
		
		return -1;

	}


}
