package app.mmcstore.dto;

public class ProviderProductDto {
	Integer providerProductId;
	Integer productId;
	String productName;
	String description;
	Double price;
	Integer qtyAvailable;

	public ProviderProductDto(Integer providerProductId, Integer productId, String productName, String description,
			Double price, Integer qtyAvailable) {
		super();
		this.providerProductId = providerProductId;
		this.productId = productId;
		this.productName = productName;
		this.description = description;
		this.price = price;
		this.qtyAvailable = qtyAvailable;
	}

	public Integer getProviderProductId() {
		return providerProductId;
	}

	public void setProviderProductId(Integer providerProductId) {
		this.providerProductId = providerProductId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQtyAvailable() {
		return qtyAvailable;
	}

	public void setQtyAvailable(Integer qtyAvailable) {
		this.qtyAvailable = qtyAvailable;
	}

}
