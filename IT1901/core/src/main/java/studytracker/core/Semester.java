package studytracker.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Contains a List with all the Course-objects.
 * 
 * Temporarly this class contains 3 removeCourse functions. We will figure out
 * which ones we will be using when we implement this function in the app.
 *
 */
public class Semester implements Iterable<Course> {

  private List<Course> courseList = new ArrayList<>();
  private Collection<SemesterListener> semesterListeners = new ArrayList<>();

  public void addCourse(Course course) {
    if (checkIfCourseExists(course)) {
      throw new IllegalArgumentException("Dette faget er allerede lagt til");
    }
    this.semester.add(course);
    this.fireSemesterChanged();
  }

  private boolean checkIfCourseExists(Course course) {
    for (Course excistingCourse: this.semester){
      if (course.getCourseName().equals(excistingCourse.getCourseName())){
        return true;
      }
    }
    return false;
  }

  @Override
  public Iterator<Course> iterator() {
    return this.semester.iterator();
  }

  public void removeCourse(int index) {
    semester.remove(index);
    this.fireSemesterChanged();
  }

  public boolean removeCourse(String name) {
    Iterator<Course> it1 = this.iterator();
    while (it1.hasNext()) {
      Course tmp = it1.next();
      if (name.equals(tmp.getCourseName())) {
        this.semester.remove(tmp);
        this.fireSemesterChanged();
        return true;
      }
    }
    return false;
  }

  public void removeCourse(Course course) {
    if (!this.semester.contains(course)) {
      throw new IllegalArgumentException("This semster does not contain this course");
    }
    this.semester.remove(course);
    this.fireSemesterChanged();
  }

  public void addTimeToCourse(String name, Double time) {
    Iterator<Course> it1 = iterator();
    while (it1.hasNext()) {
      Course tmp = it1.next();
      if (name.equals(tmp.getCourseName())) {
        tmp.addTime(time);
        this.fireSemesterChanged();
      }
    }
  }

  public List<Course> getCourses() {
    return this.semester;
  }

  public void clearSemester() {
    this.semester.clear();
    this.fireSemesterChanged();
  }

  public void addSemesterListener(SemesterListener semesterListener) {
    this.semesterListeners.add(semesterListener);
  }

  public void removeSemesterListener(SemesterListener semesterListener) {
    if (!this.semesterListeners.contains(semesterListener)) {
      throw new IllegalArgumentException("This object does not listen to the semesterclass");
    }
    this.semesterListeners.remove(semesterListener);
  }

  protected void fireSemesterChanged() {
    for (SemesterListener listener : this.semesterListeners) {
      listener.semesterChanged(this);
    }
  }
}
