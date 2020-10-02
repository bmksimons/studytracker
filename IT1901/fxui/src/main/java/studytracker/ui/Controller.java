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
  private ObservableList<String> courseList = FXCollections.observableArrayList();

  @FXML
  private Label courseName1;
  @FXML
  private Label courseName2;
  @FXML
  private Label courseName3;
  @FXML
  private Label courseName4;
  private List<Label> courseNames = new ArrayList<>();

  @FXML
  private Label courseTimer1;
  @FXML
  private Label courseTimer2;
  @FXML
  private Label courseTimer3;
  @FXML
  private Label courseTimer4;
  private List<Label> courseTimers = new ArrayList<>();

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
      Iterator<Course> semesterIt = this.semester.iterator();
      for (Label label : this.courseNames) {
        if (semesterIt.hasNext()) {
          String name = semesterIt.next().getCourseName();
          label.setText(name);
          this.courseList.add(name);
          this.pickCourse.setItems(this.courseList);
        }
      }
      Iterator<Course> semesterIt2 = this.semester.iterator();
      for (Label label : this.courseTimers) {
        if (semesterIt2.hasNext()) {
          label.setText(String.valueOf(semesterIt2.next().getTimeSpent()));
        }
      }
      System.out.println("alt gikk bra");
    } catch (JsonProcessingException e) {
      this.semester = new Semester();
      System.out.println("json processing exception");
    } catch (IOException e) {
      System.out.println("IOException");
    }
    timeToAdd.setText("0 t");
    //this.semester.addSemesterListener(semester -> this.saveSemester());
  }

  public void saveSemester() {
    try {
      mapper.writeValue(Paths.get("semester.json").toFile(), this.semester);
      this.showInformation.setText("lagrer...");
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
      System.out.println(this.semester.toString());
    }
  }

  @FXML
  private void makeCourse(Label name, Label timer) {
    name.setText(newCourse.getText());
    courseList.add(newCourse.getText());
    pickCourse.setItems(this.courseList);
    newCourse.setText("");
    timer.setText("0 t");
    this.semester.addCourse(new Course(name.getText()));
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
    }
  }

  @FXML
  private void makeStudyHours(Label name, Label timer) {
    String currentTimeString = timeToAdd.getText();
    String[] partition = currentTimeString.split(Pattern.quote(" "));
    Double hoursToAdd = Double.parseDouble(partition[0]);
    String currentStudyTime = timer.getText();
    String[] partition2 = currentStudyTime.split(Pattern.quote(" "));
    Double beforeHoursStudied = Double.parseDouble(partition2[0]);
    Double hoursStudied = beforeHoursStudied + hoursToAdd;
    timer.setText(hoursStudied + " t");
    this.semester.addTimeToCourse(name.getText(), hoursToAdd);
    System.out.println(this.semester.toString());
  }

  @FXML
  public void OnResetButtonClick() {
    for (Label label : combineLabels()) {
      label.setText("");
    }
    courseName1.setText(""); // hvorfor måtte jeg legge til denne?
    timeToAdd.setText("0 t");
    courseList.clear();
    pickCourse.setItems(courseList);
    this.semester.clearSemester();
    System.out.println(this.semester.toString());
  }

  private ArrayList<Label> combineLabels() {
    ArrayList<Label> tmp = new ArrayList<>();
    tmp.addAll(this.courseNames);
    tmp.addAll(this.courseTimers);
    return tmp;
  }
}