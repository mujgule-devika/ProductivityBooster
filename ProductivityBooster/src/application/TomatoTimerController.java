package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class TomatoTimerController {

    @FXML
    private Label timerLabel;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Button resetButton;
    
    @FXML
    private AnchorPane timerPane;

    private int timeSeconds = 1500; // 25 minutes in seconds
    private Timeline timeline;

    @FXML
    private void handleStartButton(ActionEvent event) {
        // Implement logic to start the timer
        startTimer();
    }

    @FXML
    private void handleStopButton(ActionEvent event) {
        // Implement logic to stop the timer
        timeline.stop();
    }

    @FXML
    private void handleResetButton(ActionEvent event) {
        // Implement logic to reset the timer
        timeline.stop();
        timeSeconds = 1500; // Reset to 25 minutes
        timerLabel.setText("25:00"); // Update the label
    }
    
    @FXML
    private void initialize() {
        // You can perform any additional initialization here
        updateTimerLabel();  // Ensure the label is updated initially
    }
    
    public TomatoTimerController() {
        // Call initialize method
    }
    
    private void startTimer() {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
            timeSeconds--;

            // Update the timer label with the remaining time
            timerLabel.setText(String.format("%02d:%02d", timeSeconds / 60, timeSeconds % 60));

            if (timeSeconds <= 0) {
                // Timer has reached zero, implement logic for what happens next
                // For example, switch to break time or reset the timer
                timeline.stop();
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        updateTimerLabel();
    }
    
    private void updateTimerLabel() {
        timerLabel.setText(String.format("%02d:%02d", timeSeconds / 60, timeSeconds % 60));
    }
    
}
