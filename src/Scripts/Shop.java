package Scripts;

/**
 * Trida reprezentujici obchod
 * @author Jan Kubice & Michaela Benesova
 *
 */
public class Shop extends Building{
	int[] demand;
	
	/**
	 * Konstruktor 
	 * @param index index budovy, jedna se o unikatni identifikator
	 * @param demand 
	 * @param startingSupplies
	 */
	public Shop(int index, int[] demand, int[] startingSupplies) {
		super(index, startingSupplies);
		this.demand = demand;
		
	}
	
	public boolean getArticles(Factory factory, int day, int daysTotal, int lowestCost, Simulation sim) {
		boolean isDemandSatisfied = true;
		int demandInt = 0;
		int[] want = new int[sim.getArticles()];
		
		for (int art = 0; art < want.length; art++) {
			want[art] = demand[day];
			day += day;
		}
		
		for (int i = 0; i < want.length; i++) {
			if (factory.stocks[i] < want[i]) {
				isDemandSatisfied = false;
				demandInt = factory.stocks[i];
				want[i] -= factory.stocks[i];
				factory.stocks[i] = 0;
				sim.addToTotalCost(lowestCost*demandInt);
			}
			else {
				demandInt = factory.stocks[i] - (factory.stocks[i] - want[i]);
				want[i] -= demandInt;
				factory.stocks[i] -= want[i];
				sim.addToTotalCost(lowestCost*demandInt);
			}
		}
		
		return isDemandSatisfied;
	}
}
