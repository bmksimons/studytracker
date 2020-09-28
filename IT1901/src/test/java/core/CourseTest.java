package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import studytracker.core.Course;

class CourseTest {


@Test
public void testAddTime(){
    Course c1 = new Course("matte1");
    c1.addTime(100.0);
    assertEquals(100.0, c1.getTimeSpent());
    try {
        c1.addTime(-1.0);
        fail("argument must be positive");
    }
    catch(IllegalArgumentException e){        
    }
}
    
}