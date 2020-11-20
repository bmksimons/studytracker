package studytracker.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Contains a List with all the Course-objects.
 * Delegates methods to the Course-objects.
 *
 */
public class Semester implements Iterable<Course> {

  private List<Course> courseList = new ArrayList<>();
  private Collection<SemesterListener> semesterListeners = new ArrayList<>();

  /**
   * Adds a given Course to the Semester.
   *
   * @param Course the course added to the Semester.
   */
  public void addCourse(Course course) {
    if (checkIfCourseExists(course.getCourseName())) {
      throw new IllegalArgumentException("The course has already been added");
    }
    this.courseList.add(course);
    this.fireSemesterChanged();
  }

  /**
   * Checks if a course with the same name as the parameter already exists in the Semester.
   *
   * @param courseName the name of the course to be checked.
   * @return true if the course already exists in the semester, false otherwise.
   */
  private boolean checkIfCourseExists(String courseName) {
    for (Course excistingCourse: this.courseList){
      if (courseName.toLowerCase().equals(excistingCourse.getCourseName().toLowerCase())) {
        
        return true;
      }
    }
    return false;
  }

  /**
   * Deletes a course if it exists in the Semester.
   *
   * @param courseName the name of course to be deleted.
   * @return true if the course was successfully deleted, false otherwise.
   */
  public boolean deleteCourse(String courseName) {
    Iterator<Course> it1 = this.iterator();
    while (it1.hasNext()) {
      Course tmp = it1.next();
      if (courseName.equals(tmp.getCourseName())) {
        this.courseList.remove(tmp);
        return true;
      }
    }
    return false;
  }

  /**
   * Adds time spent on a course.
   *
   * @param courseName the name of the course that will be added time to.
   * @param timeSpent the time which to be added to the course.
   */
  public void addTimeToCourse(String courseName, Double timeSpent) {
    for (Course course: this.courseList){
      if (course.getCourseName().equals(courseName)){
        course.addTime(timeSpent);
      }
    }
  }

  /**
   * Resets the semester by clearing the fields.
   *
   * @param removeListeners a boolean who tells if you want to delete the semesterlisteners.
   */
  public void resetSemester(Boolean removeListeners) {
    if (removeListeners == true){
      this.semesterListeners.clear();
      this.courseList.clear();
    } else{
      this.courseList.clear();
    }
    this.fireSemesterChanged();
  }

  public List<Course> getCourses() {
    return this.courseList;
  }

  public Course getCourse(String name){
    for (Course course: this.courseList){
      if (course.getCourseName().equals(name)){
        return course;
      }
    }return null;
  }

  public void setCourses(List<Course> courseList){
    this.courseList = courseList;
  }

  @Override
  public Iterator<Course> iterator() {
    return this.courseList.iterator();
  }

  protected void fireSemesterChanged() {
    for (SemesterListener listener : this.semesterListeners) {
      listener.semesterChanged(this);
    }
  }

  /**
   * Adds a listener who listens to the the changes of the semester.
   *
   * @param SemesterListener the listener who wants to listen to the semester.
   */
  public void addSemesterListener(SemesterListener semesterListener) {
    this.semesterListeners.add(semesterListener);
  }

  /**
   * Removes a listener.
   *
   * @param SemesterListener the listener who wants will be deleted from the list of listeners.
   */
  public void removeSemesterListener(SemesterListener semesterListener) {
    if (!this.semesterListeners.contains(semesterListener)) {
      throw new IllegalArgumentException("This object does not listen to the semesterclass");
    }
    this.semesterListeners.remove(semesterListener);
  }
}
