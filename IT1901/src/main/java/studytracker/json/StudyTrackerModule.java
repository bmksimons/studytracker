package studytracker.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;

import studytracker.core.Course;
import studytracker.core.Semester;

public class StudyTrackerModule extends SimpleModule {

  private static final String NAME = "StudyTrackerModule";
  private static final VersionUtil VERSION_UTIL = new VersionUtil() {};

  public StudyTrackerModule() {
    super(NAME, Version.unknownVersion());
    addSerializer(Course.class, new CourseSerializer());
    addSerializer(Semester.class, new SemesterSerializer());
    addDeserializer(Course.class, new CourseDeserializer());
    addDeserializer(Semester.class, new SemesterDeserializer());
  }
}