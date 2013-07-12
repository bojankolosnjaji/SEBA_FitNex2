package models;

import java.text.SimpleDateFormat;
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
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar temp = Calendar.getInstance();
		temp.setTime(startDate);
		String retd="";
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(plan.endDate);
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		startCal.set(Calendar.DAY_OF_WEEK, 1);
		Calendar calendar;
		do {
			System.out.println("Day of week:" + startCal.get(Calendar.DAY_OF_WEEK));
			if (plan.chosenDays.contains(startCal.get(Calendar.DAY_OF_WEEK)))
			{
				System.out.println("Contained");
				calendar = (Calendar) startCal.clone();
				while (!calendar.after(endCalendar))// iterate through weeks
				{
					if (!calendar.before(startCalendar))
					{
						
						retd = ("{\n\t\t\t title: '" + plan.exercise.title + "',\n");
						retd += ("\t\t\t id: " + plan.id + ",\n");
						retd += ("\t\t\t start: new Date(" + calendar.get(Calendar.YEAR) + ", " + (calendar.get(Calendar.MONTH)) + ", " + (calendar.get(Calendar.DATE)+1) + "),\n");
						retd += ("\t\t\t end: new Date(" + calendar.get(Calendar.YEAR) + ", " + (calendar.get(Calendar.MONTH)) + ", " + (calendar.get(Calendar.DATE)+1) + "),\n");	
						retd += ("\t\t\t allDay: true\n");
						retd += "\t\t\t},";
						System.out.println(retd);
						ret+=retd;
					}
					calendar.add(Calendar.DAY_OF_YEAR,7);

				}
				ret.substring(0, ret.length()-2);
			}
			else
			{
				System.out.println("Not contained");
			}
			startCal.add(Calendar.DAY_OF_WEEK, 1);
		} while (startCal.get(Calendar.DAY_OF_WEEK)!=1);
		return ret;
	}
	
	String isChecked(int dayId)
	{
		if (chosenDays.contains(dayId))
		{
			return "checked";
					
		}
		else
			return "";
		
	}
	
	public String startDateString()
	{
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		return format.format(startDate);
	}
	
	
	public String endDateString()
	{
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		return format.format(endDate);
	}
	




}
