package Scripts;

import java.util.Arrays;

/**
 * Trida reprezentujici budovu
 * Vyuziva se jako vrchol v grafu
 * @author Jan Kubice & Michaela Benešová
 *
 */
public class Building {
	public int index;
	int[] stocks;
	
	/**
	 * Konstruktor vyuzivajici pri zakladani tovarny
	 * @param index Index budovy, unikatni identifikator
	 * @param articles pocet zbozi v simulaci
	 */
	public Building(int index, int articles) {
		this.index = index;
		this.stocks = new int[articles];
	}
	
	/**
	 * Konstruktor vyuzivajici pri zakladani obchodu
	 * @param index Index budovy, unikatni identifikator
	 * @param startingSupplies pocatecti zasoby
	 */
	public Building(int index, int[] startingSupplies) {
		this.index = index;
		this.stocks = startingSupplies;
	}
	
	/**
	 * Vypise index budovy a stav zasob
	 */
	public String toString() {
		String returnStr = "";
		returnStr += String.valueOf(index);
		returnStr += "\n" + Arrays.toString(stocks);
		return returnStr;
	}
}
