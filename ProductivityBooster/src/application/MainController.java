package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private Button btnAddTask;
    
    @FXML
    private Button btnStartTimer;

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
    void addBtnClick1(ActionEvent event) {
        try {
            // Load the newTask.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("newTimer.fxml"));
            Parent root = loader.load();

            // Create a new stage for the dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Tomato Timer Dialog Box");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(btnStartTimer.getScene().getWindow());

            // Set the loaded content as the scene for the dialog stage
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            // Show the dialog and wait for it to be closed
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
