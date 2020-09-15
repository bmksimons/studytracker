package studyTracker.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Semester implements Iterable<Course> {

	final List<Course> semester = new ArrayList<>();

	public Semester(Collection<Course> courses) {
		if (!checkIfEqual(courses.toArray())) {
			semester.addAll(courses);
		}
    }

    public Semester(Course course) {
		if (!checkIfEqual(course)) {
			semester.add(course);
		}
    }
	
	public void addCourse(Course course) {
		if (!checkIfEqual(course)){
			semester.add(course);
		}
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

	@Override
	public Iterator<Course> iterator() {
		return semester.iterator();	
	}

	private boolean checkIfEqual(Object...courses) {
        Collection<Object> courses1 = Arrays.stream(courses).collect(Collectors.toUnmodifiableList());
		return (int) courses1.stream().distinct().count() == courses.length;
	}
	
}
