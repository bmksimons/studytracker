package studytracker.ui;

import javafx.application.Application;

public class studyTrackerAppTest extends ApplicationTest{

  private Controller studyTrackerController;

  
  @Override
  public void start(final Stage primaryStage) throws Exception {
    final Parent parent = FXMLLoader.load(getClass().getResource("fxApp.fxml"));
    primaryStage.setScene(new Scene(parent));
    primaryStage.show();
  }
}
