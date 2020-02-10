package app.mmcstore.test;

import static org.junit.Assert.assertTrue;

import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import app.mmcstore.dao.ProviderDao;
import app.mmcstore.entity.Provider;

public class ProviderDaoTest {

	ProviderDao providerDao;

	@Before
	public void setup() {
		System.out.println("ProviderDao test setup");
		providerDao = new ProviderDao(Persistence.createEntityManagerFactory("MMCStore"));
	}

	@Test
	public void createProvider() throws Exception {
		Boolean isSaved = providerDao.create(new Provider(1, "Alexa John", null, null));
		assertTrue("is provider saved", isSaved);
	}

	@SuppressWarnings("unused")
	@Test
	public void updateProvider() throws Exception {
		Provider provider = providerDao.find(1);
	}

}
