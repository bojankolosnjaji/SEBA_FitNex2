package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import controllers.UserTutorials;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Tutorial extends Model{
	@Required
	public
	String title;
	@Required
	@Lob
	public String content;

	public String image;
	@Required
	public Date date;
	public int numberOfVotes;
	public double rank;
	
	public TutorialCategory category;	
	
	public TutorialLevel level;
    
    @OneToMany(mappedBy="tutorial")
    public List<TutorialComment> comments;
    
   
    @OneToMany(mappedBy="tutorial")
    
    public List<Step> steps;
    
    public Tutorial(String title, String content, String image, Date date,
    		TutorialCategory category, TutorialLevel level) {
		super();
		this.title = title;
		this.content = content;
		this.image = image;
		this.date = date;
		this.category = category;
		this.level = level;
		
		steps=Step.find("tutorial_id=? order by stepOrder desc", this.id).fetch();
	}
    
    public Tutorial(String title, String content, String image, Date date,
    		TutorialCategory category, TutorialLevel level,List<Step> steps) {
		super();
		this.title = title;
		this.content = content;
		this.image = image;
		this.date = date;
		this.category = category;
		this.level = level;
		
		this.steps = steps;
	}
    
    
    public int haveUserVoted(long userId)
    {
    	UserTutorial uT = models.UserTutorial.find(
				"tutorialId=? and userId=?", id, userId).first();
    	
    	if (uT != null)
			return uT.interestLevel;
		else
			return 0;
    }
	
	
}
