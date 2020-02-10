package app.mmcstore.provider;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXSnackbar;

import app.mmcstore.common.DateFormatUtil;
import app.mmcstore.common.ITextTableGenerator;
import app.mmcstore.common.PdfViewer;
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
import javafx.stage.FileChooser;
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
	private Button reportBtn;

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
		
		reportBtn.setOnAction((e) -> {
			FileChooser pdfFileChooser = new FileChooser();// "C:\\Users\\Mgg\\Documents"
			pdfFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			pdfFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
			pdfFileChooser.setInitialFileName(loggedUser.getProvider().getProviderName().replaceAll(" ", "_")
					+ "_Bills_Report" + DateFormatUtil.dateToString(new Date(), "dd-MM-yyyy") + "");
			pdfFileChooser.setTitle("Save Bills Report");
			File file = pdfFileChooser.showSaveDialog(reportBtn.getScene().getWindow());
			if (file != null) {
				String savePath = file.getAbsolutePath();
				// System.out.println(savePath);
				String title = "MMC Store Provider Bills Report",
						subTitle = "Provider : " + loggedUser.getProvider().getProviderName();
				int colNum = 10;
				List<List<String>> dataSet = new ArrayList<List<String>>();
				String[] tableTitleList = { " Sr. No. ", " Bill No ", " Status ",
						" Customer ", " Address ", " product Name ", " Qty Requested ", " Qty Available ", " Price ", " Bill Amount " };
				dataSet.add(Arrays.asList(tableTitleList));
				int i = 1;
				for (ProviderBillDto pbd : billsObservableList) {
					List<String> dataLine = new ArrayList<>();
					dataLine.add(" " + i++ + "");
					dataLine.add(String.valueOf(pbd.getBillId()));
					dataLine.add(pbd.getStatus());
					dataLine.add(pbd.getCustomerName());
					dataLine.add(pbd.getAddress());
					dataLine.add(pbd.getProductName());
					dataLine.add(pbd.getQtyRequested().toString());
					dataLine.add(pbd.getQtyAvailable().toString());
					dataLine.add(pbd.getPrice().toString());
					dataLine.add(pbd.getBillAmount().toString());
					dataSet.add(dataLine);
				}
				Document document = new Document(PageSize.A4.rotate());
				try {
					ITextTableGenerator.createPdfTable(document, savePath, title, subTitle, colNum, dataSet);
					PdfViewer.viewPDFFile(savePath, title);
				} catch (IOException ex) {
					Logger.getLogger(ProviderProductView.class.getName()).log(Level.ERROR, null, ex);
				} catch (DocumentException ex) {
					Logger.getLogger(ProviderProductView.class.getName()).log(Level.ERROR, null, ex);
				}
			}
		});


	}

}
