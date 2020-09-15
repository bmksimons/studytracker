package studytracker.core;

public class Course {
    private String courseName;
    private double points;
    private double timeSpent;

    public Course(String courseName, double points) {
        this.courseName = courseName;
        this.points = points;
        this.timeSpent = 0;
    }

    public void addTime(double timeSpent) {
        if (timeSpent <= 0) {
            throw new IllegalArgumentException("You must add a positive number");
        }
        this.timeSpent += timeSpent;
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
