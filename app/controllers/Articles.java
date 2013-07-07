package controllers;

import java.util.Date;
import java.util.List;

import models.Article;
import models.ArticleCategory;
import models.ArticleComment;
import models.ArticleLevel;
import models.User;
import play.mvc.Controller;

public class Articles extends Controller {

    public static void loadArticle(long id) {
    	Article article = Article.findById(id);
    	User signedUser=User.convertToUser(Security.session.get("user"));
        renderTemplate("Articles/article.html", article,signedUser);
    }

    public static void articles() {
    	List<Article> articleList= models.Article.find("order by date desc").fetch();
    	if (articleList==null || articleList.isEmpty())
    	{
        	Article article1 = new models.Article("Article1", "Content1", null, new Date(), ArticleCategory.MUSCLE, ArticleLevel.BEGINNER);
        	article1.save();
        	new models.Article("Article2", "Content2", null, new Date(), ArticleCategory.NUTRITION, ArticleLevel.EXPERT).save();
        	new models.Article("Article3", "Content3", null, new Date(), ArticleCategory.RESEARCH, ArticleLevel.BEGINNER).save();
        	new models.Article("Article4", "Content4", null, new Date(), ArticleCategory.RESEARCH, ArticleLevel.BEGINNER).save();
        	articleList= models.Article.find("order by date desc").fetch();
        	System.out.println(articleList.size() + " Articles inserted");
    	}
    	System.out.println("Articles fetched");
    	User signedUser=User.convertToUser(Security.session.get("user"));
    	render(articleList,signedUser);
    }

    public static void articles_beginner() {
    	List<Article> articleList= models.Article.find("level=? order by date desc", ArticleLevel.BEGINNER).fetch();
    	User signedUser=User.convertToUser(Security.session.get("user"));
    	render(articleList,signedUser);
        
    }

    public static void articles_intermediate() {
    	List<Article> articleList= models.Article.find("level=? order by date desc", ArticleLevel.INTERMEDIATE).fetch();
    	User signedUser=User.convertToUser(Security.session.get("user"));
    	render(articleList,signedUser);
    }

    public static void articles_expert() {
    	List<Article> articleList= models.Article.find("level=? order by date desc", ArticleLevel.EXPERT).fetch();
    	User signedUser=User.convertToUser(Security.session.get("user"));
    	render(articleList,signedUser);
    }

}
