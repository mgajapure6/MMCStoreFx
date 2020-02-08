package app.mmcstore.services;

import javax.persistence.Persistence;

import app.mmcstore.dao.CustomerDao;
import app.mmcstore.dao.ProviderDao;
import app.mmcstore.dao.UserDao;
import app.mmcstore.entity.Customer;
import app.mmcstore.entity.Provider;
import app.mmcstore.entity.User;

public class UserService {

	private CustomerDao customerDao;
	private ProviderDao providerDao;
	private UserDao userDao1;

	public UserService() {
		try {
			customerDao = new CustomerDao(Persistence.createEntityManagerFactory("MMCStore"));
			providerDao = new ProviderDao(Persistence.createEntityManagerFactory("MMCStore"));
			userDao1 = new UserDao(Persistence.createEntityManagerFactory("MMCStore"));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public Boolean saveUser(User user, Customer customer, Provider provider) {

		if (customer != null) {
			try {
				customerDao.create(customer);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			user.setCustomer(customer);
			user.setProvider(null);
		}

		if (provider != null) {
			try {
				providerDao.create(provider);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			user.setCustomer(null);
			user.setProvider(provider);
		}

		try {
			userDao1.create(user);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

//	public User getByUsername(String username) {
//		HashMap<String, Object> properties = new HashMap<>();
//		properties.put("userName", username);
//		User user = null;
//		try {
//			user = (User) userDao1.findByPropertiesIfEquals(properties);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("getByUsername user::::" + user);
//		return user;
//	}

	public User getByUsernameAndPassword(String username, String password) {
		User user = null;
		try {
			user = (User) userDao1.findUserByUsernameAndPassword(username,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("getByUsernameAndPassword user::::" + user);
		return user;
	}

	public User getByUsername(String userN) {
		User user = null;
		try {
			user = (User) userDao1.getByUsername(userN);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("getByUsername user::::" + user);
		return user;
	}

	
}
