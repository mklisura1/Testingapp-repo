package testingapp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="location")
public class Location {
	
	@Id
	@GeneratedValue
	private Integer location_id;
	
	@Column(name="lattitude")
	private String latitude;
	
	private String longitude;
	
	private Date date_time;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	@Cascade({CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private User user;

	//Constructors
	public Location() {
		super();
	}
	
	public Location(Integer location_id, String latitude, String longitude, Date date_time, User user) {
		super();
		this.location_id = location_id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.date_time = date_time;
		this.user = user;
	}
	
	public Location(String latitude, String longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	//Getters & Setters
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public Integer getLocation_id() {
		return location_id;
	}
	public void setLocation_id(Integer location_id) {
		this.location_id = location_id;
	}
	public Date getDate_time() {
		return date_time;
	}
	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
