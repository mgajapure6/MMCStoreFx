package app.mmcstore.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

import app.mmcstore.dto.ProductDto;
import app.mmcstore.dto.ProviderProductDto;

@Entity
@Table
@SqlResultSetMappings({
		@SqlResultSetMapping(name = "ProductDtoMapping", 
		classes = @ConstructorResult(targetClass = ProductDto.class, columns = {
				@ColumnResult(name = "productId", type = Integer.class), @ColumnResult(name = "productName"),
				@ColumnResult(name = "description"), @ColumnResult(name = "qty", type = Integer.class),
				@ColumnResult(name = "rate", type = Double.class),
				@ColumnResult(name = "amount", type = Double.class) })),
		@SqlResultSetMapping(name = "ProviderProductDtoMapping", 
		classes = @ConstructorResult(targetClass = ProviderProductDto.class, columns = {
				@ColumnResult(name = "providerProductId", type = Integer.class),
				@ColumnResult(name = "productId", type = Integer.class), @ColumnResult(name = "productName"),
				@ColumnResult(name = "description"), @ColumnResult(name = "price", type = Double.class),
				@ColumnResult(name = "qtyAvailable", type = Integer.class) })) })
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;

	@Column(length = 100)
	private String productName;

	@Column(length = 100)
	private String description;

	@Column(length = 100)
	private Double price;

	@OneToMany(mappedBy = "product")
	private Set<ProviderProduct> providerProducts;

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

	public Set<ProviderProduct> getProviderProducts() {
		return providerProducts;
	}

	public void setProviderProducts(Set<ProviderProduct> providerProducts) {
		this.providerProducts = providerProducts;
	}

	public Product(Integer productId, String productName, String description, Double price,
			Set<ProviderProduct> providerProducts) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.description = description;
		this.price = price;
		this.providerProducts = providerProducts;
	}

	public Product() {
		super();
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", description=" + description
				+ ", price=" + price + "]";
	}

}
