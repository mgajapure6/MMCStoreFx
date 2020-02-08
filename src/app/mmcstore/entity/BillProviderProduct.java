package app.mmcstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class BillProviderProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer billProviderProductId;

	@ManyToOne
	@JoinColumn(name = "billId", nullable = false)
	private Bill bill;

	@ManyToOne
	@JoinColumn(name = "providerProductId", nullable = false)
	private ProviderProduct providerProduct;

	@Column
	private Integer qtyRequested;

	public BillProviderProduct(Integer billProviderProductId, Bill bill, ProviderProduct providerProduct,
			Integer qtyRequested) {
		super();
		this.billProviderProductId = billProviderProductId;
		this.bill = bill;
		this.providerProduct = providerProduct;
		this.qtyRequested = qtyRequested;
	}

	public BillProviderProduct() {
		super();
	}

	public Integer getBillProviderProductId() {
		return billProviderProductId;
	}

	public void setBillProviderProductId(Integer billProviderProductId) {
		this.billProviderProductId = billProviderProductId;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public ProviderProduct getProviderProduct() {
		return providerProduct;
	}

	public void setProviderProduct(ProviderProduct providerProduct) {
		this.providerProduct = providerProduct;
	}

	public Integer getQtyRequested() {
		return qtyRequested;
	}

	public void setQtyRequested(Integer qtyRequested) {
		this.qtyRequested = qtyRequested;
	}

	@Override
	public String toString() {
		return "BillProviderProduct [billProviderProductId=" + billProviderProductId + ", providerProduct="
				+ providerProduct + ", qtyRequested=" + qtyRequested + "]";
	}
	
	

}
