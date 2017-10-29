package testingapp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name="gallery_picture")
public class GalleryPicture {

	@Id
	@GeneratedValue
	private Integer picture_id;
	
	private String picture_location_lat;
	
	private String picture_location_lon;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy") 
	private Date upload_date;
	
	@Column(name="picture_server_location")
	private String picture_server_location;
	
	@Transient
	private MultipartFile picture;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@Cascade({CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name="gallery_has_gallery_picture",
            joinColumns=@JoinColumn(name="picture_id"),
            inverseJoinColumns=@JoinColumn(name="gallery_id")
        )
	private Set<Gallery> galleries = new HashSet<Gallery>();
	
	//lajkovi od usera na jednu sliku
	@ManyToMany(fetch=FetchType.EAGER)
	@Cascade({CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name="user_liked_photos",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="gallery_picture_id")
        )
	private Set<User> pictureLikes = new HashSet<User>();
	
	//Constructors
	public GalleryPicture() {
		super();
	}
	
	public GalleryPicture(Integer picture_id, String picture_location_lat, String picture_location_lon,
			Date upload_date, String picture_server_location, MultipartFile picture, Set<Gallery> galleries) {
		super();
		this.picture_id = picture_id;
		this.picture_location_lat = picture_location_lat;
		this.picture_location_lon = picture_location_lon;
		this.upload_date = upload_date;
		this.picture_server_location = picture_server_location;
		this.picture = picture;
		this.galleries = galleries;
	}
	
	//Getters & Setters
	public Integer getPicture_id() {
		return picture_id;
	}
	public void setPicture_id(Integer picture_id) {
		this.picture_id = picture_id;
	}
	public Date getUpload_date() {
		return upload_date;
	}
	public void setUpload_date(Date upload_date) {
		this.upload_date = upload_date;
	}
	public Set<Gallery> getGalleries() {
		return galleries;
	}
	public void setGalleries(Set<Gallery> galleries) {
		this.galleries = galleries;
	}
	public String getPicture_server_location() {
		return picture_server_location;
	}
	public void setPicture_server_location(String picture_server_location) {
		this.picture_server_location = picture_server_location;
	}
	public MultipartFile getPicture() {
		return picture;
	}
	public void setPicture(MultipartFile picture) {
		this.picture = picture;
	}
	public String getPicture_location_lat() {
		return picture_location_lat;
	}
	public void setPicture_location_lat(String picture_location_lat) {
		this.picture_location_lat = picture_location_lat;
	}
	public String getPicture_location_lon() {
		return picture_location_lon;
	}
	public void setPicture_location_lon(String picture_location_lon) {
		this.picture_location_lon = picture_location_lon;
	}
	public Set<User> getPictureLikes() {
		return pictureLikes;
	}
	public void setPictureLikes(Set<User> pictureLikes) {
		this.pictureLikes = pictureLikes;
	}
}
