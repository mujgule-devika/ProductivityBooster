package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;

public class MainController {

    @FXML
    private Button btnAddTask;

    @FXML
    private GridPane todoGridPane;

    @FXML
    private TextField textTaskName;

    @FXML
    private TextField textTaskDesc;
    
    @FXML 
    Button btnConfirmNewTask;

    @FXML
    void addBtnClick() {
        try {
            // Load the newTask.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("newTask.fxml"));
            Parent root = loader.load();

            // Access the controller from the loader
            MainController mainController = loader.getController();

            // Set the reference to the todoGridPane
            mainController.setTodoGridPane(todoGridPane);

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
    void addBtnConfirm() {
        // add a task to the todoGridPane

        AnchorPane taskPane = new AnchorPane();
        Label title = new Label(textTaskName.getText());
        Label desc = new Label(textTaskDesc.getText());
        
        //BTTN
        Button edit = new Button("EDIT");
        Handler1 handler1 = new Handler1();
        edit.setOnAction(handler1);
        
        
        // Set layout properties to display label and description on separate lines
        title.setLayoutY(5.0);
        desc.setLayoutY(30.0);
        
        
        
       
        taskPane.setStyle("-fx-background-color: #B0C4DE; -fx-border-color: #000000; -fx-border-width: 2px; -fx-padding: 10%;");

        taskPane.getChildren().addAll(title, desc, edit);
        
        

//        todoGridPane.add(taskPane, 0, 0);
        // Find the next available position in the GridPane
        int rowIndex = 0;
        int colIndex = 0;


        outerLoop:
            for (int i = 0; i < todoGridPane.getRowCount(); i++) {
                for (int j = 0; j < todoGridPane.getColumnCount(); j++) {
                    boolean positionOccupied = false;
                    for (Node node : todoGridPane.getChildren()) {
                        if (GridPane.getRowIndex(node) == i && GridPane.getColumnIndex(node) == j) {
                            positionOccupied = true;
                            break;
                        }
                    }

                    if (!positionOccupied) {
                        rowIndex = i;
                        colIndex = j;
                        break outerLoop;
                    }
                }
            }

            // Add the taskPane to the determined position
            todoGridPane.add(taskPane, colIndex, rowIndex);


        // Close the pop-up window
        Stage stage =   (Stage) btnConfirmNewTask.getScene().getWindow();
        stage.close();
    }
    
//edit btn task 
    void editBtnClick() {
        try {
            // Load the newTask.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("newTask.fxml"));
            Parent root = loader.load();

            // Access the controller from the loader
            MainController mainController = loader.getController();

            // Set the reference to the todoGridPane
            mainController.setTodoGridPane(todoGridPane);

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

class Handler1 implements EventHandler<ActionEvent>{
public void handle(ActionEvent event) {
	System.out.println("EDIT button clicked");
	}

}

    // Additional method to set the todoGridPane from the pop-up
    public void setTodoGridPane(GridPane todoGridPane) {
        this.todoGridPane = todoGridPane;
    }
}
