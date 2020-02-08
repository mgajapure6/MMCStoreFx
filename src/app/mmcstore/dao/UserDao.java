package app.mmcstore.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import app.mmcstore.entity.User;

public class UserDao extends BasicDAO<User> {

	private EntityManagerFactory factory;

	public UserDao(EntityManagerFactory factory) {
		super(User.class);
		this.factory = factory;
	}

	@Override
	public EntityManager getEntityManager() {
		try {
			return factory.createEntityManager();
		} catch (Exception ex) {
			System.out.println("The entity manager cannot be created!");
			return null;

		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public User findUserByUsernameAndPassword(String username, String pssword) throws Exception {
		EntityManager em = getEntityManager();
		User user = null;
		try {
			Metamodel m = em.getMetamodel();
			EntityType<User> User_ = m.entity(User.class);
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery cq = cb.createQuery();
			Root<User> root = cq.from(User_);
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(cb.equal(root.get("userName"), username));
			predicates.add(cb.equal(root.get("userPassword"), pssword));
			cq.select(root).where(predicates.toArray(new Predicate[] {}));
			user = (User) em.createQuery(cq).getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
		return user;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public User getByUsername(String userName) {
		EntityManager em = getEntityManager();
		User user = null;
		try {
			Metamodel m = em.getMetamodel();
			EntityType<User> User_ = m.entity(User.class);
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery cq = cb.createQuery();
			Root<User> root = cq.from(User_);
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(cb.equal(root.get("userName"), userName));
			cq.select(root).where(predicates.toArray(new Predicate[] {}));
			user = (User) em.createQuery(cq).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
		return user;
	}
}
