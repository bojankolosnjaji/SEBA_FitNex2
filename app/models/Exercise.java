package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Exercise extends Model{
	@Required
	String title;
	@Required
	public String content;

	public String image;
	public Date date;
	public int numberOfVotes;
	public double rank;
	
	public ExerciseCategory category;	
	
	public ExerciseLevel level;
    
    @OneToMany(mappedBy="exercise")
    public List<ExerciseComment> comments;
    
    @OneToMany(mappedBy="exercise")
    public List<UserExercisePreference> userPreferences;
    
    @OneToMany(mappedBy="exercise")
    public List<WorkoutPlan> workoutPlans;
    
    int daysPerWeek;
    
    int timePerDay; 
    
    public Exercise(String title, String content, String image, Date date,
    		ExerciseCategory category, ExerciseLevel level, int daysPerWeek, int timePerDay) {
		super();
		this.title = title;
		this.content = content;
		this.image = image;
		this.date = date;
		this.category = category;
		this.level = level;
		this.daysPerWeek = daysPerWeek;
		this.timePerDay = timePerDay;
		
	}
	
}
