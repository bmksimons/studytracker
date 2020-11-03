package studytracker.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import java.io.IOException;

import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.File;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import studytracker.ui.Controller;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Labeled;

public class ControllerStatistic implements Initializable{

  private Label label;
  @FXML
  private NumberAxis naHours;
  @FXML
  private CategoryAxis caCourses;
  @FXML
  private BarChart<String, Number> barchart;

  private HashMap<String, Double> map = new HashMap<String, Double>();

  private Controller mainController = new Controller();




  @FXML
  public void onCloseStatisticsClick(ActionEvent event) throws Exception{
     try {
        Parent statisticParent = FXMLLoader.load(getClass().getResource("fxApp.fxml"));
        Scene statisticScene = new Scene(statisticParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(statisticScene);
        window.show();

    } catch(Exception e) {
        e.printStackTrace();
    }
}


  @Override
  public void initialize(URL url, ResourceBundle rb){

    XYChart.Series<String, Number> series = new XYChart.Series<>();

    /*
    for (int i=0; i<mainController.getCourseNames().size(); i++){
      this.map.put(toString(mainController.getCourseNames().get(i).getText()), Double.valueOf(toString(mainController.getCourseTimers().get(i).getText())));
    }

    series.setName("Course Statistics");

    for (Map.Entry<String, Double> set : this.map.entrySet()){
      series.getData().add(new XYChart.Data<>(set.getKey(), set.getValue()));
    }
    */
    series.getData().add(new XYChart.Data<>("ITGK", 80));
    series.getData().add(new XYChart.Data<>("Diskmat", 60));
    series.getData().add(new XYChart.Data<>("AlgDat", 100));
    series.getData().add(new XYChart.Data<>("dfsd", 120));
    series.getData().add(new XYChart.Data<>("øjjg", 1));

    barchart.getData().add(series);
  }


}