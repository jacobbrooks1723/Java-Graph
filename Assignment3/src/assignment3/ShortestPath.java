package assignment3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ShortestPath {
	HashMap<String, HashMap<String, Edge>> h;
	ArrayList<String> visitedNodes = new ArrayList<String>();
	ArrayList<Edge> shortestPath = new ArrayList<Edge>();
	ArrayList<String> allNodes;
	ArrayList<GraphNode> nodes = new ArrayList<GraphNode>();
	HeapPQ pq = new HeapPQ();

	public ShortestPath(HashMap<String, HashMap<String, Edge>> h) {
		this.h = h;
		allNodes = createNodeSet();
		createNodeObjects();
	}

	private int findNodeInSet(String s) {
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).getPageURL().equals(s)) {
				return i;
			}
		}
		return -1;
	}

	private double getEdgeWeight(String src, String match) {
		double weight = h.get(src).get(match).getWeight();
		return weight;
	}

	private ArrayList<String> getNeighbors(String src) {
		ArrayList<String> neighbors = new ArrayList<String>();
		if (h.containsKey(src)) {
			Set<String> neighborSet = h.get(src).keySet();
			Iterator<String> i = neighborSet.iterator();
			while (i.hasNext()) {
				String s = i.next();
				neighbors.add(s);
			}
			return neighbors;
		} else {
			return null;
		}
	}

	private int getNeighborIndex(String s) {
		for (int i = 0; i < pq.getHeap().size(); i++) {
			if (pq.getHeap().get(i).getPageURL().equals(s)) {
				return i;
			}
		}
		return -1;
	}

	public GraphNode dikstra(String src, String dest) {
		GraphNode nullNode = new GraphNode("null");
		nodes.get(findNodeInSet(src)).setDistance(0);
		for (GraphNode g : nodes) {
			pq.insert(g);
		}
		while (!pq.isEmpty()) {
			GraphNode current = pq.pullMax();
			if (getNeighbors(current.getPageURL()) == null) {
				System.out.println("No path");
				break;
			} else {
				for (String s : getNeighbors(current.getPageURL())) {
					int i = getNeighborIndex(s);
					double alt = current.getDistance()
							+ getEdgeWeight(current.getPageURL(), s);
					if (alt > pq.getHeap().get(i).getDistance()) {
						pq.getHeap().get(i).setDistance(alt);
						pq.getHeap().get(i).setParent(current);
						printPath(pq.getHeap().get(i));
						System.out.println();
						if (pq.getHeap().get(i).getPageURL().equals(dest)) {
							return pq.getHeap().get(i);
						}
						pq.siftUp(i);
					}
				}
			}
		}
		return nullNode;
	}

	public void printPath(GraphNode n) {
		System.out.println(n.getPageURL());
		if (n.getParent() != null) {
			printPath(n.getParent());
		}
	}

	private void createNodeObjects() {
		for (String s : allNodes) {
			GraphNode g = new GraphNode(s);
			g.setDistance(-1);
			nodes.add(g);
		}
	}

	private ArrayList<String> createNodeSet() {
		ArrayList<String> allNodes = new ArrayList<String>();
		Set<String> srcSet = h.keySet();
		Iterator<String> i = srcSet.iterator();
		while (i.hasNext()) {
			String node = i.next();
			if (!hasNode(node, allNodes)) {
				allNodes.add(node);
			}
			Set<String> destSet = h.get(node).keySet();
			Iterator<String> j = destSet.iterator();
			while (j.hasNext()) {
				String destNode = j.next();
				if (!hasNode(destNode, allNodes)) {
					allNodes.add(destNode);
				}
			}
		}
		return allNodes;
	}

	private boolean hasNode(String node, ArrayList<String> nodes) {
		for (String s : nodes) {
			if (s.equals(node)) {
				return true;
			}
		}
		return false;
	}

}
