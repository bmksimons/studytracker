package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import studytracker.core.Course;
import studytracker.core.Semester;

public class SemesterTest{

    private Semester s1 = new Semester();
    private Course c1 = new Course("matte", s1);

    private int recievedNotificationCount = 0;
    
    @Test
    public void addCourseTest(){
        s1.addCourse(c1);
        try {
            s1.addCourse(c1);
            fail();
          ///Her er det noe feil
      } catch (Exception e) {
          
      }
  }

  @Test
  public void testFireSemesterChanged(){
      s1.addSemesterListener(list -> {
          this.recievedNotificationCount++;
      });
      assertEquals(0,this.recievedNotificationCount);
      s1.addCourse(c1);
      assertEquals(1, this.recievedNotificationCount);
      s1.removeCourse(c1);
      assertEquals(2, this.recievedNotificationCount);

  }
}
