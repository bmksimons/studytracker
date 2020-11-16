package studytracker.ui;

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
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class Controller {

  private Semester semester;
  private ObservableList<String> courseList = FXCollections.observableArrayList();
  private int maxCourses;
  private int currentNumberCourses;

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
  private String endpointUri;

  private RemoteSemesterAccess remoteAccess;

  /**
   * Initializes the Controller by loading the Semester in the restserver. Sets up
   * fields which are used in other methods in the Controller.
   *
   */
  @FXML
  public void initialize() {
    this.maxCourses = 4;
    this.currentNumberCourses = 0;
    this.endpointUri = "http://localhost:8999/studytracker/";
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

    try {
      System.out.println("Using remote endpoint @ " + endpointUri);
      remoteAccess = new RemoteSemesterAccess(new URI(endpointUri));
      this.semester = remoteAccess.getSemester();
    } catch (URISyntaxException e) {
      System.err.println(e);
      this.semester = new Semester();
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
    this.semester.addSemesterListener(semester -> this.saveSemester());
  }

  public void saveSemester() {
    this.remoteAccess.putSemester(this.semester);
  }

  /*
   * @FXML public void addCourse() { if (newCourse.getText().equals("")) {
   * showInformation.setText("Du må skrive inn et fag"); } else { if
   * (courseName1.getText().equals("")) { this.makeCourse(courseName1,
   * courseTimer1); } else if (courseName2.getText().equals("")) {
   * this.makeCourse(courseName2, courseTimer2); } else if
   * (courseName3.getText().equals("")) { this.makeCourse(courseName3,
   * courseTimer3); } else if (courseName4.getText().equals("")) {
   * this.makeCourse(courseName4, courseTimer4); } else {
   * showInformation.setText("Du kan kun legge til 4 fag"); }
   * this.newCourse.setText(""); } }
   */

  /**
   * Adds a course to the Semester.
   *
   */
  @FXML
  public void addCourse() {
    if (newCourse.getText() == "") {
      showInformation.setText("Du må skrive inn et fag");
    } else if (currentNumberCourses == maxCourses) {
      showInformation.setText("Du kan kun legge til " + maxCourses + " fag");
    } else {
      for (var i = 0; i < courseNames.size(); i++) {
        if (courseNames.get(i).getText().equals("")) {
          courseNames.get(i).setText(newCourse.getText());
          makeCourse(courseNames.get(i));
          this.currentNumberCourses += 1;
          break;
        }
      }

      for (Label courseTimer : courseTimers) {
        if (courseTimer.getText().equals("")) {
          courseTimer.setText("0 t");
          break;
        }
      }

      newCourse.setText("");
    }

  }

  @FXML
  private void makeCourse(Label courseNames) {
    try {
      this.semester.addCourse(new Course(newCourse.getText()));
      //this.remoteAccess.putCourse(this.semester.getCourse(newCourse.getText()));
      courseList.add(newCourse.getText());
      updateCourseList();

    } catch (final IllegalArgumentException e) {
      this.showInformation.setText("Kan ikke legge til et fag flere ganger");
    }
  }

  public Label getShowInformation() {
    return this.showInformation;
  }

  @FXML
  private void updateCourseList() {
    pickCourse.setItems(this.courseList);
    pickCourseDelete.setItems(this.courseList);
  }

  /**
   * Adds time to the 
   *
   */
  @FXML
  public void addTime() {
    String currentTimeString = timeToAdd.getText();
    String[] partition = currentTimeString.split(Pattern.quote(" "));

    Double currentTime = Double.parseDouble(partition[0]);
    currentTime = currentTime + 0.25;
    timeToAdd.setText(currentTime + " t");
  }

  /**
   * Opens a new fxml-window with the statistics of time spent on each course.
   *
   */
  @FXML
  public void onOpenStatisticsClick(ActionEvent event) throws Exception {
    try {
      Parent statisticParent = FXMLLoader.load(getClass().getResource("fxStatistic.fxml"));
      Scene statisticScene = new Scene(statisticParent);
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(statisticScene);
      window.show();

    } catch (Exception e) {
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

  /**
   * Adds the chosen time to a chosen subject.
   *
   */
  @FXML
  public void addStudyHours() {
    String courseChosen = pickCourse.getValue();
    if (courseChosen == null) {
      showInformation.setText("Du må velge et fag");
    } else {
      for (var i = 0; i < courseNames.size(); i++) {
        if (courseChosen.equals(courseNames.get(i).getText())) {
          makeStudyHours(courseNames.get(i), courseTimers.get(i));
          break;
        }
      }
      /*
       * if (courseChosen.equals(courseName1.getText())) {
       * this.makeStudyHours(courseName1, courseTimer1); } else if
       * (courseChosen.equals(courseName2.getText())) {
       * this.makeStudyHours(courseName2, courseTimer2); } else if
       * (courseChosen.equals(courseName3.getText())) {
       * this.makeStudyHours(courseName3, courseTimer3); } else if
       * (courseChosen.equals(courseName4.getText())) {
       * this.makeStudyHours(courseName4, courseTimer4); }
       */
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
    this.remoteAccess.addTimeToCourse(courseName.getText(), hoursToAdd);
  }

  /**
   * Resets the app by clearing the semester and emptying all the fields.
   *
   */
  @FXML
  public void onResetButtonClick() {
    this.remoteAccess.deleteSemester();
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

  /*
   * @FXML public void deleteCourse() { final String courseChosenDelete =
   * pickCourseDelete.getValue();
   * 
   * if (courseChosenDelete == null) {
   * showInformation.setText("Du må velge et fag først"); } else { if
   * (courseChosenDelete.equals(courseName1.getText())) {
   * this.makeDeleteCourse(courseName1, courseTimer1); } else if
   * (courseChosenDelete.equals(courseName2.getText())) {
   * this.makeDeleteCourse(courseName2, courseTimer2); } else if
   * (courseChosenDelete.equals(courseName3.getText())) {
   * this.makeDeleteCourse(courseName3, courseTimer3);
   * showInformation.setText("Faget er slettet"); } else if
   * (courseChosenDelete.equals(courseName4.getText())) {
   * this.makeDeleteCourse(courseName4, courseTimer4); } } }
   */

  /**
   * Deletes a chosen course from the app.
   *
   */
  @FXML
  public void deleteCourse() {
    String courseChosenDelete = pickCourseDelete.getValue();
    if (courseChosenDelete == null) {
      showInformation.setText("Du må velge et fag først");
    } else {
      for (int i = 0; i < courseNames.size(); i++) {
        if (courseNames.get(i).getText().equals(courseChosenDelete)) {
          makeDeleteCourse(courseNames.get(i), courseTimers.get(i));
        }
      }
    }
  }

  private void makeDeleteCourse(Label courseName, Label courseTime) {
    courseList.remove(courseName.getText());
    updateCourseList();
    this.remoteAccess.deleteCourse(courseName.getText());
    this.semester.removeCourse(courseName.getText());
    this.setFieldsEmpty(courseName, courseTime);
    showInformation.setText("Faget er slettet");
  }

  private ArrayList<Label> combineLabels() {
    final ArrayList<Label> tmp = new ArrayList<>();
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

  public List getCourseNames() {
    return this.courseNames;
  }

  public List getCourseTimers() {
    return this.courseTimers;
  }
}