package studytracker.ui;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import studytracker.core.Semester;
import studytracker.json.StudyTrackerModule;

public class RemoteSemesterAccess {

  private final URI endpointUri;
  private ObjectMapper objectMapper;
  private Semester semester;

  public RemoteSemesterAccess(URI endpointUri) {
    this.endpointUri = endpointUri;
    this.objectMapper = new ObjectMapper().registerModule(new StudyTrackerModule());
  }


  public Semester getSemester() {
     if (this.semester == null){
       HttpRequest request = HttpRequest.newBuilder(this.endpointUri)
       .header("Accept", "application/json")
       .GET()
       .build();
     try {
       final HttpResponse<String> response =
         HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
       final String responseString = response.body();
       this.semester = objectMapper.readValue(responseString, Semester.class);
     } catch(IOException | InterruptedException e){
       throw new RuntimeException(e);
       }
     }
     return this.semester;
   }

  public void putSemester(Semester semester) {
    //this.semester = semester;
     try{
       String json = objectMapper.writeValueAsString(semester);
       System.out.println("hello" + json);
        HttpRequest request = HttpRequest.newBuilder(this.endpointUri)
        .header("Accept", "application/json")
        .header("Content-Type", "application/json")
        .PUT(BodyPublishers.ofString(json)) //sende json hardcode, json stringen ikke filen 
        .build();
        final HttpResponse<String> response =
          HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        String responseString = response.body();
        Boolean add = objectMapper.readValue(responseString, Boolean.class);
        if (add == true){
          this.semester = semester;
        }
      } catch(IOException | InterruptedException e){
       throw new RuntimeException(e);
        }
    }
}
