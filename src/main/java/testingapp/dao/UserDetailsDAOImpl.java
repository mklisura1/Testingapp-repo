package testingapp.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import testingapp.model.UserDetails;

@Repository
public class UserDetailsDAOImpl implements UserDetailsDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public void addUserDetails(UserDetails userDetails) {
		getCurrentSession().save(userDetails);
	}


	public void updateUserDetails(UserDetails userDetails) {
		getCurrentSession().update(userDetails);
	}

	public UserDetails getUserDetails(Integer userDetails_id) {
		try {
			UserDetails u = (UserDetails) getCurrentSession().get(UserDetails.class, userDetails_id);

			return u;
		} catch (Exception e) {
			System.out.println("Error UserDetailsDAOImpl : " + e.toString());
			return null;
		}
	}

	public void deleteUserDetails(Integer userDetails_id) {
		try {
			UserDetails userDetails = getUserDetails(userDetails_id);
			if (userDetails != null) {
				getCurrentSession().delete(userDetails);
			}
		} catch (Exception e) {
			System.out.println("Error UserDetailsDAOImpl delete: " + e.toString());
		}
	}

	@SuppressWarnings("unchecked")
	public List<UserDetails> getUserDetailss() {
		return getCurrentSession().createCriteria(UserDetails.class).list();
	}
}
