package Scripts;

public class Edge{
	private int cost;
	private Building source;
	private Building destination;
	
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
