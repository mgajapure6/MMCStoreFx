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
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer accountId;

	@Column(nullable = false, length = 10)
	private Double sum;

	@ManyToOne
	@JoinColumn(name = "customerId", nullable = false)
	private Customer customer;

	public Account(Integer accountId, Double sum, Customer customer) {
		super();
		this.accountId = accountId;
		this.sum = sum;
		this.customer = customer;
	}

	public Account() {
		super();
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", sum=" + sum + ", customer=" + customer + "]";
	}

}
