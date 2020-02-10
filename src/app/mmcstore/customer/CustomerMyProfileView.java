package app.mmcstore.customer;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;

import animatefx.animation.SlideInLeft;
import app.mmcstore.entity.Customer;
import app.mmcstore.entity.User;
import app.mmcstore.services.UserService;
import app.mmcstore.start.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
	private JFXDialog alertDialog;
	
	@FXML
	private Label alertDialogTitle;
	
	@FXML
	private Button alertDialogBtn;
	
	User loggedUser = App.getUserDetail().getLoggedUser();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		errNameLabel.setVisible(false);
		errUsernameLabel.setVisible(false);
		errAddressLabel.setVisible(false);
		
		alertDialog.setDialogContainer(root);
		
		Circle circle = new Circle(70);
		circle.setStroke(Color.WHITE);
		circle.setStrokeWidth(5);
		circle.setCenterX(customerImageView.getFitWidth() / 2);
		circle.setCenterY(customerImageView.getFitHeight() / 2);
		customerImageView.setClip(circle);
		
		nameField.setText(loggedUser.getCustomer().getCustomerName());
		usernameField.setText(loggedUser.getUserName());
		addressField.setText(loggedUser.getCustomer().getAddress()==null ? "" : loggedUser.getCustomer().getAddress());
		
		saveProfileBtn.setOnAction(e->{
			if(validName() && validUserName() && validAddress()) {
				UserService userService = new UserService();
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
		
		
		alertDialogBtn.setOnAction(e->{
			alertDialog.close();
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

}
