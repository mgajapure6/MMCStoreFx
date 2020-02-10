package app.mmcstore.dto;

public class CustomerDashboardDto {

	Integer totalBill;
	Integer paidBill;
	Integer unpaidBill;

	public CustomerDashboardDto() {
		super();
	}

	public CustomerDashboardDto(Integer totalBill, Integer paidBill, Integer unpaidBill) {
		super();
		this.totalBill = totalBill;
		this.paidBill = paidBill;
		this.unpaidBill = unpaidBill;
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

}
