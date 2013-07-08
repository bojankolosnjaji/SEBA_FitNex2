package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Exercise;
import models.User;
import play.mvc.Controller;

public class WorkoutPlan extends Controller{
	
	public static void workoutplan()
	{
		//String email = Security.connected();
		User signedUser=User.convertToUser(Security.session.get("user"));
		String email=null;
		if(signedUser!=null)
			email=signedUser.email;
			
		System.out.println("Looking for email... " + email);
		User user = (User) User.find("byEmail", email).first();
		if (user==null)
		{
			Application.index(null);
		}
		else
		{
			String strPlan="";
			// load all dates
			for (models.WorkoutPlan plan: user.workoutPlans)
			{
				strPlan += models.WorkoutPlan.getPlanData(plan);
				strPlan += ",";
				
			}
			strPlan = strPlan.substring(0, strPlan.length()-2);		
			
			renderTemplate("Application/workout_plan.html", strPlan);
		}
		
	}
	
	public static void addToWorkoutPlan(long id)
	{
		Exercise exercise = Exercise.findById(id);
		renderTemplate("Application/add_workout_plan.html", exercise );
	}
	
	public static void addToWorkoutPlanForm(String txtStartDate, String txtEndDate, List<String> day, long id)
	{	
		User signedUser=User.convertToUser(Security.session.get("user"));
		Exercise exercise = Exercise.findById(id); 
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date startDate;
		try {
			startDate = format.parse(txtStartDate);
		
		Date endDate = format.parse(txtEndDate);
		System.out.println("Starting date:" + startDate);
		System.out.println("End date:" + endDate);		
		List<Integer> days = new ArrayList<Integer>();
		for (String s: day)
		{
			System.out.println("Value:" + s);
			days.add(Integer.parseInt(s));
			
		}
		
		
		models.WorkoutPlan workoutplan = new models.WorkoutPlan(exercise, signedUser, startDate, endDate, days);
		workoutplan.save();
		
		renderTemplate("Application/workout_plan.html");
		
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	
}