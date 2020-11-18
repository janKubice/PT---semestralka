package Scripts;

import java.awt.Color;

/**
 * trida reprezentujici tovarnu
 * @author Jan Kubice & Michaela Benešová
 *
 */
public class Factory extends Building{

	int[] production;
	
	/**
	 * 
	 * @param index index budovy, jedna se o unikatni identifikator
	 * @param production produkce tovarny
	 * @param articles Pocet druhu zbozi pro zalozeni pole pro zasoby
	 */
	public Factory(int index, int[] production, int articles) {
		super(index, articles);
		this.production = production;
	}

	/**
	 * Prida produkci do zasbo tovarny pro urcity den
	 * @param day den pro ktery pridavame zasoby
	 */
	public void addProduction(int day) {
		int d = day;
		WriteData.appendToFile("Produkce továrny èíslo: " + index + ", den: " + day+ "\n");
		FrameMaker.appendTA("Produkce továrny èíslo: " + index + ", den: " + day+ "\n", Color.BLUE);
		for (int art = 0; art < stocks.length; art++) {
			WriteData.appendToFile("Zboží èíslo: " + String.valueOf(art) + ", produkce: " + String.valueOf(production[day]) + "\n");
			FrameMaker.appendTA("Zboží èíslo: " + String.valueOf(art) + ", produkce: " + String.valueOf(production[day]) + "\n", Color.BLACK);
			stocks[art] = production[day];
			d += day;
		}
		
		WriteData.appendToFile("\n");
		FrameMaker.appendTA("\n", Color.WHITE);
	}
	
	/**
	 * Vymaze zasoby
	 */
	public void dumpStock() {
		int sum = 0;
		for (int i : stocks) {
			sum += i;
		}
		
		WriteData.appendToFile("Celkové vyhozené zboží z továrny: " + index + ": " + String.valueOf(sum));
		FrameMaker.appendTA("Celkové vyhozené zboží z továrny: " + index + ": " + String.valueOf(sum) + "\n", Color.RED);
		
		stocks = new int[stocks.length];
	}


}
