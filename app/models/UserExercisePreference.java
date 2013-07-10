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
public class UserExercisePreference extends Model {

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	public User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exerciseId")
	public Exercise exercise;

	

	public int interestLevel;

	public UserExercisePreference(Exercise exercise, User user,
			int interestLevel) {
		super();
		this.exercise = exercise;
		this.user = user;
		this.interestLevel = interestLevel;

	}
	
	public int doesExist() {
		UserExercisePreference uep = models.UserExercisePreference.find(
				"exerciseId=? and userId=?", exercise.id, user.id).first();
		if (uep != null)
			return uep.interestLevel;
		else
			return 0;
	}

	public int deleteUserExercisePreference() {

		int NoEntitiesDeleted = UserExercisePreference.delete(
				"exerciseId=? and userId=?", exercise.id, user.id);
		return NoEntitiesDeleted;
	}

	public <UserExercisePreference extends JPABase> UserExercisePreference add() {

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
