package controllers;

import java.util.Date;
import java.util.List;

import models.Article;
import models.ArticleCategory;
import models.ArticleLevel;
import models.TutorialCategory;
import models.TutorialLevel;
import models.User;
import models.UserArticle;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.Controller;

public class Articles extends Controller {

	public static void loadArticle(long id) {
		User signedUser = User.convertToUser(Security.session.get("user"));
		Article article = Article.findById(id);

		int haveVoted = -2;
		if (signedUser != null) {
			haveVoted = article.haveUserVoted(signedUser.id);
		}

		renderTemplate("Articles/article.html", article, signedUser, haveVoted);
	}

	public static void articles() {
		User signedUser = User.convertToUser(Security.session.get("user"));
		List<Article> articleList = models.Article.find("order by date desc")
				.fetch();

		// models.UserArticle.deleteAll();
		// models.ArticleComment.deleteAll();
		// models.Article.deleteAll();

		if (articleList == null || articleList.isEmpty()) {
			Article article1 = new models.Article("Article1", "Content1", null,
					new Date(), ArticleCategory.MUSCLE, ArticleLevel.BEGINNER);
			article1.save();
			Article article2 = new models.Article("Article2", "Content2", null,
					new Date(), ArticleCategory.NUTRITION, ArticleLevel.EXPERT)
					.save();
			new models.Article("Article3", "Content3", null, new Date(),
					ArticleCategory.RESEARCH, ArticleLevel.BEGINNER).save();
			new models.Article("Article4", "Content4", null, new Date(),
					ArticleCategory.RESEARCH, ArticleLevel.BEGINNER).save();

			String email = null;
			if (signedUser != null)
				email = signedUser.email;
			User user = User.find("byEmail", email).first();

			if (user != null) {
				System.out.println("user.id   " + user.id);
				new UserArticle(article1, user, 1).save();
				article1.rank++;
				article1.numberOfVotes++;
				article1.save();
				new UserArticle(article2, user, -1).save();
				article2.rank--;
				article2.numberOfVotes++;
				article2.save();
			}

			articleList = models.Article.find("order by date desc").fetch(10);
			System.out.println(articleList.size() + " Articles inserted");
		}
		System.out.println("Articles fetched");

		render(articleList, signedUser);
	}

	public static void rankUp(long id) {
		int addResult = UserArticles.addUserArticles(id, 1);

		if (addResult == 1) {
			Article article = Article.findById(id);
			article.rank++;
			article.numberOfVotes++;
			article.save();

			loadArticle(id);

		} else if (addResult == 0) {
			Article article = Article.findById(id);
			article.rank++;
			article.numberOfVotes--;
			article.save();

			loadArticle(id);
		} else {
			System.out.println("User did not rank up");

		}

	}

	public static void rankDown(long id) {

		int addResult = UserArticles.addUserArticles(id, -1);

		if (addResult == 1) {
			Article article = Article.findById(id);
			article.rank--;
			article.numberOfVotes++;
			article.save();

			loadArticle(id);

		} else if (addResult == 0) {
			Article article = Article.findById(id);
			article.rank--;
			article.numberOfVotes--;
			article.save();

			loadArticle(id);
		} else {
			System.out.println("User did not rank down");
		}
	}

	public static void search(String txtSearch, String selCategory,
			String selLevel) {
		List<Article> articleList = models.Article.find("order by date desc")
				.fetch(10);

		String txtSearchWithoutWhiteSp = txtSearch.replaceAll("\\s", "%");

		String queryString = "UPPER(content) LIKE ?   ";

		if (!selCategory.equals("none"))
			queryString += "and category= '"
					+ (ArticleCategory.getValue(selCategory)).getValue() + "'";

		if (!selLevel.equals("none"))
			queryString += "and level='"
					+ (ArticleLevel.getValue(selLevel)).getValue() + "'";

		JPAQuery query = models.Article.find(
				queryString + "order by date desc", "%"
						+ txtSearchWithoutWhiteSp.toUpperCase() + "%");

		List<Article> articleSearchList = query.fetch();

		int selLevelInt = TutorialLevel.getValue(selLevel).getValue() + 1;
		int selCategoryInt = TutorialCategory.getValue(selCategory).getValue() + 1;
		User signedUser = User.convertToUser(Security.session.get("user"));
		renderTemplate("Articles/articles.html", articleList,
				articleSearchList, signedUser, selLevelInt, selCategoryInt,
				txtSearch);

	}

	public static void articles_beginner() {
		List<Article> articleList = models.Article.find(
				"level=? order by date desc", ArticleLevel.BEGINNER).fetch(30);
		User signedUser = User.convertToUser(Security.session.get("user"));
		render(articleList, signedUser);

	}

	public static void articles_intermediate() {
		List<Article> articleList = models.Article.find(
				"level=? order by date desc", ArticleLevel.INTERMEDIATE)
				.fetch(30);
		User signedUser = User.convertToUser(Security.session.get("user"));
		render(articleList, signedUser);
	}

	public static void articles_expert() {
		List<Article> articleList = models.Article.find(
				"level=? order by date desc", ArticleLevel.EXPERT).fetch(30);
		User signedUser = User.convertToUser(Security.session.get("user"));
		render(articleList, signedUser);
	}

}
