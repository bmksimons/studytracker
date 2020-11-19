package studytracker.restapi;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import studytracker.core.Course;
import studytracker.core.Semester;
import studytracker.json.StudyTrackerPersistence;
import studytracker.restapi.CourseResource;

/**
 * The class which handles the Http-requests sent from RemoteSemesterAccess.
 * Delegates methodes for deleting and changing a course to CourseResource.
 */
@Path(SemesterService.STUDYTRACKER_MODEL_SERVICE_PATH)
public class SemesterService {

  private static final Logger LOG = LoggerFactory.getLogger(SemesterService.class);
  public static final String STUDYTRACKER_MODEL_SERVICE_PATH = "studytracker";
  private StudyTrackerPersistence studyTrackerPersistence = new StudyTrackerPersistence();
  private String json = "restserver/src/main/resources/studytracker/restserver/semester.json";

  @Inject
  private Semester semester;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Semester getSemester() {
    LOG.debug("getSemester({})", this.semester);
    return this.semester;
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public boolean putSemester(Semester semester) throws JsonGenerationException, JsonMappingException, IOException {
    LOG.debug("putSemester({})", semester);
    this.semester.setCourses(semester.getCourses());
    saveSemester();
    return true;
  }

  /**
   * Resets the semester.
   *
   * @return true if the code runs without errors.
   * @throws IOException
   * @throws JsonMappingException
   * @throws JsonGenerationException
   */
  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  public boolean resetSemester() throws JsonGenerationException, JsonMappingException, IOException {
    LOG.debug("resetSemester({})", semester);
    this.semester.resetSemester(false);
    saveSemester();
    return true;
  }

  /**
   * Returns the Course with the given name.
   *
   * @param name the name of the course
   */
  @Path("/{name}")
  public CourseResource getCourse(@PathParam("name") String courseName) {
    //If the courseName in the path contains "+", replace them with whitespaces.
    courseName = courseName.replace("+", " ");
    Course course = getSemester().getCourse(courseName);
    LOG.debug("Sub-resource for Semester :" + semester);
    return new CourseResource(semester, courseName, course);
  }

  /**
   * Saves the semester-object in the semester.json-file in the restserver.
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
