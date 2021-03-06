package controllers;

import java.util.Date;

import models.Article;
import models.ArticleComment;
import models.User;
import play.mvc.Controller;
import play.mvc.With;

public class ArticleComments extends Controller{
	
	public static void addComment(long articleId, String txtComment,String txtTitle)
	{
		//String email = Security.connected();
		
		User signedUser=User.convertToUser(Security.session.get("user"));
		String email=null;
		if(signedUser!=null)
			email=signedUser.email;
		
		System.out.println("Looking for email... " + email);
		User user = (User) User.find("byEmail", email).first();
		if (user==null)
		{
			Application.index(null);
		}
		System.out.println("User:" + user.firstName);
		Article article = Article.findById(articleId);
		System.out.println("Article: "+ article.content);
		ArticleComment comment = new ArticleComment(article, user, null, null, txtTitle, txtComment, new Date());
		comment.save();
		
		LogMaker.log("ArticleCommentActivity", signedUser, "has added a comment");
		
		Articles.loadArticle(article.getId());
		
	}

}
