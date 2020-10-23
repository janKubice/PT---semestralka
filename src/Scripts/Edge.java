package Scripts;

/**
 * Trida reprezentuje hranu v grafu
 * @author Jan Kubice & Michaela Benešová
 *
 */
public class Edge{
	private int cost;
	private Building source;
	private Building destination;
	
	/**
	 * Konstruktor hrany
	 * @param cost ohodnoceni hrany
	 * @param source pocatecni budova
	 * @param destination koncova budova
	 */
	public Edge(int cost, Building source, Building destination){
		this.cost = cost;
		this.source = source;
		this.destination = destination;
	}
	
	public int getCost() {
		return cost;
	}

	public Building getSource() {
		return source;
	}

	public Building getDestination() {
		return destination;
	}


	
}
