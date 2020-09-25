package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import studytracker.core.Course;
import studytracker.core.Semester;

class CourseTest {


@Test
public void testAddTime(){
    Course c1 = new Course("matte1", new Semester());
    c1.addTime(100);
    assertEquals(100, c1.getTimeSpent());
    try {
        c1.addTime(-1);
        fail("argument must be positive");
    }
    catch(IllegalArgumentException e){        
    }
}
    
}