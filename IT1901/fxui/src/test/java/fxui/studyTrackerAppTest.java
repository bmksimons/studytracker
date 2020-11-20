package fxui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import studytracker.ui.Controller;
import javafx.fxml.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

public class studyTrackerAppTest extends ApplicationTest {

  private Controller controller;

  @Override
  public void start(final Stage primaryStage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("../studytracker/ui/fxApp.fxml"));
    final Parent parent = loader.load();
    this.controller = loader.getController();
    primaryStage.setScene(new Scene(parent));
    primaryStage.show();
  }

  @BeforeEach
  public void setup() {
    clickOn("#reset");
  }

  @Test
  public void testController_studyTracker() {
    assertNotNull(this.controller);
  }

  @Test
  public void testAddNewCourse() {
    clickOn("#newCourse").write("IT1901");
    clickOn("#addCourse");
    //check if one ofe the course name matches IT1901
    assertTrue(controller.getCourseNames().stream().map(x -> x.getText()).anyMatch(a -> a.equals("IT1901"))); 
  }

  @Test
  public void testAddEqualCourse() {
    clickOn("#newCourse").write("Matte");
    clickOn("#addCourse");
    clickOn("#newCourse").write("Matte");
    assertEquals(controller.getRemoteSemesterAccess().getSemester().getCourses().size(), 1);
  }

  @Test
  public void testAddTimePlusButton() {
    clickOn("#newCourse").write("matte");
    clickOn("#addCourse");
    clickOn("#pickCourse");
    type(KeyCode.DOWN);
    type(KeyCode.ENTER);
    clickOn("#plusTime");
    clickOn("#addTime");
    assertTrue(controller.gettimeSpentOnCoursesList().stream().anyMatch(a -> a.equals("0.25 h")));
  }

  @Test
  public void testAddTimeMinusButton() {
    clickOn("#newCourse").write("matte");
    clickOn("#addCourse");
    clickOn("#pickCourse");
    type(KeyCode.DOWN);
    type(KeyCode.ENTER);
    for (int i = 0; i < 10; i++) {
      clickOn("#plusTime");
    }
    clickOn("#minusTime");
    clickOn("#addTime");
    //Checks if any timelabel match 2.25 h
    assertTrue(controller.gettimeSpentOnCoursesList().stream().anyMatch(a -> a.equals("2.25 h")));
  }

  @Test
  public void testDeleteCourse() {
    clickOn("#newCourse").write("matte1");
    clickOn("#addCourse");
    clickOn("#pickCourseDelete");
    type(KeyCode.DOWN);
    type(KeyCode.ENTER);
    clickOn("#delete");
    try {
      //checks is all the labels are set to an empty string
      assertTrue(controller.getCourseNames().stream().map(x -> x.getText()).allMatch(a -> a.equals("")));
    } catch (NoSuchElementException e) {
    }
  }

  @Test
  public void testOpenAndCloseStatisticView() {
    try {
      clickOn("#statistic");
      clickOn("#closeStatistics");
    } catch (Exception e) {
      throw e;
    }
  }

  @AfterEach
  public void resetSemester(){
    clickOn("#reset");
  }
}
