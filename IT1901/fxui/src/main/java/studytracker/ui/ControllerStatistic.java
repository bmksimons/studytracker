package studytracker.ui;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import studytracker.core.Course;
import studytracker.core.Semester;

public class ControllerStatistic implements Initializable {
  @FXML
  private NumberAxis naHours;
  @FXML
  private CategoryAxis caCourses;
  @FXML
  private BarChart<String, Number> barchart;


  /**
   * method for closing the statisticpage and opening the homepage.
   * 
   * @param event , will close the statisticwindow and open the homepage.
   * 
   * @throws Exception hvis det ikke går å åpne homepage kastes en feilmelding.
   */
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
