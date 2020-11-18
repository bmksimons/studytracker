package studytracker.ui;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.charset.StandardCharsets;
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
 * Encodes the input-string to an URL.
 * 
 * @param s the string to be encoded.
 * @return String the encoded string.
 */
  private String uriParam(String s) {
    return URLEncoder.encode(s, StandardCharsets.UTF_8);
  }

  /**
 * Makes an URI for a given course.
 * 
 * @param courseName the name of the course.
 * @return URI the URI made in the method.
 */
  private URI courseUri(String courseName){
        return endpointUri.resolve(uriParam(courseName));
  }

  /**
 * Makes an URI for the AddTimeToCourse-method.
 * 
 * @param courseName the name of the course.
 * @param hoursToAdd the amount of time we want to add to the course.
 * @return URI the URI made in the method.
 */
  private URI courseUri(String courseName, String hoursToAdd) {
    return endpointUri.resolve(uriParam(courseName) + "/newTime?addTime=" + uriParam(hoursToAdd));
  }

  /**
   * returns the Semester saved on the resterver. 
   *
   * @return The semester serialized via the Httprequest.
   */
  public Semester getSemester() {
    if (this.semester == null) {
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
   * Puts a semester to the server.
   *
   * @param Semester the Semester in the controller with direct access to the App.
   */
  public void putSemester(Semester semester) {
    try {
      String json = objectMapper.writeValueAsString(semester);
      HttpRequest request = HttpRequest
          .newBuilder(this.endpointUri)
          .header("Accept", "application/json")
          .header("Content-Type", "application/json")
          .PUT(BodyPublishers.ofString(json))
          .build();
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
  public void deleteSemester() { 
    try {
      HttpRequest request = HttpRequest
          .newBuilder(this.endpointUri)
          .header("Accept", "application/json")
          .DELETE()
          .build();
      final HttpResponse<String> response = HttpClient
          .newBuilder()
          .build()
          .send(request,
          HttpResponse.BodyHandlers.ofString());
      String responseString = response.body();
      Boolean removed = objectMapper.readValue(responseString, Boolean.class);
      if (removed == true) {
        this.semester.resetSemester(false);
      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Adds time to a given Course.
   *
   * @param courseName the name of the course which we want to change.
   * @param hoursToAdd the hours to add to the course.
   */
  public void addTimeToCourse(String courseName, Double hoursToAdd) { 
    try {
      HttpRequest request = HttpRequest
          .newBuilder(courseUri(courseName, String.valueOf(hoursToAdd))) 
          .header("Accept", "application/json")
          .POST(BodyPublishers.ofString(""))
          .build();
      final HttpResponse<String> response =
          HttpClient
          .newBuilder()
          .build()
          .send(request, 
          HttpResponse.BodyHandlers.ofString());
      String responseString = response.body();
      Boolean changedTime = objectMapper.readValue(responseString, Boolean.class);
      if (changedTime == true) {
        this.semester.getCourse(courseName).addTime(hoursToAdd);
      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Deletes the given course.
   *
   * @param courseName the name of the course which will be deleted.
   */
  public void deleteCourse(String courseName) { 
    try {
      HttpRequest request = HttpRequest
          .newBuilder(courseUri(courseName)) 
          .header("Accept", "application/json")
          .DELETE()
          .build();
      final HttpResponse<String> response = HttpClient
          .newBuilder()
          .build()
          .send(request,
          HttpResponse.BodyHandlers.ofString());
      String responseString = response.body();
      Boolean removed = objectMapper.readValue(responseString, Boolean.class);
      if (removed == true) {
        this.semester.deleteCourse(courseName);
      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}