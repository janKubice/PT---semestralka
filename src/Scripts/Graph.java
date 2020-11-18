package Scripts;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Trida reprezentujici Graf
 * Jako vrcholy grafu je vyuzita trida Building
 * hrana grafu je reprezentovana tridou Edge
 * @author Jan Kubice & Michaela Bene�ov�
 *
 */
public class Graph{
	private int vertices;
	private LinkedList<Edge> list[];

	/**
	 * Konstruktor grafu
	 * @param vertices pocet vrcholu grafu (pocet budov v simulaci)
	 */
	public Graph(int vertices) {
		this.vertices=vertices;
		list = new LinkedList[vertices];
		
		for(int i = 0; i<vertices;i++) {
			list[i] = new LinkedList<>();
		}
		
	}
	
	/**
	 * Prida hranu do grafu
	 * @param source zdrojova budova
	 * @param dest cilova budova
	 * @param cost cena dopravy mezi budovama
	 */
	public void addEdge(Building source, Building dest, int cost) {
		Edge edge1 = new Edge(cost, source, dest);
		Edge edge2 = new Edge(cost, dest, source);
		list[source.index].addFirst(edge1);
		list[dest.index].addFirst(edge2);
	}
	
	/**
	 * Vypise graf
	 */
	public void printGraph(){
        for (int i = 0; i <vertices ; i++) {
            LinkedList<Edge> list = this.list[i];
            for (int j = 0; j <list.size() ; j++) {
                System.out.println("vertex-" + i + " is connected to " +
                        list.get(j).getDestination() + " with weight " +  list.get(j).getCost());
            }
        }
    }
	
	/**
	 * seradi tovarny pripojene hranou k obchodu a vrati x-tou nejblizsi
	 * @param shop obchod pro ktery hledame tovarnu
	 * @param sim instance simulace
	 * @param x poradi tovarny v serazenem poli
	 * @return pozadovanou tovarnu
	 */
	public Factory getNearestFactoryToShop(Shop shop, Simulation sim, int x) {
		if (x >= sim.getFactories()) {
			System.out.println("Neuspokojen� popt�vka obchodu: " + shop.index);
			return null;
		}

		Factory nearest = (Factory)list[shop.index].get(0).getDestination();
		int lowestCost = list[shop.index].get(0).getCost();
		LinkedList<Edge> factoriesByCost = new LinkedList<Edge>();
		
		for (int factory = 0; factory < list[shop.index].size(); factory++) {
			factoriesByCost.add(list[shop.index].get(factory));
		}

		Collections.sort(factoriesByCost,new Comparator<Edge>() {
	         @Override
	        public int compare(Edge s1, Edge s2) {
	                return Integer.compare(s1.getCost(), s2.getCost());
	        }
	    });

		sim.lowestCostNOW = factoriesByCost.get(x).getCost();
		return (Factory)factoriesByCost.get(x).getDestination();
	}
	
	public LinkedList<Edge>[] getList() {
		return list;
	}

	public int getVertices() {
		return vertices;
	}

	
	
}


