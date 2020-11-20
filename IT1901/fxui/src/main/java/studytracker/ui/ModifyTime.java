package studytracker.ui;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class for calculating time-related issues for the controller.
 */
public class ModifyTime {

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