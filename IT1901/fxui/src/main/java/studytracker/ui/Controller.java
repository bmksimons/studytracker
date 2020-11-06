package studytracker.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import studytracker.core.Course;
import studytracker.core.Semester;
import studytracker.json.StudyTrackerModule;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import studytracker.json.StudyTrackerPersistence;


public class Controller {

  private Semester semester;
  private StudyTrackerPersistence studyTrackerPersistence = new StudyTrackerPersistence();
  private ObservableList<String> courseList = FXCollections.observableArrayList();

  @FXML
  Label courseName1;
  @FXML
  Label courseName2;
  @FXML
  Label courseName3;
  @FXML
  Label courseName4;

  private List<Label> courseNames;

  @FXML
  Label courseTimer1;
  @FXML
  Label courseTimer2;
  @FXML
  Label courseTimer3;
  @FXML
  Label courseTimer4;

  private List<Label> courseTimers;

  @FXML
  ChoiceBox<String> pickCourse;
  @FXML
  TextField timeToAdd;
  @FXML
  Button plusTime;
  @FXML
  Button minusTime;
  @FXML
  private Button addTime;
  @FXML
  private Button statistic;

  @FXML
  TextField newCourse;
  @FXML
  Button addCourse;

  @FXML
  Button reset;

  @FXML
  Label showInformation;

  @FXML
  Button delete;

  @FXML
  ChoiceBox<String> pickCourseDelete;

  @FXML
  RemoteAppController remoteAppController;

  @FXML
  private String endpointUri;

  private RemoteSemesterAccess remoteAccess;

  public Semester getSemester() {
    return this.semester;
  }

  @FXML
  public void initialize() {
    this.setEndpointUri("http://localhost:8999/studytracker/");
    // this.semester = null;
    // this.courseList = FXCollections.observableArrayList();
    this.courseNames = new ArrayList<>();
    this.courseTimers = new ArrayList<>();
    this.courseNames.add(this.courseName1);
    this.courseNames.add(this.courseName2);
    this.courseNames.add(this.courseName3);
    this.courseNames.add(this.courseName4);
    this.courseTimers.add(this.courseTimer1);
    this.courseTimers.add(this.courseTimer2);
    this.courseTimers.add(this.courseTimer3);
    this.courseTimers.add(this.courseTimer4);
    if (endpointUri != null) {
      try {
        System.out.println("Using remote endpoint @ " + endpointUri);
        remoteAccess = new RemoteSemesterAccess(new URI(endpointUri));
        this.semester = remoteAccess.getSemester();
        System.out.println(this.semester.getCourses().get(0).getCourseName() + " inne i uri ifen");
      } catch (URISyntaxException e) {
        System.err.println(e);
        this.semester = new Semester();
      }
    } else {
      try {
        this.semester = studyTrackerPersistence.readSemester("semester.json");
        System.out.println(this.semester.getCourses().get(0).getCourseName() + " inne i uri else");
      } catch (JsonProcessingException e) {
        this.semester = new Semester();
        this.showInformation.setText("json processing exception");
      } catch (IOException e) {
        this.showInformation.setText("IOException");
      }
    }
    Iterator<Course> semesterIt = this.semester.iterator();
    for (Label label : this.courseNames) {
      if (semesterIt.hasNext()) {
        String courseName = semesterIt.next().getCourseName();
        label.setText(courseName);
        this.courseList.add(courseName);
        this.updateCourseList();
      }
    }
    Iterator<Course> semesterIt2 = this.semester.iterator();
    for (Label label : this.courseTimers) {
      if (semesterIt2.hasNext()) {
        label.setText(String.valueOf(semesterIt2.next().getTimeSpent()));
      }
    }
    this.timeToAdd.setText("0 t");
    System.out.println(semester.getCourses().get(0).getCourseName() + " etter kjøring av ifen");
    this.semester.addSemesterListener(semester -> this.saveSemester());
  }

  public void setEndpointUri(String endpointUri){
    this.endpointUri = endpointUri;
  }

  public void saveSemester() {
    try {
      this.studyTrackerPersistence.writeSemester("semester.json", this.semester);
    } catch (JsonProcessingException e) {
      this.showInformation.setText("Klarte ikke lagre jsonData til fil");
    } catch (IOException e) {
      this.showInformation.setText("Klarte ikke skrive til fil");
    }
  }

  @FXML
  public void addCourse() {
    if (newCourse.getText().equals("")) {
      showInformation.setText("Du må skrive inn et fag");
    } else {
      if (courseName1.getText().equals("")) {
        this.makeCourse(courseName1, courseTimer1);
      } else if (courseName2.getText().equals("")) {
        this.makeCourse(courseName2, courseTimer2);
      } else if (courseName3.getText().equals("")) {
        this.makeCourse(courseName3, courseTimer3);
      } else if (courseName4.getText().equals("")) {
        this.makeCourse(courseName4, courseTimer4);
      } else {
        showInformation.setText("Du kan kun legge til 4 fag");
      }
      this.newCourse.setText("");
    }
  }

  public Label getShowInformation() {
    return this.showInformation;
  }

