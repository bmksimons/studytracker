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

import com.fasterxml.jackson.databind.ObjectMapper;

import studytracker.core.Semester;
import studytracker.core.Course;
import studytracker.filehandling.Load;
import studytracker.filehandling.Save;
import studytracker.json.StudyTrackerModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Controller {

    private static final String semesterWithTwoCourses =
      "{\"items\":[{\"text\":\"Øl\",\"checked\":false},{\"text\":\"Pizza\",\"checked\":true}]}";

    private Semester semester;
    private ObjectMapper mapper = new ObjectMapper();
    private ObservableList<String> courseList = FXCollections.observableArrayList();
	private Load load = new Load();
	private Save save = new Save(); 
    
    @FXML
    ListView<Course> semesterView;

	@FXML
	private Label courseName1;
	@FXML
	private Label courseName2;
	@FXML
	private Label courseName3;
	@FXML
	private Label courseName4;

	@FXML
	private Label courseTimer1;
	@FXML
	private Label courseTimer2;
	@FXML
	private Label courseTimer3;
	@FXML
	private Label courseTimer4;
	@FXML
	private TextField timeToAdd;

	@FXML
	private ChoiceBox<String> pickCourse;
	@FXML
	private Button plusTime;
	@FXML
	private Button minusTime;

	@FXML
	private TextField newCourse;
	@FXML
	private Button addCourse;

	@FXML
	private Button reset;
	@FXML
	private Button addTime;

	@FXML
    private Label showInformation;

    public Controller() {
    // setter opp data
    mapper.registerModule(new StudyTrackerModule());
    Reader reader = null;
    } 

	@FXML
	public void initialize() {
		timeToAdd.setText("0 t");
        showInformation.setText("");
        semester.addSemesterListener(semester -> this.updateSemesterView());
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

		if (courseChosen == null) {
			showInformation.setText("Du må velge et fag");
		} else {
			if (courseChosen.equals(courseName1.getText())) {
				String currentStudyTime = courseTimer1.getText();
				String[] partition2 = currentStudyTime.split(Pattern.quote(" "));
				double beforeHoursStudied = Double.parseDouble(partition2[0]);
				double hoursStudied = beforeHoursStudied + hoursToAdd;
				courseTimer1.setText(hoursStudied + " t");
			} else if (courseChosen.equals(courseName2.getText())) {
				String currentStudyTime = courseTimer2.getText();
				String[] partition2 = currentStudyTime.split(Pattern.quote(" "));
				double beforeHoursStudied = Double.parseDouble(partition2[0]);
				double hoursStudied = beforeHoursStudied + hoursToAdd;
				courseTimer2.setText(hoursStudied + " t");
			} else if (courseChosen.equals(courseName3.getText())) {
				String currentStudyTime = courseTimer3.getText();
				String[] partition2 = currentStudyTime.split(Pattern.quote(" "));
				double beforeHoursStudied = Double.parseDouble(partition2[0]);
				double hoursStudied = beforeHoursStudied + hoursToAdd;
				courseTimer3.setText(hoursStudied + " t");
			} else if (courseChosen.equals(courseName4.getText())) {
				String currentStudyTime = courseTimer4.getText();
				String[] partition2 = currentStudyTime.split(Pattern.quote(" "));
				double beforeHoursStudied = Double.parseDouble(partition2[0]);
				double hoursStudied = beforeHoursStudied + hoursToAdd;
				courseTimer4.setText(hoursStudied + " t");
			} else {
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
		try { //ha denne før alt det andre?
            save.emptyFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	public void save() {
		try {
			save.saveInFile(this.combineLabels());
		} catch (IOException e) {
			showInformation.setText("Klarte ikke lagre dataen");
		} 
	}
	
	public void load() {
		try {
            ArrayList<String> courses = load.loadFromFile("savedStudyPlanner");
            int i = 0;
            for (Label label: this.combineLabels()) {
            	label.setText(courses.get(i));
            	if (!courses.get(i).contains(".") && !courses.get(i).equals("")) {
            		courseList.add(courses.get(i));
            		pickCourse.setItems(this.courseList);
            	}
            	i += 1;
            }
		} catch (IOException e) {
			showInformation.setText("Klarte ikke finne dataen");
	    }
	}
}
