package testingapp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;



@Entity
@Table(name="gallery")
public class Gallery {

	@Id
	@GeneratedValue
	private Integer gallery_id;
	@Size(min=3, max=20)
	private String gallery_name;
	
	private String gallery_description;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	@Cascade({CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private User user;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@Cascade({CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name="gallery_has_gallery_picture",
            joinColumns=@JoinColumn(name="gallery_id"),
            inverseJoinColumns=@JoinColumn(name="picture_id")
        )
	private Set<GalleryPicture> galleryPictures = new HashSet<GalleryPicture>();
	
	//Constructors
	public Gallery() {
		super();
	}
	
	public Gallery(Integer gallery_id, String gallery_name, User user, Set<GalleryPicture> galleryPictures, String gallery_description) {
		super();
		this.gallery_id = gallery_id;
		this.gallery_name = gallery_name;
		this.user = user;
		this.galleryPictures = galleryPictures;
		this.gallery_description = gallery_description;
	}
	
	//Getters & Setters
	public Integer getGallery_id() {
		return gallery_id;
	}
	public void setGallery_id(Integer gallery_id) {
		this.gallery_id = gallery_id;
	}
	public String getGallery_name() {
		return gallery_name;
	}
	public void setGallery_name(String gallery_name) {
		this.gallery_name = gallery_name;
	}
	public String getGallery_description() {
		return gallery_description;
	}
	public void setGallery_description(String gallery_description) {
		this.gallery_description = gallery_description;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Set<GalleryPicture> getGalleryPictures() {
		return galleryPictures;
	}
	public void setGalleryPictures(Set<GalleryPicture> galleryPictures) {
		this.galleryPictures = galleryPictures;
	}
}