  @FXML
  private void makeCourse(Label courseName, Label courseTime) {
    try {
      this.semester.addCourse(new Course(newCourse.getText()));
      courseName.setText(newCourse.getText());
      courseList.add(newCourse.getText());
      updateCourseList();
      courseTime.setText("0 t");
    } catch (IllegalArgumentException e) {
      this.showInformation.setText("Kan ikke legge til et fag flere ganger");
    }
  }

  @FXML
  private void updateCourseList() {
    pickCourse.setItems(this.courseList);
    pickCourseDelete.setItems(this.courseList);
  }

  @FXML
  public void addTime() {
    String currentTimeString = timeToAdd.getText();
    String[] partition = currentTimeString.split(Pattern.quote(" "));

    Double currentTime = Double.parseDouble(partition[0]);
    currentTime = currentTime + 0.25;
    timeToAdd.setText(currentTime + " t");
  }

  @FXML
  public void onOpenStatisticsClick(ActionEvent event) throws Exception{
     try {
        Parent statisticParent = FXMLLoader.load(getClass().getResource("fxStatistic.fxml"));
        Scene statisticScene = new Scene(statisticParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(statisticScene);
        window.show();

    } catch(Exception e) {
        e.printStackTrace();
    }
      
    
  }

  @FXML
  public void removeTime() {
    String currentTimeString = timeToAdd.getText();
    String[] partition = currentTimeString.split(Pattern.quote(" "));

    Double currentTime = Double.parseDouble(partition[0]);
    if (currentTime == 0) {
      showInformation.setText("Kan ikke være negativt antall timer");
    } else {
      currentTime = currentTime - 0.25;
      timeToAdd.setText(currentTime + " t");
    }
  }

  @FXML
  public void addStudyHours() {
    String courseChosen = pickCourse.getValue();
    if (courseChosen == null) {
      showInformation.setText("Du må velge et fag");
    } else {
      if (courseChosen.equals(courseName1.getText())) {
        this.makeStudyHours(courseName1, courseTimer1);
      } else if (courseChosen.equals(courseName2.getText())) {
        this.makeStudyHours(courseName2, courseTimer2);
      } else if (courseChosen.equals(courseName3.getText())) {
        this.makeStudyHours(courseName3, courseTimer3);
      } else if (courseChosen.equals(courseName4.getText())) {
        this.makeStudyHours(courseName4, courseTimer4);
      }
      timeToAdd.setText("0 t");
      pickCourse.setValue("");
    }
  }

  @FXML
  private void makeStudyHours(Label courseName, Label courseTime) {
    String currentTimeString = timeToAdd.getText();
    String[] partition = currentTimeString.split(Pattern.quote(" "));
    Double hoursToAdd = Double.parseDouble(partition[0]);
    String currentStudyTime = courseTime.getText();
    String[] partition2 = currentStudyTime.split(Pattern.quote(" "));
    Double beforeHoursStudied = Double.parseDouble(partition2[0]);
    Double hoursStudied = beforeHoursStudied + hoursToAdd;
    courseTime.setText(hoursStudied + " t");
    this.semester.addTimeToCourse(courseName.getText(), hoursToAdd);
  }

  @FXML
  public void onResetButtonClick() {
    for (Label label : combineLabels()) {
      label.setText("");
    }
    timeToAdd.setText("0 t");
    courseList.clear();
    updateCourseList();
    this.semester.clearSemester();
  }

  @FXML
  private void setFieldsEmpty(Label courseName, Label courseTime) {
    courseName.setText("");
    courseTime.setText("");
  }

  @FXML
  public void deleteCourse() {
    String courseChosenDelete = pickCourseDelete.getValue();

    if (courseChosenDelete == null) {
      showInformation.setText("Du må velge et fag først");
    } else {
      if (courseChosenDelete.equals(courseName1.getText())) {
        this.makeDeleteCourse(courseName1, courseTimer1);
      } else if (courseChosenDelete.equals(courseName2.getText())) {
        this.makeDeleteCourse(courseName2, courseTimer2);
      } else if (courseChosenDelete.equals(courseName3.getText())) {
        this.makeDeleteCourse(courseName3, courseTimer3);
        showInformation.setText("Faget er slettet");
      } else if (courseChosenDelete.equals(courseName4.getText())) {
        this.makeDeleteCourse(courseName4, courseTimer4);
      }
    }
  }

  private void makeDeleteCourse(Label courseName, Label courseTime) {
    courseList.remove(courseName.getText());
    updateCourseList();
    this.semester.removeCourse(courseName.getText());
    this.setFieldsEmpty(courseName, courseTime);
    showInformation.setText("Faget er slettet");
  }

  private ArrayList<Label> combineLabels() {
    ArrayList<Label> tmp = new ArrayList<>();
    tmp.addAll(this.courseNames);
    tmp.addAll(this.courseTimers);
    return tmp;
  }

  public Label getCourseName1() {
    return this.courseName1;
  }

  public Label getCourseName2() {
    return this.courseName2;
  }

  public Label getCourseTimer1() {
    return this.courseTimer1;
  }

  public List getCourseNames(){
    return this.courseNames;
  }

  public List getCourseTimers(){
    return this.courseTimers;
  }
}