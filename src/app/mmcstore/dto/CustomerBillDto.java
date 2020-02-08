package app.mmcstore.dto;

import java.util.Date;

public class CustomerBillDto {

	private Integer billId;
	private Date billDate;
	private Boolean isPaid;
	private Double billAmount;

	public CustomerBillDto(Integer billId, Date billDate, Boolean isPaid, Double billAmount) {
		super();
		this.billId = billId;
		this.billDate = billDate;
		this.isPaid = isPaid;
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

	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

	public Double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

}
