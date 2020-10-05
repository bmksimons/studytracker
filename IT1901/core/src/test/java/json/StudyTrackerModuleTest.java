package json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Iterator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import studytracker.core.Course;
import studytracker.core.Semester;
import studytracker.json.StudyTrackerModule;

public class StudyTrackerModuleTest {

  private static ObjectMapper mapper;
  private final static String semesterOneCourse = "{\"courses\":[{\"courseName\":\"Matte\",\"courseTimer\":0.0}]}";

  @BeforeAll
  public static void SetUp() {
    mapper = new ObjectMapper();
    mapper.registerModule(new StudyTrackerModule());

  }

  @Test
  public void testSerializers() {
    Semester semester = new Semester();
    Course course1 = new Course("Matte");
    semester.addCourse(course1);
    try {
      assertEquals(semesterOneCourse, mapper.writeValueAsString(semester));
    } catch (JsonProcessingException e) {
      fail();
    }
  }

  @Test
  public void testDeserializers() {
    try {
      Semester semester = mapper.readValue(semesterOneCourse, Semester.class);
      Iterator<Course> it = semester.iterator();
      assertTrue(it.hasNext());
      assertEquals(it.next().getCourseName(), "Matte");
      assertFalse(it.hasNext());
    } catch (JsonProcessingException e) {
      fail();
    }
  }

  @Test
  public void testDeserializersSerializers() {
    Semester semester = new Semester();
    Course course1 = new Course("Matte");
    semester.addCourse(course1);
    try {
      String json = mapper.writeValueAsString(semester);
      Semester semester2 = mapper.readValue(json, Semester.class);
      Iterator<Course> it = semester2.iterator();
      assertTrue(it.hasNext());
      assertEquals(it.next().getCourseName(), "Matte");
      assertFalse(it.hasNext());
    } catch (JsonProcessingException e) {
      fail();
    }
  }
}