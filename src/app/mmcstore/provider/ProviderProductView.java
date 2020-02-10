
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
import com.jfoenix.controls.JFXDialog.DialogTransition;

import animatefx.animation.SlideInLeft;
import app.mmcstore.common.DateFormatUtil;
import app.mmcstore.common.ITextTableGenerator;
import app.mmcstore.common.PdfViewer;
import app.mmcstore.dto.ProviderProductDto;
import app.mmcstore.entity.Product;
import app.mmcstore.entity.ProviderProduct;
import app.mmcstore.entity.User;
import app.mmcstore.services.ProductService;
import app.mmcstore.start.App;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Callback;

public class ProviderProductView implements Initializable {
	@FXML
	private StackPane root;
	@FXML
	private TableView<ProviderProductDto> tableView;
	@FXML
	private TableColumn<ProviderProductDto, ProviderProductDto> c1;
	@FXML
	private TableColumn<ProviderProductDto, String> c2;
	@FXML
	private TableColumn<ProviderProductDto, String> c3;
	@FXML
	private TableColumn<String, ProviderProductDto> c4;
	@FXML
	private TableColumn<ProviderProductDto, Double> c5;
	@FXML
	private TableColumn<ProviderProductDto, ProviderProductDto> c6;

	@FXML
	private Button addProductBtn;
	@FXML
	private Button reportBtn;
	@FXML
	private TextField searchField;//
	@FXML
	private Button saveProductButton;
	@FXML
	private JFXDialog productDialog;
	@FXML
	private TextField productNameField;
	@FXML
	private TextArea productDescField;
	@FXML
	private TextField productQtyField;
	@FXML
	private TextField productPriceField;
	@FXML
	private JFXDialog productDeleteConfirmDialog;
	@FXML
	private VBox btnVbox;

	@FXML
	private Label lbl_pname_err;
	@FXML
	private Label lbl_pqty_err;
	@FXML
	private Label lbl_pprice_err;
	@FXML
	private Button closeDialogButton;
	@FXML
	private Button alertDialogBtn;
	@FXML
	private Label alertDialogTitle;
	@FXML
	private JFXDialog alertDialog;
	@FXML
	private Label productDialogTitle;

	User loggedUser = App.getUserDetail().getLoggedUser();

	ObservableList<ProviderProductDto> productsObservableList = FXCollections.observableArrayList();

	private String flag;

	private Integer providerProductId;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Integer getProviderProductId() {
		return providerProductId;
	}

	public void setProviderProductId(Integer providerProductId) {
		this.providerProductId = providerProductId;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFlag("N");
		saveProductButton.setText("Save Product");
		productDialogTitle.setText("Add Product");
		alertDialog.setDialogContainer(root);
		lbl_pname_err.setVisible(false);
		lbl_pqty_err.setVisible(false);
		lbl_pprice_err.setVisible(false);
		ProductService productService = new ProductService();
		// ProductDao productDao = new ProductDao();
		List<ProviderProductDto> providerProducts = productService
				.getAllProviderProductsByProviderId(loggedUser.getProvider().getProviderId(), loggedUser.getProvider());

		for (ProviderProductDto providerProduct : providerProducts) {
			productsObservableList.add(providerProduct);
		}
//		for (int i = 1; i <= 100; i++) {
//			productsObservableList
//					.add(new ProductEntity(i, "Product " + i, "Product Desc " + i, (i + 1.00), (i * 10.5)));
//		}

		// c1.setCellValueFactory(new PropertyValueFactory<>("product.productId"));

		c1.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ProviderProductDto, ProviderProductDto>, ObservableValue<ProviderProductDto>>() {
					@SuppressWarnings({ "unchecked", "rawtypes" })
					@Override
					public ObservableValue<ProviderProductDto> call(
							TableColumn.CellDataFeatures<ProviderProductDto, ProviderProductDto> p) {
						return new ReadOnlyObjectWrapper(tableView.getItems().indexOf(p.getValue()) + 1 + "");
					}
				});

