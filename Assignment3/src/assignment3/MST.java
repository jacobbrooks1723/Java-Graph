package assignment3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class MST {
	
	HashMap<String, HashMap<String,Edge>> h;
	ArrayList<String> allNodes;
	ArrayList<String> visitedNodes;
	ArrayList<Edge> mst = new ArrayList<Edge>();
	
	public MST(HashMap<String, HashMap<String,Edge>> h){
		this.h = h;
		visitedNodes = new ArrayList<String>();
		allNodes = createNodeSet();
		initializePrim();
	}
	
	private boolean alreadyVisited(String s){
		for(String v : visitedNodes){
			if(v.equals(s)){
				return true;
			}
		}
		return false;
	}
	
	private boolean hasNode(String node, ArrayList<String> nodes){
		for(String s: nodes){
			if(s.equals(node)){
				return true;
			}
		}
		return false;
	}
	
	
	private void initializePrim(){
		Random r = new Random();
		System.out.println(allNodes.size());
		int n = r.nextInt(allNodes.size());
		while(!h.containsKey(allNodes.get(n)) || alreadyVisited(allNodes.get(n))){
			n = r.nextInt(allNodes.size());
		}
		visitedNodes.add(allNodes.get(n));
		prim();
	}
	
	public boolean allSourcesVisited(){
		for(String s : visitedNodes){
			if(!h.containsKey(s)){
				return false;
			}
		}
		return true;
	}
	
	public void prim(){
		PriorityQueue pq = new PriorityQueue();
		for(String v : visitedNodes){
			if(h.containsKey(v)){
				HashMap<String, Edge> list = h.get(v);
				Collection<Edge> edges = list.values();
				Iterator<Edge> i = edges.iterator();
				while(i.hasNext()){
					Edge e = i.next();
					if(!alreadyVisited(e.getDest().getPageURL())){
						pq.add(e);
					}
				}
			}
		}
		if(pq.getQueue().size() < 1){
			initializePrim();
		}else{
			Edge e = pq.pullMax();
			visitedNodes.add(e.getDest().getPageURL());
			mst.add(e);
		}
		if(visitedNodes.size() < allNodes.size()){
			prim();
		}
	}
	
	private ArrayList<String> createNodeSet(){
		ArrayList<String> allNodes = new ArrayList<String>();
		Set<String> srcSet = h.keySet();
		Iterator<String> i = srcSet.iterator();
		while(i.hasNext()){
			String node = i.next();
			if(!hasNode(node, allNodes)){
				allNodes.add(node);
			}
			Set<String> destSet = h.get(node).keySet();
			Iterator<String> j = destSet.iterator();
			while(j.hasNext()){
				String destNode = j.next();
				if(!hasNode(destNode, allNodes)){
					allNodes.add(destNode);
				}
			}
		}
		return allNodes;
	}

}
