package studytracker.core;

public class StudyTrackerModel {

  private Semester semester = new Semester();


  @Override
  public String toString() {
    //return String.format("[StudyTrackerModel #semester=%s]", todoLists.size());
    return "";
  }

  /**
   * Checks if it is OK to add a list with the provided name.
   *
   * @param name the new name
   * @return true if the name is value, false otherwise
   */
  public boolean isValidSemesterName(String name) {
    return name.strip().length() > 0;
  }

  public Semester getSemester(){
    return this.semester;
  }

}