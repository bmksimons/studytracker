package studytracker.ui;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class for calculating time-related issues for the controller.
 */
public class ModifyTime {
  /**
   * Method for adding time to a course.
   * 
   * @param time take in a string and transform it into a double.
   * @return a string with new time +"t" for a given course.
   */
  public String addTime(String time) {
    String[] partition = time.split(Pattern.quote(" "));
    Double currentTime = Double.parseDouble(partition[0]);
    currentTime = currentTime + 0.25;
    return (currentTime + " h");
  }

  /**
   * Method for reducing the time given as input.
   * 
   * @param time string with the current time.
   * @return string with the new time.
   */
  public String removeTime(String time) {
    String[] partition = time.split(Pattern.quote(" "));
    Double currentTime = Double.parseDouble(partition[0]);

    if (currentTime == 0) {
      return ("it is not possible to add a negative amount of hours");
    } else {
      currentTime = currentTime - 0.25;
      return (currentTime + " t");
    }
  }

  /**
   * Method for converting a string into double and calculate studied time.
   * 
   * @param timeToAdd  a string that contains the time you want to add to a course 
   * @param currentTime a string with the current time spent on a course.
   * @return a list of doubles with three elements; hoursToAdd,
   *         currentTimeStudied, and newTimeStudied which is the sum of the two
   *         other.
   */
  public List<Double> modifyTimeSpent(String timeToAdd, String currentTime) {
    Double hoursToAdd = Double.parseDouble(timeToAdd.split(Pattern.quote(" "))[0]);
    Double currentTimeStudied = Double.parseDouble(currentTime.split(Pattern.quote(" "))[0]);
    Double newTimeStudied = hoursToAdd + currentTimeStudied;
    List<Double> list = Arrays.asList(hoursToAdd, currentTimeStudied, newTimeStudied);
    return list;
  }
}