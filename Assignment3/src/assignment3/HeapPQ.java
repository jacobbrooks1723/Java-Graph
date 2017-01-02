package assignment3;

import java.util.ArrayList;

public class HeapPQ {
	
	private ArrayList<GraphNode> heap = new ArrayList<GraphNode>();
	private boolean isEmpty = false;
	
	public HeapPQ(){
		GraphNode g = new GraphNode("Null");
		heap.add(g);
	}
	
	public void insert(GraphNode g){
		heap.add(g);
		siftUp(heap.size()-1);
	}
	
	public GraphNode pullMax(){
		if(heap.size() == 2){
			GraphNode max = heap.get(1);
			heap.remove(1);
			isEmpty = true;
			return max;
		}else{
			GraphNode max = heap.get(1);
			GraphNode rightMost = heap.get(heap.size()-1);
			heap.remove(heap.size()-1);
			heap.set(1, rightMost);
			siftDown(1);
			return max;
		}
	}
	
	public boolean isEmpty(){
		return isEmpty;
	}
	
	public ArrayList<GraphNode> getHeap(){
		return heap; 
	}
	
	public void siftUp(int pos){
		if(heap.get(pos).getDistance() > heap.get(pos/2).getDistance()){
			GraphNode temp = heap.get(pos/2);
			heap.set(pos/2, heap.get(pos));
			heap.set(pos, temp);
		}
		if(pos/2 != 0){
			siftUp(pos/2);
		}
	}
	
	private void siftDown(int pos){
		if(largerChild(pos) != -1){
			if(heap.get(pos).getDistance() < heap.get(largerChild(pos)).getDistance()){
				int largerChild = largerChild(pos);
				GraphNode temp = heap.get(pos);
				heap.set(pos, heap.get(largerChild));
				heap.set(largerChild, temp);
				siftDown(largerChild);
			}
		}
	}
	
	private int largerChild(int pos){
		int largerChild = -1;
		if(pos * 2 > heap.size() -1){
			return largerChild;
		}else if((pos * 2) + 1 > heap.size() -1){
			return pos * 2;
		}
		GraphNode leftChild = heap.get(pos * 2);
		GraphNode rightChild = heap.get((pos * 2) + 1);
		if(leftChild.getDistance() > rightChild.getDistance()){
			largerChild = pos * 2;
		}else{
			largerChild = (pos * 2) + 1;
		}
		return largerChild;
	}
	
}

