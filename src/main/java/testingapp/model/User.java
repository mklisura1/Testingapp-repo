package testingapp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue
	private Integer user_id;
	private String user_name;	
	private String user_password_salt;
	private String user_password_hash;	
	private String mobile_phone;
	private Date start_date;
	private String email;
	private Date last_login;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_details_id")
	@Cascade({CascadeType.SAVE_UPDATE})
	private UserDetails userDetails;
	
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	@Cascade({CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private Set<Gallery> galleries = new HashSet<Gallery>();
	
	
	@ManyToMany(fetch=FetchType.EAGER)
	@Cascade({CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name="user_has_friends", 
	                joinColumns={@JoinColumn(name="friend_id")}, 
	                inverseJoinColumns={@JoinColumn(name="user_id")})
	private Set<User> friends_to = new HashSet<User>();

	@ManyToMany(fetch=FetchType.EAGER)
	@Cascade({CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name="user_has_friends", 
	                joinColumns={@JoinColumn(name="user_id")}, 
	                inverseJoinColumns={@JoinColumn(name="friend_id")})
	private Set<User> friends = new HashSet<User>();
	
	//lajkane slike od jednog korisnika
	@ManyToMany(fetch=FetchType.EAGER)
	@Cascade({CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name="user_liked_photos",
            joinColumns=@JoinColumn(name="gallery_picture_id"),
            inverseJoinColumns=@JoinColumn(name="user_id")
        )
	private Set<GalleryPicture> likedPhotos = new HashSet<GalleryPicture>();
	
	@OneToMany(mappedBy = "user", fetch=FetchType.EAGER) 
	@Cascade({CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.SAVE_UPDATE})
	private Set<Location> locations = new HashSet<Location>();
	
	
	/*
	
	@ManyToMany(cascade={javax.persistence.CascadeType.ALL})
	@JoinTable(name="user_has_friends",
		joinColumns={@JoinColumn(name="user_id")},
		inverseJoinColumns={@JoinColumn(name="friend_id")})
	private Set<User> friends = new HashSet<User>();

	@ManyToMany(mappedBy="friends")
	private Set<User> users = new HashSet<User>();
	*/
	
	//Constructors
	public User() {
		super();
	}
	
	public User(String user_name, String user_password_salt, String user_password_hash) {
		super();
		this.user_name = user_name;
		this.user_password_salt = user_password_salt;
		this.user_password_hash = user_password_hash;
	}
	
	public User(Integer user_id, String user_name, String user_password_salt, String user_password_hash,
			String mobile_phone, Date start_date, String email, Date last_login, UserDetails userDetails, Set<Location> locations) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_password_salt = user_password_salt;
		this.user_password_hash = user_password_hash;
		this.mobile_phone = mobile_phone;
		this.start_date = start_date;
		this.email = email;
		this.last_login = last_login;
		this.userDetails = userDetails;
		this.locations = locations;
	}
	
	//Getters & Setters
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_password_salt() {
		return user_password_salt;
	}
	public void setUser_password_salt(String user_password_salt) {
		this.user_password_salt = user_password_salt;
	}
	public String getUser_password_hash() {
		return user_password_hash;
	}
	public void setUser_password_hash(String user_password_hash) {
		this.user_password_hash = user_password_hash;
	}
	public String getMobile_phone() {
		return mobile_phone;
	}
	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getLast_login() {
		return last_login;
	}
	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}
	public UserDetails getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	public Set<Gallery> getGalleries() {
		return galleries;
	}
	public void setGalleries(Set<Gallery> galleries) {
		this.galleries = galleries;
	}
	public Set<User> getFriends_to() {
		return friends_to;
	}
	public void setFriends_to(Set<User> friends_to) {
		this.friends_to = friends_to;
	}
	public Set<User> getFriends() {
		return friends;
	}
	public void setFriends(Set<User> friends) {
		this.friends = friends;
	}
	public Set<GalleryPicture> getLikedPhotos() {
		return likedPhotos;
	}
	public void setLikedPhotos(Set<GalleryPicture> likedPhotos) {
		this.likedPhotos = likedPhotos;
	}
	public Set<Location> getLocations() {
		return locations;
	}
	public void setLocations(Set<Location> locations) {
		this.locations = locations;
	}
}
