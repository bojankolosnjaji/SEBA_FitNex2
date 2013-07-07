package models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Entity
public class BMIHistory extends Model implements Serializable{
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Required
    public User user;
	
	@Required
	public Date date;
	
	@Required
	public float weight;
	@Required
	public float height;
	@Required
	public float BMIValue;

	public BMIHistory(User user, Date date, float weight, float height,
			float bMIValue) {
		super();
		this.user = user;
		this.date = date;
		this.weight = weight;
		this.height = height;
		BMIValue = bMIValue;
	}
	
	public static String getBMIHistoryChartData(User user)
	{
	    GsonBuilder gsonBuilder = new GsonBuilder();
	    Gson gson = gsonBuilder.registerTypeAdapter(BMIHistory.class, new BMIHistoryAdapter()).create();
	    String jsonHistory =  gson.toJson(user.BMIHistoryList);
	    return jsonHistory;
	}   

	
	
	

}
