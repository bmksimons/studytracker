package studytracker.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import studytracker.core.Course;
import studytracker.core.Semester;
import studytracker.json.StudyTrackerModule;

public class Controller {

  private Semester semester;
  private ObjectMapper mapper = new ObjectMapper();
  private final ObservableList<String> courseList = FXCollections.observableArrayList();
  private final int maxCourses;
  private int currentNumberCourses;

  @FXML
  private Label courseName1;
  @FXML
  private Label courseName2;
  @FXML
  private Label courseName3;
  @FXML
  private Label courseName4;
  private final ArrayList<Label> courseNames;
  

  @FXML
  private Label courseTimer1;
  @FXML
  private Label courseTimer2;
  @FXML
  private Label courseTimer3;
  @FXML
  private Label courseTimer4;
  private final List<Label> courseTimers;

  @FXML
  private ChoiceBox<String> pickCourse;
  @FXML
  private TextField timeToAdd;
  @FXML
  private Button plusTime;
  @FXML
  private Button minusTime;
  @FXML
  private Button addTime;

  @FXML
  private TextField newCourse;
  @FXML
  private Button addCourse;

  @FXML
  private Button reset;

  @FXML
  private Label showInformation;

  @FXML
  private Button delete;
  @FXML
  private ChoiceBox<String> pickCourseDelete;

  public Controller() {
    this.semester = null;
    this.mapper = new ObjectMapper();
    //this.courseList = FXCollections.observableArrayList();
    this.courseNames = new ArrayList<>();
    this.courseTimers = new ArrayList<>();
    this.maxCourses = 4;
    this.currentNumberCourses = 0;
  }

  @FXML
  public void initialize() {
    mapper.registerModule(new StudyTrackerModule());
   
    this.courseNames.add(this.courseName1);
    this.courseNames.add(this.courseName2);
    this.courseNames.add(this.courseName3);
    this.courseNames.add(this.courseName4);
    this.courseTimers.add(this.courseTimer1);
    this.courseTimers.add(this.courseTimer2);
    this.courseTimers.add(this.courseTimer3);
    this.courseTimers.add(this.courseTimer4);

    try {
      this.semester = mapper.readValue(new File("semester.json"), Semester.class);
      final Iterator<Course> semesterIt = this.semester.iterator();
      for (final Label label : this.courseNames) {
        if (semesterIt.hasNext()) {
          final String courseName = semesterIt.next().getCourseName();
          label.setText(courseName);
          this.courseList.add(courseName);
          this.updateCourseList();
        }
      }
      final Iterator<Course> semesterIt2 = this.semester.iterator();
      for (final Label label : this.courseTimers) {
        if (semesterIt2.hasNext()) {
          label.setText(String.valueOf(semesterIt2.next().getTimeSpent()));
        }
      }
    } catch (final JsonProcessingException e) {
      this.semester = new Semester();
      this.showInformation.setText("json processing exception");
    } catch (final IOException e) {
      this.showInformation.setText("IOException");
    }
    timeToAdd.setText("0 t");
    this.semester.addSemesterListener(semester -> this.saveSemester());
  }

  public void saveSemester() {
    try {
      mapper.writeValue(Paths.get("semester.json").toFile(), this.semester);
    } catch (final JsonProcessingException e) {
      this.showInformation.setText("Klarte ikke lagre jsonData til fil");
    } catch (final IOException e) {
      this.showInformation.setText("Klarte ikke skrive til fil");
    }
  }

  /*@FXML
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
  }*/

  @FXML
  public void addCourse() {
    if(newCourse.getText() == "") {
      showInformation.setText("Du må skrive inn et fag");
    } else if(currentNumberCourses == maxCourses) {
      showInformation.setText("Du kan kun legge til " + maxCourses + " fag");
    } else {
      for(Label courseName : courseNames) {
        if(courseName.getText() == "") {
          courseName.setText(newCourse.getText());
        }
      }

      for(Label courseTimer : courseTimers) {
        if(courseTimer.getText() == "") {
          courseTimer.setText("0 t");
        }
      }

      makeCourse(courseNames);
      newCourse.setText("");
    }
    
  }

  @FXML
  private void makeCourse(List<Label> courseNames) {
    for(Label courseName : courseNames) {
        try {
          this.semester.addCourse(new Course(newCourse.getText()));
          courseName.setText(newCourse.getText());
          courseList.add(newCourse.getText());
          updateCourseList();
          
        } catch (final IllegalArgumentException e) {
            this.showInformation.setText("Kan ikke legge til et fag flere ganger");
      }
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

  @FXML
  public void addTime() {
    final String currentTimeString = timeToAdd.getText();
    final String[] partition = currentTimeString.split(Pattern.quote(" "));

    Double currentTime = Double.parseDouble(partition[0]);
    currentTime = currentTime + 0.25;
    timeToAdd.setText(currentTime + " t");
  }

  @FXML
  public void removeTime() {
    final String currentTimeString = timeToAdd.getText();
    final String[] partition = currentTimeString.split(Pattern.quote(" "));

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
    final String courseChosen = pickCourse.getValue();
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
  private void makeStudyHours(final Label courseName, final Label courseTime) {
    final String currentTimeString = timeToAdd.getText();
    final String[] partition = currentTimeString.split(Pattern.quote(" "));
    final Double hoursToAdd = Double.parseDouble(partition[0]);
    final String currentStudyTime = courseTime.getText();
    final String[] partition2 = currentStudyTime.split(Pattern.quote(" "));
    final Double beforeHoursStudied = Double.parseDouble(partition2[0]);
    final Double hoursStudied = beforeHoursStudied + hoursToAdd;
    courseTime.setText(hoursStudied + " t");
    this.semester.addTimeToCourse(courseName.getText(), hoursToAdd);
  }

  @FXML
  public void onResetButtonClick() {
    for (final Label label : combineLabels()) {
      label.setText("");
    }
    timeToAdd.setText("0 t");
    courseList.clear();
    updateCourseList();
    this.semester.clearSemester();
  }

  @FXML
  private void setFieldsEmpty(final Label courseName, final Label courseTime) {
    courseName.setText("");
    courseTime.setText("");
  }

  /*@FXML
  public void deleteCourse() {
    final String courseChosenDelete = pickCourseDelete.getValue();

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
  }*/

  @FXML
  public void deleteCourse() {
    String courseChosenDelete = pickCourseDelete.getValue();
    if(courseChosenDelete == null) {
        showInformation.setText("Du må velge et fag først");
    } else {
      for(int i=0; i<courseNames.size(); i++) {
        if (courseNames.get(i).getText() == courseChosenDelete) {
          makeDeleteCourse(courseNames.get(i), courseTimers.get(i));
        }
      }
    }
  }

  private void makeDeleteCourse(final Label courseName, final Label courseTime) {
    courseList.remove(courseName.getText());
    updateCourseList();
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
}