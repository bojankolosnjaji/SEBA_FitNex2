package models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class ExerciseRelatedCategory extends Model {
	
	@ManyToOne(fetch=FetchType.LAZY)	
	public Exercise exercise;
	
	@ManyToOne(fetch=FetchType.LAZY)
	public Category category;
	
	public ExerciseRelatedCategory(Exercise exercise, Category category) {
		super();
		this.exercise = exercise;
		this.category = category;
		
	}
	

}
