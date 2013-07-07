package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class RelatedEquipment extends Model{
	
	
	@ManyToOne(fetch=FetchType.LAZY)	
	public Exercise excercise;
	
    public String name;
	public String image;
	public String URl;	

}
