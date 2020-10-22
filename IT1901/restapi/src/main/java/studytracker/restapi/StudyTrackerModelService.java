package studytracker.restapi;
@Path(StudyTrackerModelService.STUDYTRACKER_MODEL_SERVICE_PATH)
public class StudyTrackerModelService {
  public static final STUDYTRACKER_MODEL_SERVICE_PATH = 'studyTracker';

  @inject
  private StudyTrackerModel studyTrackerModel;
  

  @GET
  @Produces(MediaType.APPLICTION_JSON)
  public StudyTrackerModel getStudyTrackerModel() {
      return this.studyTrackerModel;
  }
  @Path('/{name}')
  public StudyTrackerResource getStudyTrackerList(@PathParam('name') String name) {
    Semester semester = getStudyTrackerModel
  }

}


}