package Scripts;

import java.io.File;
import java.io.FileNotFoundException;
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
	 * Nacte textovy soubor a nastavi data simulatoru
	 * @throws FileNotFoundException chyba pri nenalezenem souboru
	 */
	public void setDataToSimulation() throws FileNotFoundException {
		File file = new File(path);
		Scanner sc = new Scanner(file);
		String line;
		int onLine = 0, indexTravel = 0, indexSupp = 0, indexFac = 0, indexDemand = 0;
		
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			if (!line.contains("#") && line.length() != 0) {
				if (onLine == 0) { //nacte prvni radek
					readFirstLine(line);
				}
				else if ((onLine >= 1) && (onLine < simulation.getFactories() + 1)) {
					simulation.setTravelCostLine(indexTravel, lineToNumbers(line, simulation.getShops()));
					indexTravel++;
				}
				else if ((onLine >= simulation.getFactories() + 1) && (onLine < simulation.getArticles() + simulation.getFactories() + 1)) {
					simulation.setStartingSuppliesLine(indexSupp, lineToNumbers(line, simulation.getShops()));
					indexSupp++;
				}
				else if ((onLine >= simulation.getArticles() + simulation.getFactories() + 1) && (onLine < (simulation.getArticles() * simulation.getDays()) + simulation.getArticles() + simulation.getFactories() + 1)) {
					simulation.setFactoriesProductionLine(indexFac, lineToNumbers(line, simulation.getFactories()));
					indexFac++;
				}
				else if ((onLine >= (simulation.getArticles() * simulation.getDays()) + simulation.getArticles() + simulation.getFactories() + 1) && (onLine < (simulation.getDays() * simulation.getArticles()) + (simulation.getArticles() * simulation.getDays()) + simulation.getArticles() + simulation.getFactories() + 1)) {
					simulation.setDemantLine(indexDemand, lineToNumbers(line, simulation.getShops()));
					indexDemand++;
				}
				onLine++;
			}
		}
		//simulation.arrays();
		System.out.println("done");
		sc.close();
	}
	
	/**
	 * Prevede prvni radku textoveho souboru na cisla a nastavi je Simulatoru. 
	 * (pocet tovaren, obchodu, druhy zbozi, dnu)
	 * @param line String obsahujici prvni radku s potrebnymi informacemi
	 */
	private void readFirstLine(String line) {
		int[] numbers = lineToNumbers(line, 4);
		
		simulation.setFactories(numbers[0]);
		simulation.setShops(numbers[1]);
		simulation.setArticles(numbers[2]);
		simulation.setDays(numbers[3]);
		simulation.initMatrixes();
	}
	
	/**
	 * Prevede pozadovanou radku na arraylist cisel
	 * @param line radka k prevedeni
	 * @return arrayList cisel
	 */
	private int[] lineToNumbers(String line, int lenght) {
		char[] charLine = line.toCharArray();
		int[] numbers = new int[lenght];
		String tempString = "";
		int charindex = 0;
		int arrIndex = 0;
		for (char c : charLine) {
			charindex++;
			
			if (c != ' ') {
				tempString += c;
			}
			else {
				numbers[arrIndex] = Integer.parseInt(tempString);
				tempString = "";
				arrIndex++;
			}
			
			if (charindex == line.length() && c != ' ') {
				numbers[arrIndex] = Integer.parseInt(tempString);
				tempString = "";
				arrIndex++;
			}
		}
		
		return numbers;
	}
	
	
}
