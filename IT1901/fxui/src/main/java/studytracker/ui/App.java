package studytracker.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

  @Override
  public void start(final Stage primaryStage) throws Exception {
    
    FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("fxApp.fxml"));
    Parent parent1 = fxmlLoader1.load();
    FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("fxStatistic.fxml"));
    Parent parent2 = fxmlLoader2.load();

    fxmlLoader1.getController().setController(fxmlLoader2.getController());
    fxmlLoader2.getController().setController(fxmlLoader1.getController());

    Scene statisticScene = new Scene(parent2);
    fxmlLoader1.getController().setStatisticScene(statisticScene);
    primaryStage.setScene(new Scene(parent1));
    primaryStage.show();
  }

  public static void main(final String[] args) {
    launch(args);
  }
}
