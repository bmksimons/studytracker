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
   * Initialize this StudyTrackerConfig.
   *
   * @param semester semester instance to serve.
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
   * Initializing the StudyTrackerConfig with the semester saved in semester.json.
   */
  public StudyTrackerConfig() {
    this(createSemester());
  }

  public Semester getSemester() {
    return this.semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  private static Semester createSemester() {
    StudyTrackerPersistence studyTrackerPersistence = new StudyTrackerPersistence();
    URL url = StudyTrackerConfig.class.getResource("semester.json");
    if (url != null) {
      try (Reader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)) {
        return studyTrackerPersistence.readSemester(reader);
      } catch (IOException e) {
        System.err.println("Couldn't read semester.json so rigging semester manually" + e);
      }
    }
    Semester semester = new Semester();
    return semester;
  }
}