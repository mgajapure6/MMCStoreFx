package app.mmcstore.customer;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXSnackbarLayout;

import app.mmcstore.entity.BillProviderProduct;
import app.mmcstore.entity.ProviderProduct;
import app.mmcstore.entity.User;
import app.mmcstore.services.BillService;
import app.mmcstore.services.ProductService;
import app.mmcstore.start.App;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class CustomerProductView implements Initializable {

	@FXML
	private StackPane spRoot;

	@FXML
	private FlowPane productFlowPane;

	@FXML
	private JFXBadge cartBadge;

	@FXML
	private VBox productListAppenderVbox;

	@FXML
	private JFXDialog cartDialog;

	@FXML
	private BorderPane cartListAppenderBorderPane;

	@FXML
	private Button cartBuyNowBtn;

	@FXML
	private Label cartTotalAmt;

	@FXML
	private Button placeOrderBtn;

	@FXML
	private JFXDialog placeOrderDialog;

	@FXML
	private JFXButton cancelPlaceOrderBtn;

	@FXML
	private VBox placeOrderListView;

//	@FXML
//	private TextField payingAmountField;

	@FXML
	private Text placeOrderBillTo;
	@FXML
	private Text placeOrderBillAddr;
	@FXML
	private Text placeOrderTotQty;
	@FXML
	private Text placeOrderTotAmt;
	@FXML
	private Label alertDialogTitle;
	@FXML
	private Button alertDialogBtn;

	@FXML
	private JFXDialog alertDialog;

	@FXML
	private ToggleGroup payNowPayLaterToggleGroup;
	
	@FXML
	private TextField searchField;

	
	User loggedUser = App.getUserDetail().getLoggedUser();
	
	ObservableList<ProviderProduct> productsObservableList = FXCollections.observableArrayList();

	ListView<ProviderProduct> productListView = new ListView<>();
	ListView<ProviderProduct> cartListView = new ListView<>();


	JFXSnackbar snackbar;
	
	Long customerUnpaidBillCount ;
	
	@FXML
	VBox cartDialogBottomVbox;
	
	@FXML
	private Label unpaidErr;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				tabOne();
			}
		});
	}

	public void tabOne() {
		
		//ProductDao productDao = new ProductDao();
		ProductService productService = new ProductService();
		//BillDao billDao = new BillDao();
		BillService billService = new BillService();
		
		customerUnpaidBillCount = billService.getCustomerUnpaidBillCountByCustomerId(loggedUser.getCustomer().getCustomerId());
		System.out.println("customerUnpaidBillCount::"+customerUnpaidBillCount);
		if(customerUnpaidBillCount>=2) {
			cartDialogBottomVbox.setDisable(true);
			unpaidErr.setStyle("-fx-text-fill: red !important;-fx-font-size: 14.0px !important;");
			unpaidErr.setText("Sorry, you are not eligible to purchase from the store. \nUpto 2 bills you can purchase on pay later. \nYou have ("+customerUnpaidBillCount+") pending bills payment due. \nPlease pay now to eligible for purchase.");
		}else {
			cartDialogBottomVbox.setDisable(false);
			unpaidErr.setText("");
		}
		
		
		List<ProviderProduct> providerProducts = productService.getAllProviderProducts();
		//List<Bill> bills = billService.getBillsByCustomerId(loggedUser.getCustomer().getCustomerId());

		for (ProviderProduct providerProduct : providerProducts) {
			productsObservableList.add(providerProduct);
		}
		
		productListView.getItems().addAll(productsObservableList);
		productListView.setCellFactory(prodListView -> new ProductListViewCell());
		
		cartListView.setMaxHeight(300);

		cartListView.setCellFactory(prodListView -> new CartListViewCell());

		productListView.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue) {
				} else {
					productListView.getSelectionModel().clearSelection();
				}
			}
		});

		cartListView.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue) {
				} else {
					cartListView.getSelectionModel().clearSelection();
				}
			}
		});
		
		cartBuyNowBtn.setDisable(true);

		VBox.setVgrow(productListView, Priority.ALWAYS);
		VBox.setVgrow(cartListView, Priority.ALWAYS);

		productListAppenderVbox.getChildren().add(productListView);
		cartListAppenderBorderPane.setCenter(cartListView);

		snackbar = new JFXSnackbar((Pane) spRoot);
		snackbar.setStyle("-jfx-background-color: #f44336");

		cartDialog.setDialogContainer(spRoot);
		cartDialog.prefWidth(700);

		placeOrderDialog.setDialogContainer(spRoot);

		alertDialog.setDialogContainer(spRoot);

