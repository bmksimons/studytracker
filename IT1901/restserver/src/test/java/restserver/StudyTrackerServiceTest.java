package restserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import studytracker.core.Course;
import studytracker.core.Semester;
import studytracker.restapi.SemesterService;
import studytracker.restserver.StudyTrackerConfig;
import studytracker.restserver.StudyTrackerModuleObjectMapperProvider;


/**
 * Tests setting up the Semester with StudyTrackerConfig. 
 * Tests the methods in restapi. 
 */
public class StudyTrackerServiceTest extends JerseyTest {

  @Override
  protected ResourceConfig configure() {
    final StudyTrackerConfig config = new StudyTrackerConfig();
    enable(TestProperties.LOG_TRAFFIC);
    enable(TestProperties.DUMP_ENTITY);
    config.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "WARNING");
    return config;
  }

  @Override
  protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
    return new GrizzlyTestContainerFactory();
  }

  private ObjectMapper objectMapper;

  @Override
  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();
    objectMapper = new StudyTrackerModuleObjectMapperProvider().getContext(getClass());
  }

  @AfterEach
  public void tearDown() throws Exception {
    super.tearDown();
  }

  @Test
  public void testGetSemester() {
    Response getResponse = target(SemesterService.STUDYTRACKER_MODEL_SERVICE_PATH)
        .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8").get();
    assertEquals(200, getResponse.getStatus());
    try {
      Semester semester = objectMapper.readValue(getResponse.readEntity(String.class), Semester.class);
      assertNotNull(semester);
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testPutSemester() {
    Response getResponse = target(SemesterService.STUDYTRACKER_MODEL_SERVICE_PATH)
        .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8").get();
    assertEquals(200, getResponse.getStatus());
    try {
      Semester semester = objectMapper.readValue(getResponse.readEntity(String.class), Semester.class);
      semester.resetSemester(false);
      semester.addCourse(new Course("matte"));
      assertEquals("matte", semester.getCourses().iterator().next().getCourseName());
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testDeleteSemester() {
    Response getResponse = target(SemesterService.STUDYTRACKER_MODEL_SERVICE_PATH)
        .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8").get();
    assertEquals(200, getResponse.getStatus());
    try {
      Semester semester = objectMapper.readValue(getResponse.readEntity(String.class), Semester.class);
      semester.resetSemester(false);
      assertFalse(semester.iterator().hasNext());
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testDeleteCourse() {
    Response getResponse = target(SemesterService.STUDYTRACKER_MODEL_SERVICE_PATH)
        .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8").get();
    assertEquals(200, getResponse.getStatus());
    try {
      Semester semester = objectMapper.readValue(getResponse.readEntity(String.class), Semester.class);
      semester.resetSemester(false);
      semester.addCourse(new Course("matte"));
      assertEquals("matte", semester.getCourses().iterator().next().getCourseName());
      semester.deleteCourse("matte");
      assertFalse(semester.iterator().hasNext());
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testAddTimeToCourse() {
    Response getResponse = target(SemesterService.STUDYTRACKER_MODEL_SERVICE_PATH)
        .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8").get();
    assertEquals(200, getResponse.getStatus());
    try {
      Semester semester = objectMapper.readValue(getResponse.readEntity(String.class), Semester.class);
      semester.resetSemester(false);
      semester.addCourse(new Course("matte"));
      semester.addTimeToCourse("matte", 0.25);
      assertEquals(semester.getCourse("matte").getTimeSpent(), 0.25);
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

}