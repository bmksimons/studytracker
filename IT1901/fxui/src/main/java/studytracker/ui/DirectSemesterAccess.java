package studytracker.ui;

import java.io.IOException;

import studytracker.core.Semester;
import studytracker.json.StudyTrackerPersistence;

public class DirectSemesterAccess implements SemesterAccess {

  private Semester semester;

  public DirectSemesterAccess(Semester semester) {
    this.semester = semester;
  }

  private void saveSemester() {
    StudyTrackerPersistence studyTrackerPersistence = new StudyTrackerPersistence();
    try {
      // studyTrackerPersistence.readSemester("restserver/src/main/resources/default-semester.json");
      this.semester = studyTrackerPersistence.readSemester("semester.json");
    } catch (IOException e) {
      System.out.println("Couldn't read default-semester.json, so rigging Semester manually (" + e + ")");
      this.semester = new Semester();
      }
  }

  @Override
  public Semester getSemester() {
    // TODO Auto-generated method stub
    return null;
  }

}