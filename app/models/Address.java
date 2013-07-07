package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
 
import play.db.jpa.Model;

@Entity
public class Address extends Model{
	
	 
	public String street;
	public String number;
	public String city;
	public String province;
	public String country;
	
	@OneToOne(mappedBy="address", cascade=CascadeType.ALL)
    public User user;
	
	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Address(String street, String number, String city, String province,
			String country) {
		super();
		this.street = street;
		this.number = number;
		this.city = city;
		this.province = province;
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	
	
	

}
