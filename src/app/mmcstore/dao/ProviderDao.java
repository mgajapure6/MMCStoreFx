package app.mmcstore.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import app.mmcstore.entity.Provider;

public class ProviderDao extends BasicDAO<Provider> {

	private EntityManagerFactory factory;

	public ProviderDao(EntityManagerFactory factory) {
		super(Provider.class);
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

}
