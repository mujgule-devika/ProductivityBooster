package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

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
    private Sound alarmSound;

    @FXML
    private void handleStartButton(ActionEvent event) {
        // Implement logic to start the timer
        startTimer();
    }

    @FXML
    private void handleStopButton(ActionEvent event) {
        // Implement logic to stop the timer
        timeline.stop();
        stopAlarmSound();
    }

    @FXML
    private void handleResetButton(ActionEvent event) {
        // Implement logic to reset the timer
        timeline.stop();
        timeSeconds = 1500; // Reset to 25 minutes
        updateTimerLabel();
        timerPane.setStyle("-fx-background-color: #FCCA46;"); // Reset background color
        stopAlarmSound();
    }

    @FXML
    private void initialize() {
        // You can perform any additional initialization here
        updateTimerLabel();  // Ensure the label is updated initially

        // Load the alarm sound
        try {
            alarmSound = new Sound("/Users/vedant/Documents/GitHub/ProductivityBooster/ProductivityBooster/src/application/analog-watch-alarm_daniel-simion.wav");
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void startTimer() {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
            timeSeconds--;

            // Update the timer label with the remaining time
            updateTimerLabel();

            if (timeSeconds <= 0) {
                // Timer has reached zero, implement logic for what happens next
                timeline.stop();
                handleTimerEnd();
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    private void updateTimerLabel() {
        timerLabel.setText(String.format("%02d:%02d", timeSeconds / 60, timeSeconds % 60));
    }

    private void handleTimerEnd() {
        // Play the alarm sound
        playAlarmSound();

        // Change the background color to red
        timerPane.setStyle("-fx-background-color: #FF0000;");

        // You can add more logic here based on what should happen when the timer reaches 00:00
    }

    private void playAlarmSound() {
        if (alarmSound != null) {
            try {
                alarmSound.playAudio();
            } catch (IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopAlarmSound() {
        if (alarmSound != null) {
            try {
                alarmSound.stopAudio();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

