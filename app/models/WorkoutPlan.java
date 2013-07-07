package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class WorkoutPlan extends Model{
	
	@ManyToOne(fetch=FetchType.LAZY)	
	public Exercise exercise;
	
	@ManyToOne(fetch=FetchType.LAZY)
	public User user;

	public Date startDate;
	
	public Date endDate;
	
	
	public WorkoutPlan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WorkoutPlan(User user, Exercise exercise, Date startDate,
			Date endDate) {
		super();
		this.user = user;
		this.exercise = exercise;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	
	
	
}