//		payingAmountField.textProperty().addListener(new ChangeListener<String>() {
//			@Override
//			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//				if (StringCheckerUtil.isInteger(newValue) || StringCheckerUtil.isDouble(newValue)) {
//
//					if (Double.parseDouble(newValue) > Double
//							.parseDouble(cartTotalAmt.getText().trim().split(" ")[1])) {
//						payingAmountField.getStyleClass().add("tferror");
//						placeOrderBtn.setDisable(true);
//						placeOrderBalAmt.setText(
//								"$ " + String.valueOf(Double.parseDouble(cartTotalAmt.getText().trim().split(" ")[1])));
//					} else {
//						payingAmountField.getStyleClass().remove("tferror");
//						placeOrderBtn.setDisable(false);
//						placeOrderBalAmt.setText(
//								"$ " + String.valueOf(Double.parseDouble(cartTotalAmt.getText().trim().split(" ")[1])
//										- Double.parseDouble(newValue)));
//					}
//				} else if (!newValue.isEmpty()) {
//					System.out.println("adding class:: err");
//					payingAmountField.getStyleClass().add("tferror");
//					placeOrderBtn.setDisable(true);
//					placeOrderBalAmt.setText(
//							"$ " + String.valueOf(Double.parseDouble(cartTotalAmt.getText().trim().split(" ")[1])));
//				} else {
//					placeOrderBalAmt.setText(
//							"$ " + String.valueOf(Double.parseDouble(cartTotalAmt.getText().trim().split(" ")[1])));
//				}
//
//			}
//		});

		cartBuyNowBtn.setOnAction(event -> {
			Integer itmsCount = 0;
			for (ProviderProduct providerProduct : cartListView.getItems()) {
				itmsCount = itmsCount + providerProduct.getQtyAvailable();
			}
			placeOrderBillTo.setText(loggedUser.getCustomer().getCustomerName());
			placeOrderBillAddr.setText(loggedUser.getCustomer().getAddress());
			placeOrderTotQty.setText(itmsCount.toString());
			placeOrderTotAmt.setText(cartTotalAmt.getText());
			placeOrderDialog.setTransitionType(DialogTransition.CENTER);
			placeOrderDialog.show();
		});

		placeOrderBtn.setOnAction(event -> {
			ObservableList<ProviderProduct> list = cartListView.getItems();
			Set<BillProviderProduct> billProviderProductSet = new HashSet<>();
			for (ProviderProduct providerProduct : list) {
				BillProviderProduct bpp = new BillProviderProduct();
				bpp.setQtyRequested(providerProduct.getQtyAvailable());
				bpp.setProviderProduct(providerProduct);
				billProviderProductSet.add(bpp);
			}
			JFXRadioButton rb = (JFXRadioButton) payNowPayLaterToggleGroup.getSelectedToggle();
			boolean isPaid = rb.getText().equals("Pay Later")  ? false : true; 
			boolean isSaved = billService.saveBill(loggedUser.getCustomer(), billProviderProductSet, isPaid);
			if (isSaved) {
				cartListView.getItems().clear();
				placeOrderDialog.close();
				cartDialog.close();
				cartTotalAmt.setText("$ 0.00");
				cartBadge.setText("0");
				alertDialogTitle.setText("Order Placed Successfully");
				alertDialog.setTransitionType(DialogTransition.CENTER);
				alertDialogBtn.getStyleClass().remove("btn-danger");
				alertDialogBtn.getStyleClass().add("btn-info");
				alertDialog.show();

				// Alerts.success("Success", "Order Placed Successfully");
			} else {
				// Alerts.error("Error", "Something went worng.. Unable to save this order.");
				alertDialogTitle.setText("Something went worng.. Unable to save this order");
				alertDialog.setTransitionType(DialogTransition.CENTER);
				alertDialogBtn.getStyleClass().remove("btn-info");
				alertDialogBtn.getStyleClass().add("btn-danger");
				alertDialog.show();
			}
			// bill.setBillProviderProducts(set);

		});

		cancelPlaceOrderBtn.setOnAction(event -> {
			placeOrderDialog.close();
		});

		alertDialogBtn.setOnAction(event -> {
			alertDialog.close();
		});
		
		payNowPayLaterToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov,
		        Toggle old_toggle, Toggle new_toggle) {
		            if (payNowPayLaterToggleGroup.getSelectedToggle() != null) {
		            	JFXRadioButton rb = (JFXRadioButton) payNowPayLaterToggleGroup.getSelectedToggle();
		                System.out.println("toggle::"+rb.getText());
		            }                
		        }
		});
		
		FilteredList<ProviderProduct> filteredData = new FilteredList<>(productsObservableList, p -> true);
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(productProp -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if (productProp.getProduct().getProductName().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (productProp.getProduct().getDescription().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (Double.toString(productProp.getProduct().getPrice()).contains(newValue)) {
					return true;
				}
				return false; // Does not match.
			});
		});

