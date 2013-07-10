package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Category extends Model implements Comparable<Category>{
	
    public String name;
	public String image;
	
	@OneToMany(mappedBy="category")
	public List<ExerciseRelatedCategory> exerciseRelatedCategory;
	
	@OneToMany(mappedBy="category")
	public List<RelatedEquipment> relatedEquipment;	
	
	public Category(String name, String image) {
		super();
		this.name = name;
		this.image = image;
		
	}
	
	public double getRank()
	{
		double Rank=0;
		for (ExerciseRelatedCategory exRelCat : exerciseRelatedCategory) {
			Rank+=exRelCat.exercise.rank;
		}
		return Rank/exerciseRelatedCategory.size();
		
	}

	@Override
	public int compareTo(Category o) {
		
		double rank1=getRank();
		double rank2=o.getRank();
		
		
        if(rank1>rank2)
            return -1;
        else if(rank1<rank2)
            return +1;
        else
            return 0;
	}
}
