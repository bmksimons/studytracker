package studytracker.restapi;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import studytracker.core.Course;
import studytracker.core.Semester;
import studytracker.json.StudyTrackerPersistence;

/**
 * This class contains the methodes for deleting and changing a course which were delegated from SemesterService.
 */
public class CourseResource {

  private final Semester semester;
  private String courseName;
  private Course course;
  private StudyTrackerPersistence studyTrackerPersistence;
  private static final Logger LOG = LoggerFactory.getLogger(SemesterService.class);
  private String json = "restserver/src/main/resources/studytracker/restserver/semester.json";

  /**
 * Intializes the class with the semester from SemesterService, the name of the course and the course-object. 
 */
  public CourseResource(Semester semester, String courseName, Course course) {
    this.studyTrackerPersistence = new StudyTrackerPersistence();
    this.semester = semester;
    this.courseName = courseName;
    this.course = course;
  }

  private void checkSemester() {
    if (this.semester.getCourse(courseName) == null) {
      throw new IllegalArgumentException("no course named");
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Course getCourse() {
    checkSemester();
    LOG.debug("getCourse({})", courseName);
    return this.course;
  }

  /**
   * Updates the time spent on the Course.
   *
   * @param courseTimer the new time
   * @return true if the code runs without errors
   * @throws IOException
   * @throws JsonMappingException
   * @throws JsonGenerationException
   */
  @POST
  @Path("/newTime")
  @Produces(MediaType.APPLICATION_JSON)
  public boolean addTimeToCourse(@QueryParam("addTime") String hoursToAdd)
      throws JsonGenerationException, JsonMappingException, IOException {
    checkSemester();
    LOG.debug("addTimeToCourse({})", courseName);
    this.semester.getCourse(this.course.getCourseName()).addTime(Double.valueOf(hoursToAdd));
    saveSemester();
    return true;
  }

  /**
   * Deletes the course.
   *
   * @return true if the code runs without errors
   * @throws IOException
   * @throws JsonMappingException
   * @throws JsonGenerationException
   */
  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  public boolean removeCourse() throws JsonGenerationException, JsonMappingException, IOException {
    checkSemester();
    LOG.debug("deleteCourse({})", courseName);
    this.semester.deleteCourse(this.semester.getCourse(this.course.getCourseName()).getCourseName());
    saveSemester();
    return true;
  }

  /**
   * Saves the semester-object in the semester.json-file in the restserver
   */
  private void saveSemester() {
    if (studyTrackerPersistence != null) {
      try {
        this.studyTrackerPersistence.writeSemester(json, semester);
      } catch (IllegalStateException | IOException e) {
        System.err.println("Could't save the semester: " + e);
      }
    }
  }
}