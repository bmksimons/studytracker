package studyTracker.filehandling;

/**
 * Saves or removes the information from the strudytrackerapp in a file with formate .txt.
 *
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javafx.scene.control.Label;

public class Save {
	
	public final static String SAVE_FOLDER = "src/";
    
     /**
     * Saves the information from the studytrackerapp.
     * 
     * @param labels  an array with the name of the courses and the time spent on each course.
     * 
     */

	public void saveInFile(ArrayList<Label> labels) throws IOException {
		try (PrintWriter writer = new PrintWriter (getFilePath("savedStudyPlanner"))){
			for (Label label: labels) {
				writer.println(label.getText());
			}
			writer.flush();
			writer.close();
		}
    }
    
    /**
     * Clears all information saved in the .txt-file.
     * 
     */
	public void saveEmptyFile() throws IOException {
		try (PrintWriter writer = new PrintWriter(getFilePath("savedStudyPlanner"))){
			writer.print("");
			writer.flush();
			writer.close();
		}
    }
    
	private static String getFilePath(String filename) {
		return SAVE_FOLDER + filename + ".txt";
	}
}
