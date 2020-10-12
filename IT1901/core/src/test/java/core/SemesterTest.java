package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import studytracker.core.Course;
import studytracker.core.Semester;

public class SemesterTest {

  private Semester s1;
  private Course c1;
  private Course c2;

  private int recievedNotificationCount = 0;

  @BeforeEach
  public void setUp() {
    s1 = new Semester();
    c1 = new Course("matte");
    c2 = new Course("IT");
  }

  @Test
  public void addDuplicateCourseTest() {
    s1.addCourse(c1);
    try {
      s1.addCourse(c1);
      fail();
    } catch (Exception e) {

    }
  }

  @Test
  public void addTimeToCourseTest(){
    s1.addCourse(c1);
    s1.addTimeToCourse(c1.getCourseName(), 1.5);
    assertEquals(1.5, c1.getTimeSpent());
  }

  @Test
  public void removeCourseTest() {
    s1.addCourse(c1);
    s1.removeCourse(c1);
    try{
      s1.removeCourse(c2);
      fail();
    } catch(Exception e){
    }
  }

  @Test
  public void clearSemesterTest(){
    s1.addCourse(c2);
    s1.clearSemester();
    assertFalse(s1.iterator().hasNext());
  }

  @Test
  public void testFireSemesterChanged() {
    s1.addSemesterListener(list -> {
      this.recievedNotificationCount++;
    });
    assertEquals(0, this.recievedNotificationCount);
    s1.addCourse(c1);
    assertEquals(1, this.recievedNotificationCount);
    s1.removeCourse(c1);
    assertEquals(2, this.recievedNotificationCount);

  }
}
