package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class UserExercisePreference extends Model{
	
	
	@ManyToOne(fetch=FetchType.LAZY)	
	public Exercise exercise;
	
	@ManyToOne(fetch=FetchType.LAZY)
	public User user;
	
	public int interestLevel;
	
//	@ManyToMany
//	  @JoinTable(
//	      name="UserExcercisePreference_RELATION",
//	      joinColumns={@JoinColumn(name="ART_RELATEE_ID", referencedColumnName="ID")},
//	      inverseJoinColumns={@JoinColumn(name="ART_RELATER_ID", referencedColumnName="ID")})
//    public List<Article> relatedArticles;
    
	
}