//		SortedList<ProviderProduct> sortedData = new SortedList<>(filteredData);
//		sortedData.comparatorProperty().bind(productListView.comparatorProperty());
		productListView.setItems(filteredData);
		
		
	}

	@FXML
	public void openCart() {
		calculateCartValue();
		cartDialog.setTransitionType(DialogTransition.CENTER);
		cartDialog.show();
	}

	void calculateCartValue() {
		Double totAmt = 0.0;
		for (ProviderProduct providerProduct : cartListView.getItems()) {
			Double itmsTotAmt = providerProduct.getProduct().getPrice() * providerProduct.getQtyAvailable();
			System.out.println("itmsTotAmt: " + itmsTotAmt);
			totAmt = totAmt + itmsTotAmt;
		}
		System.out.println("totAmt: " + totAmt);
		cartTotalAmt.setText("$ " + String.valueOf(totAmt));
	}

	class ProductListViewCell extends ListCell<ProviderProduct> {
		@FXML
		private Label productName;

		@FXML
		private Label productDesc;

		@FXML
		private Label productPrice;

		@FXML
		private Button addToCartBtn;

		@FXML
		private HBox hboxProcudtListCell;

		private FXMLLoader mLLoader;

		@Override
		protected void updateItem(ProviderProduct pe, boolean empty) {
			super.updateItem(pe, empty);

			if (empty || pe == null) {
				setText(null);
				setGraphic(null);
				setOpacity(0);
			} else {
				setOpacity(1);
				if (mLLoader == null) {
					mLLoader = new FXMLLoader(getClass().getResource("ProductListCell.fxml"));
					mLLoader.setController(this);
					try {
						mLLoader.load();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				setStyle("-fx-border-width: 0 0 1.0 0 !important; -fx-border-color: #f5f5f5 !important;");

				addToCartBtn.getStyleClass().add(pe == null ? "" : "itmId" + pe.getProduct().getProductId());
				addToCartBtn.getStyleClass().add(pe == null ? "" : "listItemIndex" + getIndex());
				addToCartBtn.setId("listCellAddToCartBtn"+pe.getProduct().getProductId());
				addToCartBtn.setOnAction(event -> {
					if (cartListView.getItems().size()>0 && cartListView.getItems().contains(pe)) {
						JFXSnackbarLayout sbl = new JFXSnackbarLayout(
								pe.getProduct().getProductName() + " is already added to cart ");
						
						snackbar.enqueue(new SnackbarEvent(sbl, Duration.millis(2000)));
					}else {
						pe.setQtyAvailable(1);
						cartListView.getItems().add(pe);
						JFXSnackbarLayout sbl = new JFXSnackbarLayout(
								pe.getProduct().getProductName() + " is added to cart ");
						
						snackbar.enqueue(new SnackbarEvent(sbl, Duration.millis(2000)));
					}
					
					cartBadge.setText(String.valueOf(cartListView.getItems().size()));
					
					//Button btn = (Button) productListView.lookup("#listCellAddToCartBtn"+pe.getProduct().getProductId());
					//btn.setDisable(true);
					//addToCartBtn.setDisable(true);

				});

				productName.setText(String.valueOf(pe.getProduct().getProductName()));
				productDesc.setText(String.valueOf(pe.getProduct().getDescription()));
				productPrice.setText("$ " + pe.getProduct().getPrice().toString());
				setGraphic(hboxProcudtListCell);
			}

		}

	}

	class CartListViewCell extends ListCell<ProviderProduct> {
		@FXML
		private Label cartProductName;

		@FXML
		private Label cartProductDesc;

		@FXML
		private Spinner<Integer> cartProductQty;

		@FXML
		private Label cartProductUnitPrice;

		@FXML
		private Label cartProductTotAmt;

		@FXML
		private Button removeCartItmBtn;

		@FXML
		private HBox hboxCartListCell;

		private FXMLLoader mLLoader;

		IntegerSpinnerValueFactory spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
				Integer.MAX_VALUE, 1, 1);

		@Override
		protected void updateItem(ProviderProduct pe, boolean empty) {
			super.updateItem(pe, empty);

			if (empty || pe == null) {
				setText(null);
				setGraphic(null);
				setOpacity(0);
			} else {
				setOpacity(1);
				if (mLLoader == null) {
					mLLoader = new FXMLLoader(getClass().getResource("CartListCell.fxml"));
					mLLoader.setController(this);

					try {
						mLLoader.load();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
				
				setStyle("-fx-border-width: 0 0 1.0 0 !important; -fx-border-color: #f5f5f5 !important;");
				
				cartProductQty.setId("cartProductQtyField"+ pe.getProduct().getProductId());

				cartProductQty.setValueFactory(spinnerValueFactory);
				spinnerValueFactory.setValue(pe.getQtyAvailable());
				cartProductTotAmt.setText("$ " + String.valueOf((1 * pe.getProduct().getPrice())));

				cartProductQty.valueProperty().addListener((obs, oldValue, newValue) -> {
					pe.setQtyAvailable(newValue);
					cartProductTotAmt.setText("$ " + newValue * pe.getProduct().getPrice());
					calculateCartValue();
				});

				removeCartItmBtn.getStyleClass().add(pe == null ? "" : "itmId" + pe.getProduct().getProductId());
				removeCartItmBtn.getStyleClass().add(pe == null ? "" : "listItemIndex" + getIndex());

				removeCartItmBtn.setOnAction(event -> {

					cartListView.getItems().remove(getIndex());

					cartBadge.setText(String.valueOf(cartListView.getItems().size()));

					if (cartListView.getItems().size() <= 0) {
						cartBuyNowBtn.setDisable(true);
					} else {
						cartBuyNowBtn.setDisable(false);
					}

					calculateCartValue();

					snackbar.fireEvent(new SnackbarEvent(
							new JFXSnackbarLayout(pe.getProduct().getProductName() + " is remove from cart ", "CLOSE",
									action -> snackbar.close()),
							Duration.millis(2000), null));
					
				});

				if (cartListView.getItems().size() <= 0) {
					cartBuyNowBtn.setDisable(true);
				} else {
					cartBuyNowBtn.setDisable(false);
				}

				cartProductName.setText(String.valueOf(pe.getProduct().getProductName()));
				cartProductDesc.setText(String.valueOf(pe.getProduct().getDescription()));
				cartProductUnitPrice.setText("$ " + pe.getProduct().getPrice().toString());
				setGraphic(hboxCartListCell);
			}

		}

	}

}
