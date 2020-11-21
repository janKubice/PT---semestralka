package Scripts;

import java.awt.Color;
import java.util.Arrays;

import javax.swing.JOptionPane;


/**
 * Trida reprezentujici simulaci
 * 
 * @author Jan Kubice & Michaela Benešová
 *
 */
public class Simulation {
	private int factories, shops, articles, days;
	private int[][] travelCosts;
	private int[][] startingSupplies;
	private int[][] factoriesProduction;
	private int[][] demand;
	private Building[] buildings;

	public int totalCost = 0;
	private int dayCost = 0;
	public int lowestCostNOW = 0;
	public boolean cancel = false;

	Graph graph;
	public boolean canSimulate;
	
	public int day;
	public int building;

	public int getFactories() {
		return factories;
	}

	public void setFactories(int factories) {
		this.factories = factories;
	}

	public int getShops() {
		return shops;
	}

	public void setShops(int shops) {
		this.shops = shops;
	}

	public int getArticles() {
		return articles;
	}

	public void setArticles(int articles) {
		this.articles = articles;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int[][] getTravelCosts() {
		return travelCosts;
	}

	public int getTravelCosts(int i, int j) {
		return travelCosts[i][j];
	}

	public void setTravelCosts(int[][] travelCosts) {
		this.travelCosts = travelCosts;
	}

	public int[][] getStartingSupplies() {
		return startingSupplies;
	}

	public void setStartingSupplies(int[][] startingSupplies) {
		this.startingSupplies = startingSupplies;
	}

	public int getStartingSupplies(int i, int j) {
		return this.startingSupplies[i][j];
	}

	public int[][] getFatctoriesProduction() {
		return factoriesProduction;
	}

	public int getFactoriesProduction(int i, int j) {
		return factoriesProduction[i][j];
	}

	public void setFatctoriesProduction(int[][] fatctoriesProduction) {
		this.factoriesProduction = fatctoriesProduction;
	}

	public int[][] getDemant() {
		return demand;
	}

	public int getDemand(int i, int j) {
		return demand[i][j];
	}

	public void setDemant(int[][] demant) {
		this.demand = demant;
	}

	public void setDemantLine(int index, int[] demant) {
		this.demand[index] = demant;
	}

	public void setTravelCostLine(int index, int[] cost) {
		this.travelCosts[index] = cost;
	}

	public void setFactoriesProductionLine(int index, int[] production) {
		this.factoriesProduction[index] = production;
	}

	public void setStartingSuppliesLine(int index, int[] suplies) {
		this.startingSupplies[index] = suplies;
	}

	public void setBuildings(Building[] buildings) {
		this.buildings = buildings;
	}

	public void addToDayCost(int cost) {
		this.dayCost += cost;
	}

	/**
	 * Nastavi velikost dimenzi matici
	 */
	public void initMatrixes() {
		travelCosts = new int[factories][shops];
		startingSupplies = new int[articles][shops];
		factoriesProduction = new int[articles * days][factories];
		demand = new int[articles * days][shops];
	}

	/**
	 * Vypise informace o simulaci
	 */
	@Override
	public String toString() {
		String returnString = "";
		returnString += "Factories: " + factories + "\n";
		returnString += "Shops: " + shops + "\n";
		returnString += "Articles: " + articles + "\n";
		returnString += "Days: " + days + "\n";
		return returnString;
	}

	/**
	 * Vypise matice lze vyuzit pred vytvorenim nebo v prubehu tvoreni grafu, pote
	 * jsou matice smazany
	 */
	public void arrays() {
		System.out.println(Arrays.deepToString(travelCosts));
		System.out.println(Arrays.deepToString(startingSupplies));
		System.out.println(Arrays.deepToString(factoriesProduction));
		System.out.println(Arrays.deepToString(demand));
	}

	/**
	 * Nastaveni simulace pres spustenim
	 */
	public void setUpSimulation() {
		graph = new Graph(getFactories() + getShops());
		buildings = new Building[graph.getVertices()];

		for (int i = 0; i < graph.getVertices(); i++) {
			if (i < getFactories())
				buildings[i] = new Factory(i, getProduction(i), getArticles());
			else
				buildings[i] = new Shop(i, getDemand(i), getStartingSupplies(i));
		}

		for (int i = 0; i < getFactories(); i++) {
			for (int j = getFactories(); j < buildings.length; j++) {
				graph.addEdge(buildings[i], buildings[j], getTravelCosts(i, j - getFactories()));
			}
		}
		
		day = 0;
		building = 0;
	}

	/**
	 * Spusti simulaci na danem grafui
	 * 
	 * @param graph graf vytvoreny z tovaren a obchodu
	 */
	public void startSimulation() {
		Factory fac;
		cancel = false;
		boolean itIsOK;
		int index = 0;

		for (int day = 0; day < days; day++) {

			for (int building = 0; building < graph.getList().length; building++) {
				if (cancel)
					return;
				
				if (buildings[building] instanceof Factory) {
					((Factory) buildings[building]).addProduction(day);
				} else {
					index = 0;
					do {
						if (day == 0)
							((Shop) buildings[building]).takeFromStocks(day, days, this);

						fac = graph.getNearestFactoryToShop((Shop) buildings[building], this, index);
						if (fac == null)
							return;

						itIsOK = ((Shop) buildings[building]).transportArticles(fac, day, days, lowestCostNOW, this);
						index++;
					} while (!itIsOK);
				}
			}
			

			System.out.print(returnDayCost(day) + "\n");
			FrameMaker.appendTP(returnDayCost(day), Color.green);
			totalCost += dayCost;
			
			dayCost = 0;
		}
		System.out.println("celkova cena prepravy: " + totalCost);
		WriteData.appendToFile("celkova cena prepravy: " + String.valueOf(totalCost));
		FrameMaker.appendTP("celkova cena prepravy: " + totalCost, Color.GREEN);
		JOptionPane.showMessageDialog(null, "Simulace dokonèena");
		WriteData.closeFile();
	}
	
	/**
	 * Pokroci o jeden den v simulaci v simulaci, nastavi potrebne parametry
	 */
	public void nextStep() {
		if (cancel)
			return;
		
		if (building == graph.getList().length-1) {
			building = 0;
			day++;
			System.out.print(returnDayCost(day) + "\n");
			FrameMaker.appendTP(returnDayCost(day-1) + "\n", Color.black);
			totalCost += dayCost;
			dayCost = 0;
		}
		
		if (day == days) {
			System.out.println("celkova cena prepravy: " + totalCost);
			WriteData.appendToFile("Celková cena pøepravy: " + String.valueOf(totalCost));
			FrameMaker.appendTP("Celková cena pøepravy: " + String.valueOf(totalCost) + "/n", Color.GREEN);
			FrameMaker.appendTP("Simulace dokonèena!\n", Color.GREEN, true);
			JOptionPane.showMessageDialog(null, "Simulace dokonèena");
			WriteData.closeFile();
			canSimulate = false;
		}
		building++;
			
	}
	
	/**
	 * Nasimuluje produkci a transport
	 */
	public void simulate() {
		if (cancel)
			return;
		
		Factory fac;
		cancel = false;
		boolean itIsOK;
		int index = 0;
		
		if (buildings[building] instanceof Factory) {
			((Factory) buildings[building]).addProduction(day);
		} else {
			index = 0;
			do {
				if (day == 0)
					((Shop) buildings[building]).takeFromStocks(day, days, this);

				fac = graph.getNearestFactoryToShop((Shop) buildings[building], this, index);
				if (fac == null) {
					cancel = true;
					return;
				}

				itIsOK = ((Shop) buildings[building]).transportArticles(fac, day, days, lowestCostNOW, this);
				index++;
			} while (!itIsOK);
		}
	}

	/**
	 * Vrati string s prehledem o cene dany den
	 * @param day den simulace
	 * @return string ve formatu "Cena prepravy za den_simulace den: cena_dne"
	 */
	public String returnDayCost(int day) {
		return "Cena prepravy za " + (day + 1) + ". den: " + dayCost;
	}

	/**
	 * 
	 * @param i
	 * @param simulation
	 * @return
	 */
	public int[] getProduction(int i) {
		int[] production = new int[getDays() * getArticles()];

		for (int j = 0; j < getDays() * getArticles(); j++) {
			production[j] = getFactoriesProduction(j, i);
		}

		return production;
	}

	/**
	 * 
	 * @param i
	 * @param simulation
	 * @return
	 */
	public int[] getDemand(int i) {
		int[] demand = new int[getDays() * getArticles()];

		for (int j = 0; j < getDays() * getArticles(); j++) {
			demand[j] = getDemand(j, i - getFactories());
		}

		return demand;
	}

	/**
	 * 
	 * @param i
	 * @param simulation
	 * @return
	 */
	public int[] getStartingSupplies(int i) {
		int[] supplies = new int[getArticles()];

		for (int j = 0; j < getArticles(); j++) {
			supplies[j] = getStartingSupplies(j, i - getFactories());
		}

		return supplies;
	}
	
	public void restartSim() {
		
	}

}
