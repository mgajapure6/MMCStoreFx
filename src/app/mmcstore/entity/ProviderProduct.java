package app.mmcstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class ProviderProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer providerProductId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "providerId", nullable = false)
	private Provider provider;

	@ManyToOne
	@JoinColumn(name = "productId", nullable = false)
	private Product product;

	@Column
	private Integer qtyAvailable;

	public Integer getProviderProductId() {
		return providerProductId;
	}

	public void setProviderProductId(Integer providerProductId) {
		this.providerProductId = providerProductId;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQtyAvailable() {
		return qtyAvailable;
	}

	public void setQtyAvailable(Integer qtyAvailable) {
		this.qtyAvailable = qtyAvailable;
	}

	public ProviderProduct(Integer providerProductId, Provider provider, Product product, Integer qtyAvailable) {
		super();
		this.providerProductId = providerProductId;
		this.provider = provider;
		this.product = product;
		this.qtyAvailable = qtyAvailable;
	}

	public ProviderProduct() {
		super();
	}

	@Override
	public String toString() {
		return "ProviderProduct [providerProductId=" + providerProductId + ", provider=" + provider + ", product="
				+ product + ", qtyAvailable=" + qtyAvailable + "]";
	}

}
