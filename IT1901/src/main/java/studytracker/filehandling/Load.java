package studytracker.filehandling;

/**
 * Loads the content from a file with formate .txt.
 *
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Load {
    
    public final static String SAVE_FOLDER = "src/main/java/studytracker/filehandling/";
    
    /**
     *
    * @param filename the name of the file
    * @return an arrayList with the information from the file
    */
	public ArrayList<String> loadFromFile(String filename) throws IOException {
		try (Scanner scanner = new Scanner(new File(getFilePath(filename)))){
			ArrayList<String> labels = new ArrayList<>();
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				labels.add(line);
			}
			scanner.close();
		return labels;
		}
	}
	
	private static String getFilePath(String filename) {
		return SAVE_FOLDER + filename + ".txt";
	}
}
