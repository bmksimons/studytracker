package studytracker.json;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import studytracker.core.Semester;

public class StudyTrackerPersistence {

  private ObjectMapper mapper;

  public StudyTrackerPersistence() {
    mapper = new ObjectMapper();
    mapper.registerModule(new StudyTrackerModule());
  }
  
  public void writeSemester(String fileName, Semester semester)
      throws JsonGenerationException, JsonMappingException, IOException {
    mapper.writeValue(Paths.get(fileName).toFile(), semester);
  }

  public Semester readSemester(Reader reader) throws IOException {
    return mapper.readValue(reader, Semester.class);
  }

}