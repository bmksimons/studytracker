package restserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

public class StudyTrackerServiceTest extends JerseyTest {

  protected boolean shouldLog() {
    return false;
  }

  @Override
  protected ResourceConfig configure() {
    final StudyTrackerConfig config = new StudyTrackerConfig();
    if (shouldLog()) {
      enable(TestProperties.LOG_TRAFFIC);
      enable(TestProperties.DUMP_ENTITY);
      config.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "WARNING");
    }
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
        .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
        .get();
    assertEquals(200, getResponse.getStatus());
    try {
      Semester semester = objectMapper.readValue(getResponse.readEntity(String.class), Semester.class);
      semester.clearSemester();
      assertFalse(semester.iterator().hasNext());
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testPutSemester() {
    Response getResponse = target(SemesterService.STUDYTRACKER_MODEL_SERVICE_PATH)
        .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
        .get();
    assertEquals(200, getResponse.getStatus());
    try {
      Semester semester = objectMapper.readValue(getResponse.readEntity(String.class), Semester.class);
      semester.clearSemester();
      semester.addCourse(new Course("matte"));
      assertEquals("matte", semester.getCourses().iterator().next().getCourseName());
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

  // @Test
  // public void testGetCourse() {
  //   Response getResponse = target(SemesterService.STUDYTRACKER_MODEL_SERVICE_PATH)
  //       .path("matte")
  //       .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
  //       .get();
  //   assertEquals(200, getResponse.getStatus());
  //   try {
  //     Course course = objectMapper.readValue(getResponse.readEntity(String.class), Course.class);
  //     assertEquals("matte", course.getCourseName());
  //   } catch (JsonProcessingException e) {
  //     fail(e.getMessage());
  //   }
  // }

}