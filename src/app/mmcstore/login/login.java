
package app.mmcstore.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.gn.GNAvatarView;

import animatefx.animation.Flash;
import animatefx.animation.Pulse;
import animatefx.animation.SlideInLeft;
import app.mmcstore.common.Section;
import app.mmcstore.common.SectionManager;
import app.mmcstore.common.UserDetail;
import app.mmcstore.common.ViewManager;
import app.mmcstore.entity.User;
import app.mmcstore.services.UserService;
import app.mmcstore.start.App;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class login implements Initializable {

	@FXML
	private GNAvatarView avatar;
	@FXML
	private HBox box_username;
	@FXML
	private HBox box_password;
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private Button login;

	@FXML
	private Label lbl_password;
	@FXML
	private Label lbl_username;
	@FXML
	private Label lbl_error;

	private RotateTransition rotateTransition = new RotateTransition();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		rotateTransition.setNode(avatar);
		rotateTransition.setByAngle(360);
		rotateTransition.setDuration(Duration.seconds(1));
		rotateTransition.setAutoReverse(true);

		addEffect(password);
		addEffect(username);

		setupListeners();

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
	}

	private boolean validPassword() {
		return !password.getText().isEmpty() && password.getLength() > 3;
	}

	private boolean validUsername() {
		return !username.getText().isEmpty() && username.getLength() > 3;
	}

	@FXML
	private void loginAction() {
		Pulse pulse = new Pulse(login);
		pulse.setDelay(Duration.millis(20));
		pulse.play();
		if (validPassword() && validUsername())
			enter();
		else {
			lbl_password.setVisible(true);
			lbl_username.setVisible(true);
		}
	}

	private void enter() {
		UserService userService = new UserService();
		// UserDao userDao = new UserDao();
		System.out.println("userDao start");
		User user = userService.getByUsernameAndPassword(this.username.getText(), this.password.getText());
		System.out.println("userDao end");
		System.out.println("dbuser::" + user);

		if (user != null && user.getUserName().equals(this.username.getText())
				&& user.getUserPassword().equals(this.password.getText())) {
			Section section = new Section();
			section.setLogged(true);
			section.setUserLogged(this.username.getText());
			SectionManager.save(section);
			App.setUserDetail(user);
			try {
				if (user.getIsCustomer()) {
					App.decorator.setContent(
							FXMLLoader.load(getClass().getResource("/app/mmcstore/customer/CustomerMain.fxml")));
				} else {
					App.decorator.setContent(FXMLLoader.load(getClass().getResource("/app/mmcstore/provider/ProviderMain.fxml")));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			App.decorator.setBarHeight(50);

			UserDetail detail = App.getUserDetail();
			detail.setText(user.getIsCustomer() ? "Customer : " + user.getCustomer().getCustomerName()
					: "Provider : " + user.getProvider().getProviderName());
			detail.setHeader(user.getUserName());

			App.decorator.addCustom(App.getUserDetail());

//			App.getUserDetail().setProfileAction(event -> {
//				App.getUserDetail().getPopOver().hide();
//				// Main.ctrl.title.setText("Profile");
//				// Main.ctrl.body.setContent(ViewManager.getInstance().get("profile"));
//			});

			App.getUserDetail().setSignAction(event -> {
				App.getUserDetail().getPopOver().hide();
				App.decorator.setContent(ViewManager.getInstance().get("login"));
				this.username.setText("");
				this.password.setText("");
				App.decorator.removeCustom(App.getUserDetail());
			});

			TimerTask timerTask = new TimerTask() {
				@Override
				public void run() {
					Platform.runLater(() -> {
						// add notification in later
						// TrayNotification tray = new TrayNotification();
						// tray.setNotificationType(NotificationType.NOTICE);
						// tray.setRectangleFill(Color.web(""));
						// tray.setTitle("Welcome!");
						// tray.setMessage("Welcome back " + username);
						// tray.showAndDismiss(Duration.millis(10000));
					});
				}
			};

			Timer timer = new Timer();
			timer.schedule(timerTask, 300);

		} else {
			lbl_error.setVisible(true);
		}
	}

	@FXML
	private void switchCreate() {
		App.decorator.setContent(ViewManager.getInstance().get("account"));
	}
}
