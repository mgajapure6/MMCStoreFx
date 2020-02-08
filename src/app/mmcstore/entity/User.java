package app.mmcstore.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "userData")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	@Column(length = 100)
	private String userName;
	@Column(length = 100)
	private String userPassword;
	@Column(length = 100)
	private Boolean isCustomer;
	@Column(length = 100)
	private Boolean isProvider;
	@OneToOne
	private Customer customer;
	@OneToOne
	private Provider provider;
	@Column(length = 100)
	private String status;
	@Column(length = 100)
	private String email;
	@Column(length = 100)
	private Date registerDate;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Boolean getIsCustomer() {
		return isCustomer;
	}

	public void setIsCustomer(Boolean isCustomer) {
		this.isCustomer = isCustomer;
	}

	public Boolean getIsProvider() {
		return isProvider;
	}

	public void setIsProvider(Boolean isProvider) {
		this.isProvider = isProvider;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public User(Integer userId, String userName, String userPassword, Boolean isCustomer, Boolean isProvider,
			Customer customer, Provider provider, String status, String email, Date registerDate) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.isCustomer = isCustomer;
		this.isProvider = isProvider;
		this.customer = customer;
		this.provider = provider;
		this.status = status;
		this.email = email;
		this.registerDate = registerDate;
	}

	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userPassword=" + userPassword + ", isCustomer="
				+ isCustomer + ", isProvider=" + isProvider + ", customer=" + customer + ", provider=" + provider
				+ ", status=" + status + ", email=" + email + ", registerDate=" + registerDate + "]";
	}

}
