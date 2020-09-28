package studytracker.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import studytracker.core.Semester;
import studytracker.core.Course;
import studytracker.json.SemesterDeserializer;
import studytracker.json.StudyTrackerModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Controller {

    private Semester semester;
    private ObjectMapper mapper = new ObjectMapper();
    private ObservableList<String> courseList = FXCollections.observableArrayList();
    SemesterDeserializer semesterDeserializer = new SemesterDeserializer();

	@FXML private Label courseName1;
	@FXML private Label courseName2;
	@FXML private Label courseName3;
    @FXML private Label courseName4;
    private List<Label> courseNames = new ArrayList<>();

	@FXML private Label courseTimer1;
	@FXML private Label courseTimer2;
	@FXML private Label courseTimer3;
    @FXML private Label courseTimer4;
    private List<Label> courseTimers = new ArrayList<>();

    @FXML private ChoiceBox<String> pickCourse;
    @FXML private TextField timeToAdd;
	@FXML private Button plusTime;
    @FXML private Button minusTime;
	@FXML private Button addTime;

	@FXML private TextField newCourse;
    @FXML private Button addCourse;

    @FXML private Button reset;

    @FXML private Label showInformation;
    
    @FXML private ListView<Course> semesterView;

    private final static String semesterWithTwoCourses = "{\"semester\":[{\"courseName\":\"matte\",\"courseTimer\":2.0}]}"; 

	@FXML
	public void initialize() {
        mapper.registerModule(new StudyTrackerModule());
        try{
            this.semester = mapper.readValue(semesterWithTwoCourses, Semester.class);
            Iterator<Course> semesterIterator = this.semester.iterator();
            for (Label label: courseNames){
                if (semesterIterator.hasNext()){
                    label.setText(semesterIterator.next().getCourseName());
                }
            }
            for (Label label: courseTimers){
                if (semesterIterator.hasNext()){
                    label.setText(String.valueOf(semesterIterator.next().getTimeSpent()));
                }
            }
        }catch(JsonProcessingException e){
            this.semester = new Semester();
            System.out.println(this.semester.toString());
        }
        timeToAdd.setText("0 t");
        semester.addSemesterListener(semester -> this.saveSemester());
    }

    public void saveSemester(){
        try{
            mapper.writeValue(Paths.get("semester.json").toFile(), this.semester);
            this.showInformation.setText("lagrer...");
        } catch(JsonProcessingException e){
            this.showInformation.setText("Klarte ikke lagre jsonData til fil");
        }catch(IOException e){
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
            }System.out.println(this.semester.toString());
		}
    }
    
    @FXML
    private void makeCourse(Label name, Label timer){
        name.setText(newCourse.getText());
        courseList.add(newCourse.getText());
		pickCourse.setItems(this.courseList);
		newCourse.setText("");
        timer.setText("0 t");
        this.semester.addCourse(new Course(name.getText(), this.semester));
    }

	@FXML
	public void addTime() {
		String currentTimeString = timeToAdd.getText();
		String[] partition = currentTimeString.split(Pattern.quote(" "));

		double currentTime = Double.parseDouble(partition[0]);
		currentTime = currentTime + 0.25;
        timeToAdd.setText(currentTime + " t"); 
	}

	@FXML
	public void removeTime() {
		String currentTimeString = timeToAdd.getText();
		String[] partition = currentTimeString.split(Pattern.quote(" "));

		double currentTime = Double.parseDouble(partition[0]);
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
                this.makeStudyHours(courseTimer1);
			} else if (courseChosen.equals(courseName2.getText())) {
                this.makeStudyHours(courseTimer2);
			} else if (courseChosen.equals(courseName3.getText())) {
                this.makeStudyHours(courseTimer3);
			} else if (courseChosen.equals(courseName4.getText())) {
                this.makeStudyHours(courseTimer4);
            }
		}
    }
    
    @FXML
    private void makeStudyHours(Label timer){
        String currentTimeString = timeToAdd.getText();
		String[] partition = currentTimeString.split(Pattern.quote(" "));
		double hoursToAdd = Double.parseDouble(partition[0]);
        String currentStudyTime = timer.getText();
		String[] partition2 = currentStudyTime.split(Pattern.quote(" "));
		double beforeHoursStudied = Double.parseDouble(partition2[0]);
		double hoursStudied = beforeHoursStudied + hoursToAdd;
        timer.setText(hoursStudied + " t");
        this.semester.getCourses().get(3).addTime(hoursToAdd);
        System.out.println(this.semester.toString());
    }

	@FXML
	public void OnResetButtonClick() {
		for (Label label : combineLabels()) {
			label.setText("");
		}
		courseName1.setText("");
        timeToAdd.setText("0 t");
        courseList.clear();
        pickCourse.setItems(courseList);
        this.semester.clearSemester();
        System.out.println(this.semester.toString());
	}
	
	private ArrayList<Label> combineLabels() {
		ArrayList<Label> tmp = new ArrayList<>();
		tmp.add(courseName1);
		tmp.add(courseName2);
		tmp.add(courseName3);
		tmp.add(courseName4);
		tmp.add(courseTimer1);
		tmp.add(courseTimer2);
		tmp.add(courseTimer3);
		tmp.add(courseTimer4);
		return tmp;
    }
}