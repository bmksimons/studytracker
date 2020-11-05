package studytracker.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import studytracker.ui.Controller;

public class RemoteAppController {

  @FXML
  Controller controller;
  @FXML
  String endpointUri = "http://localhost:8999/studytracker/";

  @FXML
  public void initialize() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(getClass().getResource("fxApp.fxml"));
    Parent controllerParent = fxmlLoader.load();
    
    this.controller = fxmlLoader.getController();
    this.controller.setEndpointUri(this.endpointUri);

    //Scene controllerScene = new Scene(controllerParent);
    //Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

    //window.setScene(controllerScene);
    //window.show();    
  }
}