package assignment3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

public class Graph {

	private ArrayList<GraphNode> srcNodes =  new ArrayList<GraphNode>();
	private HashMap<String, HashMap<String, Edge>> adjMatrix = new HashMap<String, HashMap<String, Edge>>();
	
	private boolean hasNode(GraphNode gIn){
		for(GraphNode g : srcNodes){
			if(g.getPageURL().equals(gIn.getPageURL())){
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Edge> getMST(){
		MST mst = new MST(adjMatrix);
		return mst.mst;
	}
	
	public GraphNode getShortestPath(String src, String dest){
		ShortestPath s = new ShortestPath(adjMatrix);
		GraphNode g = s.dikstra(src, dest);
		return g;
	}
	
	public ArrayList<String> printPath(GraphNode g, ArrayList<String> path){
		path.add(g.getPageURL());
		if(g.getParent() != null){
			return printPath(g.getParent(), path);
		}
		return path;
	}
	
	public void loadGraph() throws FileNotFoundException{
		File f = new File("edges1.txt");
		Scanner fileScanner = new Scanner(f);
		while(fileScanner.hasNext()){
			String src = fileScanner.next();
			String dest = fileScanner.next();
			double weight = fileScanner.nextDouble();
			GraphNode srcNode = new GraphNode(src);
			GraphNode destNode = new GraphNode(dest);
			Edge e = new Edge(srcNode, destNode, weight);
			if(adjMatrix.containsKey(srcNode.getPageURL())){
				if(!adjMatrix.get(srcNode.getPageURL()).containsKey(destNode.getPageURL())){
					adjMatrix.get(srcNode.getPageURL()).put(destNode.getPageURL(), e);
				}
			}else{
				HashMap<String, Edge> adjList = new HashMap<String, Edge>();
				adjList.put(destNode.getPageURL(), e);
				adjMatrix.put(srcNode.getPageURL(), adjList);
			}
			if(!hasNode(srcNode)){
				srcNodes.add(srcNode);
			}
		}
	}
	
	
}
