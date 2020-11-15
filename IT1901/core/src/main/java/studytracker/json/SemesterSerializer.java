package studytracker.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import studytracker.core.Course;
import studytracker.core.Semester;

public class SemesterSerializer extends JsonSerializer<Semester> {

  /*
   * format: { "courses" : [ { "courseName" : "..." , "courseTimer" : Double}, ... , ] }
   */
  @Override
  public void serialize(Semester semester, JsonGenerator jGen, SerializerProvider serializerProvider)
      throws IOException {
    jGen.writeStartObject();
    jGen.writeArrayFieldStart("courses");
    for (Course course : semester) {
      jGen.writeObject(course);
    }
    jGen.writeEndArray();
    jGen.writeEndObject();
  }
}