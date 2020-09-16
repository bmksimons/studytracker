package studytracker.filehandling;

/**
 * Saves or removes the information from the strudytracker in a file with formate .txt.
 *
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javafx.scene.control.Label;

public class Save {
	
	public final static String SAVE_FOLDER = "src/main/java/studytracker/filehandling/";
    
     /**
     * Saves the information from the studytracker.
     * 
     * format: { courseName1
     *           courseName2
     *           courseName3
     *           courseName4
     *           courseTimer1
     *           courseTimer2
     *           courseTimer3
     *           courseTimer4 }
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
    
	public void emptyFile() throws IOException {
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
