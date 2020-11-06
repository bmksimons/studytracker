package studytracker.restapi;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import studytracker.core.Semester;
import studytracker.json.StudyTrackerPersistence;
//import studytracker.restapi.StudyTrackerResource;

@Path(SemesterService.STUDYTRACKER_MODEL_SERVICE_PATH)
public class SemesterService {

  private static final Logger LOG = LoggerFactory.getLogger(SemesterService.class);
  public static final String STUDYTRACKER_MODEL_SERVICE_PATH = "studytracker";
  private StudyTrackerPersistence studyTrackerPersistence;

  @Inject
  private Semester semester;

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public Semester getSemester() {
     return this.semester;
   }

  // @Path("/{name}")
  // public StudyTrackerResource getStudyTracker() {
  // Semester semester = getSemester();
  // LOG.debug("Sub-resource for Semester :" + semester);
  // return new StudyTrackerResource(semester);
  // }

  // @GET
  // @Produces(MediaType.APPLICATION_JSON)
  // public Semester getSemester() throws JsonParseException, JsonMappingException, IOException {
  //   this.semester = this.studyTrackerPersistence.readSemester("semester.json");
  //   return this.semester;
  // }

  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  public boolean removeSemester() {
    this.semester.resetSemester();
    return true;
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public void putSemester() throws JsonGenerationException, JsonMappingException, IOException {
    this.studyTrackerPersistence.writeSemester("semester.json", this.semester);
  }
}
