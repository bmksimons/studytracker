package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import studytracker.core.Course;

class CourseTest {


@Test
public void testAddTime(){
    Course c1 = new Course("matte1", 7.5);
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