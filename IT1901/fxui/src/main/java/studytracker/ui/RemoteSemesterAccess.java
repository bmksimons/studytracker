 package studytracker.ui;

 import java.io.IOException;
 import java.net.URI;
 import java.net.http.HttpClient;
 import java.net.http.HttpRequest;
 import java.net.http.HttpResponse;

 import com.fasterxml.jackson.databind.ObjectMapper;

 import studytracker.core.Semester;
 import studytracker.json.StudyTrackerModule;

 public class RemoteSemesterAccess {

   private final URI endpointUri;

   private ObjectMapper objectMapper;

   private Semester semester;

   public RemoteSemesterAccess(URI endpointUri){
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
       System.out.println("Semester: " + this.semester.getCourses().get(0).getCourseName());
     } catch(IOException | InterruptedException e){
       throw new RuntimeException(e);
       }
     }
     return this.semester;
   } 
 }