package models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
	
	@Column
    @ElementCollection(targetClass=Integer.class)
	public List<Integer> chosenDays;
	
	
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
		this.chosenDays = new ArrayList<Integer>();
	}

	public WorkoutPlan(Exercise exercise, User user, Date startDate,
			Date endDate, List<Integer> chosenDays) {
		super();
		this.exercise = exercise;
		this.user = user;
		this.startDate = startDate;
		this.endDate = endDate;
		this.chosenDays = chosenDays;
	}
	
	
	public static String getPlanData(WorkoutPlan plan)
	{
		String ret="";
		Date startDate = plan.startDate;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		String retd="";
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(plan.endDate);
		while (!calendar.after(endCalendar))
		{
		
		retd = ("{\n title: '" + plan.exercise.title + "',\n");
		retd += (" start: new Date(" + calendar.get(Calendar.YEAR) + ", " + (calendar.get(Calendar.MONTH)+1) + ", " + calendar.get(Calendar.DATE) + ")\n},");
		calendar.add(Calendar.DAY_OF_YEAR,7);
		System.out.println(retd);
		ret+=retd;
		}
		ret.substring(0, ret.length()-2);
		return ret;
	}
	
	
	
	
}
