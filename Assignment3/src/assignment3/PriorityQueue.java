package assignment3;

import java.util.ArrayList;

public class PriorityQueue {
	
	private ArrayList<Edge> queue = new ArrayList<Edge>();
	
	public Edge pullMax(){
		Edge e = queue.get(0);
		queue.remove(0);
		return e;
	}
	
	public ArrayList<Edge> getQueue(){
		return queue;
	}
	
	
	public void add(Edge e){
		queue.add(e);
		Edge[] queueArray = listToArray();
		Edge[] sorted = selectionSort(queueArray, 0);
		queue = new ArrayList<Edge>();
		for(Edge sortedNode : sorted){
			queue.add(sortedNode);
		}
	}
	
	private Edge[] listToArray(){
		Edge[] result = new Edge[queue.size()];
		for(int i = 0; i < queue.size(); i++){
			result[i] = queue.get(i);
		}
		return result;
	}
	
	private Edge[] selectionSort(Edge[] nums, int count) {
		int minIndex = minIndex(nums, count);
		Edge minVal = nums[minIndex];
		Edge temp = nums[count];

		nums[minIndex] = temp;
		nums[count] = minVal;
		count++;

		if (count < nums.length) {
			return selectionSort(nums, count);
		} else {
			return nums;
		}
	}

	private int minIndex(Edge[] n, int count) {
		double currentMin = n[count].getWeight();
		int minIndex = count;
		for (int i = count; i < n.length; i++) {
			if (currentMin < n[i].getWeight()) {
				currentMin = n[i].getWeight();
				minIndex = i;
			}
		}
		return minIndex;
	}
	
}
