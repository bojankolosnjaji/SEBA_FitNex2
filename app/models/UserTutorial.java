package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.JPABase;
import play.db.jpa.Model;

@Entity
public class UserTutorial extends Model {

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	public User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tutorialId")
	public Tutorial tutorial;

	

	public int interestLevel;

	public UserTutorial(Tutorial tutorial, User user,
			int interestLevel) {
		super();
		this.tutorial = tutorial;
		this.user = user;
		this.interestLevel = interestLevel;

	}
	
	public int doesExist() {
		UserTutorial uep = models.UserTutorial.find(
				"tutorialId=? and userId=?", tutorial.id, user.id).first();
		if (uep != null)
			return uep.interestLevel;
		else
			return 0;
	}

	public int deleteUserTutorial() {

		int NoEntitiesDeleted = UserTutorial.delete(
				"tutorialId=? and userId=?", tutorial.id, user.id);
		return NoEntitiesDeleted;
	}

	public <UserTutorial extends JPABase> UserTutorial add() {

		if (doesExist()==0)
			return super.save();
		return null;
	}

	// @ManyToMany
	// @JoinTable(
	// name="UserExcercisePreference_RELATION",
	// joinColumns={@JoinColumn(name="ART_RELATEE_ID",
	// referencedColumnName="ID")},
	// inverseJoinColumns={@JoinColumn(name="ART_RELATER_ID",
	// referencedColumnName="ID")})
	// public List<Article> relatedArticles;

}
