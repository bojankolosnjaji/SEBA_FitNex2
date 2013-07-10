package models;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.GenericModel;
 
@Entity
public class User extends GenericModel implements Serializable{
	@Id @GeneratedValue (strategy = GenerationType.SEQUENCE) 
	public Long id; 
	
	@Unique
	@Required
    public String email;
	
	@Required
    public String password;
	
	@Required
    public String firstName;
	@Required
    public String lastName;
	@Required
    public Date dateOfBirth;
	@Required
    public Gender gender;
    public double weight;
    public double height;
    public Date joinDate;
    
    @OneToMany(mappedBy="user")
    public List<ArticleComment> articleComments;
    
    @OneToMany(mappedBy="user")
    public List<TutorialComment> tutorialComments;
    
    @OneToMany(mappedBy="user")
    public List<ExerciseComment> exerciseComments;
    
    @OneToMany(mappedBy="user")
    public List<UserExercisePreference> userExercisePreferences;
    
    @OneToMany(mappedBy="user")
    public List<BMIHistory> BMIHistoryList;
    
    @OneToMany(mappedBy="user")
    public List<WorkoutPlan> workoutPlans;
    
    @OneToOne
    @JoinColumn(name="address_id", referencedColumnName="id") 
    public Address address;
    public String phone;
    public String mobile;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String email, String password, String firstName,
			String lastName, Date dateOfBirth, Gender gender, double weight,
			Address address, String phone, String mobile) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.weight = weight;
		this.joinDate = new Date();
		this.articleComments = new ArrayList<ArticleComment>();
		this.tutorialComments = new ArrayList<TutorialComment>();
		this.exerciseComments = new ArrayList<ExerciseComment>();
		//this.userExercisePreferences = new ArrayList<UserExercisePreference>();
		this.BMIHistoryList= new ArrayList<BMIHistory>();
		this.workoutPlans = new ArrayList<WorkoutPlan>();
		this.address = address;
		this.phone = phone;
		this.mobile = mobile;	
		
		System.out.println(email  + "" + password + " " + firstName + " " + lastName + "" + dateOfBirth + " " + gender + " "  + weight + joinDate);
		
	}
	
	public String convertToString()
	{
		return id+";"+email+";"+firstName+";"+lastName+";"+weight;
	}
	
	public static User convertToUser(String format)
	{
		User user = null;
		
		if (format != null) {
			String[] str = format.split(";");
			
			if (str.length == 5) {
				user = new User();
				user.id = Long.valueOf(str[0]);
				user.email = str[1];
				user.firstName = str[2];
				user.lastName = str[3];
				user.weight = Double.valueOf(str[4]);

			}
		}
		
		return user;
	}
	
	public String getBMIChartData()
	{		
		boolean first = true;
		Calendar calendar = Calendar.getInstance();          
		String ret = "";
		for (BMIHistory history: BMIHistoryList)
		{
			calendar.setTime(history.date);
			if (first)
			{
				ret += "[" ;
				first = false;
			}
			else
			{
				ret += "],";
			}
			ret += ("[Date.UTC(" + calendar.get(Calendar.YEAR) + "," + calendar.get(Calendar.MONTH) + "," + calendar.get(Calendar.DATE) + ")," + history.BMIValue);
		}
		ret += "]]";
		return ret;
	}
	
	public String toString() {
	    return email;
	}
	
}