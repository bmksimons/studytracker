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
public class studyTrackerAppTest extends ApplicationTest{

  private Controller controller;
  private Course course1, course2; 
  private Semester semester;
  
  // @Override
  // public void start(final Stage primaryStage) throws Exception {
  //   final Parent parent = FXMLLoader.load(getClass().getResource("../studytracker/ui/fxApp.fxml")); 
  //   primaryStage.setScene(new Scene(parent));
  //   primaryStage.show();
  // }

   @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("../studytracker/ui/fxApp.fxml"));
    final Parent root = loader.load();
    this.controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }

  @BeforeEach
  public void setup(){
    clickOn("#reset");
    course1 = new Course("Matte 1");
    course2 = new Course("Algdat");
    semester = new Semester();
  }

  @Test
  public void testController_studyTracker() {
    assertNotNull(this.controller);
    // assertNotNull(this.semester);
  }

  @Test
  public void testAddNewCourse(){
    clickOn("#newCourse").write("IT1901");
    clickOn("#addCourse");
    assertEquals(controller.getCourseNames().stream().map( x -> x.getText()).anyMatch(a -> a.equals("IT1901")),true);
  } 
  
  @Test
  public void testAddEqualCourse(){
    clickOn("#newCourse").write("Matte 1");
    clickOn("#addCourse");
    clickOn("newCourse").write("Matte 1");
    if (!controller.getShowInformation().getText().equals("Dette faget er allerede lagt til")){
      fail();
    }
    assertNull(controller.labelsForTesting().get(1));
      
    }
  

  @Test
  public void testAddTimePlusButton(){
    clickOn("#newCourse").write("matte 1");
    clickOn("#addCourse");
    clickOn("#pickCourse");
    type(KeyCode.DOWN);
    type(KeyCode.ENTER);
    clickOn("#plusTime");
    clickOn("#addTime");
    assertEquals(controller.gettimeSpentOnCoursesList().stream().anyMatch(a->a.equals("0.25 t")), true);
  
  }

  @Test
  public void testAddTimeMinusButton(){
    clickOn("#newCourse").write("matte 1");
    clickOn("#addCourse");
    clickOn("#pickCourse");
    type(KeyCode.DOWN);
    type(KeyCode.ENTER);
    for (int i=0 ; i<10 ; i++){
    clickOn("#plusTime");
    }
    clickOn("#minusTime");
    clickOn("#addTime");
    assertEquals(controller.gettimeSpentOnCoursesList().stream().anyMatch(a->a.equals("2.25 t")), true);
  }

  @Test
  public void testDeleteCourseSimple(){
    clickOn("#newCourse").write("matte 1");
    clickOn("#addCourse");
    clickOn("#pickCourseDelete");
    type(KeyCode.DOWN);
    type(KeyCode.ENTER);
    clickOn("#delete");
    assertEquals(controller.getCourseNames().stream().allMatch(a -> a.equals("")), true);
  }

}
