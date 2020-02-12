
package app.mmcstore.login;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import com.gn.GNAvatarView;

import animatefx.animation.Flash;
import animatefx.animation.Pulse;
import animatefx.animation.SlideInLeft;
import app.mmcstore.common.Mask;
import app.mmcstore.common.Section;
import app.mmcstore.common.SectionManager;
import app.mmcstore.common.UserDetail;
import app.mmcstore.common.ViewManager;
import app.mmcstore.entity.Customer;
import app.mmcstore.entity.Provider;
import app.mmcstore.entity.User;
import app.mmcstore.services.UserService;
import app.mmcstore.start.App;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class Account implements Initializable {

	@FXML
	private GNAvatarView avatar;

	@FXML
	private HBox box_fullname;
	@FXML
	private HBox box_username;
	@FXML
	private HBox box_email;
	@FXML
	private HBox box_password;

	@FXML
	private TextField fullname;
	@FXML
	private TextField username;
	@FXML
	private TextField email;
	@FXML
	private TextField password;

	@FXML
	private Label lbl_password;
	@FXML
	private Label lbl_fullname;
	@FXML
	private Label lbl_email;
	@FXML
	private Label lbl_username;

	@FXML
	private Label lbl_error;

	@FXML
	private Button register;

	@FXML
	private ComboBox<String> accType;

	private RotateTransition rotateTransition = new RotateTransition();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		rotateTransition.setNode(avatar);
		rotateTransition.setByAngle(360);
		rotateTransition.setDuration(Duration.seconds(1));
		rotateTransition.setAutoReverse(true);

		addEffect(email);
		addEffect(fullname);
		addEffect(username);
		addEffect(password);

		Mask.nameField(fullname);
		Mask.noInitSpace(username);
		Mask.noSpaces(username);
		setupListeners();

		accType.setValue("Customer");
	}

	@FXML
	private void register() throws Exception {
		Pulse pulse = new Pulse(register);
		pulse.setDelay(Duration.millis(20));
		pulse.play();

		if (validEmail() && validFullName() && validFullName() && validUsername() && validPassword()) {

			String userN = username.getText();
			UserService userService = new UserService();
			// UserDao userDao = new UserDao();
			User user = userService.getByUsername(userN);
			System.out.println("check user::" + user);
			if (user == null) {
				setProperties();
			} else {
				lbl_error.setVisible(true);
			}
//			String extension = "properties";
//
//			File directory = new File("user/");
//			File file = new File("user/" + user + "." + extension);
//
//			if (!directory.exists()) {
//				directory.mkdir();
//				file.createNewFile();
//				setProperties();
//			} else if (!file.exists()) {
//				file.createNewFile();
//				setProperties();
//			} else {
//				lbl_error.setVisible(true);
//			}
		} else if (!validUsername()) {
			lbl_username.setVisible(true);
		} else if (!validFullName()) {
			lbl_fullname.setVisible(true);
		} else if (!validEmail()) {
			lbl_email.setVisible(true);
		} else {
			lbl_password.setVisible(true);
		}
	}

	private void setProperties() throws IOException {

		//Section section = new Section(true, username.getText());
		//SectionManager.save(section);
		Boolean isCustomer = false;
		Customer customer = new Customer();
		Provider provider = new Provider();
		if (accType.getValue().equals("Customer")) {
			isCustomer = true;
			provider = null;
			customer = new Customer(null, fullname.getText(), null, null, null);
		} else {
			isCustomer = false;
			provider = new Provider(null, fullname.getText(), null, null);
			customer = null;
		}

		User user = new User(null, username.getText(), password.getText(), isCustomer, !isCustomer, null, null,
				"Active", email.getText(), new Date());

		UserService userService = new UserService();
		userService.saveUser(user, customer, provider);

		App.setUserDetail(user);

		UserDetail detail = App.getUserDetail();
		detail.setText(user.getIsCustomer() ? "Customer : "+user.getCustomer().getCustomerName() : "Provider : "+user.getProvider().getProviderName());
		detail.setHeader(user.getUserName());

		App.decorator.addCustom(detail);
//		detail.setProfileAction(event -> {
//			App.getUserDetail().getPopOver().hide();
//			// Main.ctrl.title.setText("Profile");
//			// Main.ctrl.body.setContent(ViewManager.getInstance().get("profile"));
//
//		});

		detail.setSignAction(event -> {
			App.getUserDetail().getPopOver().hide();
			//SectionManager.save(new Section(false, ""));

			this.password.setText("");
			this.email.setText("");
			this.fullname.setText("");
			this.username.setText("");

			App.decorator.setContent(ViewManager.getInstance().get("login"));

			App.decorator.removeCustom(detail);
		});

		if (accType.getValue().equals("Customer")) {
			App.decorator.setContent(FXMLLoader.load(getClass().getResource("/app/mmcstore/customer/CustomerMain.fxml")));
		} else {
			App.decorator.setContent(FXMLLoader.load(getClass().getResource("/app/mmcstore/provider/ProviderMain.fxml")));
		}
		
		App.decorator.setBarHeight(50);

	}

	@FXML
	private void back() {
		App.decorator.setContent(ViewManager.getInstance().get("login"));
	}

	private void addEffect(Node node) {
		node.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
			rotateTransition.play();
			Pulse pulse = new Pulse(node.getParent());
			pulse.setDelay(Duration.millis(100));
			pulse.setSpeed(5);
			pulse.play();
			node.getParent().setStyle("-icon-color : -success; -fx-border-color : -success");
		});

		node.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!node.isFocused())
				node.getParent().setStyle("-icon-color : -dark-gray; -fx-border-color : transparent");
			else
				node.getParent().setStyle("-icon-color : -success; -fx-border-color : -success");
		});
	}

	private boolean validPassword() {
		return !password.getText().isEmpty() && password.getLength() > 3;
	}

	private boolean validUsername() {
		return !username.getText().isEmpty() && username.getLength() > 3;
	}

	private boolean validFullName() {
		return !fullname.getText().isEmpty() && fullname.getLength() > 3;
	}

	private boolean validEmail() {
		return Mask.isEmail(email);
	}

	private void setupListeners() {
		password.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!validPassword()) {
				if (!newValue) {
					Flash swing = new Flash(box_password);
					lbl_password.setVisible(true);
					new SlideInLeft(lbl_password).play();
					swing.setDelay(Duration.millis(100));
					swing.play();
					box_password.setStyle("-icon-color : -danger; -fx-border-color : -danger");
				} else {
					lbl_password.setVisible(false);
				}
			} else {
				lbl_error.setVisible(false);
			}
		});

		username.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!validUsername()) {
				if (!newValue) {
					Flash swing = new Flash(box_username);
					lbl_username.setVisible(true);
					new SlideInLeft(lbl_username).play();
					swing.setDelay(Duration.millis(100));
					swing.play();
					box_username.setStyle("-icon-color : -danger; -fx-border-color : -danger");
				} else {
					lbl_username.setVisible(false);
				}
			} else {
				lbl_error.setVisible(false);
			}
		});

		email.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!validEmail()) {
				if (!newValue) {
					Flash swing = new Flash(box_email);
					lbl_email.setVisible(true);
					new SlideInLeft(lbl_email).play();
					swing.setDelay(Duration.millis(100));
					swing.play();
					box_email.setStyle("-icon-color : -danger; -fx-border-color : -danger");
				} else {
					lbl_email.setVisible(false);
				}
			} else {
				lbl_error.setVisible(false);
			}
		});

		fullname.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!validFullName()) {
				if (!newValue) {
					Flash swing = new Flash(box_fullname);
					lbl_fullname.setVisible(true);
					new SlideInLeft(lbl_fullname).play();
					swing.setDelay(Duration.millis(100));
					swing.play();
					box_fullname.setStyle("-icon-color : -danger; -fx-border-color : -danger");
				} else {
					lbl_fullname.setVisible(false);
				}
			} else {
				lbl_error.setVisible(false);
			}
		});
	}
}
