package studytracker.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class AppIT extends ApplicationTest{

  private Controller controller;

  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("fxApp_it.fxml"));
    final Parent root = loader.load();
    this.controller = loader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }

  @BeforeEach
  public void setup(){
      clickOn("#reset");
  }

  @Test
  public void testController_initial() {
    assertNotNull(this.controller);
  }

  @Test
  public void testAddNewCourse() {
    clickOn("#newCourse").write("IT1901");
    clickOn("#addCourse");
    assertEquals(controller.getCourseNames().stream().map(x -> x.getText()).anyMatch(a -> a.equals("IT1901")), true);
  }

  @Test
  public void testAddEqualCourse() {
    clickOn("#newCourse").write("Matte");
    clickOn("#addCourse");
    clickOn("#newCourse").write("Matte");
    assertEquals(controller.getRemoteSemesterAccess().getSemester().getCourses().size(), 1);
  }

  @Test
  public void testDeleteCourseSimple() {
    clickOn("#newCourse").write("matte1");
    clickOn("#addCourse");
    clickOn("#pickCourseDelete");
    type(KeyCode.DOWN);
    type(KeyCode.ENTER);
    clickOn("#delete");
    try {
      assertEquals(controller.getCourseNames().stream().map(x -> x.getText()).allMatch(a -> a.equals("")), true);
    } catch (NoSuchElementException e) {
    }
  } 

  @AfterEach
  public void resetSemester(){
    clickOn("#reset");
  }
}