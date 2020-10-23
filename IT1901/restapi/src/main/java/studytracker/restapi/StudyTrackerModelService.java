package studytracker.restapi;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import studytracker.core.Semester;
import studytracker.core.StudyTrackerModel;


@Path(StudyTrackerModelService.STUDYTRACKER_MODEL_SERVICE_PATH)
public class StudyTrackerModelService {
  
  private static final Logger LOG = LoggerFactory.getLogger(StudyTrackerModelService.class);
  public static final String STUDYTRACKER_MODEL_SERVICE_PATH = "studyTracker";

  @Inject
  private StudyTrackerModel studyTrackerModel;
  

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public StudyTrackerModel getStudyTrackerModel() {
    return this.studyTrackerModel;
  }
  
  @Path("/{name}")
  public StudyTrackerResource getStudyTrackerList(@PathParam("name") String name) {
    Semester semester = getStudyTrackerModel().getSemester();
    LOG.debug("Sub-resource for TodoList " + name + ": " + semester);
    return new StudyTrackerResource(this.studyTrackerModel, semester);
  }
}