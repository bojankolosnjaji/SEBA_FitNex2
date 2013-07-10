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
	public Category category;
	
    public String name;
	public String image;
	public String URl;	
	
	
	public RelatedEquipment(Category category, String name,String image,String URl) {
		super();
		this.category = category;
		this.name = name;
		this.image = image;
		this.URl = URl;
		
	}

}
