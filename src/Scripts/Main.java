package Scripts;

import java.io.FileNotFoundException;

import javax.swing.JFrame;

/**
 * 
 * @author Jan Kubice & Michaela Benešová
 *
 */
public class Main {

	/**
	 * Vstupni bod programu
	 * @param args
	 */
	public static void main(String[] args) {

		FrameMaker frameMaker = new FrameMaker();
		Simulation simulation = new Simulation();
		ReadData readData = new ReadData("Inputs/test_optim.txt", simulation);
		JFrame frame = frameMaker.makeMenu();
		Graph graph;
		
		frame.setVisible(true);
		
		try {
			readData.setDataToSimulation();
		} catch (FileNotFoundException e) {
			System.out.print("Nepodaøilo se naèíst data");
			System.out.println(e.getMessage());
		}
		
		System.out.println(simulation.toString());
		

		graph = new Graph(simulation.getFactories() + simulation.getShops());
		Building[] buildings = new Building[graph.getVertices()];
		
		for (int i = 0; i < graph.getVertices(); i++) {
			if (i < simulation.getFactories())
				buildings[i] = new Factory(i, getProduction(i,simulation), simulation.getArticles());
			else
				buildings[i] = new Shop(i, getDemand(i,simulation), getStartingSupplies(i, simulation));
		}
		
		for (int i = 0; i < simulation.getFactories(); i++) {
			for (int j = simulation.getFactories(); j < buildings.length; j++) {
				graph.addEdge(buildings[i], buildings[j], simulation.getTravelCosts(i, j - simulation.getFactories()));
			}
		}
		
		
		simulation.setBuildings(buildings);
		simulation.startSimulation(graph);

	}
	
	/**
	 * 
	 * @param i
	 * @param simulation
	 * @return
	 */
	public static int[] getProduction(int i, Simulation simulation) {
		 int[] production = new int[simulation.getDays()*simulation.getArticles()];
		 
		 for(int j = 0; j < simulation.getDays()*simulation.getArticles();j++) {
			 production[j] = simulation.getFactoriesProduction(j, i);
		 }
		 
		 return production;
	}
	
	/**
	 * 
	 * @param i
	 * @param simulation
	 * @return
	 */
	public static int[] getDemand(int i, Simulation simulation) {
		int[] demand = new int[simulation.getDays()*simulation.getArticles()];
		 
		 for(int j = 0; j < simulation.getDays()*simulation.getArticles();j++) {
			 demand[j] = simulation.getDemand(j, i - simulation.getFactories());
		 }
		 
		 return demand;
	}
	
	/**
	 * 
	 * @param i
	 * @param simulation
	 * @return
	 */
	public static int[] getStartingSupplies(int i, Simulation simulation) {
		 int[] supplies = new int[simulation.getArticles()];
		 
		 for(int j = 0; j < simulation.getArticles();j++) {
			 supplies[j] = simulation.getStartingSupplies(j, i - simulation.getFactories());
		 }
		 
		 return supplies;
	}
	
	
	
}
