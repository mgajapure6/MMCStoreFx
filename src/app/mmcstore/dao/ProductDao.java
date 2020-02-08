package app.mmcstore.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import app.mmcstore.entity.Product;

public class ProductDao extends BasicDAO<Product> {

	private EntityManagerFactory factory;

	public ProductDao(EntityManagerFactory factory) {
		super(Product.class);
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
