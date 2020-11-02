package studytracker.restapi;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import studytracker.core.Course;
import studytracker.core.Semester;


@Path(SemesterService.STUDYTRACKER_MODEL_SERVICE_PATH)
public class SemesterService {
  
  private static final Logger LOG = LoggerFactory.getLogger(SemesterService.class);
  public static final String STUDYTRACKER_MODEL_SERVICE_PATH = "studytracker";

  @Inject
  private Semester semester;
  

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Semester getSemester() {
    return this.semester;
  }
  
  @Path("/{name}")
  public StudyTrackerResource getStudyTracker() {
    Semester semester = getSemester();
    LOG.debug("Sub-resource for Semester :" + semester);
    return new StudyTrackerResource(semester);
  }
}