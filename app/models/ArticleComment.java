package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class ArticleComment extends Model{
	
	
	@ManyToOne(fetch=FetchType.LAZY)	
	public Article article;
	
	@ManyToOne(fetch=FetchType.LAZY)
	public User user;
	
	@ManyToOne
    public ArticleComment parent;
    @OneToMany(mappedBy="parent")
    private List<ArticleComment> children;
	
    public String title;
    @Required
    @Lob
	public String content;
	public Date date;
	
	public ArticleComment(Article article, User user, ArticleComment parent,
			List<ArticleComment> children, String title, String content,
			Date date) {
		super();
		this.article = article;
		this.user = user;
		this.parent = parent;
		this.children = children;
		this.title = title;
		this.content = content;
		this.date = date;
	}
	
	

}
