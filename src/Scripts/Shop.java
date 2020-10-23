package Scripts;

import java.util.Arrays;

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
	
	/**
	 * Vezme z predane tovarny zasoby a preveze je do obchodu, pricte cenu dopravy v simulaci
	 * @param factory tovarna ze ktere bereme zasoby
	 * @param day pro ktery den chceme prevazet zbozi
	 * @param daysTotal celkovy pocet dni simulace
	 * @param lowestCost cena prepravy 
	 * @param sim instance simulace
	 * @return
	 */
	public boolean getArticles(Factory factory, int day, int daysTotal, int lowestCost, Simulation sim) {
		boolean isDemandSatisfied = true;
		int demandInt = 0;
		int[] want = new int[sim.getArticles()];
		int d = day;
		
		for (int art = 0; art < want.length; art++) {
			want[art] = demand[d];
			d += daysTotal;
		}
		
		for (int i = 0; i < want.length; i++) {
			if (factory.stocks[i] < want[i]) {
				isDemandSatisfied = false;
				demandInt = factory.stocks[i];
				want[i] -= factory.stocks[i];
				factory.stocks[i] = 0;
				sim.addToDayCost(lowestCost*demandInt);
			}
			else {
				demandInt = factory.stocks[i] - (factory.stocks[i] - want[i]);
				want[i] -= demandInt;
				factory.stocks[i] -= want[i];
				sim.addToDayCost(lowestCost*demandInt);
			}
		}
		
		d = day;
		for (int art = 0; art < want.length; art++) {
			demand[d] = want[art];
			d += daysTotal;
		}
		
		return isDemandSatisfied;
	}
	
	/**
	 * Vezme zasoby ze skladu obchodu a odecte je od pozadavku
	 * @param day den simulace ve kterem odecitame zasoby
	 * @param daysTotal celkovy pocet dni simulace
	 * @param sim instance simulace
	 */
	public void takeFromStocks(int day, int daysTotal, Simulation sim) {
		int demandInt = 0;
		int[] want = new int[sim.getArticles()];
		int d = day;
		
		for (int art = 0; art < want.length; art++) {
			want[art] = demand[d];
			d += daysTotal;
		}
		
		for (int i = 0; i < want.length; i++) {
			if (stocks[i] < want[i]) {
				demandInt = stocks[i];
				want[i] -= stocks[i];
				stocks[i] = 0;
			}
			else {
				demandInt = stocks[i] - (stocks[i] - want[i]);
				want[i] -= demandInt;
				stocks[i] -= want[i];
			}
		}
		
		d = day;
		for (int art = 0; art < want.length; art++) {
			demand[d] = want[art];
			d += daysTotal;
		}
	}
}
