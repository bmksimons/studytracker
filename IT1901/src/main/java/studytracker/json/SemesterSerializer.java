package studytracker.json;

import studytracker.core.Semester;
import studytracker.core.Course;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class SemesterSerializer extends JsonSerializer<Semester> {

    /*
   * format:
   * {
   *    "courses": [ ... ]
   * }
   */
  @Override
  public void serialize(Semester semester,
                        JsonGenerator jGen,
                        SerializerProvider serializerProvider) throws IOException {
    jGen.writeStartObject();
    jGen.writeArrayFieldStart("courses");
    for (Course course : semester) {
        jGen.writeObject(course);
    }
    jGen.writeEndArray();
    jGen.writeEndObject();
  }
}