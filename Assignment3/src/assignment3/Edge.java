package assignment3;

public class Edge {
	private GraphNode source;
	private GraphNode dest;
	private double weight;
	
	public Edge(GraphNode source, GraphNode dest, double weight){
		this.source = source;
		this.dest = dest;
		this.weight = weight;
	}
	
	public GraphNode getSource(){
		return source;
	}
	
	public GraphNode getDest(){
		return dest;
	}
	
	public double getWeight(){
		return weight;
	}
}
