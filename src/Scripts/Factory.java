package Scripts;

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

	public void addProduction(int day) {
		for (int art = 0; art < stocks.length; art++) {
			stocks[art] = production[day];
			day += day;
		}
	}
	
	public void dumpStock() {
		stocks = new int[stocks.length];
	}


}
