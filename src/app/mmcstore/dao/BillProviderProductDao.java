package app.mmcstore.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import app.mmcstore.entity.BillProviderProduct;

public class BillProviderProductDao extends BasicDAO<BillProviderProduct> {

	private EntityManagerFactory factory;

	public BillProviderProductDao(EntityManagerFactory factory) {
		super(BillProviderProduct.class);
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
