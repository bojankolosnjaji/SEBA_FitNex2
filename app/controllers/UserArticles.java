package controllers;

import models.Article;
import models.User;
import models.UserArticle;
import play.mvc.Controller;


public class UserArticles extends Controller {

	public static int addUserArticles(long id, int interestLevel) {
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

		Article article = Article.findById(id);

		

		UserArticle uA = new UserArticle(article, user,
				interestLevel);

		int doesExist = uA.doesExist();
		
		System.out.println("doesExist: " + doesExist);
		
		if (doesExist == 0) {
			
			if(interestLevel==1)
			LogMaker.log("UserArticleActivity", signedUser, "now prefer "
					+ article.title);
			
			else {
				LogMaker.log("UserArticleActivity", signedUser,
						"now do not prefer " + article.title);
			}
			
			uA.add();
			return 1;
			
		} else {

			if (doesExist == 1 && interestLevel == 1) {
				System.out.println("User Article already exists");
				return -1;

			} else if ((doesExist == 1 && interestLevel == -1)) {

				LogMaker.log("UserArticleActivity", signedUser,
						"now is nuetral after liking " + article.title);
				uA.deleteUserArticles();
				return 0;

			} else if ((doesExist == -1 && interestLevel == 1)) {

				LogMaker.log("UserArticleActivity", signedUser, "now is nuetral after disliking "
						+ article.title);
				uA.deleteUserArticles();
				return 0;

			} else if ((doesExist == -1 && interestLevel == -1)) {

				System.out.println("User Article already exists");
				return -1;

			}
		}
		
		
		return -1;

	}


}
