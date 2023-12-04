package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

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
    Button btnConfirmNewTask;
    
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
        title.setFont(Font.font("Heiti SC", FontWeight.BOLD, FontPosture.REGULAR, 20));  
        desc.setFont(Font.font("Heiti SC", FontWeight.BOLD, FontPosture.REGULAR, 20)); 

        //BTTN
        Button edit = new Button("Edit");
        EditHandler editHandler = new EditHandler(taskPane);
        edit.setOnAction(editHandler);
        edit.setMinWidth(98);
        edit.setMinHeight(44);
        edit.setStyle("-fx-background-radius: 20px; -fx-background-color: #FFF;");
        
        AnchorPane.setRightAnchor(edit, 50d); // distance 0 from right side of 
        AnchorPane.setTopAnchor(edit, 70d); // distance 0 from top
            
        // Set layout properties to display label and description on separate lines
        title.setLayoutY(30.0);
        title.setLayoutX(60.0);
        desc.setLayoutY(70.0);
        desc.setLayoutX(60.0);
        
        //Complete button
        Button complete = new Button("Complete");
        CompleteHandler completeHandler = new CompleteHandler(taskPane);
        complete.setOnAction(completeHandler);
        complete.setMinWidth(98);
        complete.setMinHeight(44);
        complete.setStyle("-fx-background-radius: 20px; -fx-background-color: #FFF;");
        
        AnchorPane.setRightAnchor(complete, 50d); // distance 0 from right side of 
        AnchorPane.setTopAnchor(complete, 120d); // distance 0 from top
              
        taskPane.setStyle("-fx-background-color: #FCCA46; -fx-padding: 10%;  -fx-background-radius: 20px;");

        taskPane.getChildren().addAll(title, desc, edit, complete);
        
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
    void editBtnClick(AnchorPane node, GridLocation gridLoc) {
        try {
        	
            // Load the newTask.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editTask.fxml"));
            Parent root = loader.load();

            // Access the controller from the loader
            MainController mainController = loader.getController();
            
            mainController.setTodoGridPane(todoGridPane);
            mainController.setCompleteGridPane(completeGridPane);

            System.out.println(node.getChildren());
			Label title = (Label) node.getChildren().get(0);
			Label desc = (Label) node.getChildren().get(1);
			

//            System.out.println(root.getChildrenUnmodifiable().get(0));
			Label label = (Label) root.lookup("#editTaskTitleLabel");
			String currentLabelText = label.getText();
			String newLabelText = currentLabelText + ": " + title.getText();
			label.setText(newLabelText);
            
            TextField x = (TextField) root.lookup("#editTextTaskName");
            x.setText(title.getText());
            
            TextField y = (TextField) root.lookup("#editTextTaskDesc");
            y.setText(desc.getText());
            
            GridPaneStore.map.put(title.getText(), gridLoc);
            
            
            
//			if(editTextTaskName==null) {
//				System.out.println("editTextTaskName is NULL, exiting application");
//				System.exit(1);
//			}
			
//			mainController.editTextTaskName.setText(title.getText());
//			mainController.editTextTaskDesc.setText(desc.getText());


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

	class EditHandler implements EventHandler<ActionEvent>{
		
		private AnchorPane node;

		public EditHandler(AnchorPane node) {
			this.node = node;
		}
		
		public void handle(ActionEvent event) {
			System.out.println("EDIT button clicked");
			//print out indexes
			System.out.println(GridPane.getColumnIndex(node) + " " + GridPane.getRowIndex(node));
			
			GridLocation grid = new GridLocation(GridPane.getColumnIndex(node), GridPane.getRowIndex(node));
			editBtnClick(node, grid);
		}
	
	}
	
	@FXML
	void editBtnConfirm(ActionEvent event){
		
		System.out.println("Confirm edit button clicked");
		
		//set gridpane item contents as the contents in this form
		
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
        Stage stage =   (Stage) btnConfirmEditTask.getScene().getWindow();
        stage.close();

	}
	
	class CompleteHandler implements EventHandler<ActionEvent>{
		
		private AnchorPane node;

		public CompleteHandler(AnchorPane node) {
			this.node = node;
		}
		
		public void handle(ActionEvent event) {
			System.out.println("Complete button clicked");
			
			System.out.println(completeGridPane);
			
			
			Label oldTitle = (Label) node.getChildren().get(0);
			Label oldDesc = (Label) node.getChildren().get(1);
			
			//create new pane
			AnchorPane taskPane = new AnchorPane();
	        Label title = new Label(oldTitle.getText());
	        Label desc = new Label(oldDesc.getText());
	        title.setFont(Font.font("Heiti SC", FontWeight.BOLD, FontPosture.REGULAR, 20));  
	        desc.setFont(Font.font("Heiti SC", FontWeight.BOLD, FontPosture.REGULAR, 20)); 
	            
	        // Set layout properties to display label and description on separate lines
	        title.setLayoutY(30.0);
	        title.setLayoutX(60.0);
	        desc.setLayoutY(70.0);
	        desc.setLayoutX(60.0);
	              
	        taskPane.setStyle("-fx-background-color: #FCCA46; -fx-padding: 10%;  -fx-background-radius: 20px;");

	        taskPane.getChildren().addAll(title, desc);
	        
//	        todoGridPane.add(taskPane, 0, 0);
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
                	if(!list.isEmpty()) {
                		todoGridPane.add(list.get(0), j, i);
                		list.remove(0);
                	}
                }
	        }
            
	        
	        //set global complete count ++
	        GridPaneStore.incrementCompletedTaskCount();
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
    
//    public void setEditPaneItems(TextField editTextTaskName, TextField editTextTaskDesc) {
//    	this.editTextTaskName = editTextTaskName;
//    	this.editTextTaskDesc = editTextTaskDesc;
//    }
    
    
}
