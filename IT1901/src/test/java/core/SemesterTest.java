package core;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import studytracker.core.Course;
import studytracker.core.Semester;

class SemesterTest{

  @Test
  public void addCourseTest(){
      Course c1 = new Course("matte", 2);
      Semester s1 = new Semester();
      s1.addCourse(c1);
      try {
          s1.addCourse(c1);
          fail();
          ///Her er det noe feil
      } catch (Exception e) {
          
      }
  }
}