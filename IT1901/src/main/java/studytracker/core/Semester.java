package studytracker.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains a List with all the Course-objects. 
 * 
 * Temporarly this class contains 3 constructors, 3 removeCourse functions and 2 checkIfEqual functions.
 * We will figure out which ones we will be using when we implement this class in the app.
 *
 */

public class Semester implements Iterable<Course> {

    final List<Course> semester = new ArrayList<>();
    
    public Semester(){
    }

	public Semester(Collection<Course> courses) {
		if (!checkIfEqual(courses.toArray())) {
			throw new IllegalArgumentException();
        }
        semester.addAll(courses);
    }

    public Semester(Course course) {
		if (checkIfEqual1(course)) {
			throw new IllegalArgumentException("Dette faget er allerede lagt til");
        }
        semester.add(course);
    }
	
	public void addCourse(Course course) {
		if (checkIfEqual1(course)) {
			throw new IllegalArgumentException("Dette faget er allerede lagt til");
        }
        semester.add(course);
	}
	
	public int getSemesterSize() {
		return semester.size();
	}
	
	public void removeCourse(int index) {
		semester.remove(index);
	}
	
	public boolean removeCourse(String name) {
		Iterator<Course> it1 = iterator();
		while (it1.hasNext()) {
			Course tmp = it1.next();
			if (name.equals(tmp.getCourseName())) {
				semester.remove(tmp);
				return true;
			}
		}
		return false;
    }
    
    public void removeCourse(Course course) {
        if (this.semester.contains(course)) {
            this.semester.remove(course); 
        }
        throw new IllegalArgumentException("This semster does not contain this course");    
    }

	@Override
	public Iterator<Course> iterator() {
		return semester.iterator();	
	}

	private boolean checkIfEqual(Object...courses) {
        Collection<Object> courses1 = Arrays.stream(courses).collect(Collectors.toUnmodifiableList());
		return (int) courses1.stream().distinct().count() == courses.length;
    }
    
    private boolean checkIfEqual1(Course course){
        Iterator<Course> it1 = iterator();
		while (it1.hasNext()) {
			Course tmp = it1.next();
			if (course.getCourseName().equals(tmp.getCourseName())) {
				return true;
			}
		}
		return false;
    }
}
