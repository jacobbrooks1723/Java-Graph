package assignment3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import java.util.PriorityQueue;

public class Main {
	public static void main(String[] args) throws IOException{
		//Data d = new Data();
		//d.parse("https://en.wikipedia.org/wiki/Voiceless_velar_fricative");
		Graph g = new Graph();
		g.loadGraph();
		//UserInterface u = new UserInterface();
		//u.setVisible(true);
		GraphNode n = g.getShortestPath("https://en.wikipedia.org/wiki/Voiceless_velar_fricative", "https://en.wikipedia.org/wiki/Phonetics");
		printPath(n);
	}
	
	static void printPath(GraphNode g){
		System.out.println(g.getPageURL());
		if(g.getParent() != null){
			printPath(g.getParent());
		}
	}
	
}
