package fxui.src.test;

import java.beans.Transient;

import javafx.application.Application;
import studytracker.core.Course;
import studytracker.core.Semester;

public class studyTrackerAppTest extends ApplicationTest{

  private Controller controller;
  private Course course1, course2; 
  private Semester semester;
  
  @Override
  public void start(final Stage primaryStage) throws Exception {
    final Parent parent = FXMLLoader.load(getClass().getResource("fxApp.fxml"));
    final Parent root = loader.load();
    this.controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();

  }
  @BeforeEach
  public void setup(){
    course1 = new Course("Matte 1");
    course2 = new Course("Algdat");
    //semester = new Semester();
  }

  @Test
  public void testController_todoList() {
    assertNotNull(this.controller);
    assertNotNull(this.semester);
  }

  @Test
  public void testAddNewCourse(){
    clickOn("#newCourse").write("IT1901");
    clickOn("#addCourse");
    assertEqual(this.controller.courseName1.getText(), "IT1901");
  } 
  
  @Test
  public void testAddEqualCourse(){
    clickOn("#newCourse").write("Matte 1");
    clickOn("#addCourse");
    try {
      clickOn("#newCourse").write("Matte 1");
      clickOn("#addCourse");
      fail();
    } catch (Exception illegalStatException) {
      //TODO: handle exception
    }
  }

  @Test
  public void testAddTime(){
    clickOn("#newCourse").write("matte 1");
    clickOn("#addCourse");
    clickOn("#pickCourse").clickOn("matte 1")
    clickOn("#timeToAdd").write("10");
    clickOn("#addTime");
    assertEqual(this.controller.courseTimer1.getText(), "10 t");
    clickOn("#plusTime");
    clickOn("#addTime");
    assertEqual(this.controller.courseTimer1,getText(), "20.25 t");
  }
}
