package Scripts;

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
	private int[][] fatctoriesProduction;
	private int[][] demant;
	
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
	public void setTravelCosts(int[][] travelCosts) {
		this.travelCosts = travelCosts;
	}
	public int[][] getStartingSupplies() {
		return startingSupplies;
	}
	public void setStartingSupplies(int[][] startingSupplies) {
		this.startingSupplies = startingSupplies;
	}
	public int[][] getFatctoriesProduction() {
		return fatctoriesProduction;
	}
	public void setFatctoriesProduction(int[][] fatctoriesProduction) {
		this.fatctoriesProduction = fatctoriesProduction;
	}
	public int[][] getDemant() {
		return demant;
	}
	public void setDemant(int[][] demant) {
		this.demant = demant;
	}
	public void setDemantLine(int index, int[] demant) {
		this.demant[index] = demant;
	}
	public void setTravelCostLine(int index, int[] cost) {
		this.travelCosts[index] = cost;
	}
	public void setFactoriesProductionLine(int index, int[] production) {
		this.fatctoriesProduction[index] = production;
	}
	public void setStartingSuppliesLine(int index, int[] suplies) {
		this.startingSupplies[index] = suplies;
	}
	
	/**
	 * Nastaví velikost první dimenze maticí
	 */
	public void initMatrixes() {
		travelCosts = new int[factories][shops];
		startingSupplies = new int[factories][shops];
		fatctoriesProduction = new int[articles*days][factories];
		demant = new int[articles*days][factories];
	}
	
	@Override
	public String toString() {
		String returnString = "";
		returnString += "Factories: " + factories + "\n";
		returnString += "Shops: " + shops + "\n";
		returnString += "Articles: " + articles + "\n";
		returnString += "Days: " + days + "\n";
		return returnString;
	}
	
	public void arrays() {
		System.out.println(Arrays.deepToString(travelCosts));
	}
}
