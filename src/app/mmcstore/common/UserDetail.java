
package app.mmcstore.common;

import org.controlsfx.control.PopOver;

import com.gn.GNAvatarView;
import com.gn.decorator.component.GNControl;

import app.mmcstore.entity.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class UserDetail extends GNControl {

	private User user;
	public static PopOver root;

	private Button signOut = new Button("Sign Out");
	// private Button profile = new Button("Profile");
	private PopOver popOver = new PopOver();

	private StringProperty header = new SimpleStringProperty();
	
	public User getLoggedUser() {
		return user;
	}

//	public UserDetail() {
//		super("", "");
//		//UserDetail.root = popOver;
//		//popOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
//		//popOver.setArrowIndent(0);
//		//popOver.setArrowSize(0);
//		//popOver.setCornerRadius(0);
//		//popOver.setContentNode(configLayout());
//	}

	public UserDetail(User user) {
		super("", "");
		this.user = user;
		this.headerProperty().set(
				user.getIsCustomer() ? "Customer : "+user.getCustomer().getCustomerName() : "Provider : "+user.getProvider().getProviderName());
		UserDetail.root = popOver;
		popOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
		popOver.setArrowIndent(0);
		popOver.setArrowSize(0);
		popOver.setCornerRadius(0);
		popOver.setContentNode(configLayout());
	}

	@Override
	public Node icon() {
		Image image = new Image(getClass().getResource("/img/img_avatar.png").toExternalForm());
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(30);
		imageView.setFitWidth(30);

		Circle circle = new Circle(12);
		circle.setStroke(Color.WHITE);
		circle.setStrokeWidth(5);
		circle.setCenterX(imageView.getFitWidth() / 2);
		circle.setCenterY(imageView.getFitHeight() / 2);
		imageView.setClip(circle);

		return imageView;
	}

	@Override
	public Node status() {
		return null;
	}

	@Override
	public Node action() {
		Hyperlink link = new Hyperlink();
		link.textProperty().bind(super.textProperty());
		link.setMinHeight(30);
		link.setOnMouseClicked(event -> popOver.show(link, 0));
		return link;
	}

	private VBox configLayout() {

		VBox box = new VBox();
		VBox background = new VBox();
		Label header = new Label();
		header.textProperty().bind(headerProperty());
		Label subTitle = new Label();
		HBox content = new HBox();
		HBox layoutContent = new HBox();
		layoutContent.setAlignment(Pos.CENTER);

		header.getStyleClass().add("h4");

//        System.out.println(getName());
		header.textProperty().bind(headerProperty());
		subTitle.setText(user.getIsCustomer()
				? "Customer since " +  DateFormatUtil.dateToString(user.getRegisterDate(), "dd-MM-YYYY")
				: "Provider since " +  DateFormatUtil.dateToString(user.getRegisterDate(), "dd-MM-YYYY"));

		GNAvatarView gnAvatar = new GNAvatarView();
		gnAvatar.setImage(new Image(getClass().getResource("/img/img_avatar.png").toExternalForm()));

		background.setPrefHeight(500);
		box.setPrefWidth(287);
		box.setPrefHeight(200);

		signOut.getStyleClass().addAll("outlined");
		// profile.getStyleClass().addAll("outlined");

		signOut.setPrefWidth(100);
		// profile.setPrefWidth(100);

		signOut.setMinHeight(40);
		// profile.setMinHeight(40);
		
		layoutContent.setPadding(new Insets(10, 10, 10, 10));

		layoutContent.getChildren().add(signOut);
		// layoutContent.add(profile, 1, 0);

		ColumnConstraints column1 = new ColumnConstraints(100, 100, Double.MAX_VALUE);
		ColumnConstraints column2 = new ColumnConstraints(100, 100, Double.MAX_VALUE);

		RowConstraints row = new RowConstraints(100, 100, Double.MAX_VALUE);

		column1.setHalignment(HPos.CENTER);
		column2.setHalignment(HPos.CENTER);

		column2.setHgrow(Priority.ALWAYS);
		column1.setHgrow(Priority.ALWAYS);

		row.setValignment(VPos.CENTER);

		// layoutContent.getColumnConstraints().addAll(column1, column2);
		// layoutContent.getRowConstraints().addAll(row);

		background.setAlignment(Pos.CENTER);
		background.getChildren().addAll(gnAvatar, header, subTitle);

		content.setPrefHeight(300);
		content.setAlignment(Pos.CENTER);

		content.getChildren().add(layoutContent);

		VBox.setVgrow(content, Priority.ALWAYS);
		HBox.setHgrow(layoutContent, Priority.ALWAYS);

		box.getChildren().addAll(background, content);

		popOver.getRoot().getStylesheets()
				.add(getClass().getResource("/css/poplight.css").toExternalForm());

		return box;
	}

	public String getName() {
		return user.getIsCustomer() ? user.getCustomer().getCustomerName() : user.getProvider().getProviderName();
	}

	public PopOver getPopOver() {
		return popOver;
	}

	public void setSignAction(EventHandler<MouseEvent> event) {
		this.signOut.setOnMouseClicked(event);
	}

//	public void setProfileAction(EventHandler<MouseEvent> event) {
//		this.profile.setOnMouseClicked(event);
//	}

	public void setHeader(String header) {
		headerProperty().setValue(header);
	}

	public String getHeader() {
		return header.get();
	}

	public StringProperty headerProperty() {
		return header;
	}
}