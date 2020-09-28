package studytracker.ui;

import javafx.scene.control.ListCell;
import studytracker.core.Course;

public class SemesterCell extends ListCell<Course> {
    
    @Override
    protected void updateItem(Course item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null){
            setText(null);
            setGraphic(null);
        }
        else {
            setText(item.getCourseName());
            setGraphic(null);
        }
    }
}