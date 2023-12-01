package application;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
	
//	@FXML
//	AddPageController addPageController;
	
    @FXML Button btnConfirmNewTask;
    
    @FXML TextField textTaskName;
    
    @FXML TextField textTaskDesc;

    @FXML
    private Button btnAddTask;

    @FXML
    void addBtnClick(ActionEvent event) {
        try {
            // Load the newTask.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("newTask.fxml"));
            Parent root = loader.load();

            // Create a new stage for the dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Task Dialog");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(btnAddTask.getScene().getWindow());

            // Set the loaded content as the scene for the dialog stage
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            // Show the dialog and wait for it to be closed
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addBtnConfirm(ActionEvent event) {
    	System.out.println("Content of text - title:" + textTaskName.getText());
    	System.out.println("Content of text - description:" + textTaskDesc.getText());
    	
    	TaskItem newTask = new TaskItem(textTaskName.getText(),textTaskDesc.getText());
    	
    	AnchorPane taskPane = new AnchorPane();
    	
    	Label title = new Label(newTask.getTitle());
    	Label desc = new Label(newTask.getDescription());
    
    	
    	taskPane.getChildren().addAll(title,desc);
    	
    	todoGridPane.add(taskPane, 0, 0);
//    	addTaskTitleLabel.setText(textTaskName.getText());
    	
    	Stage stage = (Stage) btnConfirmNewTask.getScene().getWindow();
        stage.close();
    	
    }
    
    @FXML GridPane todoGridPane;

//    @FXML
//    void addBtnConfirm(ActionEvent event) {
//    	System.out.println("Content of text - title:" + addPageController.textTaskName.getText());
//    	System.out.println("Content of text - description:" + addPageController.textTaskDesc.getText());
//    	
//    	TaskItem newTask = new TaskItem(addPageController.textTaskName.getText(),addPageController.textTaskDesc.getText());
//    	
//    	AnchorPane taskPane = new AnchorPane();
//    	
//    	Label title = new Label(newTask.getTitle());
//    	Label desc = new Label(newTask.getDescription());
//    
//    	
//    	taskPane.getChildren().addAll(title,desc);
//    	
////    	mainController.todoGridPane.add(taskPane, 0, 0);
////    	addTaskTitleLabel.setText(textTaskName.getText());
//    	
//    	Stage stage = (Stage) addPageController.btnConfirmNewTask.getScene().getWindow();
//        stage.close();
//    	
//    }
    
//    public void initialize() {
//    	addPageController.btnConfirmNewTask.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//            	System.out.println("Content of text - title:" + addPageController.textTaskName.getText());
//            	System.out.println("Content of text - description:" + addPageController.textTaskDesc.getText());
//            	
//            	TaskItem newTask = new TaskItem(addPageController.textTaskName.getText(),addPageController.textTaskDesc.getText());
//            	
//            	AnchorPane taskPane = new AnchorPane();
//            	
//            	Label title = new Label(newTask.getTitle());
//            	Label desc = new Label(newTask.getDescription());
//            
//            	
//            	taskPane.getChildren().addAll(title,desc);
//            	
////            	mainController.todoGridPane.add(taskPane, 0, 0);
////            	addTaskTitleLabel.setText(textTaskName.getText());
//            	
//            	Stage stage = (Stage) addPageController.btnConfirmNewTask.getScene().getWindow();
//                stage.close();
//            }
//        });
//    }
}
