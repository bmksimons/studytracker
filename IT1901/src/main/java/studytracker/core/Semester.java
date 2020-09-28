package studytracker.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Contains a List with all the Course-objects. 
 * 
 * Temporarly this class contains 3 constructors, 3 removeCourse functions and 2 checkIfEqual functions.
 * We will figure out which ones we will be using when we implement this class in the app.
 *
 */

public class Semester implements Iterable<Course> {

    final List<Course> semester = new ArrayList<>();
    private Collection<SemesterListener> semesterListeners = new ArrayList<>();
	
	public void addCourse(Course course) {
		if (checkIfEqual(course)) {
			throw new IllegalArgumentException("Dette faget er allerede lagt til");
        }
        this.semester.add(course);
        this.fireSemesterChanged();
	}
	
	public int getSemesterSize() {
		return semester.size();
	}
	
	public void removeCourse(int index) {
        semester.remove(index);
        this.fireSemesterChanged();
	}
	
	public boolean removeCourse(String name) {
		Iterator<Course> it1 = iterator();
		while (it1.hasNext()) {
			Course tmp = it1.next();
			if (name.equals(tmp.getCourseName())) {
                semester.remove(tmp);
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
    
    public List<Course> getCourses(){
        return this.semester;
    }

	@Override
	public Iterator<Course> iterator() {
		return semester.iterator();	
    }
    
    public void clearSemester(){
        this.semester.clear();
        this.fireSemesterChanged();
    }
    
    private boolean checkIfEqual(Course course){
        Iterator<Course> it1 = this.iterator();
		while (it1.hasNext()) {
			if (course.getCourseName().equals(it1.next().getCourseName())) {
				return true;
			}
		}
		return false;
    }

    public void addSemesterListener(SemesterListener semesterListener){
        this.semesterListeners.add(semesterListener);
    }

    public void removeSemesterListener(SemesterListener semesterListener){
        if (!this.semesterListeners.contains(semesterListener)){
            throw new IllegalArgumentException("This object does not listen do the semesterclass");
        }
        this.semesterListeners.remove(semesterListener);
    }

    protected void fireSemesterChanged(){
        for (SemesterListener listener : this.semesterListeners){
            listener.SemesterChanged(this);
        }
        //this.semesterListeners.stream().map(l -> l.SemesterChanged(this));
    }

     @Override
     public String toString() {
         String resultat = "";
         for (Course course: this.semester){
             resultat += course.getCourseName() + " tid: " + String.valueOf(course.getTimeSpent());
         }
         return resultat;
     }
}
