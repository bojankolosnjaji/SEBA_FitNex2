package models;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Exercise extends Model{
	@Required
	public String title;
	@Required
	@Lob
	public String content;

	public String image;
	public Date date;
	public int numberOfVotes;
	public double rank;
	
	@OneToMany(mappedBy="exercise")
	public List<ExerciseRelatedCategory> category;	
	
    
    @OneToMany(mappedBy="exercise")
    public List<ExerciseComment> comments;
    
    @OneToMany(mappedBy="exercise")
    public List<UserExercisePreference> userExercisePreferences;
    
    @OneToMany(mappedBy="exercise")
    public List<WorkoutPlan> workoutPlans;
    
    public ExerciseLevel level;
    
    int daysPerWeek;
    
    int timePerDay; 
    
    public Exercise(String title, String content, String image, Date date,
    		ExerciseCategory category, ExerciseLevel level, int daysPerWeek, int timePerDay) {
		super();
		this.title = title;
		this.content = content;
		this.image = image;
		this.date = date;
		//this.category = category;
		this.level = level;
		this.daysPerWeek = daysPerWeek;
		this.timePerDay = timePerDay;
		
	}
    
    public int haveUserVoted(long userId)
    {
    	UserExercisePreference uep = models.UserExercisePreference.find(
				"exerciseId=? and userId=?", id, userId).first();
    	
    	if (uep != null)
			return uep.interestLevel;
		else
			return 0;
    }
    
    public  TreeSet<Category> getRelatedCategories()
    {
    	TreeSet <models.Category> exerCategories=new TreeSet <models.Category>();
    	for (ExerciseRelatedCategory exRelatedCat : category) {
    		exerCategories.add(exRelatedCat.category);
		}
    	
    	return exerCategories;
    }
    public  LinkedHashSet<RelatedEquipment> getRelatedEquipments()
    {
    	TreeSet <models.Category> exerCategories=getRelatedCategories();
    	
    	LinkedHashSet<RelatedEquipment> relEquip=new LinkedHashSet<RelatedEquipment>();
		for (Category category : exerCategories) {
			System.out.println("Cat "+category.name);
			relEquip.addAll(category.relatedEquipment);
		}
		
		return relEquip;
    	
    }
	
}
