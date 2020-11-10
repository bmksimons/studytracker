package studytracker.restapi;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

   @PUT
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public void putCourse(Course course) throws JsonGenerationException, JsonMappingException, IOException {
     this.studyTrackerPersistence.writeSemester("restserver/src/main/resources/studytracker/restserver/semester.json", semester);
   }

   @PUT
   public void putCourse() throws JsonGenerationException, JsonMappingException, IOException {
     this.putCourse(null);
   }

   @DELETE
   @Produces(MediaType.APPLICATION_JSON)
   public boolean removeCourse() throws JsonGenerationException, JsonMappingException, IOException {
     checkSemester();
     this.putCourse(); //??
     return true;
   }
 }