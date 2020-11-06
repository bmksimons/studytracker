package studytracker.ui;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import studytracker.core.Course;
import studytracker.core.Semester;
import studytracker.ui.Controller;

public class RemoteAppController {

  @FXML
  Controller controller;
  @FXML
  String endpointUri = "http://localhost:8999/studytracker/";


  public Semester getInitialSemester(){
    Semester semester = new Semester();
    ArrayList<Course> courseList = new ArrayList<>();
    courseList.add(new Course("Matte 1"));
    return semester;
  }
  @FXML
  public void initialize() {
    RemoteSemesterAccess remoteSemesterAccess = null;
    //Denne vil alltid trrigge nå
    if (endpointUri != null) {
      try {
        System.out.println("Using remote endpoint @"+ endpointUri);
        remoteSemesterAccess = new RemoteSemesterAccess(new URI(endpointUri));
        
      } catch (URISyntaxException e) {
        System.out.println(e);
      }
    if (remoteSemesterAccess == null){
      //Her må vi hente ut lokale filer eller et standard sett med info i 
      //i remoteAppAccsess
    
    }
  }
  }

    // FXMLLoader fxmlLoader = new FXMLLoader();
    // fxmlLoader.setLocation(getClass().getResource("fxApp.fxml"));
    //Parent controllerParent = fxmlLoader.load();
    
    //this.controller = fxmlLoader.getController();
    //this.controller.setEndpointUri(this.endpointUri);

    //Scene controllerScene = new Scene(controllerParent);
    //Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

    //window.setScene(controllerScene);
    //window.show();    
  
}