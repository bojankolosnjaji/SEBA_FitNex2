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
public class TutorialComment extends Model{
		
	@ManyToOne(fetch=FetchType.LAZY)
	@Required
	public Tutorial tutorial;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Required
	public User user;
	
	@ManyToOne
	@Required
    public TutorialComment parent;
    @OneToMany(mappedBy="parent")
    private List<TutorialComment> children;
	
    
    public String title;
    @Required
    @Lob
	public String content;
    @Required
	public Date date;
	
	public TutorialComment(Tutorial tutorial, User user, TutorialComment parent,
			List<TutorialComment> children, String title, String content,
			Date date) {
		super();
		this.tutorial = tutorial;
		this.user = user;
		this.parent = parent;
		this.children = children;
		this.title = title;
		this.content = content;
		this.date = date;
	}

}
