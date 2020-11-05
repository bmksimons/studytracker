package studytracker.restapi;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import studytracker.core.Semester;
import studytracker.json.StudyTrackerPersistence;

public class StudyTrackerResource {

  private final Semester semester;
  private StudyTrackerPersistence studyTrackerPersistence;
  private static final Logger LOG = LoggerFactory.getLogger(SemesterService.class);

  public StudyTrackerResource(Semester semester) {
    this.studyTrackerPersistence = new StudyTrackerPersistence();
    this.semester = semester;
  }

  private void checkSemester() {
    if (this.semester == null) {
      throw new IllegalArgumentException("no semester named");
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Semester getSemester() {
    checkSemester();
    return this.semester;
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public void putSemester(Semester semester) throws JsonGenerationException, JsonMappingException, IOException {
    this.studyTrackerPersistence.writeSemester("semester.json", semester);
  }

  @PUT
  public void putSemester() throws JsonGenerationException, JsonMappingException, IOException {
    this.putSemester(null);
  }

  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  public boolean removeSemester() {
    checkSemester();
    this.semester.resetSemester();
    return true;
  }
}