package studytracker.restapi;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import studytracker.core.Course;
import studytracker.core.Semester;
 import studytracker.json.StudyTrackerPersistence;

 public class CourseResource {

   private final Semester semester;
   private String name;
   private Course course;
   private StudyTrackerPersistence studyTrackerPersistence;
   private static final Logger LOG = LoggerFactory.getLogger(SemesterService.class);
   private String json = "restserver/src/main/resources/studytracker/restserver/semester.json";

   public CourseResource(Semester semester, String name, Course course) {
     this.studyTrackerPersistence = new StudyTrackerPersistence();
     this.semester = semester;
     this.name = name;
     this.course = course;
   }

   private void checkSemester() {
     if (this.semester.getCourse(name)==null) {
       throw new IllegalArgumentException("no course named");
     }
   }

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public Course getCourse() {
     checkSemester();
     return this.course;
   }

  //  @PUT
  //  @Consumes(MediaType.APPLICATION_JSON)
  //  @Produces(MediaType.APPLICATION_JSON)
  //  public boolean putCourse(Course course) throws JsonGenerationException, JsonMappingException, IOException {
  //    this.semester.addCourse(course);
  //    this.studyTrackerPersistence.writeSemester(json, semester);
  //    return true;
  //  }

   /**
    * Renames the TodoList.
    *
    * @param newName the new name
    * @throws IOException
    * @throws JsonMappingException
    * @throws JsonGenerationException
    */
   @POST
   @Path("/timeSpent")
   @Produces(MediaType.APPLICATION_JSON)
   public boolean addTimeToCourse(@QueryParam("timeSpent") String timeSpent)
       throws JsonGenerationException, JsonMappingException, IOException {
    checkSemester();
    this.semester.getCourse(this.course.getCourseName()).setTime(Double.valueOf(timeSpent));
    this.studyTrackerPersistence.writeSemester(json, this.semester);
    // Response getResponse = target(SemesterService.STUDYTRACKER_MODEL_SERVICE_PATH)
    //     .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
    //     .get();
    return true;
  }

   @DELETE
   @Produces(MediaType.APPLICATION_JSON)
   public boolean removeCourse() throws JsonGenerationException, JsonMappingException, IOException {
     checkSemester();
     this.semester.removeCourse(this.semester.getCourse(this.course.getCourseName()));
     this.studyTrackerPersistence.writeSemester(json, semester);
     return true;
   }
 }