		c2.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ProviderProductDto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<ProviderProductDto, String> pp) {
						return new SimpleObjectProperty<>(pp.getValue().getProductName());
					}

				});
		c3.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ProviderProductDto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<ProviderProductDto, String> pp) {
						return new SimpleObjectProperty<>(pp.getValue().getDescription());
					}

				});
		c4.setCellValueFactory(new PropertyValueFactory<>("qtyAvailable"));
		c5.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ProviderProductDto, Double>, ObservableValue<Double>>() {
					@Override
					public ObservableValue<Double> call(TableColumn.CellDataFeatures<ProviderProductDto, Double> pp) {
						return new SimpleObjectProperty<>(pp.getValue().getPrice());
					}

				});

		c6.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

		c6.setCellFactory(param -> new TableCell<ProviderProductDto, ProviderProductDto>() {
			@Override
			protected void updateItem(ProviderProductDto ppd, boolean empty) {
				super.updateItem(ppd, empty);

				if (ppd == null) {
					setGraphic(null);
					return;
				}

				HBox hb = new HBox();
				hb.setAlignment(Pos.CENTER_RIGHT);
				hb.setSpacing(15.0);

				FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);
				editIcon.setSize("20.0");
				editIcon.setFill(Color.WHITE);
				Button editBtn = new Button("", editIcon);
				Tooltip editTip = new Tooltip("Edit Product");
				Tooltip.install(editBtn, editTip);
				editBtn.getStyleClass().add("btn-info");

				editBtn.setOnAction(e -> {
					setFlag("E");
					setProviderProductId(ppd.getProviderProductId());
					saveProductButton.setText("Update Product");
					productDialogTitle.setText("Update Product");
					tableView.getSelectionModel().select(getIndex());
					productNameField.setText(ppd.getProductName());
					productDescField.setText(ppd.getDescription());
					productQtyField.setText(String.valueOf(ppd.getQtyAvailable()));
					productPriceField.setText(String.valueOf(ppd.getPrice()));
					productDialog.setTransitionType(DialogTransition.CENTER);
					productDialog.show();
				});

				hb.getChildren().addAll(editBtn);
				setGraphic(editBtn);
			}

		});
		tableView.setItems(productsObservableList);

		FilteredList<ProviderProductDto> filteredData = new FilteredList<>(productsObservableList, p -> true);
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(productProp -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if (productProp.getProductName().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (productProp.getDescription().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (Double.toString(productProp.getPrice()).contains(newValue)) {
					return true;
				}
				return false; // Does not match.
			});
		});

		SortedList<ProviderProductDto> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tableView.comparatorProperty());
		tableView.setItems(sortedData);

		productDialog.setDialogContainer(root);
		productDeleteConfirmDialog.setDialogContainer(root);

		addProductBtn.setOnAction((e) -> {
			setFlag("N");
			setProviderProductId(0);
			saveProductButton.setText("Save Product");
			productDialogTitle.setText("Add Product");
			productNameField.setText("");
			productDescField.setText("");
			productQtyField.setText("");
			productPriceField.setText("");
			productDialog.setTransitionType(DialogTransition.CENTER);
			productDialog.show();
		});

		root.setOnMouseClicked((e) -> {
			tableView.getSelectionModel().clearSelection();
		});

		btnVbox.setOnMouseClicked((e) -> {
			tableView.getSelectionModel().clearSelection();
		});

		saveProductButton.setOnMouseClicked((e) -> {
			if (validProductName() && validProductPrice() && validProductQty()) {
				Product product = null;
				boolean isSaved = false;
				if (getFlag().equals("N")) {
					product = new Product(null, productNameField.getText(), productDescField.getText(),
							Double.parseDouble(productPriceField.getText()), null);
					System.out.println("product save:::" + product.toString());
					ProviderProduct pp = new ProviderProduct();
					pp.setProduct(product);
					pp.setProvider(loggedUser.getProvider());
					pp.setProviderProductId(getProviderProductId());
					pp.setQtyAvailable(Integer.parseInt(productQtyField.getText()));
					isSaved = productService.saveProduct(pp);
				} else {
					ProviderProductDto p = productsObservableList.get(tableView.getSelectionModel().getSelectedIndex());
					product = new Product(p.getProductId(), productNameField.getText(), productDescField.getText(),
							Double.parseDouble(productPriceField.getText()), null);
					ProviderProduct pp = new ProviderProduct();
					pp.setProduct(product);
					pp.setProvider(loggedUser.getProvider());
					pp.setProviderProductId(getProviderProductId());
					pp.setQtyAvailable(Integer.parseInt(productQtyField.getText()));
					System.out.println("ProviderProductId update:::" + getProviderProductId());
					isSaved = productService.updateProduct(pp);
				}

				if (isSaved) {
					productsObservableList.clear();
					List<ProviderProductDto> pps = productService.getAllProviderProductsByProviderId(
							loggedUser.getProvider().getProviderId(), loggedUser.getProvider());

					for (ProviderProductDto providerProduct : pps) {
						productsObservableList.add(providerProduct);
					}
					productDialog.close();
					if (getFlag().equals("N")) {
						alertDialogTitle.setText("Product Saved Successfully");
					} else {
						alertDialogTitle.setText("Product Updated Successfully");
					}

					alertDialog.setTransitionType(DialogTransition.CENTER);
					alertDialogBtn.getStyleClass().remove("btn-danger");
					alertDialogBtn.getStyleClass().add("btn-info");
					alertDialog.show();
					productNameField.setText("");
					productDescField.setText("");
					productQtyField.setText("");
					productPriceField.setText("");

				} else {
					if (getFlag().equals("N")) {
						alertDialogTitle.setText("Error.. Unable to save product");
					} else {
						alertDialogTitle.setText("Error.. Unable to update product");
					}
					alertDialog.setTransitionType(DialogTransition.CENTER);
					alertDialogBtn.getStyleClass().remove("btn-info");
					alertDialogBtn.getStyleClass().add("btn-danger");
					alertDialog.show();
				}
			} else {
				// validProductName() ? :
				if (!validProductName()) {
					lbl_pname_err.setVisible(true);
				} else {
					lbl_pname_err.setVisible(false);
				}
				if (!validProductQty()) {
					lbl_pqty_err.setVisible(true);
				} else {
					lbl_pqty_err.setVisible(false);
				}
				if (!validProductPrice()) {
					lbl_pprice_err.setVisible(true);
				} else {
					lbl_pprice_err.setVisible(false);
				}
			}
		});

		productNameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!validProductName()) {
				if (!newValue) {
					lbl_pname_err.setVisible(true);
					new SlideInLeft(lbl_pname_err).play();
				} else {
					lbl_pname_err.setVisible(false);
				}
			} else {
				lbl_pname_err.setVisible(false);
			}
		});

		productQtyField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!validProductQty()) {
				if (!newValue) {
					lbl_pqty_err.setVisible(true);
					new SlideInLeft(lbl_pqty_err).play();
				} else {
					lbl_pqty_err.setVisible(false);
				}
			} else {
				lbl_pqty_err.setVisible(false);
			}
		});

		productPriceField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!validProductQty()) {
				if (!newValue) {
					lbl_pprice_err.setVisible(true);
					new SlideInLeft(lbl_pprice_err).play();
				} else {
					lbl_pprice_err.setVisible(false);
				}
			} else {
				lbl_pprice_err.setVisible(false);
			}
		});

		closeDialogButton.setOnAction((e) -> {
			productNameField.setText("");
			productDescField.setText("");
			productQtyField.setText("");
			productPriceField.setText("");
			productDialog.close();
		});

		alertDialogBtn.setOnAction((e) -> {
			setFlag("N");
			saveProductButton.setText("Save Product");
			productDialogTitle.setText("Add Product");
			productNameField.setText("");
			productDescField.setText("");
			productQtyField.setText("");
			productPriceField.setText("");
			alertDialog.close();
		});

		reportBtn.setOnAction((e) -> {
			FileChooser pdfFileChooser = new FileChooser();// "C:\\Users\\Mgg\\Documents"
			pdfFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
			pdfFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
			pdfFileChooser.setInitialFileName(loggedUser.getProvider().getProviderName().replaceAll(" ", "_")
					+ "_Products_Report" + DateFormatUtil.dateToString(new Date(), "dd-MM-yyyy") + "");
			pdfFileChooser.setTitle("Save Products Report");
			File file = pdfFileChooser.showSaveDialog(reportBtn.getScene().getWindow());
			if (file != null) {
				String savePath = file.getAbsolutePath();
				// System.out.println(savePath);
				String title = "MMC Store Products Report",
						subTitle = "Provider : " + loggedUser.getProvider().getProviderName();
				int colNum = 5;
				List<List<String>> dataSet = new ArrayList<List<String>>();
				String[] tableTitleList = { " Sr. No. ", " Product Name ", " Product Description ",
						" Available Quantity ", " Product Price " };
				dataSet.add(Arrays.asList(tableTitleList));
				int i = 1;
				for (ProviderProductDto ppd : productsObservableList) {
					List<String> dataLine = new ArrayList<>();
					dataLine.add(" " + i++ + "");
					dataLine.add(ppd.getProductName());
					dataLine.add(ppd.getDescription());
					dataLine.add(ppd.getQtyAvailable().toString());
					dataLine.add(ppd.getPrice().toString());
					dataSet.add(dataLine);
				}
				Document document = new Document(PageSize.A4);
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

	@FXML
	public void closeConfirmDialog() {
		productDeleteConfirmDialog.close();
	}

	@FXML
	public void deleteConfirmFromDialog() {
		ProviderProductDto ppd = productsObservableList.get(tableView.getSelectionModel().getSelectedIndex());
		ProductService productService = new ProductService();
		Boolean isDeleted = productService.deleteProviderProduct(ppd);
		if (isDeleted) {
			productsObservableList.remove(tableView.getSelectionModel().selectedItemProperty().get());
			productDeleteConfirmDialog.close();
			alertDialogTitle.setText("Product Deleted Successfully");
			alertDialog.setTransitionType(DialogTransition.CENTER);
			alertDialogBtn.getStyleClass().remove("btn-danger");
			alertDialogBtn.getStyleClass().add("btn-info");
			alertDialog.show();

		} else {
			alertDialogTitle.setText("Something went worng.. Unable to delete this product");
			alertDialog.setTransitionType(DialogTransition.CENTER);
			alertDialogBtn.getStyleClass().remove("btn-info");
			alertDialogBtn.getStyleClass().add("btn-danger");
			alertDialog.show();
		}

	}

	private boolean validProductName() {
		return !productNameField.getText().isEmpty() && productNameField.getLength() > 2;
	}

	private boolean validProductPrice() {
		return !productPriceField.getText().isEmpty() && isDouble(productPriceField.getText());
	}

	private boolean validProductQty() {
		return !productQtyField.getText().isEmpty() && isInteger(productQtyField.getText());
	}

	void checkFormValidity() {
		if (validProductName() && validProductPrice() && validProductQty()) {
			saveProductButton.setDisable(false);
		} else {
			saveProductButton.setDisable(true);
		}

	}

	public boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

	public boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}
}
