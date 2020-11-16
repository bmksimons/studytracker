package studytracker.core;

/**
 * An object of the class Course represents a course in a Semester.
 * Contains a name and the time spent studying the course.
 *
 */
public class Course {

  private String courseName;
  private Double timeSpent;

  /**
 * The class contains two constructors: With or without initializing a course name.  
 */
  public Course(String courseName) {
    this.courseName = courseName;
    this.timeSpent = 0.0;
  }

  /**
 * This constructor is only used when dezerializing a course from a json-file. 
 */
  public Course() {
    this("");
  }  

  /**
 * Adds time to the course
 * 
 * @param timeSpent the time which to be added to the course
 *
 */
  public void addTime(Double timeSpent) {
    if (timeSpent < 0) {
      throw new IllegalArgumentException("You must add a positive number");
    }
    this.timeSpent += timeSpent;
  }

  public void setCourseName(String name) {
    this.courseName = name;
  }

  public String getCourseName() {
    return this.courseName;
  }

  public Double getTimeSpent() {
    return this.timeSpent;
  }
}
