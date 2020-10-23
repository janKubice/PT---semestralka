package Scripts;

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
		for (int art = 0; art < stocks.length; art++) {
			stocks[art] = production[day];
			d += day;
		}
	}
	
	/**
	 * Vymaze zasoby
	 */
	public void dumpStock() {
		stocks = new int[stocks.length];
	}


}
