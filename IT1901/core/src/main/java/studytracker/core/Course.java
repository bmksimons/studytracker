package studytracker.core;

/**
 * An object of the class represents a course, with its name and time spent
 * studying the course.
 *
 */
public class Course {
  private String courseName;
  private Double timeSpent;

  public Course(String courseName) {
    this.courseName = courseName;
    this.timeSpent = 0.0;
  }

  public Course() {
    this.courseName = "";
    this.timeSpent = 0.0;
  }

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

  public void setTime(Double timeSpent) {
    this.timeSpent = timeSpent;
  }
}
