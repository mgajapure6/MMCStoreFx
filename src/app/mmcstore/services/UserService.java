package app.mmcstore.services;

import java.util.List;

import javax.persistence.Persistence;

import app.mmcstore.dao.AccountDao;
import app.mmcstore.dao.CustomerDao;
import app.mmcstore.dao.ProviderDao;
import app.mmcstore.dao.UserDao;
import app.mmcstore.entity.Account;
import app.mmcstore.entity.Customer;
import app.mmcstore.entity.Provider;
import app.mmcstore.entity.User;

public class UserService {

	private CustomerDao customerDao;
	private ProviderDao providerDao;
	private UserDao userDao;
	private AccountDao accountDao;

	public UserService() {
		try {
			customerDao = new CustomerDao(Persistence.createEntityManagerFactory("MMCStore"));
			providerDao = new ProviderDao(Persistence.createEntityManagerFactory("MMCStore"));
			userDao = new UserDao(Persistence.createEntityManagerFactory("MMCStore"));
			accountDao = new AccountDao(Persistence.createEntityManagerFactory("MMCStore"));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public Boolean saveUser(User user, Customer customer, Provider provider) {

		if (customer != null) {
			try {
				
				customerDao.create(customer);
				Account acc = new Account();
				acc.setSum(0.00);
				acc.setCustomer(customer);
				accountDao.create(acc);
				
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
			userDao.create(user);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean updateCustomerAccount(Double sum, Integer custId) {
		try {
			//account.setCustomer(customer);
			String sql = "update account set sum = "+sum+" WHERE customerId="+custId; 
			return accountDao.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	

	public boolean updateUser(User user, Customer customer, Provider provider) {
		if (customer != null) {
			try {
				customerDao.update(customer);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			user.setCustomer(customer);
			user.setProvider(null);
		}

		if (provider != null) {
			try {
				providerDao.update(provider);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			user.setCustomer(null);
			user.setProvider(provider);
		}

		try {
			userDao.update(user);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}


	public User getByUsernameAndPassword(String username, String password) {
		User user = null;
		try {
			user = (User) userDao.findUserByUsernameAndPassword(username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public User getByUsername(String userN) {
		User user = null;
		try {
			user = (User) userDao.getByUsername(userN);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("getByUsername user::::" + user);
		return user;
	}
	
	public Double getCustomerAccountBalance(Integer cid) {
		Double amt = 0.0;
		String sql = "SELECT sum from account WHERE CUSTOMERID = "+cid;
		try {
			List l = accountDao.executeQuery(sql);
			System.out.println("amt::"+l);
			amt = (Double) l.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return amt;
	}

}
