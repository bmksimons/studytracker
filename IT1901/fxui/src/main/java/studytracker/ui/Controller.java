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
import jdk.nashorn.api.tree.ArrayLiteralTree;
import studytracker.core.Course;
import studytracker.core.Semester;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class Controller {

  private Semester semester;
  private ObservableList<String> courseList = FXCollections.observableArrayList();
  private int maxCourses =  4;
  private int currentNumberCourses = 0;
  private ModifyTime modifyTime = new ModifyTime();

  @FXML
  Label courseName1;
  @FXML
  Label courseName2;
  @FXML
  Label courseName3;
  @FXML
  Label courseName4;

  private List<Label> courseNames = new ArrayList<>();

  @FXML
  Label timeSpentOnCourse1;
  @FXML
  Label timeSpentOnCourse2;
  @FXML
  Label timeSpentOnCourse3;
  @FXML
  Label timeSpentOnCourse4;

  private List<Label> timeSpentOnCourses  = new ArrayList<>();;

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

  @FXML
  public void initialize() {

    this.endpointUri = "http://localhost:8999/studytracker/";
    // this.courseList = FXCollections.observableArrayList();
    this.courseNames = new ArrayList<>();
    this.timeSpentOnCourses = new ArrayList<>();
    addLabelsToList();

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
    for (Label label : this.timeSpentOnCourses) {
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


/**
 * methode for adding a course to the app, and displayng it.
 */
  @FXML
  public void addCourse() {
    if(newCourse.getText() == "") {
      showInformation.setText("Du må skrive inn et fag");
    } else if(currentNumberCourses == maxCourses) {
      showInformation.setText("Du kan kun legge til " + maxCourses + " fag");
    } else {
      for(var i=0; i<courseNames.size(); i++) {
        if(courseNames.get(i).getText().equals("")) {
          makeCourse(courseNames.get(i));
          this.currentNumberCourses += 1;
          break;
        }
      }
      for(Label timeSpentOnCourse : timeSpentOnCourses) {
        if(timeSpentOnCourse.getText().equals("")) {
          timeSpentOnCourse.setText("0 t");
          break;
        }
      }
      newCourse.setText("");
    }
    
  }
/**
 * Methode for adding a new Course-object to the Semester and setting the text for the given label to equal the new course name. 
 * @param courseNamee , the first empty label in courseNames.
 *  
 */
  @FXML
  private void makeCourse(Label courseName) {
        try {
          courseName.setText(newCourse.getText());
          this.semester.addCourse(new Course(newCourse.getText()));
          courseList.add(newCourse.getText());
          updateCourseList();
          
        } catch (final IllegalArgumentException e) {
            this.showInformation.setText("Kan ikke legge til et fag flere ganger");
      }
    }


  public Label getShowInformation() {
    return this.showInformation;
  }
/** 
 * methode for updating the dropdown listview with new courses 
 * */
  @FXML
  private void updateCourseList() {
    pickCourse.setItems(this.courseList);
    pickCourseDelete.setItems(this.courseList);
  }
/**
 * methode for increasing the time you want to add. 
 */
  @FXML
  public void increaseTime() {
    timeToAdd.setText(modifyTime.addTime(timeToAdd.getText()));
  }

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
/**
 * methode for reducing time to add.
 */
  @FXML
  public void reduceTimeToAdd() {
    String time = modifyTime.removeTime(timeToAdd.getText());
    if (time == "kan ikke legge til negativt antall timer"){
      showInformation.setText(time);
    }
    else {
      timeToAdd.setText(time);
    }
  }
/** 
 * Methode for adding time to a given course. 
 */
  @FXML
  public void addStudyHours() {
    String courseChosen = pickCourse.getValue();
    if (courseChosen == null) {
      showInformation.setText("Du må velge et fag");
    } else {
      for(var i=0; i<courseNames.size(); i++) {
        if(courseChosen.equals(courseNames.get(i).getText())) {
          makeStudyHours(courseNames.get(i), timeSpentOnCourses.get(i));
          break;
        }
      }
      timeToAdd.setText("0 t");
      pickCourse.setValue("");
    }
  }
/**
 *method for adding and updating time spent on a course
 @param courseName,CourseTime labels to get information from and update with new info.
   */ 
  @FXML
  private void makeStudyHours(Label courseName, Label courseTime) {
    List<Double> timeValue = modifyTime.makeStudyHours(timeToAdd.getText(), courseTime.getText());
    courseTime.setText(timeValue.get(2) + " t");
    this.semester.addTimeToCourse(courseName.getText(), timeValue.get(0));
  }
/**
 * resets the App, all courses will be deleted, and the semester will become empty. 
 */
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
/**
 * Deletes a given course. If no courses has been chosen, it will return a message to the user to choose a course. 
 */
  @FXML
  public void deleteCourse() {
    String courseChosenDelete = pickCourseDelete.getValue();
    if(courseChosenDelete == null) {
        showInformation.setText("Du må velge et fag først");
    } else {
      for(int i=0; i<courseNames.size(); i++) {
        if (courseNames.get(i).getText().equals(courseChosenDelete)) {
          makeDeleteCourse(courseNames.get(i), timeSpentOnCourses.get(i));
        }
      }
    }
  }
/**
 * methode for removing a course from the semester. I t will also reset the inputlabels to empty.  
 * @param courseName label to get the course name 
  * @param courseTime label to get the course time 
 */
  private void makeDeleteCourse(Label courseName, Label courseTime) {
    courseList.remove(courseName.getText());
    updateCourseList();
    this.semester.removeCourse(courseName.getText());
    this.setFieldsEmpty(courseName, courseTime);
    showInformation.setText("Faget er slettet");
  }

  private ArrayList<Label> combineLabels() {
    final ArrayList<Label> tmp = new ArrayList<>();
    tmp.addAll(this.courseNames);
    tmp.addAll(this.timeSpentOnCourses);
    return tmp;
  }

/**
 * methode for creating a list of strings containg time spent on the courses.
 *  Used for testing.
 * @return a list of string
 */
  public List<String> gettimeSpentOnCoursesList() {
    List<String> tmp = new ArrayList<>();
    Iterator<Label> iterator = timeSpentOnCourses.iterator();

    while(iterator.hasNext()) {
      tmp.add(iterator.next().getText());
    }
    return tmp;
  }

  public List<Label> getCourseNames() {
    return this.courseNames;
  }

  public List<Label> gettimeSpentOnCourses() {
    return this.timeSpentOnCourses;
  }

  /**
   * methode for passing the labels to the testclass
   * @return a list with relevant labels. 
   */
  public List<Label> labelsForTesting(){
    List<Label> labelsForTesting = combineLabels();
    return labelsForTesting;
  }

  public void addLabelsToList(){
  this.courseNames.add(this.courseName1);
    this.courseNames.add(this.courseName2);
    this.courseNames.add(this.courseName3);
    this.courseNames.add(this.courseName4);
    this.timeSpentOnCourses.add(this.timeSpentOnCourse1);
    this.timeSpentOnCourses.add(this.timeSpentOnCourse2);
    this.timeSpentOnCourses.add(this.timeSpentOnCourse3);
    this.timeSpentOnCourses.add(this.timeSpentOnCourse4);
}
}