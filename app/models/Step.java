package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Step extends Model{
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "tutorial_id")
	public Tutorial tutorial;
	
	public int stepOrder;
	public String title;
	public String content;
	public String image;
	
	public Step(Tutorial tutorial, int stepOrder,String title, String content, String image) {
		super();
		this.tutorial = tutorial;
		this.stepOrder = stepOrder;
		this.title = title;
		this.content = content;
		this.image = image;

	}

}
