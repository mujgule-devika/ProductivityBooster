package application;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MainController {

    @FXML
    private Button btnAddTask;
    
    @FXML
    private Button btnStartTimer;

    @FXML
    private GridPane todoGridPane;

    @FXML
    private GridPane completeGridPane;

    @FXML
    private TextField textTaskName;

    @FXML
    private TextField textTaskDesc;

    @FXML
    private TextField timerInput;

    @FXML
    private Button btnConfirmNewTask;
    
    @FXML
    private AnchorPane EmptyPane;

    @FXML
    private VBox MilestonesVBox;
    
    @FXML
    Label addTaskTitleLabel;
    
    @FXML
    Button btnConfirmEditTask;
    
    @FXML
    Label editTaskTitleLabel;
    
    @FXML
    TextField editTextTaskDesc;

    @FXML
    TextField editTextTaskName;

    private Timeline timeline;
    
    private Sound alarmSound;
    
    @FXML
    private void initialize() {
        // You can perform any additional initialization here
        // Load the alarm sound
        try {
            alarmSound = new Sound("../ProductivityBooster/src/application/analog-watch.wav");
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addBtnClick() {
        try {
            // Load the newTask.fxml file
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("newTask.fxml"));
            Parent root1 = loader1.load();

            // Access the controller from the loader
            MainController mainController = loader1.getController();

            // Set the reference to the todoGridPane
            mainController.setTodoGridPane(todoGridPane);
            mainController.setCompleteGridPane(completeGridPane);
            mainController.setMilestonesVBox(MilestonesVBox);

            // Create a new stage for the dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Task Dialog");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(btnAddTask.getScene().getWindow());

            // Set the loaded content as the scene for the dialog stage
            Scene scene = new Scene(root1);
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
        title.setFont(Font.font("Heiti SC", FontWeight.BOLD, FontPosture.REGULAR, 22));
        desc.setFont(Font.font("Heiti SC", FontWeight.LIGHT, FontPosture.REGULAR, 20));
        title.setAlignment(Pos.CENTER);
        desc.setAlignment(Pos.CENTER);
        // User-input timer duration
        int timerDuration = Integer.parseInt(timerInput.getText());
        
        Label timerDisplay = new Label();
        timerDisplay.setFont(Font.font("Heiti SC", FontWeight.BOLD, FontPosture.REGULAR, 26));
        timerDisplay.setMinWidth(98);
        // Button to start/edit the timer
        Button timerButton = new Button("Start Timer");
        TimerHandler timerHandler = new TimerHandler(taskPane, timerDuration, timerButton, timerDisplay);
        timerButton.setOnAction(timerHandler);
        timerButton.setMinWidth(98);
        timerButton.setMinHeight(44);
        timerButton.setStyle("-fx-background-radius: 20px; -fx-background-color: #FFF;");
        timerButton.setAlignment(Pos.CENTER);
        
        AnchorPane.setLeftAnchor(timerButton, 85d); // distance 0 from right side
        AnchorPane.setTopAnchor(timerButton, 180d); // distance 0 from top

        // Button to edit the task
        Button edit = new Button("Edit");
        EditHandler editHandler = new EditHandler(taskPane);
        edit.setOnAction(editHandler);
        edit.setMinWidth(98);
        edit.setMinHeight(44);
        edit.setStyle("-fx-background-radius: 20px; -fx-background-color: #FFF;");
        edit.setAlignment(Pos.CENTER);

        AnchorPane.setLeftAnchor(edit, 30d); // distance 0 from right side
        AnchorPane.setTopAnchor(edit, 240d); // distance 0 from top
        
        // Set layout properties to display label and description on separate lines
        title.setLayoutY(30.0);
        title.setLayoutX(100.0);
        
        desc.setLayoutY(60.0);
        desc.setLayoutX(60.0);
        desc.setMaxWidth(160.0);
        desc.setWrapText(true);
        timerDisplay.setLayoutX(100);
        timerDisplay.setLayoutY(100.0);
        
        title.setAlignment(Pos.CENTER);
        desc.setAlignment(Pos.CENTER);
        

        // Button to complete the task
        Button complete = new Button("Complete");
        CompleteHandler completeHandler = new CompleteHandler(taskPane);
        complete.setOnAction(completeHandler);
        complete.setMinWidth(98);
        complete.setMinHeight(44);
        complete.setStyle("-fx-background-radius: 20px; -fx-background-color: #FFF;");
        complete.setAlignment(Pos.CENTER);
         
        AnchorPane.setLeftAnchor(complete, 140d); // distance 0 from right side
        AnchorPane.setTopAnchor(complete, 240d); // distance 0 from top

        taskPane.setStyle("-fx-background-color: #FCCA46; -fx-padding: 10%;  -fx-background-radius: 20px;");

        taskPane.getChildren().addAll(title, desc, timerDisplay, timerButton, edit, complete);

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
        Stage stage = (Stage) btnConfirmNewTask.getScene().getWindow();
        stage.close();
    }

    class TimerHandler implements EventHandler<ActionEvent> {

        private AnchorPane node;
        private int duration;
        private Button timerButton;
        private Label timerDisplay;

        public TimerHandler(AnchorPane node, int duration, Button timerButton, Label timerDisplay) {
            this.node = node;
            this.duration = duration;
            this.timerButton = timerButton;
            this.timerDisplay = timerDisplay;
        }

        public void handle(ActionEvent event) {
            System.out.println("Timer button clicked");

            if (timeline != null) {
                timeline.stop();
            }

            final int[] timeSeconds = { duration * 60};  // Use an array

            timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(1),
                            new EventHandler<ActionEvent>() {
                                // KeyFrame event handler
                                public void handle(ActionEvent event) {
                                    timeSeconds[0]--;  // Update the array element
                                    // Update the displayed timer
                                    int minutes = timeSeconds[0] / 60;
                                    int seconds = timeSeconds[0] % 60;
                                    timerDisplay.setText(String.format("%02d:%02d", minutes, seconds));
                                    timerDisplay.setLayoutX(100.0);
                                    timerDisplay.setLayoutY(110.0);

                                    if (timeSeconds[0] <= 0) {
                                        // Stop the timer when it reaches zero
                                        timeline.stop();
                                        
                                        handleTimerEnd(node);
                                    }
                                }
                            }));
            timeline.play();
            timerButton.setDisable(true);
        }
    }



    class EditHandler implements EventHandler<ActionEvent> {

        private AnchorPane node;

        public EditHandler(AnchorPane node) {
            this.node = node;
        }

        public void handle(ActionEvent event) {
            GridLocation grid = new GridLocation(GridPane.getColumnIndex(node), GridPane.getRowIndex(node));

            // Check if the task has an active timer
            if (hasActiveTimer(node)) {
                resumeTimer(node);
            }

            editBtnClick(node, grid);
        }

        // Check if the AnchorPane has an active timer
        private boolean hasActiveTimer(AnchorPane node) {
            return node.getChildren().stream()
                    .anyMatch(child -> child instanceof Button && ((Button) child).getText().equals("Start Timer"));
        }

        // Resume the timer associated with the AnchorPane
        private void resumeTimer(AnchorPane node) {
            Label timerDisplay = (Label) node.getChildren().get(2);
            Button timerButton = (Button) node.getChildren().get(3);

            // Extract the remaining time from the label
            String timeString = timerDisplay.getText();
            
            if(timeString == null || timeString == "") {
            		return;
            }
            
            final int[] remainingTime = {Integer.parseInt(timeString.substring(0, 2)) * 60 +
                    Integer.parseInt(timeString.substring(3))};

            // Start a new timeline with the remaining time
            Timeline timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(1),
                            new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent event) {
                                    remainingTime[0]--;
                                    int minutes = remainingTime[0] / 60;
                                    int seconds = remainingTime[0] % 60;
                                    timerDisplay.setText(String.format("%02d:%02d", minutes, seconds));
                                    timerDisplay.setLayoutX(100.0);
                                    timerDisplay.setLayoutY(110.0);

                                    if (remainingTime[0] <= 0) {
                                        timeline.stop();
                                    }
                                }
                            }));
            timeline.play();
            timerButton.setDisable(true);
        }

    }


    void editBtnClick(AnchorPane node, GridLocation gridLoc) {
        if (timeline != null) {
            timeline.pause();
        }

        try {
            // Load the newTask.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editTask.fxml"));
            Parent root = loader.load();

            // Access the controller from the loader
            MainController mainController = loader.getController();

            mainController.setTodoGridPane(todoGridPane);
            mainController.setCompleteGridPane(completeGridPane);

            Label title = (Label) node.getChildren().get(0);
            Label desc = (Label) node.getChildren().get(1);

            Label label = (Label) root.lookup("#editTaskTitleLabel");
            String currentLabelText = label.getText();
            String newLabelText = currentLabelText + ": " + title.getText();
            label.setText(newLabelText);

            TextField x = (TextField) root.lookup("#editTextTaskName");
            x.setText(title.getText());

            TextField y = (TextField) root.lookup("#editTextTaskDesc");
            y.setText(desc.getText());

            GridPaneStore.map.put(title.getText(), gridLoc);

            // Set the loaded content as the scene for the dialog stage
            Scene scene = new Scene(root);

            // Create a new stage for the dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Task Dialog");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(node.getChildren().get(0).getScene().getWindow());

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

    
    
    @FXML
    void editBtnConfirm(ActionEvent event) {

        if (timeline != null) {
            timeline.play();
        }

        String labelName = editTaskTitleLabel.getText();

        String oldTaskName = labelName.substring(16);

        String editName = editTextTaskName.getText();
        String editDesc = editTextTaskDesc.getText();

        GridLocation gridLoc = GridPaneStore.map.get(oldTaskName);

        AnchorPane taskPane = getAnchorPaneInGrid(gridLoc.getX(), gridLoc.getY(), todoGridPane);

        Label title = (Label) taskPane.getChildren().get(0);
        Label desc = (Label) taskPane.getChildren().get(1);

        title.setText(editName);
        desc.setText(editDesc);

        GridPaneStore.map.remove(oldTaskName);

        // Close the pop-up window
        Stage stage = (Stage) btnConfirmEditTask.getScene().getWindow();
        stage.close();
    }

    class CompleteHandler implements EventHandler<ActionEvent> {

        private AnchorPane node;

        public CompleteHandler(AnchorPane node) {
            this.node = node;
        }

        public void handle(ActionEvent event) {
        	
        	stopAlarmSound();

            Label oldTitle = (Label) node.getChildren().get(0);
            Label oldDesc = (Label) node.getChildren().get(1);
            Label oldTimerDisplay= (Label) node.getChildren().get(2);


            //create new pane
            AnchorPane taskPane = new AnchorPane();
            Label title = new Label(oldTitle.getText());
            Label desc = new Label(oldDesc.getText());
            String stg = new String();
            
            if (oldTimerDisplay.getText().isEmpty()) {
            	stg = "";
            } else {stg ="Time Saved: " + oldTimerDisplay.getText();
            
            };
            
            Label taskCompleted = new Label(stg);
           


            title.setFont(Font.font("Heiti SC", FontWeight.BOLD, FontPosture.REGULAR, 22));
            desc.setFont(Font.font("Heiti SC", FontWeight.MEDIUM, FontPosture.REGULAR, 20));
            taskCompleted.setFont(Font.font("Heiti SC", FontWeight.BOLD, FontPosture.REGULAR, 20));

            // Set layout properties to display label and description on separate lines
            title.setLayoutY(30.0);
            title.setLayoutX(110.0);
          
            desc.setLayoutY(80.0);
            desc.setLayoutX(80.0);
            desc.setMaxWidth(150.0);
            desc.setWrapText(true);
            taskCompleted.setLayoutX(80.0);
            taskCompleted.setLayoutY(140.0);
            taskCompleted.setMaxWidth(120.0);
            taskCompleted.setWrapText(true);

            title.setAlignment(Pos.CENTER);
            desc.setAlignment(Pos.CENTER);
            taskCompleted.setAlignment(Pos.CENTER);

            taskPane.setStyle("-fx-background-color: #FE7F2D; -fx-padding: 10%;  -fx-background-radius: 20px;");

            taskPane.getChildren().addAll(title, desc, taskCompleted);

            // Find the next available position in the GridPane
            int rowIndex = 0;
            int colIndex = 0;

            outerLoop:
            for (int i = 0; i < completeGridPane.getRowCount(); i++) {
                for (int j = 0; j < completeGridPane.getColumnCount(); j++) {
                    boolean positionOccupied = false;
                    for (Node node : completeGridPane.getChildren()) {
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
            completeGridPane.add(taskPane, colIndex, rowIndex);

            //remove pane from todoGridPane
            todoGridPane.getChildren().remove(node);

            //get all elements of todoGridPane
            List<Node> list = FXCollections.observableArrayList();
            for (Node node : todoGridPane.getChildren()) {
                list.add(node);
            }

            //clear old gridpane
            todoGridPane.getChildren().clear();

            //reset all elements of todoGridPane
            for (int i = 0; i < todoGridPane.getRowCount(); i++) {
                for (int j = 0; j < todoGridPane.getColumnCount(); j++) {
                    if (!list.isEmpty()) {
                        todoGridPane.add(list.get(0), j, i);
                        list.remove(0);
                    }
                }
            }

          //set global complete count ++
	        GridPaneStore.incrementCompletedTaskCount();
	        updateMilestones();
		}
	
	}
	
	public AnchorPane getAnchorPaneInGrid (int x, int y, GridPane gridPane) {
		
	    ObservableList<Node> gridItems = gridPane.getChildren();

	    for (Node node : gridItems) {
	        if(GridPane.getRowIndex(node) == y && GridPane.getColumnIndex(node) == x) {
	            return (AnchorPane) node;
	        }
	    }

	    return null;
	}

    // Additional method to set the todoGridPane from the pop-up
    public void setTodoGridPane(GridPane todoGridPane) {
        this.todoGridPane = todoGridPane;
    }
    
    public void setCompleteGridPane(GridPane completeGridPane) {
    	this.completeGridPane = completeGridPane;
    }
    
    public void setMilestonesVBox(VBox MilestonesVBox) {
    	this.MilestonesVBox = MilestonesVBox;
    }
    
    public void updateMilestones() {
    	//update milestones here
    	int currentCount = GridPaneStore.getCompletedTaskCount();
    	
    	if(currentCount == 3) {
    		//gold medal
    		ObservableList<Node> list = MilestonesVBox.getChildren();
    		
    		AnchorPane pane = new AnchorPane();
    		pane.setPadding(new Insets(5,5,5,5));
    		
    		//creating the image object
    	      InputStream stream;
			try {
				stream = new FileInputStream("../ProductivityBooster/src/application/gold.png");
				Image image = new Image(stream);
	    	      //Creating the image view
	    	      ImageView imageView = new ImageView(image);
	    	      
	    	      imageView.setFitWidth(100);
	    	      imageView.setPreserveRatio(true);
//	    	      imageView.setLayoutY(10);
	    	      
	    	      pane.getChildren().add(imageView);
	    	      
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("File gold.png not found!");
				e.printStackTrace();
			}

    		list.add(pane);
    		
    	}else if(currentCount == 2) {
    		//silver medal
    		ObservableList<Node> list = MilestonesVBox.getChildren();
    		
    		AnchorPane pane = new AnchorPane();
    		pane.setPadding(new Insets(5,5,5,5));
    		
    		//creating the image object
    	      InputStream stream;
			try {
				stream = new FileInputStream("../ProductivityBooster/src/application/silver.png");
				Image image = new Image(stream);
	    	      //Creating the image view
	    	      ImageView imageView = new ImageView(image);
	    	      
	    	      imageView.setFitWidth(100);
	    	      imageView.setPreserveRatio(true);
	    	      
//	    	      imageView.setLayoutY(10);
	    	      
	    	      pane.getChildren().add(imageView);
	    	      
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("File silver.png not found!");
				e.printStackTrace();
			}

    		list.add(pane);
    		
    	}else if(currentCount == 1) {
    		//bronze medal
    		ObservableList<Node> list = MilestonesVBox.getChildren();
    		list.remove(0);
    		
    		AnchorPane pane = new AnchorPane();
    		pane.setPadding(new Insets(5,5,5,5));
    		Label label = new Label("Congratulations! Completed tasks:");
    		label.setFont(Font.font("Heiti SC", FontWeight.BOLD, FontPosture.REGULAR, 12));
    		label.setMaxWidth(100);
    		label.setWrapText(true);
    		

    		pane.getChildren().add(label);
    		
    		Label numLabel = new Label("1");
    		numLabel.setAlignment(Pos.CENTER);
    		numLabel.setMaxWidth(100);
    		numLabel.setWrapText(true);
    		numLabel.setFont(Font.font("Heiti SC", FontWeight.BOLD, FontPosture.REGULAR, 16)); 
    		
    		AnchorPane numPane = new AnchorPane();
    		numPane.setPadding(new Insets(5,5,5,5));
    		
    		numPane.getChildren().add(numLabel);
    		
    		AnchorPane pane2 = new AnchorPane();
    		pane2.setPadding(new Insets(5,5,5,5));
    		
    		//creating the image object
    	      InputStream stream;
			try {
				stream = new FileInputStream("../ProductivityBooster/src/application/bronze.png");
				Image image = new Image(stream);
	    	      //Creating the image view
	    	      ImageView imageView = new ImageView(image);
	    	      
	    	      imageView.setFitWidth(100);
	    	      imageView.setPreserveRatio(true);
//	    	      imageView.setLayoutY(10);
	    	      
	    	      pane2.getChildren().add(imageView);
	    	      
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("File bronze.png not found!");
				e.printStackTrace();
			}

    		list.addAll(pane, numPane, pane2);
    		
    	}
    	
    	ObservableList<Node> list = MilestonesVBox.getChildren();
    	AnchorPane firstPane = (AnchorPane) list.get(1);
		Label label = (Label) firstPane.getChildren().get(0);
		label.setText(""+GridPaneStore.getCompletedTaskCount());
		
    }
    
    private void handleTimerEnd(Node node) {
        // Play the alarm sound
        playAlarmSound();

        // Change the background color to red
        node.setStyle("-fx-background-color: #FF0000;");

        // You can add more logic here based on what should happen when the timer reaches 00:00
    }
    
    private void playAlarmSound() {
        if (alarmSound != null) {
            try {
                alarmSound.playAudio();
                System.out.println("Timer reached zero! Starting alarm!");
                
                // And From your main() method or any other method
                Timer timer = new Timer();
                timer.schedule(new DelayedAlarmSoundStop(), 5000);
                
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
    
    class DelayedAlarmSoundStop extends TimerTask {
        public void run() {
        	System.out.println("5 seconds passed. Timer stopped!");
        	stopAlarmSound(); 
        }
    }


}

