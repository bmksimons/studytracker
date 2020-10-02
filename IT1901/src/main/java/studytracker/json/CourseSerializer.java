package studytracker.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import studytracker.core.Course;

public class CourseSerializer extends JsonSerializer<Course> {

  /*
   * format: { "courseName": "...", "courseTimer": Double }
   */
  @Override
  public void serialize(Course course, 
                          JsonGenerator jGen, 
                              SerializerProvider serializerProvider) throws IOException {
    jGen.writeStartObject();
    jGen.writeStringField("courseName", course.getCourseName());
    jGen.writeNumberField("courseTimer", course.getTimeSpent());
    jGen.writeEndObject();
  }
}