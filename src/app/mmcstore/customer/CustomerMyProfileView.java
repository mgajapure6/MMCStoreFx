package app.mmcstore.customer;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;

import animatefx.animation.SlideInLeft;
import app.mmcstore.dao.AccountDao;
import app.mmcstore.entity.Account;
import app.mmcstore.entity.Customer;
import app.mmcstore.entity.User;
import app.mmcstore.services.UserService;
import app.mmcstore.start.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CustomerMyProfileView implements Initializable{
	
	@FXML
	private StackPane root;
	
	@FXML
	private VBox subRoot;
	
	@FXML
	private ImageView customerImageView;
	
	@FXML
	private TextField nameField;
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private TextField addressField;
	
	@FXML
	private Button saveProfileBtn;
	
	@FXML
	private Label errNameLabel;
	
	@FXML
	private Label errUsernameLabel;
	
	@FXML
	private Label errAddressLabel;
	
	@FXML
	private Label errAmountLabel;
	
	@FXML
	private JFXDialog alertDialog;
	
	@FXML
	private JFXDialog updateAmountDialog;
	
	@FXML
	private Label alertDialogTitle;
	
	@FXML
	private Button alertDialogBtn;
	
	@FXML
	private ImageView accountImageView;
	
	@FXML
	private Label accountBalanceLabel;
	
	@FXML
	private Button updateAccountBalanceBtn;
	
	@FXML
	private Button updateAccountAmountBtn;
	
	@FXML
	private Button  closeAccountDialog;
	
	@FXML
	private TextField enterAcciuntAmountField;
	
	User loggedUser = App.getUserDetail().getLoggedUser();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		errNameLabel.setVisible(false);
		errUsernameLabel.setVisible(false);
		errAddressLabel.setVisible(false);
		errAmountLabel.setVisible(false);
		
		alertDialog.setDialogContainer(root);
		updateAmountDialog.setDialogContainer(root);
		
		UserService userService = new UserService();
		
		Double existAmt = userService.getCustomerAccountBalance(loggedUser.getCustomer().getCustomerId());
		accountBalanceLabel.setText("$ "+existAmt);
		
		Circle circle = new Circle(70);
		circle.setStroke(Color.WHITE);
		circle.setStrokeWidth(5);
		circle.setCenterX(customerImageView.getFitWidth() / 2);
		circle.setCenterY(customerImageView.getFitHeight() / 2);
		customerImageView.setClip(circle);
		
		Circle circle1 = new Circle(70);
		circle1.setStroke(Color.WHITE);
		circle1.setStrokeWidth(5);
		circle1.setCenterX(accountImageView.getFitWidth() / 2);
		circle1.setCenterY(accountImageView.getFitHeight() / 2);
		accountImageView.setClip(circle1);
		
		nameField.setText(loggedUser.getCustomer().getCustomerName());
		usernameField.setText(loggedUser.getUserName());
		addressField.setText(loggedUser.getCustomer().getAddress()==null ? "" : loggedUser.getCustomer().getAddress());
		
		saveProfileBtn.setOnAction(e->{
			if(validName() && validUserName() && validAddress()) {
				loggedUser.setUserName(usernameField.getText());
				Customer customer = loggedUser.getCustomer();
				customer.setCustomerName(nameField.getText());
				customer.setAddress(addressField.getText());
				Boolean isUpdated = userService.updateUser(loggedUser, customer, null);
				
				if(isUpdated) {
					alertDialogTitle.setText("Profile updated successfully");
					alertDialog.setTransitionType(DialogTransition.CENTER);
					alertDialogBtn.getStyleClass().remove("btn-danger");
					alertDialogBtn.getStyleClass().add("btn-info");
					alertDialog.show();
					loggedUser.setCustomer(customer);

				} else {
					alertDialogTitle.setText("Error.. Unable to update profile");
					alertDialog.setTransitionType(DialogTransition.CENTER);
					alertDialogBtn.getStyleClass().remove("btn-info");
					alertDialogBtn.getStyleClass().add("btn-danger");
					alertDialog.show();
				}
				
			}else {
				if (!validName()) {
					errNameLabel.setVisible(true);
					new SlideInLeft(errNameLabel).play();
				} else {
					errNameLabel.setVisible(false);
				}
				if (!validUserName()) {
					errUsernameLabel.setVisible(true);
					new SlideInLeft(errUsernameLabel).play();
				} else {
					errUsernameLabel.setVisible(false);
				}
				if (!validAddress()) {
					errAddressLabel.setVisible(true);
					new SlideInLeft(errAddressLabel).play();
				} else {
					errAddressLabel.setVisible(false);
				}
			}
		});
		
		nameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!validName()) {
				if (!newValue) {
					errNameLabel.setVisible(true);
					new SlideInLeft(errNameLabel).play();
				} else {
					errNameLabel.setVisible(false);
				}
			} else {
				errNameLabel.setVisible(false);
			}
		});
		
		usernameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!validUserName()) {
				if (!newValue) {
					errUsernameLabel.setVisible(true);
					new SlideInLeft(errUsernameLabel).play();
				} else {
					errUsernameLabel.setVisible(false);
				}
			} else {
				errUsernameLabel.setVisible(false);
			}
		});
		
		addressField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!validAddress()) {
				if (!newValue) {
					errAddressLabel.setVisible(true);
					new SlideInLeft(errAddressLabel).play();
				} else {
					errAddressLabel.setVisible(false);
				}
			} else {
				errAddressLabel.setVisible(false);
			}
		});
		
		enterAcciuntAmountField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!validAmount()) {
				if (!newValue) {
					errAmountLabel.setVisible(true);
					new SlideInLeft(errAmountLabel).play();
				} else {
					errAmountLabel.setVisible(false);
				}
			} else {
				errAmountLabel.setVisible(false);
			}
		});
		
		
		alertDialogBtn.setOnAction(e->{
			alertDialog.close();
		});
		
		
		updateAccountBalanceBtn.setOnAction(e->{
			updateAmountDialog.show();
		});
		
		updateAccountAmountBtn.setOnAction(e->{
			String amt = enterAcciuntAmountField.getText();
			if (!validAmount()) {
				errAmountLabel.setVisible(true);
				new SlideInLeft(errAmountLabel).play();
			} else {
				errAmountLabel.setVisible(false);
				
				Double existAmt1 = userService.getCustomerAccountBalance(loggedUser.getCustomer().getCustomerId());
				Double newAmt = existAmt1+Double.parseDouble(amt.equals("") ? "0.0" : amt);
				Boolean isUpdated = userService.updateCustomerAccount(newAmt,loggedUser.getCustomer().getCustomerId());
				if(isUpdated) {
					accountBalanceLabel.setText("$ "+newAmt);
					alertDialogTitle.setText("Bank Account Balance updated successfully");
					alertDialog.setTransitionType(DialogTransition.CENTER);
					alertDialogBtn.getStyleClass().remove("btn-danger");
					alertDialogBtn.getStyleClass().add("btn-info");
					alertDialog.show();
					updateAmountDialog.close();
				} else {
					alertDialogTitle.setText("Error.. Unable to update bank account balance");
					alertDialog.setTransitionType(DialogTransition.CENTER);
					alertDialogBtn.getStyleClass().remove("btn-info");
					alertDialogBtn.getStyleClass().add("btn-danger");
					alertDialog.show();
				}
			}
			
			
		});
		
		closeAccountDialog.setOnAction(e->{
			
			updateAmountDialog.close();
		});
	}
	
	private boolean validName() {
		return !nameField.getText().isEmpty() && nameField.getLength() > 2;
	}

	private boolean validUserName() {
		return !usernameField.getText().isEmpty() && usernameField.getLength() > 2;
	}

	private boolean validAddress() {
		return !addressField.getText().isEmpty() && addressField.getLength() > 2;
	}
	
	private boolean validAmount() {
		return !enterAcciuntAmountField.getText().isEmpty() && isDouble(enterAcciuntAmountField.getText());
	}
	
	public boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

	public boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

}
