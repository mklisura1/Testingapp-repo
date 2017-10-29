package testingapp.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import testingapp.model.Location;

@Repository
public class LocationDAOImpl implements LocationDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public void addLocation(Location location) {
		getCurrentSession().save(location);
	}


	public void updateLocation(Location location) {
		getCurrentSession().update(location);
	}

	public Location getLocation(Integer location_id) {
		try {
			Location u = (Location) getCurrentSession().get(Location.class, location_id);

			return u;
		} catch (Exception e) {
			System.out.println("Error LocationDAOImpl : " + e.toString());
			return null;
		}
	}

	public void deleteLocation(Integer location_id) {
		try {
			Location location = getLocation(location_id);
			if (location != null) {
				getCurrentSession().delete(location);
			}
		} catch (Exception e) {
			System.out.println("Error LocationDAOImpl delete: " + e.toString());
		}
	}

	@SuppressWarnings("unchecked")
	public List<Location> getLocations() {
		return getCurrentSession().createCriteria(Location.class).list();
	}
}
