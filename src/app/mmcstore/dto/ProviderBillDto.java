package app.mmcstore.dto;

import java.util.Date;

public class ProviderBillDto {

	private Integer 	billId;
	private Date 		billDate;
	private String      status;
	private String 		customerName;
	private String 		address;
	private String 		productName;
	private String 		description;
	private Integer 	qtyRequested;
	private Integer 	qtyAvailable;
	private Double 		price;
	private Double 		billAmount;

	public ProviderBillDto(Integer billId, Date billDate, String status, String customerName, String address,
			String productName, String description, Integer qtyRequested, Integer qtyAvailable, Double price,
			Double billAmount) {
		super();
		this.billId = billId;
		this.billDate = billDate;
		this.status = status;
		this.customerName = customerName;
		this.address = address;
		this.productName = productName;
		this.description = description;
		this.qtyRequested = qtyRequested;
		this.qtyAvailable = qtyAvailable;
		this.price = price;
		this.billAmount = billAmount;
	}

	public Integer getBillId() {
		return billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Integer getQtyRequested() {
		return qtyRequested;
	}

	public void setQtyRequested(Integer qtyRequested) {
		this.qtyRequested = qtyRequested;
	}

	public Integer getQtyAvailable() {
		return qtyAvailable;
	}

	public void setQtyAvailable(Integer qtyAvailable) {
		this.qtyAvailable = qtyAvailable;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

	@Override
	public String toString() {
		return "ProviderBillDto [billId=" + billId + ", billDate=" + billDate + ", status=" + status + ", customerName="
				+ customerName + ", address=" + address + ", productName=" + productName + ", description="
				+ description + ", qtyRequested=" + qtyRequested + ", qtyAvailable=" + qtyAvailable + ", price=" + price
				+ ", billAmount=" + billAmount + "]";
	}

}
