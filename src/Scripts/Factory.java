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
		WriteData.appendToFile("Produkce továrny èíslo: " + getIndex() + ", den: " + day+ "\n");
		
		FrameMaker.appendTP("Produkce továrny èíslo: " + getIndex() + ", den: " + day+ "\n", Color.BLUE);
		
		for (int art = 0; art < getStocks().length; art++) {
			WriteData.appendToFile("Zboží èíslo: " + String.valueOf(art) + ", produkce: " + String.valueOf(production[day]) + "\n");
			FrameMaker.appendTP("Zboží èíslo: " + String.valueOf(art) + ", produkce: " + String.valueOf(production[day]) + "\n", Color.BLACK);
			getStocks()[art] = production[day];
			d += day;
		}
		
		WriteData.appendToFile("\n");
		FrameMaker.appendTP("\n", Color.WHITE);
	}
	
	/**
	 * Vymaze zasoby
	 */
	public void dumpStock() {
		int sum = 0;
		for (int i : getStocks()) {
			sum += i;
		}
		
		WriteData.appendToFile("Celkové vyhozené zboží z továrny tento den: " + getIndex() + ": " + String.valueOf(sum));
		FrameMaker.appendTP("Celkové vyhozené zboží z továrny tento den: " + getIndex() + ": " + String.valueOf(sum) + "\n", Color.RED);
		
		setStocks(new int[getStocks().length]);
	}


}
