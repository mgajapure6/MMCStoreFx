package app.mmcstore.start;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.gn.decorator.GNDecorator;
import com.gn.decorator.options.ButtonType;
import com.sun.javafx.application.LauncherImpl;

import app.mmcstore.common.Section;
import app.mmcstore.common.UserDetail;
import app.mmcstore.common.ViewManager;
import app.mmcstore.entity.User;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
	
	static Logger logger = Logger.getLogger(App.class);

	public static final GNDecorator decorator = new GNDecorator();
	public static final Scene scene = decorator.getScene();

	public static ObservableList<String> stylesheets;
	public static HostServices hostServices;
	private static UserDetail userDetail = null;

	private float increment = 0;
	private float progress = 0;
	@SuppressWarnings("unused")
	private Section section;
	@SuppressWarnings("unused")
	private User user;

	public static GNDecorator getDecorator() {
		return decorator;
	}

	public static void setUserDetail(User user) {
		userDetail = new UserDetail(user);
	}

	public static UserDetail getUserDetail() {
		return userDetail;
	}

	@Override
	public synchronized void init() {
		//section = SectionManager.get();

		float total = 43; // the difference represents the views not loaded
		increment = 100f / total;

		load("login", "login");
		load("login", "account");
		try {
			wait(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		configServices();
		initialScene();
		stylesheets = decorator.getScene().getStylesheets();
		stylesheets.addAll(getClass().getResource("/css/fonts.css").toExternalForm(),
				getClass().getResource("/css/material-color.css").toExternalForm(),
				getClass().getResource("/css/skeleton.css").toExternalForm(),
				getClass().getResource("/css/light.css").toExternalForm(),
				getClass().getResource("/css/bootstrap.css").toExternalForm(),
				getClass().getResource("/css/shape.css").toExternalForm(),
				getClass().getResource("/css/typographic.css").toExternalForm(),
				getClass().getResource("/css/helpers.css").toExternalForm(),
				getClass().getResource("/css/master.css").toExternalForm(),
				getClass().getResource("/css/newCss.css").toExternalForm());

		decorator.setMaximized(true);
		decorator.getStage().getIcons().add(new Image("/img/logo2.png"));
		decorator.show();
	}

	private void initialScene() {

		decorator.setTitle("MMC Store");
		decorator.addButton(ButtonType.FULL_EFFECT);
		decorator.initTheme(GNDecorator.Theme.CUSTOM);
		decorator.setContent(ViewManager.getInstance().get("login"));

		decorator.getStage().setOnCloseRequest(event -> {
			App.getUserDetail().getPopOver().hide();

			Platform.exit();
		});
	}

	private void load(String module, String name) {
		try {
			ViewManager.getInstance().put(name,
					FXMLLoader.load(getClass().getResource("/app/mmcstore/" + module + "/" + name + ".fxml")));
			preloaderNotify();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private synchronized void preloaderNotify() {
		progress += increment;
		LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
	}

	public static void main(String[] args) {
		logger.info("Launching App From main Method");
		LauncherImpl.launchApplication(App.class, Loader.class, args);
	}

	private void configServices() {
		hostServices = getHostServices();
	}

}
