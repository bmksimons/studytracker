package main.java.studytracker.restserver;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import studytracker.core.Semester;
import studytracker.core.StudyTrackerModel;
import studytracker.json.StudyTrackerPersistence;
import studytracker.restapi.StudyTrackerModelService;

public class StudyTrackerConfig extends ResourceConfig {

  private StudyTrackerModel studyTrackerModel;

  private Semester semester;

  /**
   * Initialize this TodoConfig.
   *
   * @param StudyTrackerModel todoModel instance to serve
   */
  public StudyTrackerConfig(StudyTrackerModel studyTrackerModel) {
    setStudyTrackerModel(studyTrackerModel);
    register(StudyTrackerModelService.class);
    register(StudyTrackerModuleObjectMapperProvider.class);
    register(JacksonFeature.class);
    register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(StudyTrackerConfig.this.studyTrackerModel);
      }
    });
  }

  /**
   * Initialize this TodoConfig with a default TodoModel.
   */
  public StudyTrackerConfig() {
    this(createDefaultStudyTrackerModel());
  }

  public StudyTrackerModel getStudyTrackerModel() {
    return studyTrackerModel;
  }

  public void setStudyTrackerModel(StudyTrackerModel studyTrackerModel) {
    this.studyTrackerModel = studyTrackerModel;
  }

  private static StudyTrackerModel createDefaultStudyTrackerModel() {
    StudyTrackerPersistence studyTrackerPersistence = new StudyTrackerPersistence();
    URL url = StudyTrackerConfig.class.getResource("default-studytrackermodel.json");
    if (url != null) {
      try (Reader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)) {
        return studyTrackerPersistence.readStudyTrackerModel(reader);
      } catch (IOException e) {
        System.out.println("Couldn't read default-studytrackermodel.json, so rigging StudyTrackerModel manually ("
            + e + ")");
      }
    }
    return studyTrackerModel;
  }

  private Semester createDefaultStudyTracker() {
    StudyTrackerPersistence studyTrackerPersistence = new StudyTrackerPersistence();
    try {
      studyTrackerPersistence.readSemester("default-studytrackermodel");
      this.semester = this.studyTrackerPersistence.readSemester("default-studytrackermodel");
      return this.semester;
    } catch (IOException e) {
        System.out.println("Couldn't read default-studytrackermodel.json, so rigging StudyTrackerModel manually ("
            + e + ")");
        this.semester = new Semester();
        return this.semester;
      }
    }
}