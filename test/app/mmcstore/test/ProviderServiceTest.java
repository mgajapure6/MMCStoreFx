package app.mmcstore.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import app.mmcstore.dto.ProviderBillDto;
import app.mmcstore.entity.Provider;
import app.mmcstore.services.ProviderService;

public class ProviderServiceTest {

	ProviderService providerService;

	@Before
	public void setup() {
		System.out.println("setup");
		providerService = new ProviderService();
	}

	@Test
	public void saveProvider() {
		Boolean isSaved = providerService.saveProvider(new Provider(1, "Alexa John", null, null));
		assertTrue("is saved", isSaved);
	}

	@Test
	public void getProviderBillsById() {
		List<ProviderBillDto> pbds = providerService.getProviderBillsById(1);
		assertTrue("is greater than 0", pbds.size() > 0);
		assertTrue("not empty", !pbds.isEmpty());
	}

}
