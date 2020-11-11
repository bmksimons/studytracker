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

import studytracker.core.Course;
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

  private String uriParam(String s) {
    return URLEncoder.encode(s, StandardCharsets.UTF_8);
  }

  private URI courseUri(String courseName) {
    return endpointUri.resolve(uriParam(courseName));
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
  public void deleteSemester() { 
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

  // public void putCourse(Course course) {
  //   try {
  //     String json = objectMapper.writeValueAsString(course);
  //     System.out.println(json);
  //     HttpRequest request = HttpRequest.newBuilder(courseUri(course.getCourseName()))
  //         .header("Accept", "application/json")
  //         .header("Content-Type", "application/json")
  //         .PUT(BodyPublishers.ofString(json))
  //         .build();
  //     System.out.println(json);
  //     final HttpResponse<String> response = HttpClient.newBuilder()
  //         .build().send(request,
  //         HttpResponse.BodyHandlers.ofString());
  //     String responseString = response.body();
  //     System.out.println(json);
  //     Boolean add = objectMapper.readValue(responseString, Boolean.class);
  //     System.out.println(json);
  //     if (add == true) {
  //       System.out.println("putcourse blir kjørt i remotesemesteraccess");
  //     }
  //     this.semester.addCourse(course);
  //   } catch (IOException e) { //IOException | InterruptedException e
  //     throw new RuntimeException(e);
  //   }
  // }

  /**
   * Adds time to a Course.
   *
   * @param oldName the name of the TodoList to change
   * @param newName the new name
   */
  public void addTimeToCourse(String courseName, Double timeSpent) {
    try {
      System.out.println(courseName);
      HttpRequest request = HttpRequest
          .newBuilder(courseUri(courseName))
          .header("Accept", "application/json")
          .header("Content-Type", "application/x-www-form-urlencoded")
          .POST(BodyPublishers.ofString("timeSpent=" + uriParam(String.valueOf(timeSpent))))
          .build();
      final HttpResponse<String> response =
          HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
      String responseString = response.body();
      System.out.println(courseName);
      Boolean changedTime = objectMapper.readValue(responseString, Boolean.class);
      System.out.println(courseName);
      if (changedTime == true) {
        System.out.println(courseName);
        this.semester.getCourse(courseName).setTime(timeSpent);
      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Deletes the Course with the same name as the param
   *
   * @param courseName the name of the course which will be deleted
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
        System.out.println("deleteCourse blir kjørt i remotesemesteraccess");
      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
