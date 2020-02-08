package app.mmcstore.provider;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXSnackbar;

import app.mmcstore.common.DateFormatUtil;
import app.mmcstore.dto.ProviderBillDto;
import app.mmcstore.entity.User;
import app.mmcstore.services.ProviderService;
import app.mmcstore.start.App;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ProviderBillView implements Initializable {

	@FXML
	private StackPane spRoot;

	@FXML
	private VBox billListAppenderVbox;

	@FXML
	private Label alertDialogTitle;

	@FXML
	private Button alertDialogBtn;

	@FXML
	private JFXDialog alertDialog;

	@FXML
	private TextField searchField;

	@FXML
	private TableView<ProviderBillDto> tableView;
	@FXML
	private TableColumn<ProviderBillDto, ProviderBillDto> c1;
	@FXML
	private TableColumn<ProviderBillDto, Integer> c2;
	@FXML
	private TableColumn<ProviderBillDto, String> c3;
	@FXML
	private TableColumn<ProviderBillDto, String> c4;
	@FXML
	private TableColumn<ProviderBillDto, String> c5;
	@FXML
	private TableColumn<ProviderBillDto, String> c6;
	@FXML
	private TableColumn<ProviderBillDto, String> c7;
	@FXML
	private TableColumn<ProviderBillDto, Integer> c8;
	@FXML
	private TableColumn<ProviderBillDto, Integer> c9;
	@FXML
	private TableColumn<ProviderBillDto, Double> c10;
	@FXML
	private TableColumn<ProviderBillDto, Double> c11;

	User loggedUser = App.getUserDetail().getLoggedUser();

	ObservableList<ProviderBillDto> billsObservableList = FXCollections.observableArrayList();

	JFXSnackbar snackbar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				initData();
			}
		});
	}

	public void initData() {
		ProviderService providerService = new ProviderService();
		List<ProviderBillDto> providerBills = providerService
				.getProviderBillsById(loggedUser.getProvider().getProviderId());

		for (ProviderBillDto pbdto : providerBills) {
			billsObservableList.add(pbdto);
		}

		c1.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ProviderBillDto, ProviderBillDto>, ObservableValue<ProviderBillDto>>() {
					@SuppressWarnings({ "unchecked", "rawtypes" })
					@Override
					public ObservableValue<ProviderBillDto> call(
							TableColumn.CellDataFeatures<ProviderBillDto, ProviderBillDto> p) {
						return new ReadOnlyObjectWrapper(tableView.getItems().indexOf(p.getValue()) + 1 + "");
					}
				});
		c2.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ProviderBillDto, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ProviderBillDto, Integer> pp) {
						return new SimpleObjectProperty<>(pp.getValue().getBillId());
					}

				});
		c3.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ProviderBillDto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<ProviderBillDto, String> pp) {
						return new SimpleObjectProperty<>(
								DateFormatUtil.dateToString(pp.getValue().getBillDate(), "dd-MM-YYYY"));
					}

				});

		c4.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ProviderBillDto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<ProviderBillDto, String> pp) {
						return new SimpleObjectProperty<>(pp.getValue().getStatus());
					}

				});
		c5.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ProviderBillDto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<ProviderBillDto, String> pp) {
						return new SimpleObjectProperty<>(pp.getValue().getCustomerName());
					}

				});
		c6.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ProviderBillDto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<ProviderBillDto, String> pp) {
						return new SimpleObjectProperty<>(pp.getValue().getAddress());
					}

				});
		c7.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ProviderBillDto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<ProviderBillDto, String> pp) {
						return new SimpleObjectProperty<>(pp.getValue().getProductName());
					}

				});
		c8.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ProviderBillDto, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ProviderBillDto, Integer> pp) {
						return new SimpleObjectProperty<>(pp.getValue().getQtyRequested());
					}

				});
		c9.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ProviderBillDto, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ProviderBillDto, Integer> pp) {
						return new SimpleObjectProperty<>(pp.getValue().getQtyAvailable());
					}

				});
		c10.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ProviderBillDto, Double>, ObservableValue<Double>>() {
					@Override
					public ObservableValue<Double> call(TableColumn.CellDataFeatures<ProviderBillDto, Double> pp) {
						return new SimpleObjectProperty<>(pp.getValue().getPrice());
					}

				});
		c11.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ProviderBillDto, Double>, ObservableValue<Double>>() {
					@Override
					public ObservableValue<Double> call(TableColumn.CellDataFeatures<ProviderBillDto, Double> pp) {
						return new SimpleObjectProperty<>(pp.getValue().getBillAmount());
					}

				});

		FilteredList<ProviderBillDto> filteredData = new FilteredList<>(billsObservableList, p -> true);
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(pb -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if (pb.getProductName().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (pb.getDescription().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (pb.getCustomerName().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (pb.getAddress().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (pb.getStatus().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (DateFormatUtil.dateToString(pb.getBillDate(), "dd-MM-YYYY").contains(lowerCaseFilter)) {
					return true;
				} else if (Double.toString(pb.getPrice()).contains(newValue)) {
					return true;
				} else if (Double.toString(pb.getBillAmount()).contains(newValue)) {
					return true;
				} else if (Integer.toString(pb.getBillId()).contains(newValue)) {
					return true;
				} else if (Integer.toString(pb.getQtyAvailable()).contains(newValue)) {
					return true;
				} else if (Integer.toString(pb.getQtyRequested()).contains(newValue)) {
					return true;
				}
				return false; // Does not match.
			});
		});

		SortedList<ProviderBillDto> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tableView.comparatorProperty());
		tableView.setItems(sortedData);

		snackbar = new JFXSnackbar((Pane) spRoot);
		snackbar.setStyle("-jfx-background-color: #f44336");

		alertDialog.setDialogContainer(spRoot);

		// billListAppenderVbox.

		alertDialogBtn.setOnAction(event -> {
			alertDialog.close();
		});

	}

}
