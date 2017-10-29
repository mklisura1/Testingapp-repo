package testingapp.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import testingapp.model.Gallery;

@Repository
public class GalleryDAOImpl implements GalleryDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public void addGallery(Gallery gallery) {
		getCurrentSession().save(gallery);
	}


	public void updateGallery(Gallery gallery) {
		getCurrentSession().update(gallery);
	}

	public Gallery getGallery(Integer gallery_id) {
		try {
			Gallery u = (Gallery) getCurrentSession().get(Gallery.class, gallery_id);

			return u;
		} catch (Exception e) {
			System.out.println("Error GalleryDAOImpl : " + e.toString());
			return null;
		}
	}

	public void deleteGallery(Integer gallery_id) {
		try {
			Gallery gallery = getGallery(gallery_id);
			if (gallery != null) {
				getCurrentSession().delete(gallery);
			}
		} catch (Exception e) {
			System.out.println("Error GalleryDAOImpl delete: " + e.toString());
		}
	}

	@SuppressWarnings("unchecked")
	public List<Gallery> getGalleries() {
		return getCurrentSession().createCriteria(Gallery.class).list();
	}
}
