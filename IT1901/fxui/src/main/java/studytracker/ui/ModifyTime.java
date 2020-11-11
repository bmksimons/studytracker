package studytracker.ui;

import java.util.regex.Pattern;

public class ModifyTime {

public String addTime(String time){
    String[] partition = time.split(Pattern.quote(" "));
    Double currentTime = Double.parseDouble(partition[0]);
    currentTime = currentTime + 0.25;
    return (currentTime +" t");
  }

  public String removeTime(String time){
    String[] partition = time.split(Pattern.quote(" "));
    Double currentTime = Double.parseDouble(partition[0]);

    if (currentTime == 0) {
      return ("kan ikke legge til negativt antall timer");
    } else {
      currentTime = currentTime - 0.25;
      return(currentTime + " t");
    }
  }
}