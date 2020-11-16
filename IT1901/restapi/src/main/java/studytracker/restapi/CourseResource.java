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
    * Updates the time spent on the Course
    *
    * @param courseTimer the new time
    * @throws IOException
    * @throws JsonMappingException
    * @throws JsonGenerationException
    */
   @POST
   @Path("/newTime")
   //@Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public boolean addTimeToCourse(@QueryParam("settime") String courseTimer)
       throws JsonGenerationException, JsonMappingException, IOException {
    checkSemester();
    System.out.println("addTimeToCourse blir kjørt i courseResource");
    this.semester.getCourse(this.course.getCourseName()).setTime(Double.valueOf(courseTimer));
    this.studyTrackerPersistence.writeSemester(json, this.semester);
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