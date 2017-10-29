package testingapp.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="user_details")
public class UserDetails {

	@Id
	@GeneratedValue
	private Integer user_details_id;
	@Size(min=3, max=20)
	private String first_name;	
	@Size(min=3, max=20)
	private String last_name;
	@DateTimeFormat(pattern = "dd/MM/yyyy") 
	private Date birth_date;
	@Email @NotEmpty
	private String email;
	private String mobile_phone;
	private String country;
	private Integer age;
	@Size(min=3, max=20)
	private String address;
	
	//Constructors
	public UserDetails() {
		super();
	}

	public UserDetails(Integer user_details_id, String first_name, String last_name, Date birth_date, String email,
			String mobile_phone, String country, Integer age, String address) {
		super();
		this.user_details_id = user_details_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.birth_date = birth_date;
		this.email = email;
		this.mobile_phone = mobile_phone;
		this.country = country;
		this.age = age;
		this.address = address;
	}


	//Getters & Setters
	public Integer getUser_details_id() {
		return user_details_id;
	}
	public void setUser_details_id(Integer user_details_id) {
		this.user_details_id = user_details_id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public Date getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile_phone() {
		return mobile_phone;
	}
	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
