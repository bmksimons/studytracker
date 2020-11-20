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
  Button addTime;
  @FXML
  Button statistic;

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
  String endpointUri;

  private RemoteSemesterAccess remoteAccess;

  /**
   * Initializes the Controller by loading the Semester from the restserver.
   */
  @FXML
  public void initialize() {
    this.endpointUri = "http://localhost:8999/studytracker/";
    this.addLabelsToList();
    try {
      System.out.println("Using remote endpoint @ " + endpointUri);
      this.setRemoteSemesterAccess(new RemoteSemesterAccess(new URI(endpointUri)));
      this.semester = remoteAccess.getSemester();
    } catch (URISyntaxException e) {
      System.err.println(e);
      this.semester = new Semester();
    }
    this.initializeLabels();
    this.timeToAdd.setText("0");
    this.semester.addSemesterListener(semester -> this.saveSemester());
  }

  /**
   * Sets the correct name and time spent on the labels while initializing the app.
   */
  private void initializeLabels() {
    Iterator<Course> semesterIt = this.semester.iterator();
    int inputIndex = 0;
    while (semesterIt.hasNext()) {
      Course course = semesterIt.next();
      String courseName = course.getCourseName();
      this.courseNames.get(inputIndex).setText(courseName);
      this.courseList.add(courseName);
      this.timeSpentOnCourses.get(inputIndex).setText(String.valueOf(course.getTimeSpent()));
      this.updateDropDownMenus();
      inputIndex += 1;
      currentNumberCourses += 1;
    }
  }

  private void setRemoteSemesterAccess(RemoteSemesterAccess remoteAccess){
    this.remoteAccess = remoteAccess;
  }

  /**
   * Delegates saving the semester in the restserver by calling putSemester in RemoteSemesterAccess.
   */
  public void saveSemester() {
    this.remoteAccess.putSemester(this.semester);
  }

  /**
   * Method for adding a course to the app, and displayng it. If no errors are
   * given, the course will be created in the method createCourse().
   */
  @FXML
  public void addCourse() {
    Boolean added = false;
    if (newCourse.getText().strip() == "") {
      showInformation.setText("You have to write a course name");
    } else if (currentNumberCourses == maxCourses) {
      showInformation.setText("You can only have " + maxCourses + " courses");
    } else {
      for (var i = 0; i < courseNames.size(); i++) {
        if (courseNames.get(i).getText().equals("")) {
          added = createCourse(courseNames.get(i));
          break;
        }
      }
      // Checks if a course was added succsessfully.
      if (added) {
        this.currentNumberCourses += 1;
        for (Label courseTimer : timeSpentOnCourses) {
          if (courseTimer.getText().equals("")) {
            courseTimer.setText("0.0");
            break;
          }
        }
      }
    }
    newCourse.setText("");
  }

  /**
   * Method for adding a new Course-object to the Semester and setting the text
   * for the given label to equal the new course name.
   *
   * @param courseName the first empty label in courseNames.
   * @return true if the course was succsessfully added, false if otherwise
   */
  @FXML
  private boolean createCourse(Label courseName) {
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

  /**
   * Method for updating the dropdown listview with new courses.
   */
  @FXML
  private void updateDropDownMenus() {
    pickCourse.setItems(this.courseList);
    pickCourseDelete.setItems(this.courseList);
  }

  /**
   * Method for increasing the time you want to add.
   */
  @FXML
  public void increaseTime() {
    timeToAdd.setText(modifyTime.increaseTime(timeToAdd.getText()));
  }

  /**
   * Opens a new fxml-window with the statistics of time spent on each course.
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
   * Method for reducing time to add.
   */
  @FXML
  public void reduceTimeToAdd() {
    try{
        String time = modifyTime.reduceTime(timeToAdd.getText());
        timeToAdd.setText(time);
    } catch (IllegalArgumentException e){
      showInformation.setText("It is not possible to add a negative amount of hours");
    }
  }

  /**
   * Checks if a course is chosen. If so, calls modifyTimeSpent.
   */
  @FXML
  public void addStudyHours() {
    String courseChosen = pickCourse.getValue();
    System.out.println(courseChosen);
    if (courseChosen == null) {
      showInformation.setText("You must choose a course");
    }else{
    try {
      for (var i = 0; i < courseNames.size(); i++) {
        if (courseChosen.equals(courseNames.get(i).getText())) {
          modifyTimeSpent(courseNames.get(i), timeSpentOnCourses.get(i));
          break;
        }
      }
      timeToAdd.setText("0");
      showInformation.setText("");
    }
    catch (Exception NumberFormatException ) {
      showInformation.setText("You must add a number, not a word or a letter.");
      timeToAdd.setText("0");
    }
  }
}

  /**
   * Method that gets called by addStudyHours to modify the semester and labels
   * with the added study hours.
   * 
   * @param courseName the name of the course which to be added study hours.
   * @param courseTime the label to be added study hours.
   */
  @FXML
  private void modifyTimeSpent(Label courseName, Label courseTime) {
    List<Double> timeValue = modifyTime.calculateTimeToAdd(timeToAdd.getText(), courseTime.getText());
    this.semester.addTimeToCourse(courseName.getText(), timeValue.get(0));
    this.remoteAccess.addTimeToCourse(courseName.getText(), timeValue.get(0));
    courseTime.setText(timeValue.get(1).toString());
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
    timeToAdd.setText("0");
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
   * Checks if a course is chosen, if so, calls deleteCourse with the given course
   * and updates the course view.
   */
  @FXML
  public void updateCourseViewAfterDeletion() {
    String courseChosenDelete = pickCourseDelete.getValue();
    if (pickCourseDelete.getValue() == null) {
      showInformation.setText("You have to choose a course");
    } else {
      for (int i = 0; i < courseNames.size(); i++) {
        if (courseNames.get(i).getText().equals(courseChosenDelete)) {
          deleteCourse(courseNames.get(i), timeSpentOnCourses.get(i));
          // Checks if there are empty spaces in the course view after deletion, and fills
          // up the spaces.
          while (i < 3 && !courseNames.get(i + 1).getText().equals("")) {
            courseNames.get(i).setText(courseNames.get(i + 1).getText());
            courseNames.get(i + 1).setText("");
            timeSpentOnCourses.get(i).setText(timeSpentOnCourses.get(i + 1).getText());
            timeSpentOnCourses.get(i + 1).setText("");
            i += 1;
          }
          break;
        }
      }
    }
  }

  /**
   * Method for removing a course from the semester. It will also reset the
   * inputlabels to empty.
   * 
   * @param courseName label to get the course name
   * @param courseTime label to get the course time
   */
  private void deleteCourse(Label courseName, Label courseTime) {
    this.semester.deleteCourse(courseName.getText());
    this.remoteAccess.deleteCourse(courseName.getText());
    courseList.remove(courseName.getText());
    updateDropDownMenus();
    this.setFieldsEmpty(courseName, courseTime);
    currentNumberCourses -= 1;
    showInformation.setText("The course has been deleted");
  }

  /**
   * Method for adding elements in courseNames and timeSepntOnCourses
   * 
   * @return ArrayList with all coursenames and time spent on each of the courses.
   */
  private ArrayList<Label> combineLabels() {
    ArrayList<Label> tmp = new ArrayList<>();
    tmp.addAll(this.courseNames);
    tmp.addAll(this.timeSpentOnCourses);
    return tmp;
  }

  /**
   * Combines the labels to their own list. One for coursenames and one for time
   * spent on courses
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


  /**
   * All methods below are for testing purposes.
   */

   public RemoteSemesterAccess getRemoteSemesterAccess() {
    return this.remoteAccess;
  } 

  /**
   * @return courseNames
   */
  public List<Label> getCourseNames() {
    return this.courseNames;
  }

  /**
   * @return timeSpentOnCourses
   */
  public List<Label> gettimeSpentOnCourses() {
    return this.timeSpentOnCourses;
  }

  /**
   * Method for passing the labels to the testclass.
   * 
   * @return a list with relevant labels.
   */
  public List<Label> labelsForTesting() {
    List<Label> labelsForTesting = combineLabels();
    return labelsForTesting;
  }

  public Label getShowInformation() {
    return this.showInformation;
  }

  /**
   * Method for creating a list of strings contaning time spent on the courses.
   * Used for testing.
   * 
   * @return a list of strings
   */
  public List<String> getTimeSpentOnCoursesList() {
    List<String> tmp = new ArrayList<>();
    Iterator<Label> iterator = timeSpentOnCourses.iterator();
    while (iterator.hasNext()) {
      tmp.add(iterator.next().getText());
    }
    return tmp;
  }
}