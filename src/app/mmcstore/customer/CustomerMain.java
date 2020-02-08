
package app.mmcstore.customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CustomerMain implements Initializable {

	private ObservableList<Button> items = FXCollections.observableArrayList();

	@FXML
	private VBox drawer;

	@FXML
	private ScrollPane scroll;

	@FXML
	private Label title;

	@FXML
	private Button hamburger;

	@FXML
	private StackPane root;

	@FXML
	private HBox main;

	@FXML
	private HBox navigationHbox;

	@FXML
	private VBox sideBarViews;

	@FXML
	private ScrollPane contentBody;

	@FXML
	private VBox content;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		populateSideBar();
		try {
			showProductView();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void populateSideBar() {
		for (Node node : sideBarViews.getChildren()) {
			if (node instanceof Button) {
				items.add((Button) node);
			}
		}
	}

	@FXML
	public void showProductView() throws IOException {
		title.setText("Products");
		contentBody.setContent(FXMLLoader.load(getClass().getResource("CustomerProductView.fxml")));
	}

	@FXML
	public void showMyOrderView() throws IOException {
		title.setText("My Orders");
		contentBody.setContent(FXMLLoader.load(getClass().getResource("CustomerMyOrderView.fxml")));
	}

	@FXML
	private void altLayout() {

		int minimum = 70;
		int max = 250;

		if (drawer.getPrefWidth() == max) {

			drawer.setPrefWidth(minimum);
			drawer.getChildren().remove(navigationHbox);

			for (Node node : sideBarViews.getChildren()) {
				if (node instanceof Button) {
					((Button) node).setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					((Button) node).setAlignment(Pos.BASELINE_CENTER);
				} else if (node instanceof TitledPane) {
					((TitledPane) node).setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
					((TitledPane) node).setAlignment(Pos.BASELINE_CENTER);
					((TitledPane) node).setExpanded(false);
					((TitledPane) node).setCollapsible(false);
				} else {
					break;
				}
			}
			addEvents();
		} else {
			drawer.setPrefWidth(max);
			drawer.getChildren().addAll(navigationHbox);
			navigationHbox.toBack();
			for (Node node : sideBarViews.getChildren()) {
				if (node instanceof Button) {
					((Button) node).setContentDisplay(ContentDisplay.LEFT);
					((Button) node).setAlignment(Pos.BASELINE_LEFT);
				} else if (node instanceof TitledPane) {
					((TitledPane) node).setContentDisplay(ContentDisplay.RIGHT);
					((TitledPane) node).setAlignment(Pos.BASELINE_RIGHT);
					((TitledPane) node).setCollapsible(true);
				} else {
					break;
				}
			}
		}
	}

	private void addEvents() {
		VBox drawerContent;
		for (Node node : drawer.getChildren()) { // root
			if (node instanceof ScrollPane) {
				drawerContent = (VBox) ((ScrollPane) node).getContent();
				for (Node child : drawerContent.getChildren()) {
					if (child instanceof Button) {
						child.setOnMouseEntered(e -> {

						});
					} else if (child instanceof TitledPane) {

					}
				}
			} else {
			}
		}
	}
}
