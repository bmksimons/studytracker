package fxui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import studytracker.core.Course;
import studytracker.core.Semester;
import studytracker.ui.Controller;
import javafx.fxml.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import javafx.stage.Window;
public class studyTrackerAppTest extends ApplicationTest{

  private Controller controller;
  private Course course1, course2; 
  private Semester semester;
  
  @Override
  public void start(final Stage primaryStage) throws Exception {
    final Parent parent = FXMLLoader.load(getClass().getResource("../studytracker/ui/fxApp.fxml")); 
    primaryStage.setScene(new Scene(parent));
    primaryStage.show();
    System.out.println(primaryStage.getTitle());
  }
  //  @Override
  // public void start(final Stage stage) throws Exception {
  //   final FXMLLoader loader = new FXMLLoader(getClass().getResource("../studytracker/ui/fxApp.fxml"));
  //   final Parent root = loader.load();
  //   this.controller = loader.getController();
  //   stage.setScene(new Scene(root));
  //   stage.show();
  // }

  @BeforeEach
  public void setup(){
    clickOn("#reset");
    //course1 = new Course("Matte 1");
    course2 = new Course("Algdat");
    //semester = new Semester();
  }

  // @Test
  // public void testController_studyTracker() {
  //   assertNotNull(this.controller);
  //   // assertNotNull(this.semester);
  // }

  // @Test
  // public void testAddNewCourse(){
  //   clickOn("#newCourse").write("IT1901");
  //   clickOn("#addCourse");
  //   assertEquals("IT1901",controller.getCourseName1().getText());
  // } 
  
  // @Test
  // public void testAddEqualCourse(){
  //   clickOn("#newCourse").write("Matte 1");
  //   clickOn("#addCourse");
  //   clickOn("newCourse").write("Matte 1");
  //   if (!controller.getShowInformation().getText().equals("Dette faget er allerede lagt til")){
  //     fail();
  //   }
  //   assertNull(controller.getCourseName2());
      
  //   }
  

  // @Test
  // public void testAddTimePlusButton(){
  //   clickOn("#newCourse").write("matte 1");
  //   clickOn("#addCourse");
  //   clickOn("#pickCourse");
  //   type(KeyCode.DOWN);
  //   type(KeyCode.ENTER);
  //   clickOn("#plusTime");
  //   clickOn("#addTime");
  //   assertEquals(controller.getCourseTimer1().getText(), "0.25 t");
  
  // }

  // @Test
  // public void testAddTimeMinusButton(){
  //   clickOn("#newCourse").write("matte 1");
  //   clickOn("#addCourse");
  //   clickOn("#pickCourse");
  //   type(KeyCode.DOWN);
  //   type(KeyCode.ENTER);
  //   for (int i=0 ; i<10 ; i++){
  //   clickOn("#plusTime");
  //   }
  //   clickOn("#minusTime");
  //   clickOn("#addTime");
  //   assertEquals("2.25 t", controller.getCourseTimer1().getText());
  // }

  // @Test
  // public void testStatisticView(){
  //   clickOn("#statistic");
  //   assertEquals("Statistics View", open.get(0).getTitle());
  // }
}
