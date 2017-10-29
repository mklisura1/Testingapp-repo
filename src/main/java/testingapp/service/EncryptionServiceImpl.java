package testingapp.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import testingapp.dao.UserDAO;

@Service
@Transactional
public class EncryptionServiceImpl implements EncryptionService{

	@Autowired
	private UserDAO userDAO;
	
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
