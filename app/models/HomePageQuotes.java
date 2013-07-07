package models;

import javax.persistence.Entity;
import javax.persistence.Lob;

import play.db.jpa.Model;

@Entity
public class HomePageQuotes extends Model{
	
	public String content;
	public String person;
	
	public String image;
	
	

}
