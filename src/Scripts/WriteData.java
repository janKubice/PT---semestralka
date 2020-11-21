package Scripts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Trida pro zapisovani do souboru
 * @author Jan Kubice & Michaela Benešová
 *
 */
public class WriteData {

	private static FileWriter file;
	
	/**
	 * Vytvori soubor pro zapisovani
	 * @return cestu k souboru
	 */
	public static String createFile() {
		String name = Settings.savePath + generateFileName() + ".txt";
		try {
			File myObj = new File(name);
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			System.out.println(e.getMessage());
		}
		
		return name;
	}

	/**
	 * Otevre soubor a nastaviho jako aktualne otevreny.
	 * Lze mit jen jeden otevreny soubor
	 * @param name soubor k otevreni
	 * @param append zda si do souboru pripisovat nebo ne
	 */
	public static void openFile(String name, boolean append) {
		try {
			file = new FileWriter(name, append);
		} catch (IOException e) {
			System.err.println("An error occurred.");
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Vygeneruje nazev souboru 
	 * @return nazev vygenerovaneho souboru
	 */
	private static String generateFileName() {
		return "output" + new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date());
	}
	
	/**
	 * Zapise text do aktualne otevrenho souboru
	 * @param text text k zapsani do souboru
	 */
	public static void appendToFile(String text) {
		try {
			file.write(text);

		} catch (IOException e) {
			System.err.println("An error occurred.");
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Zavre aktualne otevreny soubor
	 */
	public static void closeFile() {
		if (file == null)
			return;
		
		try {
			file.close();
		} catch (IOException e) {
			System.err.println("An error occurred.");			
			System.out.println(e.getMessage());
		}
	}
	
}
