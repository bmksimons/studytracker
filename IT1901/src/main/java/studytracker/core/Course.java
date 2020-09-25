package studytracker.core;

/**
 * An object of the class represents a course, with its name, value and time spent studying the course.  
 *
 */
public class Course {
    private String courseName;
    private double points;
    private double timeSpent;
    private Semester semester;

    public Course(String courseName, Semester semester) {
        this.courseName = courseName;
        this.timeSpent = 0;
        this.semester = semester;
    }

    public Course() {
       this.courseName = "";
       this.points = 0.0;
       this.timeSpent = 0;
    }

    public void addTime(double timeSpent) {
        if (timeSpent <= 0) {
            throw new IllegalArgumentException("You must add a positive number");
        }
        this.timeSpent += timeSpent;
        this.semester.fireSemesterChanged();
    }

    public void setCourseName(String name){
        this.courseName = name;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public double getPoints() {
        return this.points;
    }

    public double getTimeSpent() {
        return this.timeSpent;
    }
}
