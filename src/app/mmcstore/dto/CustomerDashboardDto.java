package app.mmcstore.dto;

public class CustomerDashboardDto {

	Integer totalBill;
	Integer paidBill;
	Integer unpaidBill;
	Double unpaidAmount;

	public CustomerDashboardDto() {
		super();
	}

	public CustomerDashboardDto(Integer totalBill, Integer paidBill, Integer unpaidBill, Double unpaidAmount) {
		super();
		this.totalBill = totalBill;
		this.paidBill = paidBill;
		this.unpaidBill = unpaidBill;
		this.unpaidAmount = unpaidAmount;
	}

	public Double getUnpaidAmount() {
		return unpaidAmount;
	}

	public void setUnpaidAmount(Double unpaidAmount) {
		this.unpaidAmount = unpaidAmount;
	}

	public Integer getTotalBill() {
		return totalBill;
	}

	public void setTotalBill(Integer totalBill) {
		this.totalBill = totalBill;
	}

	public Integer getPaidBill() {
		return paidBill;
	}

	public void setPaidBill(Integer paidBill) {
		this.paidBill = paidBill;
	}

	public Integer getUnpaidBill() {
		return unpaidBill;
	}

	public void setUnpaidBill(Integer unpaidBill) {
		this.unpaidBill = unpaidBill;
	}

	@Override
	public String toString() {
		return "CustomerDashboardDto [totalBill=" + totalBill + ", paidBill=" + paidBill + ", unpaidBill=" + unpaidBill
				+ ", unpaidAmount=" + unpaidAmount + "]";
	}
	
	

}
