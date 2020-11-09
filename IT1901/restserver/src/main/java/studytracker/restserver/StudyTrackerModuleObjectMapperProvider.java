package studytracker.restserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import studytracker.json.StudyTrackerModule;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudyTrackerModuleObjectMapperProvider implements ContextResolver<ObjectMapper> {

  private final ObjectMapper objectMapper;

  public StudyTrackerModuleObjectMapperProvider() {
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new StudyTrackerModule());
  }

  @Override
  public ObjectMapper getContext(final Class<?> type) {
    return objectMapper;
  }
}