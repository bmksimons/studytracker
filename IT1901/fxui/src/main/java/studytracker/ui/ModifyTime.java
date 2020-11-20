package studytracker.ui;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class for calculating time-related issues for the controller.
 */
public class ModifyTime {
  /**
   * Methode for adding time to a course.
   * 
   * @param time take in a string and transform it into a double.
   * 
   * @return a string with new time +"t" for a given course.
   */
  public String addTime(String time) {
    String[] partition = time.split(Pattern.quote(" "));
    Double currentTime = Double.parseDouble(partition[0]);
    currentTime = currentTime + 0.25;
    return (currentTime.toString());
  }

  /**
   * Methode for reducing the time given as input.
   * 
   * @param time , string with current time
   * 
   * @return string with new time.
   */
  public String removeTime(String time) {
    String[] partition = time.split(Pattern.quote(" "));
    Double currentTime = Double.parseDouble(partition[0]);

    if (currentTime == 0) {
      return ("it is not possible to add a negative amount of hours");
    } else {
      currentTime = currentTime - 0.25;
      return (currentTime.toString());
    }
  }

  /**
   * Method for converting a string into double and calculate studied time.
   * 
   * @param timeToAdd , a string that contains the time you want to add to a course 
   * @param currentTime a string with the current time spent on a course.
   * @return a list of doubles with three elements; hoursToAdd,
   *         currentTimeStuided, and newTimeStudied which is the sum of the two
   *         other.
   */
  public List<Double> makeStudyHours(String timeToAdd, String currentTime) {
    Double HoursToAdd = Double.parseDouble(timeToAdd.split(Pattern.quote(" "))[0]);
    Double currentTimeStudied = Double.parseDouble(currentTime.split(Pattern.quote(" "))[0]);
    Double newTimeStudied = HoursToAdd + currentTimeStudied;
    List<Double> list = Arrays.asList(HoursToAdd, currentTimeStudied, newTimeStudied);
    return list;
  }
}