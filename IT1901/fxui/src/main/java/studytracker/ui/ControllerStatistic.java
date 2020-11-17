package studytracker.ui;

import javafx.fxml.FXML;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.util.HashMap;
import java.util.Map;
import java.net.URI;
import java.net.URISyntaxException;
import studytracker.core.Course;
import studytracker.core.Semester;

public class ControllerStatistic implements Initializable {
  @FXML
  private NumberAxis naHours;
  @FXML
  private CategoryAxis caCourses;
  @FXML
  private BarChart<String, Number> barchart;

  @FXML
  public void onCloseStatisticsClick(ActionEvent event) throws Exception {
    try {
      Parent statisticParent = FXMLLoader.load(getClass().getResource("fxApp.fxml"));
      Scene statisticScene = new Scene(statisticParent);
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(statisticScene);
      window.setTitle("Home");
      window.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    HashMap<String, Double> courseMap = new HashMap<String, Double>();
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    Semester semester = null;
    try {
      RemoteSemesterAccess remoteSemesterAccess = new RemoteSemesterAccess(
          new URI("http://localhost:8999/studytracker/"));
      semester = remoteSemesterAccess.getSemester();
    } catch (URISyntaxException e) {
      System.err.println(e);
    }
    if (semester != null) {
      for (Course course : semester) {
        courseMap.put(course.getCourseName(), course.getTimeSpent());
      }
    }
    series.setName("Course Statistics");
    for (Map.Entry<String, Double> set : courseMap.entrySet()) {
      series.getData().add(new XYChart.Data<>(set.getKey(), set.getValue()));
    }
    barchart.getData().add(series);
  }
}
