package Scripts;

/**
 * Trida pro ulozeni parametru aplikace
 * @author Jan Kubice & Michaela Benešová
 *
 */
public class Settings {
	private static int simulationDelay = 200;
	private static int loopDelay = 200;
	private static int lastLoopDelay;
	
	private static String filePath;
	private static String savePath;

	public static int getSimulationDelay() {
		return simulationDelay;
	}

	public static void setSimulationDelay(int simulationDelay) {
		Settings.simulationDelay = simulationDelay;
	}

	public static int getLoopDelay() {
		return loopDelay;
	}

	public static void setLoopDelay(int loopDelay) {
		Settings.loopDelay = loopDelay;
	}

	public static int getLastLoopDelay() {
		return lastLoopDelay;
	}

	public static void setLastLoopDelay(int lastLoopDelay) {
		Settings.lastLoopDelay = lastLoopDelay;
	}
	
	public static String getFilePath() {
		return filePath;
	}

	/**
	 * Zvysi cas vyuzity pro uspani pred jednotlivyma trasportama
	 */
	public static void addLoopDelay() {
		if (loopDelay < 200)
			loopDelay += 20;
		else if (loopDelay < 1000)
			loopDelay += 100;
		
		lastLoopDelay = loopDelay;
	}
	
	/**
	 * Snizi cas vyuzity pro uspani pred jednotlivyma trasportama
	 */
	public static void takeLoopDelay() {
		if (loopDelay > 200)
			loopDelay -= 100;
		else if (loopDelay > 70)
			loopDelay -= 20;
		else if (loopDelay > 10)
			loopDelay -= 5;
		
		lastLoopDelay = loopDelay;
	}
	
	/**
	 * Nastavi cas pred jednotlivyma trasportama na 0
	 */
	public static void letFree() {
		loopDelay = 0;
		
		lastLoopDelay = loopDelay;
	}
	
	/**
	 * Nastavi cas pred jednotlivyma trasportama na puvodni hodnotu
	 */
	public static void unLetFree() {
		loopDelay = lastLoopDelay;
	}
	
	/**
	 * Nastavi cestu k txt souborum 
	 * @param path cesta k souborum
	 */
	public static void setPath(String path) {
		filePath = path;
	}
	
	/**
	 * Nastavi cestu kam ukladat soubory vygenerovane simulaci
	 * @param path cesta do slozky
	 */
	public static void setSavePath(String path) {
		savePath = path;
	}
	
	/**
	 * nastavi cestu k txt souborum 
	 * @return cestu k txt souborum 
	 */
	public static String getPath() {
		if (filePath == "" || filePath == null)
			return "Not set";
		
		return filePath;
	}
	
	/**
	 * nastavi cestu do slozky kam se ukladaji vygenerovane soubory
	 * @return cesta do slozky kam se ukladaji vygenerovane soubory
	 */
	public static String getSavePath() {
		if (savePath == "" || savePath == null)
			return "Not set";
		
		return savePath;
	}
	
}
