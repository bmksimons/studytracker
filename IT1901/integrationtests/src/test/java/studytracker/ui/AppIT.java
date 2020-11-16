package studytracker.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;

import javax.print.DocFlavor.URL;

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
  public void setupItems() throws URISyntaxException {
    try (Reader reader = new InputStreamReader(getClass().getResourceAsStream("it_semester.json"))) {
      String port = System.getProperty("studytracker.port");
      assertNotNull(port, "No studytracker.port system property set");
      URI baseUri = new URI("http://localhost:" + port + "/studytracker/");
      System.out.println("Base RemoteTodoModelAcces URI: " + baseUri);
      this.controller.setRemoteSemesterAccess(new RemoteSemesterAccess(baseUri));
      clickOn("#reset");
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testController_initial() {
    assertNotNull(this.controller);
  }

  // @Test
  // public void testAddNewCourse() {
  //   clickOn("#newCourse").write("IT1901");
  //   clickOn("#addCourse");
  //   assertEquals(controller.getCourseNames().stream().map(x -> x.getText()).anyMatch(a -> a.equals("IT1901")), true);
  // }

  // @Test
  // public void testAddEqualCourse() {
  //   clickOn("#newCourse").write("Matte");
  //   clickOn("#addCourse");
  //   clickOn("#newCourse").write("Matte");
  //   assertEquals(controller.getRemoteSemesterAccess().getSemester().getCourses().size(), 1);
  // }

  // @Test
  // public void testAddTimePlusButton() {
  //   clickOn("#newCourse").write("matte");
  //   clickOn("#addCourse");
  //   clickOn("#pickCourse");
  //   type(KeyCode.DOWN);
  //   type(KeyCode.ENTER);
  //   clickOn("#plusTime");
  //   clickOn("#addTime");
  //   assertEquals(controller.gettimeSpentOnCoursesList().stream().anyMatch(a -> a.equals("0.25 t")), true);

  // }

  // @Test
  // public void testAddTimeMinusButton() {
  //   clickOn("#newCourse").write("matte");
  //   clickOn("#addCourse");
  //   clickOn("#pickCourse");
  //   type(KeyCode.DOWN);
  //   type(KeyCode.ENTER);
  //   for (int i = 0; i < 10; i++) {
  //     clickOn("#plusTime");
  //   }
  //   clickOn("#minusTime");
  //   clickOn("#addTime");
  //   assertEquals(controller.gettimeSpentOnCoursesList().stream().anyMatch(a -> a.equals("2.25 t")), true);
  // }

  // @Test
  // public void testDeleteCourseSimple() {
  //   clickOn("#newCourse").write("matte1");
  //   clickOn("#addCourse");
  //   clickOn("#pickCourseDelete");
  //   type(KeyCode.DOWN);
  //   type(KeyCode.ENTER);
  //   clickOn("#delete");
  //   try {
  //     assertEquals(controller.getCourseNames().stream().map(x -> x.getText()).allMatch(a -> a.equals("")), true);
  //   } catch (NoSuchElementException e) {
  //   }
  // }
}