package Scripts;

import java.awt.Color;
import java.util.Arrays;

/**
 * Trida reprezentujici obchod
 * 
 * @author Jan Kubice & Michaela Benesova
 *
 */
public class Shop extends Building {
	int[] demand;

	/**
	 * Konstruktor obchodu
	 * 
	 * @param index            index budovy, jedna se o unikatni identifikator
	 * @param demand           pole celkove poptavky obchodu
	 * @param startingSupplies pocatecni zasoby obchodu
	 */
	public Shop(int index, int[] demand, int[] startingSupplies) {
		super(index, startingSupplies);
		this.demand = demand;

	}

	/**
	 * Vezme z predane tovarny zasoby a preveze je do obchodu, pricte cenu dopravy v
	 * simulaci
	 * 
	 * @param factory    tovarna ze ktere bereme zasoby
	 * @param day        pro ktery den chceme prevazet zbozi
	 * @param daysTotal  celkovy pocet dni simulace
	 * @param lowestCost cena prepravy
	 * @param sim        instance simulace
	 * @return true pokud byla splnena poptavka, jinak false
	 */
	public boolean transportArticles(Factory factory, int day, int daysTotal, int lowestCost, Simulation sim) {
		boolean isDemandSatisfied = true;
		int demandInt = 0;
		int[] want = new int[sim.getArticles()];
		int d = day;

		WriteData.appendToFile("Továrna " + factory.getIndex() + " převáží: \n");

		for (int art = 0; art < want.length; art++) {
			want[art] = demand[d];
			d += daysTotal;
		}

		for (int i = 0; i < want.length; i++) {
			if (want[i] == 0)
				continue;

			try {
				Thread.sleep(Settings.loopDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (factory.getStocks()[i] < want[i]) {
				isDemandSatisfied = false;
				demandInt = factory.getStocks()[i];
				want[i] -= factory.getStocks()[i];

				WriteData.appendToFile("Převoz zboží :" + i + " do obchodu: " + (getIndex() - sim.getFactories())
						+ " z továrny " + factory.getIndex() + " - počet zboží: "
						+ String.valueOf(factory.getStocks()[i]) + "\n");

				FrameMaker.appendTP(String.format(
						"Převod pomocí %s zboží %d do obchodu %d z továrny %d. Počet zboží %d, cena přepravy %d \n",
						RandomEmoji.getRandomTransportEmoji(), i, (getIndex() - sim.getFactories()), factory.getIndex(),
						factory.getStocks()[i], lowestCost * demandInt), Color.black);

				factory.getStocks()[i] = 0;
				sim.addToDayCost(lowestCost * demandInt);
				
			} else {
				demandInt = factory.getStocks()[i] - (factory.getStocks()[i] - want[i]);
				want[i] -= demandInt;
				WriteData.appendToFile("Převoz zboží :" + i + " do obchodu: " + (getIndex() - sim.getFactories())
						+ " z továrny " + factory.getIndex() + " - počet zboží: " + String.valueOf(demandInt) + "\n");
				
				FrameMaker.appendTP(String.format(
						"Převoz pomocí %s zboží pomocí %d do obchodu %d z továrny %d. Počet žboží %d, cena přepravy %d \n",
						RandomEmoji.getRandomTransportEmoji(), i, (getIndex() - sim.getFactories()), factory.getIndex(),
						factory.getStocks()[i], lowestCost * demandInt), Color.black);
				
				factory.getStocks()[i] -= want[i];
				sim.addToDayCost(lowestCost * demandInt);
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
	 * 
	 * @param day       den simulace ve kterem odecitame zasoby
	 * @param daysTotal celkovy pocet dni simulace
	 * @param sim       instance simulace
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
			if (getStocks()[i] < want[i]) {
				demandInt = getStocks()[i];
				want[i] -= getStocks()[i];
				getStocks()[i] = 0;
			} else {
				demandInt = getStocks()[i] - (getStocks()[i] - want[i]);
				want[i] -= demandInt;
				getStocks()[i] -= want[i];
			}
		}

		d = day;
		for (int art = 0; art < want.length; art++) {
			demand[d] = want[art];
			d += daysTotal;
		}
	}

	/**
	 * Zjisti jake zbozi a kolik chybelo
	 * 
	 * @return jednorozmerne pole kde na prvnim indexu je index zbozi a na druhem
	 *         indexu pocet kolik zbozi chybelo
	 */
	public int[] getBadArticle() {
		for (int i = 0; i < demand.length; i++) {
			if (demand[i] != 0)
				return new int[] { i, demand[i] };
		}
		return null;
	}
}
