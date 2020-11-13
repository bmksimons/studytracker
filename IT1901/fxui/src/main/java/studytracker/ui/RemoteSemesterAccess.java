package studytracker.ui;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import com.fasterxml.jackson.databind.ObjectMapper;
import studytracker.core.Semester;
import studytracker.json.StudyTrackerModule;

/**
 * The class that connects the semester in the Controller class with the
 * restserver and the restapi. Delegates the Http-request which are handled in
 * the SemesterService class in restapi
 */
public class RemoteSemesterAccess {

  private final URI endpointUri;
  private ObjectMapper objectMapper;
  private Semester semester;

  public RemoteSemesterAccess(URI endpointUri) {
    this.endpointUri = endpointUri;
    this.objectMapper = new ObjectMapper().registerModule(new StudyTrackerModule());
  }

  /**
   * returns the Semester saved on the resterver.
   *
   * @return The semester serialized via the Httprequest.
   */
  public Semester getSemester() {
    if (this.semester == null) {
      System.out.println("get blir kjørt i remotesemesteraccess");
      HttpRequest request = HttpRequest
        .newBuilder(this.endpointUri)
        .header("Accept", "application/json")
        .GET()
        .build();
      try {
        final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
            HttpResponse.BodyHandlers.ofString());
        final String responseString = response.body();
        this.semester = objectMapper.readValue(responseString, Semester.class);
      } catch (IOException | InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    return this.semester;
  }

  /**
   * returns the Semester saved on the resterver.
   *
   * @param Semester the Semester in the controller with direct access to the App.
   */
  public void putSemester(Semester semester) {
    try {
      String json = objectMapper.writeValueAsString(semester);
      System.out.println("hello" + json);
      HttpRequest request = HttpRequest
          .newBuilder(this.endpointUri)
          .header("Accept", "application/json")
          .header("Content-Type", "application/json")
          .PUT(BodyPublishers.ofString(json)).build();
      final HttpResponse<String> response = HttpClient.newBuilder()
          .build().send(request,
          HttpResponse.BodyHandlers.ofString());
      String responseString = response.body();
      Boolean add = objectMapper.readValue(responseString, Boolean.class);
      if (add == true) {
        this.semester = semester;
      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Deletes the semester by clearing all the courses.
   *
   * @param the Semester in the controller with direct access to the App.
   */
  public void deleteSemester(Semester semester) { // trenger jeg egt ta inn semester?
    try {
      HttpRequest request = HttpRequest.newBuilder(this.endpointUri).header("Accept", "application/json").DELETE()
          .build();
      final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
          HttpResponse.BodyHandlers.ofString());
      String responseString = response.body();
      Boolean removed = objectMapper.readValue(responseString, Boolean.class);
      if (removed == true) {
        System.out.println("delete blir kjørt i remotesemesteraccess");
      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
