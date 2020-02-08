package app.mmcstore.dto;

public class ProductDto {

	public Integer productId;
	public String productName;
	public String description;
	public Integer qty;
	public Double rate;
	public Double amount;

	public ProductDto(Integer productId, String productName, String description, Integer qty, Double rate,
			Double amount) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.description = description;
		this.qty = qty;
		this.rate = rate;
		this.amount = amount;
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

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
