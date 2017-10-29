package testingapp.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import testingapp.dao.UserDAO;
import testingapp.model.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;	

	public void addUser(User user) {
		userDAO.addUser(user);
	}

	public void updateUser(User user) {
		userDAO.updateUser(user);
	}

	public User getUser(Integer user_id) {
		return userDAO.getUser(user_id);		
	}
	
	public void deleteUser(Integer user_id) {	
		userDAO.deleteUser(user_id);
	}

	@Override
	public List<User> getUsers() {
		return userDAO.getUsers();
	}

	@Override
	public User getUserByUsername(String user_name) {
		return userDAO.getUserByUsername(user_name);
	}

	@Override
	public User getUserByUsernameRegistration(String user_name) {
		return userDAO.getUserByUsernameRegistration(user_name);
	}
	
	// Dio za enkripciju passworda
	@Override
	public String getSalt() throws NoSuchAlgorithmException {
		return userDAO.getSalt();
	}

	@Override
	public String generatePasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return userDAO.generatePasswordHash(password);
	}

	@Override
	public String toHex(byte[] array) throws NoSuchAlgorithmException {
		return userDAO.toHex(array);
	}

	@Override
	public boolean validatePassword(String inputPassword, String hashedPassword)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		return userDAO.validatePassword(inputPassword, hashedPassword);
	}

	@Override
	public byte[] fromHex(String hex) throws NoSuchAlgorithmException {
		return userDAO.fromHex(hex);
	}
	
}
