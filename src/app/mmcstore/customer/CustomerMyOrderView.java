package app.mmcstore.customer;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSnackbar;

import app.mmcstore.common.DateFormatUtil;
import app.mmcstore.dto.ProductDto;
import app.mmcstore.entity.Bill;
import app.mmcstore.entity.User;
import app.mmcstore.services.BillService;
import app.mmcstore.start.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CustomerMyOrderView implements Initializable {

	@FXML
	private StackPane spRoot;

	JFXSnackbar snackbar;

	ListView<Bill> myOrderListView = new ListView<>();

	@FXML
	private Label alertDialogTitle;
	@FXML
	private Button alertDialogBtn;

	@FXML
	private JFXDialog alertDialog;

	@FXML
	Label myOrdersTotalOrders;
	@FXML
	Label myOrderPendingBills;
	@FXML
	Label myOrderPendingAmt;
	@FXML
	Label myOrderPaidAmt;
	@FXML
	Label myOrderNumberTitle;
	@FXML
	BorderPane myOrderDetailListAppenderBorderPane;
	@FXML
	Label myOrderTotalAmt;
	@FXML
	Button myOrderOkBtn;

	@FXML
	VBox billListAppenderVbox;

	@FXML
	private JFXDialog myOrderDetailDialog;
	
	@FXML
	private ToggleGroup payNowPayLaterToggleGroup;
	
	@FXML
	HBox radioHBox;

	User loggedUser = App.getUserDetail().getLoggedUser();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tabOne();
	}
	
	
	public void tabOne() {
		//BillDao billDao = new BillDao();
		BillService billService = new BillService();
		List<Bill> bills = billService.getBillsByCustomerId(loggedUser.getCustomer().getCustomerId());

		for (Bill bill : bills) {
			myOrderListView.getItems().add(bill);
		}

		myOrderListView.setCellFactory(prodListView -> new MyOrderListViewCell());

//		myOrderListView.focusedProperty().addListener(new ChangeListener<Boolean>() {
//			@Override
//			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
//					Boolean newPropertyValue) {
//				if (newPropertyValue) {
//				} else {
//					myOrderListView.getSelectionModel().clearSelection();
//				}
//			}
//		});

		VBox.setVgrow(myOrderListView, Priority.ALWAYS);
		billListAppenderVbox.getChildren().add(myOrderListView);

		snackbar = new JFXSnackbar((Pane) spRoot);
		snackbar.setStyle("-jfx-background-color: #f44336");

		alertDialog.setDialogContainer(spRoot);
		
		myOrderDetailDialog.setDialogContainer(spRoot);

		alertDialogBtn.setOnAction(event -> {
			alertDialog.close();
		});
		
		myOrderOkBtn.setOnAction(event -> {
			
			if(radioHBox.isVisible()) {
				JFXRadioButton rb = (JFXRadioButton) payNowPayLaterToggleGroup.getSelectedToggle();
				boolean isPaid = rb.getText().equals("Pay Later")  ? false : true; 
				if(isPaid) {
					int indexCell = myOrderListView.getSelectionModel().getSelectedIndex();
					Bill billl = myOrderListView.getSelectionModel().selectedItemProperty().get();
					billl.setCustomer(loggedUser.getCustomer());
					billl.setIsPaid(true);
					boolean isSaved = billService.updateOnlyBill(billl);
					if(isSaved) {
						alertDialogTitle.setText("Order Updated Successfully");
						alertDialog.setTransitionType(DialogTransition.CENTER);
						alertDialogBtn.getStyleClass().remove("btn-danger");
						alertDialogBtn.getStyleClass().add("btn-success");
						alertDialog.show();
						myOrderDetailDialog.close();
						//tabOne();
						myOrderListView.getItems().remove(indexCell);
						
						myOrderListView.getItems().add(billl);
					}else {
						alertDialogTitle.setText("Something went worng.. Unable to save this order");
						alertDialog.setTransitionType(DialogTransition.CENTER);
						alertDialogBtn.getStyleClass().remove("btn-success");
						alertDialogBtn.getStyleClass().add("btn-danger");
						alertDialog.show();
					}
				}else {
					myOrderDetailDialog.close();
				}
			}else {
				myOrderDetailDialog.close();
			}
		});
	}

	class MyOrderListViewCell extends ListCell<Bill> {

		@FXML
		private Button moCellViewOrderBtn;

		@FXML
		private HBox hboxMyOrderListCell;

		@FXML
		Label moCellOrderId;
		@FXML
		Label moCellDate;
		@FXML
		Label moCellOrderAmt;
		@FXML
		Label myCellStatus;

		private FXMLLoader mLLoader;

		@Override
		protected void updateItem(Bill bill, boolean empty) {
			super.updateItem(bill, empty);

			if (empty || bill == null) {
				setText(null);
				setGraphic(null);
				setOpacity(0);
			} else {
				setOpacity(1);
				if (mLLoader == null) {
					mLLoader = new FXMLLoader(getClass().getResource("MyOderCell.fxml"));
					mLLoader.setController(this);
					try {
						mLLoader.load();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				moCellOrderId.setText("# Order Number " + bill.getBillId());

				moCellDate.setText("# Order Date " + DateFormatUtil.dateToString(bill.getBillDate(), "dd-MM-YYYY"));

				moCellOrderAmt.setText("$ " + bill.getBillAmount());

				myCellStatus.setText(bill.getIsPaid() ? "Paid" : "Unpaid");

				moCellViewOrderBtn.setOnAction(event -> {
					BillService billService = new BillService();
					ListView<ProductDto> myOrderDetialListView = new ListView<>();

					List<ProductDto> pdts = billService.getBillDetailById(bill.getBillId());

					for (ProductDto productDto : pdts) {
						myOrderDetialListView.getItems().add(productDto);
					}

					myOrderDetialListView.setPrefHeight(200);
					
					if(!bill.getIsPaid()) {
						radioHBox.setVisible(true);
					}else {
						radioHBox.setVisible(false);
					}
					
					myOrderListView.getSelectionModel().select(getIndex());
					

					VBox.setVgrow(myOrderDetialListView, Priority.ALWAYS);
					myOrderDetailListAppenderBorderPane.setCenter(myOrderDetialListView);
					myOrderDetialListView.setCellFactory(prodListView -> new MyOrderDetailListViewCell());
					myOrderNumberTitle.setText("# Order number " + bill.getBillId());
					myOrderTotalAmt.setText("$ " + bill.getBillAmount());
					myOrderDetailDialog.setTransitionType(DialogTransition.CENTER);
					myOrderDetailDialog.show();
				});
				setGraphic(hboxMyOrderListCell);
			}

		}

	}

	class MyOrderDetailListViewCell extends ListCell<ProductDto> {

		@FXML
		private HBox hboxOrderDetailListCell;

		@FXML
		Label moDetailCellProductName;

		@FXML
		Label moDetailCellProductDesc;
		@FXML
		Label moDetailCellProductQty;
		@FXML
		Label moDetailCellProductUnitPrice;
		@FXML
		Label moDetailCellProductTotAmt;

		private FXMLLoader mLLoader;

		@Override
		protected void updateItem(ProductDto pd, boolean empty) {
			super.updateItem(pd, empty);

			if (empty || pd == null) {
				setText(null);
				setGraphic(null);
				setOpacity(0);
			} else {
				setOpacity(1);
				if (mLLoader == null) {
					mLLoader = new FXMLLoader(getClass().getResource("MyOrderDetailListCell.fxml"));
					mLLoader.setController(this);
					try {
						mLLoader.load();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				moDetailCellProductName.setText("" + pd.getProductName());

				moDetailCellProductDesc.setText("" + pd.getDescription());

				moDetailCellProductQty.setText("" + pd.getQty());

				moDetailCellProductUnitPrice.setText("" + pd.getRate());

				moDetailCellProductTotAmt.setText("" + pd.getAmount());

//				moCellViewOrderBtn.setOnAction(event -> {
//
//				});
				setGraphic(hboxOrderDetailListCell);
			}

		}

	}

}
