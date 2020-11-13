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
    return this.semester;
  }

  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  public boolean removeSemester() throws JsonGenerationException, JsonMappingException, IOException {
    System.out.println("Delete blir kjørt i semesterservice");
    this.semester.clearSemester();
    this.studyTrackerPersistence.writeSemester(json, this.semester);
    return true;
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Boolean putSemester(Semester semester) throws JsonGenerationException, JsonMappingException, IOException {
    System.out.println("put blir kjørt");
    this.semester.setCourses(semester.getCourses());
    this.studyTrackerPersistence.writeSemester(json, this.semester);
    return true;
  }

  @Path("/{name}")
  public CourseResource getCourse(@PathParam("name") String name) {
    Course course = getSemester().getCourse(name);
    LOG.debug("Sub-resource for Semester :" + semester);
    return new CourseResource(semester, name, course);
  }
}
