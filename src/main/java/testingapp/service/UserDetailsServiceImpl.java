package testingapp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import testingapp.dao.UserDetailsDAO;
import testingapp.model.UserDetails;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserDetailsDAO userDetailsDAO;	

	public void addUserDetails(UserDetails userDetails) {
		userDetailsDAO.addUserDetails(userDetails);
	}

	public void updateUserDetails(UserDetails userDetails) {
		userDetailsDAO.updateUserDetails(userDetails);
	}

	public UserDetails getUserDetails(Integer userDetails_id) {
		return userDetailsDAO.getUserDetails(userDetails_id);		
	}
	
	public void deleteUserDetails(Integer userDetails_id) {	
		userDetailsDAO.deleteUserDetails(userDetails_id);
	}

	@Override
	public List<UserDetails> getUserDetailss() {
		return userDetailsDAO.getUserDetailss();
	}
}
