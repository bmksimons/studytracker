package studytracker.restserver;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import studytracker.core.Semester;
import studytracker.json.StudyTrackerPersistence;
import studytracker.restapi.SemesterService;

public class StudyTrackerConfig extends ResourceConfig {

  private Semester semester;

  /**
   * Initialize this TodoConfig.
   *
   * @param StudyTrackerModel todoModel instance to serve
   */
  public StudyTrackerConfig(Semester semester) {
    setSemester(semester);
    register(SemesterService.class);
    register(StudyTrackerModuleObjectMapperProvider.class);
    register(JacksonFeature.class);
    register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(StudyTrackerConfig.this.semester);
      }
    });
  }

  /**
   * Initialize this TodoConfig with a default TodoModel.
   */
  public StudyTrackerConfig() {
    this.createDefaultSemester();
  }

  public Semester getSemester() {
    return this.semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  private Semester createDefaultSemester() {
    StudyTrackerPersistence studyTrackerPersistence = new StudyTrackerPersistence();
    try {
      studyTrackerPersistence.readSemester("default-semester");
      this.semester = studyTrackerPersistence.readSemester("default-semester");
      return this.semester;
    } catch (IOException e) {
        System.out.println("Couldn't read default-semester.json, so rigging Semester manually ("
            + e + ")");
        this.semester = new Semester();
        return this.semester;
      }
    }
}