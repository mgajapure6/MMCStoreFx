package app.mmcstore.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Transient;

import app.mmcstore.dto.CustomerBillDto;
import app.mmcstore.dto.CustomerDashboardDto;
import app.mmcstore.dto.ProviderBillDto;

@Entity
@Table
@SqlResultSetMappings({
		@SqlResultSetMapping(name = "ProviderBillDtoMapping", 
		classes = @ConstructorResult(targetClass = ProviderBillDto.class, columns = {
				@ColumnResult(name = "billId", type = Integer.class),
				@ColumnResult(name = "billDate", type = Date.class),
				@ColumnResult(name = "status", type = String.class),
				@ColumnResult(name = "customerName", type = String.class),
				@ColumnResult(name = "address", type = String.class),
				@ColumnResult(name = "productName", type = String.class),
				@ColumnResult(name = "description", type = String.class),
				@ColumnResult(name = "qtyRequested", type = Integer.class),
				@ColumnResult(name = "qtyAvailable", type = Integer.class),
				@ColumnResult(name = "price", type = Double.class),
				@ColumnResult(name = "billAmount", type = Double.class) })),

		@SqlResultSetMapping(name = "CustomerBillDtoMapping", 
		classes = @ConstructorResult(targetClass = CustomerBillDto.class, columns = {
				@ColumnResult(name = "billId", type = Integer.class),
				@ColumnResult(name = "billDate", type = Date.class),
				@ColumnResult(name = "isPaid", type = Boolean.class),
				@ColumnResult(name = "billAmount", type = Double.class) })),
		
		@SqlResultSetMapping(name = "CustomerDashboardDtoMapping", 
		classes = @ConstructorResult(targetClass = CustomerDashboardDto.class, columns = {
				@ColumnResult(name = "totalBill", type = Integer.class),
				@ColumnResult(name = "paidBill", type = Integer.class),
				@ColumnResult(name = "unpaidBill", type = Integer.class),
				@ColumnResult(name = "unpaidAmount", type = Double.class)})) 
		
})
public class Bill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer billId;

	@Column(length = 100)
	private Date billDate;

	@Column(length = 100)
	private Boolean isPaid;

	@ManyToOne
	@JoinColumn(name = "customerId", nullable = false)
	private Customer customer;

	@Transient
	private Double billAmount;

	@OneToMany(mappedBy = "bill")
	private Set<BillProviderProduct> billProviderProducts;

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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<BillProviderProduct> getBillProviderProducts() {
		return billProviderProducts;
	}

	public void setBillProviderProducts(Set<BillProviderProduct> billProviderProducts) {
		this.billProviderProducts = billProviderProducts;
	}

	public Double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

	public Bill(Integer billId, Date billDate, Boolean isPaid, Customer customer,
			Set<BillProviderProduct> billProviderProducts) {
		super();
		this.billId = billId;
		this.billDate = billDate;
		this.isPaid = isPaid;
		this.customer = customer;
		this.billProviderProducts = billProviderProducts;
	}

	public Bill() {
		super();
	}

	@Override
	public String toString() {
		return "Bill [billId=" + billId + ", billDate=" + billDate + ", isPaid=" + isPaid + ", billProviderProducts="
				+ billProviderProducts + "]";
	}

}
