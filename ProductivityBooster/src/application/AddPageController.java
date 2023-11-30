package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddPageController {

	@FXML
	private MainController mainController;
	
    @FXML
    private Button btnConfirmNewTask;
    
    @FXML
    private TextField textTaskName;
    
    @FXML
    private TextField textTaskDesc;
    
//    @FXML
//    private Label addTaskTitleLabel;
    
    @FXML
    void addBtnConfirm(ActionEvent event) {
    	System.out.println("Content of text - title:" + textTaskName.getText());
    	System.out.println("Content of text - description:" + textTaskDesc.getText());
    	
    	TaskItem newTask = new TaskItem(textTaskName.getText(),textTaskDesc.getText());
    	
    	AnchorPane taskPane = new AnchorPane();
    	
    	Label title = new Label(newTask.getTitle());
    	Label desc = new Label(newTask.getDescription());
    
    	
    	taskPane.getChildren().addAll(title,desc);
    	
//    	mainController.todoGridPane.add(taskPane, 0, 0);
//    	addTaskTitleLabel.setText(textTaskName.getText());
    	
    	Stage stage = (Stage) btnConfirmNewTask.getScene().getWindow();
        stage.close();
    	
    }
}
