package Scripts;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * T��da staraj�c� se o na�ten� dat z textov�ho souboru a nastaven� parametru simulace
 * @author Jan Kubice & Michaela Bene�ov�
 */
public class ReadData {
	String path;
	Simulation simulation;
	
	public ReadData(String path, Simulation simulator) {
		this.path = path;
		this.simulation = simulator;
	}
	
	/**
	 * Na�te textov� soubor a nastav� data simulatoru
	 * @throws FileNotFoundException chyba p�i nenalezen�m souboru
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
	 * P�evede prvn� ��dku textov�ho souboru na ��sla a nastav� je Simul�toru. 
	 * (po�et tov�ren, obchod�, druh� zbo��, dn�)
	 * @param line String obsahuj�c� prvn� ��dku s pot�ebn�mi informacemi
	 */
	private void readFirstLine(String line) {
		ArrayList<Integer> numbers = lineToNumbers(line);
		
		simulation.setFactories(numbers.get(0));
		simulation.setShops(numbers.get(1));
		simulation.setArticles(numbers.get(2));
		simulation.setDays(numbers.get(3));
	}
	
	/**
	 * P�evede po�adovanou ��dku na arraylist ��sel
	 * @param line ��dka k p�eveden�
	 * @return arrayList ��sel
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
