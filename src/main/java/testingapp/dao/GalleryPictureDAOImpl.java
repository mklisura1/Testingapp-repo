package testingapp.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import testingapp.model.GalleryPicture;

@Repository
public class GalleryPictureDAOImpl implements GalleryPictureDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public void addGalleryPicture(GalleryPicture galleryPicture) {
		getCurrentSession().save(galleryPicture);
	}


	public void updateGalleryPicture(GalleryPicture galleryPicture) {
		getCurrentSession().update(galleryPicture);
	}

	public GalleryPicture getGalleryPicture(Integer galleryPicture_id) {
		try {
			GalleryPicture u = (GalleryPicture) getCurrentSession().get(GalleryPicture.class, galleryPicture_id);

			return u;
		} catch (Exception e) {
			System.out.println("Error GalleryPictureDAOImpl : " + e.toString());
			return null;
		}
	}

	public void deleteGalleryPicture(Integer galleryPicture_id) {
		try {
			GalleryPicture galleryPicture = getGalleryPicture(galleryPicture_id);
			if (galleryPicture != null) {
				getCurrentSession().delete(galleryPicture);
			}
		} catch (Exception e) {
			System.out.println("Error GalleryPictureDAOImpl delete: " + e.toString());
		}
	}

	@SuppressWarnings("unchecked")
	public List<GalleryPicture> getGalleryPictures() {
		return getCurrentSession().createCriteria(GalleryPicture.class).list();
	}
}
