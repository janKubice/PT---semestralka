package Scripts;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Tøída starající se o naètení dat z textového souboru a nastavení parametru simulace
 * @author Jan Kubice & Michaela Benešová
 */
public class ReadData {
	String path;
	Simulation simulation;
	
	public ReadData(String path, Simulation simulator) {
		this.path = path;
		this.simulation = simulator;
	}
	
	/**
	 * Naète textovı soubor a nastaví data simulatoru
	 * @throws FileNotFoundException chyba pøi nenalezeném souboru
	 */
	public void setDataToSimulation() throws FileNotFoundException {
		File file = new File(path);
		Scanner sc = new Scanner(file);
		String line;
		int onLine = 0;
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			if (!line.contains("#") && line.length() != 0) {
				if (onLine == 0) {
					readFirstLine(line);
					simulation.setTravelCostsSize();
				}
				if ((onLine >= 1) && (onLine <= simulation.getFactories() + 1)) {
					setLineOfCostMatrix(onLine - 1, line);
				}
				onLine++;
			}
		}
		
		sc.close();
	}
	
	/**
	 * Pøevede první øádku textového souboru na èísla a nastaví je Simulátoru. 
	 * (poèet továren, obchodù, druhé zboí, dnù)
	 * @param line String obsahující první øádku s potøebnımi informacemi
	 */
	private void readFirstLine(String line) {
		ArrayList<Integer> numbers = lineToNumbers(line);
		
		simulation.setFactories(numbers.get(0));
		simulation.setShops(numbers.get(1));
		simulation.setArticles(numbers.get(2));
		simulation.setDays(numbers.get(3));
	}
	
	/**
	 * Pøevede poadovanou øádku na arraylist èísel
	 * @param line øádka k pøevedení
	 * @return arrayList èísel
	 */
	private ArrayList<Integer> lineToNumbers(String line) {
		char[] charLine = line.toCharArray();
		ArrayList<Integer> numbers = new ArrayList<>();
		String tempString = "";
		int charindex = 0;
		for (char c : charLine) {
			charindex++;
			if (c != ' ') {
				tempString += c;
			}
			else {
				numbers.add(Integer.parseInt(tempString));
				tempString = "";
			}
			
			if (charindex == line.length() && c != ' ') {
				numbers.add(Integer.parseInt(tempString));
				tempString = "";
			}
		}
		return numbers;
	}
	
	private void setLineOfCostMatrix(int lineIIndex, String line) {
		
	}
	
	
	
}
