package app.mmcstore.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Persistence;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import app.mmcstore.dao.ProductDao;
import app.mmcstore.dao.ProviderProductDao;
import app.mmcstore.dto.ProviderProductDto;
import app.mmcstore.entity.Product;
import app.mmcstore.entity.Provider;
import app.mmcstore.entity.ProviderProduct;

public class ProductService {

	private ProviderProductDao providerProductDao;
	private ProductDao productDao1;

	public ProductService() {
		try {
			providerProductDao = new ProviderProductDao(Persistence.createEntityManagerFactory("MMCStore"));
			productDao1 = new ProductDao(Persistence.createEntityManagerFactory("MMCStore"));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public Boolean saveProduct(Product product, Provider provider, Integer qty) {

		if (product != null) {
			try {
				productDao1.create(product);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		ProviderProduct providerProduct = new ProviderProduct();
		providerProduct.setProduct(product);
		providerProduct.setProvider(provider);
		providerProduct.setQtyAvailable(qty);

		try {
			providerProductDao.create(providerProduct);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Boolean updateProduct(Product product) {
		boolean isUpdated = false;
		if (product != null) {
			try {
				isUpdated = productDao1.update(product);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return isUpdated;
		}

		return isUpdated;
	}

	public List<ProviderProduct> getAllProviderProducts() {
		List<ProviderProduct> providerProducts = null;
		try {
			providerProducts = providerProductDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		return providerProducts;
	}

	@SuppressWarnings("rawtypes")
	public List<ProviderProductDto> getAllProviderProductsByProviderId(Integer providerId, Provider provider) {
		List<ProviderProductDto> providerProducts = new ArrayList<>();
		try {
			String query = "Select pp.providerProductId, pp.productId, p.productName, p.description, p.price, pp.qtyAvailable from providerproduct pp, product p where p.productId=pp.productId and pp.providerId ="
					+ providerId;
			List l = providerProductDao.executeQuery(query, "ProviderProductDtoMapping");
			providerProducts = getProviderProductDtoList(l);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return providerProducts;
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	private List<ProviderProduct> getTypedList(List list, Provider provider) {
		List<ProviderProduct> providerProducts = new ArrayList<>();
		Gson gson = new Gson();
		for (Object object : list) {
			JsonElement jsonElement = gson.toJsonTree(object);
			ProviderProduct pp = gson.fromJson(jsonElement, ProviderProduct.class);
			Product p = gson.fromJson(jsonElement, Product.class);
			pp.setProduct(p);
			pp.setProvider(provider);
			providerProducts.add(pp);
		}
		return providerProducts;
	}

	@SuppressWarnings("rawtypes")
	private List<ProviderProductDto> getProviderProductDtoList(List list) {
		List<ProviderProductDto> providerProductDto = new ArrayList<>();
		Gson gson = new Gson();
		for (Object object : list) {
			JsonElement jsonElement = gson.toJsonTree(object);
			ProviderProductDto pp = gson.fromJson(jsonElement, ProviderProductDto.class);
			providerProductDto.add(pp);
		}
		return providerProductDto;
	}

	public Boolean deleteProviderProduct(ProviderProductDto ppd) {

		return null;
	}

}
