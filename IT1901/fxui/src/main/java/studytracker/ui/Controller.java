package studytracker.ui;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import studytracker.core.Course;
import studytracker.core.Semester;

public class Controller {

  private Semester semester;
  private ObservableList<String> courseList = FXCollections.observableArrayList();
  private int maxCourses = 4;
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

  private List<Label> timeSpentOnCourses = new ArrayList<>();

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
    this.endpointUri = "http://localhost:8999/studytracker/";
    addLabelsToList();
    try {
      System.out.println("Using remote endpoint @ " + endpointUri);
      this.setRemoteSemesterAccess(new RemoteSemesterAccess(new URI(endpointUri)));
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
        updateDropDownMenus();
      }
    }
    Iterator<Course> semesterIt2 = this.semester.iterator();
    for (Label label : this.timeSpentOnCourses) {
      if (semesterIt2.hasNext()) {
        label.setText(String.valueOf(semesterIt2.next().getTimeSpent()) + " h");
      }
    }
    this.timeToAdd.setText("0 h");
    this.semester.addSemesterListener(semester -> this.saveSemester());
  }

  public void setRemoteSemesterAccess(RemoteSemesterAccess remoteAccess) {
    this.remoteAccess = remoteAccess;
  }

  public void saveSemester() {
    this.remoteAccess.putSemester(this.semester);
  }

  /**
   * methode for adding a course to the app, and displayng it.
   */
  @FXML
  public void addCourse() {
    Boolean added = false;
    if (newCourse.getText() == "") {
      showInformation.setText("You have to write a course name");
    } else if (currentNumberCourses == maxCourses) {
      showInformation.setText("you can only have " + maxCourses + " courses");
    } else {
      for (var i = 0; i < courseNames.size(); i++) {
        if (courseNames.get(i).getText().equals("")) {
          added = makeCourse(courseNames.get(i));
          this.currentNumberCourses += 1;
          break;
        }
      }
      // checks if a course is added succsessfully
      if (added == true) {
        for (Label courseTimer : timeSpentOnCourses) {
          if (courseTimer.getText().equals("")) {
            courseTimer.setText("0.0 h");
            break;
          }
        }
      }
      newCourse.setText("");
    }
  }

  /**
   * Methode for adding a new Course-object to the Semester and setting the text
   * for the given label to equal the new course name.
   *
   * @param courseNamee , the first empty label in courseNames.
   * @return true if the course was succsessfully added, false if otherwise
   * 
   */
  @FXML
  private boolean makeCourse(Label courseName) {
    try {
      Course course = new Course(newCourse.getText());
      this.semester.addCourse(course);
      courseName.setText(newCourse.getText());
      courseList.add(newCourse.getText());
      updateDropDownMenus();
      return true;
    } catch (final IllegalArgumentException e) {
      this.showInformation.setText("You cannot add several copies of the same course");
      return false;
    }
  }

  public Label getShowInformation() {
    return this.showInformation;
  }

  /**
   * methode for updating the dropdown listview with new courses.
   */
  @FXML
  private void updateDropDownMenus() {
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
      window.setTitle("Statistics View");
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
    if (time == "it is not possible to add a negative amount of hours") {
      showInformation.setText(time);
    } else {
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
      showInformation.setText("You must choose a course");
    } else {
      for (var i = 0; i < courseNames.size(); i++) {
        if (courseChosen.equals(courseNames.get(i).getText())) {
          makeStudyHours(courseNames.get(i), timeSpentOnCourses.get(i));
          break;
        }
      }
      timeToAdd.setText("0 h");
      showInformation.setText("");
    }
  }

  /**
   * Method for adding and updating time spent on a course.
   * 
   * @param courseName 
   * 
   * @param labels 
   * 
   */
  @FXML
  private void makeStudyHours(Label courseName, Label courseTime) {
    List<Double> timeValue = modifyTime.makeStudyHours(timeToAdd.getText(), courseTime.getText());
    this.semester.addTimeToCourse(courseName.getText(), timeValue.get(0));
    this.remoteAccess.addTimeToCourse(courseName.getText(), timeValue.get(0));
    courseTime.setText(timeValue.get(2) + " h");
  }

  /**
   * Resets the App, all courses will be deleted, and the semester will become
   * empty.
   */
  @FXML
  public void onResetButtonClick() {
    this.remoteAccess.deleteSemester();
    for (Label label : combineLabels()) {
      label.setText("");
    }
    timeToAdd.setText("0 h");
    courseList.clear();
    updateDropDownMenus();
    currentNumberCourses = 0;
    this.semester.resetSemester(false);
    showInformation.setText("The semester was deleted");
  }

  @FXML
  private void setFieldsEmpty(Label courseName, Label courseTime) {
    courseName.setText("");
    courseTime.setText("");
  }

  /**
   * Deletes a given course. If no courses has been chosen, it will return a
   * message to the user to choose a course.
   */
  @FXML
  public void deleteCourse() {
    String courseChosenDelete = pickCourseDelete.getValue();
    if (courseChosenDelete == null) {
      showInformation.setText("You have to choose a course");
    } else {
      for (int i = 0; i < courseNames.size(); i++) {
        if (courseNames.get(i).getText().equals(courseChosenDelete)) {
          makeDeleteCourse(courseNames.get(i), timeSpentOnCourses.get(i));
        }
      }
    }
  }

  /**
   * methode for removing a course from the semester. I t will also reset the
   * inputlabels to empty.
   * 
   * @param courseName label to get the course name
   * 
   * @param courseTime label to get the course time
   * 
   */
  private void makeDeleteCourse(Label courseName, Label courseTime) {
    this.semester.deleteCourse(courseName.getText());
    this.remoteAccess.deleteCourse(courseName.getText());
    courseList.remove(courseName.getText());
    updateDropDownMenus();
    this.setFieldsEmpty(courseName, courseTime);
    currentNumberCourses -= 1;
    showInformation.setText("The course has been deleted");
  }

  private ArrayList<Label> combineLabels() {
    final ArrayList<Label> tmp = new ArrayList<>();
    tmp.addAll(this.courseNames);
    tmp.addAll(this.timeSpentOnCourses);
    return tmp;
  }

  /**
   * methode for creating a list of strings containg time spent on the courses.
   * Used for testing.
   * 
   * @return a list of string
   * 
   */
  public List<String> gettimeSpentOnCoursesList() {
    List<String> tmp = new ArrayList<>();
    Iterator<Label> iterator = timeSpentOnCourses.iterator();

    while (iterator.hasNext()) {
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
   * methode for passing the labels to the testclass.
   * 
   * @return a list with relevant labels.
   */
  public List<Label> labelsForTesting() {
    List<Label> labelsForTesting = combineLabels();
    return labelsForTesting;
  }

 /**
  * combines the labels to their own list. One for coursenames and one for time spent on courses
  */
  public void addLabelsToList() {
    this.courseNames.add(this.courseName1);
    this.courseNames.add(this.courseName2);
    this.courseNames.add(this.courseName3);
    this.courseNames.add(this.courseName4);
    this.timeSpentOnCourses.add(this.timeSpentOnCourse1);
    this.timeSpentOnCourses.add(this.timeSpentOnCourse2);
    this.timeSpentOnCourses.add(this.timeSpentOnCourse3);
    this.timeSpentOnCourses.add(this.timeSpentOnCourse4);
  }

  public RemoteSemesterAccess getRemoteSemesterAccess() {
    return this.remoteAccess;
  }
}