package Scripts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteData {

	private static String filePath = "";
	private static String fileName = "file";
	private static FileWriter file;
	
	public static void createFile() {
		try {
			File myObj = new File(filePath + fileName + ".txt");
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public static void openFile() {
		try {
			file = new FileWriter(filePath + fileName + ".txt", true);
		} catch (IOException e) {
			System.err.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	public static void appendToFile(String text) {
		try {
			file.write(text);

		} catch (IOException e) {
			System.err.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	public static void closeFile() {
		try {
			file.close();
		} catch (IOException e) {
			System.err.println("An error occurred.");			
			e.printStackTrace();
		}
	}
	
	public static void setFile(String path, String name) {
		filePath = path;
		fileName = name;
	}
}
