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
import java.util.List;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import studytracker.core.Semester;
import studytracker.core.Course;
import studytracker.filehandling.Load;
import studytracker.filehandling.Save;
import studytracker.json.StudyTrackerModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Controller {

    private Semester semester;
    private ObjectMapper mapper = new ObjectMapper();
    private ObservableList<String> courseList = FXCollections.observableArrayList();

	@FXML private Label courseName1;
	@FXML private Label courseName2;
	@FXML private Label courseName3;
	@FXML private Label courseName4;

	@FXML private Label courseTimer1;
	@FXML private Label courseTimer2;
	@FXML private Label courseTimer3;
	@FXML private Label courseTimer4;
	@FXML private TextField timeToAdd;

	@FXML private ChoiceBox<String> pickCourse;
	@FXML private Button plusTime;
	@FXML private Button minusTime;

	@FXML private TextField newCourse;
	@FXML private Button addCourse;

	@FXML private Button reset;
	@FXML private Button addTime;

    @FXML private Label showInformation;
    
    @FXML private ListView<Course> semesterView;

    private final static String semesterWithTwoCourses = "{\"semester\":[{\"courseName\":\"course1\",\"courseTimer\":courseTimer1}]}";

    public Controller() {
        mapper.registerModule(new StudyTrackerModule());
        try{
            this.semester = mapper.readValue(semesterWithTwoCourses, Semester.class);
        }catch(JsonProcessingException e){
            this.semester = new Semester();
        }
    } 

	@FXML
	public void initialize() {
        timeToAdd.setText("0 t");
        this.updateSemesterView();
        semester.addSemesterListener(semester -> this.updateSemesterView());
        this.semesterView.setCellFactory(listeView -> new SemesterCell());
    }
    
    protected void updateSemesterView(){
        List<Course> viewList = semesterView.getItems();
        viewList.clear();
        viewList.addAll(semester.getCourses());
    }

	@FXML
	public void addCourse() {
		if (newCourse.getText().equals("")) {
			showInformation.setText("Du må skrive inn et fag");
		} else {
            if (courseName1.getText().equals("")) {
                courseName1.setText(newCourse.getText());
                courseList.add(newCourse.getText());
			    pickCourse.setItems(this.courseList);
				newCourse.setText("");
                courseTimer1.setText("0 t");
			} else if (courseName2.getText().equals("")) {
                courseName2.setText(newCourse.getText());
                courseList.add(newCourse.getText());
			    pickCourse.setItems(this.courseList);
				newCourse.setText("");
                courseTimer2.setText("0 t");
			} else if (courseName3.getText().equals("")) {
                courseName3.setText(newCourse.getText());
                courseList.add(newCourse.getText());
			    pickCourse.setItems(this.courseList);
				newCourse.setText("");
                courseTimer3.setText("0 t");
			} else if (courseName4.getText().equals("")) {
                courseName4.setText(newCourse.getText());
                courseList.add(newCourse.getText());
			    pickCourse.setItems(this.courseList);
				newCourse.setText("");
                courseTimer4.setText("0 t");
			} else {
				showInformation.setText("Du kan kun legge til 4 fag");
            }
            this.semester.addCourse(new Course(newCourse.getText(), this.semester));
		}
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
		String currentTimeString = timeToAdd.getText();
		String[] partition = currentTimeString.split(Pattern.quote(" "));

		double hoursToAdd = Double.parseDouble(partition[0]);

        String courseChosen = pickCourse.getValue();
        
        String currentStudyTime = courseTimer1.getText();
		String[] partition2 = currentStudyTime.split(Pattern.quote(" "));
		double beforeHoursStudied = Double.parseDouble(partition2[0]);
		double hoursStudied = beforeHoursStudied + hoursToAdd;

		if (courseChosen == null) {
			showInformation.setText("Du må velge et fag");
		} else {
            try{
                if (courseChosen.equals(courseName1.getText())) {
                 courseTimer1.setText(hoursStudied + " t");
			    } else if (courseChosen.equals(courseName2.getText())) {
				    courseTimer2.setText(hoursStudied + " t");
			    } else if (courseChosen.equals(courseName3.getText())) {
				    courseTimer3.setText(hoursStudied + " t");
			    } else if (courseChosen.equals(courseName4.getText())) {
				    courseTimer4.setText(hoursStudied + " t");
            }
            for (Course course : this.semester){
                if (course.getCourseName().equals(courseChosen)){
                    course.addTime(hoursToAdd);
                }
            }
        }catch(Exception e){
                showInformation.setText("Noe gikk galt");
            }
		}
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