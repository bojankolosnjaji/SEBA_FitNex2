package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Article extends Model{
	@Required
	String title;
	@Required
	public String content;
	
	public String image;
	@Required
	public Date date;
	public int numberOfVotes;
	public double rank;
	
	public ArticleCategory category;	
	
	public ArticleLevel level;
	
	@ManyToMany
	  @JoinTable(
	      name="ARTICLE_RELATION",
	      joinColumns={@JoinColumn(name="ART_RELATEE_ID", referencedColumnName="ID")},
	      inverseJoinColumns={@JoinColumn(name="ART_RELATER_ID", referencedColumnName="ID")})
    public List<Article> relatedArticles;
    
    @OneToMany(mappedBy="article")
    public List<ArticleComment> comments;

	public Article(String title, String content, String image, Date date,
			ArticleCategory category, ArticleLevel level) {
		super();
		this.title = title;
		this.content = content;
		this.image = image;
		this.date = date;
		this.category = category;
		this.level = level;		 
	}
    
    
	
	
}
