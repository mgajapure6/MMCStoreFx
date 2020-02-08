
package app.mmcstore.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

public abstract class BasicDAO<T> {
	private Class<T> entityClass;

	public BasicDAO(Class<T> eClass) {
		this.entityClass = eClass;
	}

	public abstract EntityManager getEntityManager();

	public boolean create(T entity) throws Exception {
		EntityManager em = getEntityManager();
		try {

			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			return false;
		} finally {
			em.close();
		}
	}

	public boolean update(T entity) throws Exception {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(entity);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			return false;
		} finally {
			em.close();
		}
	}

	public boolean delete(T entity, int entityId) throws Exception {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			em.remove((T) em.find(this.entityClass, entityId));
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			return false;
		} finally {
			em.close();
		}
	}

	public T find(int id) throws Exception {
		EntityManager em = getEntityManager();
		T ret = null;
		try {
			ret = (T) em.find(this.entityClass, id);
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
		return ret;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> findAll() throws Exception {
		EntityManager em = getEntityManager();
		List<T> returnValues = null;
		try {

			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(this.entityClass));
			returnValues = em.createQuery(cq).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
		return returnValues;
	}

	@SuppressWarnings("rawtypes")
	public List executeQuery(String query) throws Exception {
		EntityManager em = getEntityManager();
		List list = null;
		try {
			Query q = em.createNativeQuery(query);
			list = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<T> executeQuery(String query, T entity) throws Exception {
		EntityManager em = getEntityManager();
		List<T> list = new ArrayList<T>();
		try {
			Query q = em.createNativeQuery(query);
			list = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
		return list;
	}

	public List<?> executeQuery(String query, Class<?> resultClass) throws Exception {
		EntityManager em = getEntityManager();
		List<?> list = new ArrayList<>();
		try {
			Query q = em.createNativeQuery(query, resultClass.getClass());
			list = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<T> executeQuery(String query, String mappingName) {
		EntityManager em = getEntityManager();
		List<T> list = new ArrayList<>();
		try {
			Query q = em.createNativeQuery(query, mappingName);
			list = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
		return list;
	}
}
