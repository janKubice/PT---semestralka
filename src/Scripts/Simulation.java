package Scripts;

import java.security.Identity;
import java.util.Arrays;

/**
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
	
	private int totalCost = 0;
	public int lowestCostNOW = 0;

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
	public void addToTotalCost(int cost) {
		this.totalCost += cost;
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
	 * Spusti simulaci na danem grafu
	 * 
	 * @param graph graf vytvoreny z tovaren a obchodu
	 */
	public void startSimulation(Graph graph) {
		Factory fac;
		boolean itIsOK;
		int index = 0;
		
		for (int day = 0; day < days; day++) {
			
			for (int building = 0; building < graph.getList().length; building++) {
				
				for (int i = 0; i < graph.getList()[building].size(); i++) {
					index = 0;
					if (buildings[building] instanceof Factory) {
						((Factory)buildings[building]).addProduction(day);
					}
					else {		
						do {
							fac = graph.getNearestFactoryToShop((Shop)buildings[building], this, index);
							itIsOK = ((Shop)buildings[building]).getArticles(fac, day, days, lowestCostNOW, this);
							index++;
						}while(!itIsOK);
						
					}
				}
			}
			printDay(day);
		}
		System.out.println();
	}
	
	public void printDay(int day) {
		System.out.print(day+1 + ": ");
		System.out.println(totalCost);
	}

}
