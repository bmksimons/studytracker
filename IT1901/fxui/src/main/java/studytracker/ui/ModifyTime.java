package studytracker.ui;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class for calculating time-related issues for the controller.
 */
public class ModifyTime {

  /**
   * Method for converting a string containing.
   * 
   * @param time string with the current time.
   * @return a string with new time for a given course.
   */
  public String increaseTime(String time) {
    String[] partition = time.split(Pattern.quote(" "));
    Double currentTime = Double.parseDouble(partition[0]);
    currentTime = currentTime + 0.25;
    return (currentTime.toString());
  }

  /**
   * Method for reducing the time given as input.
   * 
   * @param time string with the current time.
   * @return string with the new time.
   */
  public String reduceTime(String time) {
    String[] partition = time.split(Pattern.quote(" "));
    Double currentTime = Double.parseDouble(partition[0]);
    if (currentTime == 0) {
      throw new IllegalArgumentException("It is not possible to add a negative amount of hours");
    } else {
      currentTime = currentTime - 0.25;
      return (currentTime.toString());
    }
  }

  /**
   * Method for converting a string into double and calculate studied time.
   * 
   * @param timeToAdd   a string that contains the time you want to add to a
   *                    course.
   * @param currentTime a string with the current time spent on a course.
   * @return a list of doubles with two elements; hoursToAdd and newTimeStudied.
   */
  public List<Double> calculateTimeToAdd(String timeToAdd, String currentTime) {
    Double hoursToAdd = Double.valueOf(timeToAdd.strip());
    Double currentTimeStudied = Double.parseDouble(currentTime.split(Pattern.quote(" "))[0]);
    Double newTimeStudied = hoursToAdd + currentTimeStudied;
    List<Double> list = Arrays.asList(hoursToAdd, newTimeStudied);
    return list;
  }
}