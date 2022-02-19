package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable {

	private DepartmentService service;

	@FXML
	private TableView<Department> tableViewDepartment;

	@FXML
	private TableColumn<Department, Integer> tableColumnId;

	@FXML
	private TableColumn<Department, String> tableColumnName;

	@FXML
	private Button btNew;

	private ObservableList<Department> obsListDep;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		
		Stage parentStage = Utils.currentStage(event);
		Department dep = new Department();
		createDialogForm(dep, "/gui/DepartmentForm.fxml", parentStage);
	}

	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	public void initializeNodes() {

		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {

		if (service == null) {
			throw new IllegalStateException("Service was null");
		}

		List<Department> listDepartments = service.findAll();
		obsListDep = FXCollections.observableArrayList(listDepartments);
		tableViewDepartment.setItems(obsListDep);
	}
	
	private void createDialogForm(Department obj, String absName, Stage parentStage) {
		
		try {
			
			 FXMLLoader loader = new FXMLLoader(getClass().getResource(absName));
			 Pane pane = loader.load();
			 
			 DepartmentFormController controller = loader.getController();
			 controller.setDepartment(obj);
			 controller.setDepartmentService(new DepartmentService());
			 controller.updateFormData();
			 
			 Stage dialogStage = new Stage();
			 dialogStage.setTitle("Enter Department data");
			 dialogStage.setScene(new Scene(pane));
			 dialogStage.setResizable(false);
			 dialogStage.initOwner(parentStage);
			 dialogStage.initModality(Modality.WINDOW_MODAL);
			 dialogStage.showAndWait();
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

}
