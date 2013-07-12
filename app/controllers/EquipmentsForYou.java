package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import models.Category;
import models.Exercise;
import models.ExerciseComment;
import models.ExerciseRelatedCategory;
import models.RelatedEquipment;
import models.User;
import models.UserExercisePreference;
import models.UserExercisePreferencesComparator;
import play.mvc.Controller;

public class EquipmentsForYou extends Controller {

	public static void displayEquipments() {
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

		TreeSet<models.Category> userCategories = new TreeSet<models.Category>();

		Comparator<UserExercisePreference> myCom = new UserExercisePreferencesComparator();

		Collections.sort(user.userExercisePreferences, myCom);

		for (UserExercisePreference userPreference : user.userExercisePreferences) {
			

			if (userPreference.interestLevel == 1) {
				System.out.println("Ex " + userPreference.exercise.title + ",  "
						+ userPreference.exercise.rank + ",  "
						+ userPreference.exercise.numberOfVotes+ ",  "+userPreference.interestLevel);
				Exercise ex = userPreference.exercise;
				for (ExerciseRelatedCategory exRelatedCat : ex.category) {
					models.Category cat=exRelatedCat.category;
					userCategories.add(cat);
				}
			}

		}
		
		System.out.println("userCategories Size " + userCategories.size());

		LinkedHashSet<RelatedEquipment> relEquip = new LinkedHashSet<RelatedEquipment>();
		for (Category category : userCategories) {
			System.out.println("Cat " + category.name);
			relEquip.addAll(category.relatedEquipment);
		}

		renderTemplate("Application/equipments_for_you.html", relEquip,
				signedUser);

	}
}
