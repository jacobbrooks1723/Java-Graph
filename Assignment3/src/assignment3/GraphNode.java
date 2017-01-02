package assignment3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GraphNode {
	private String pageTitle;
	private String pageURL;
	private boolean visited = false;
	private double distance = Double.MAX_VALUE;
	private GraphNode parent;
	
	public GraphNode(String pageURL){
		this.pageURL = pageURL;
	}
	
	public void setParent(GraphNode p){
		parent = p;
	}
	
	public GraphNode getParent(){
		return parent;
	}
	
	public void setDistance(double d){
		distance = d;
	}
	
	public double getDistance(){
		return distance;
	}
	
	public void setVisited(boolean b){
		visited = b;
	}
	
	public boolean beenVisited(){
		return visited;
	}
	
	public String getPageURL(){
		return pageURL;
	}
	
	public String getPageTitle(){
		String preTitle = pageURL.substring(30, pageURL.length());
		String title = "";
		char[] chars = preTitle.toCharArray();
		for(char c : chars){
			if(c == '_'){
				c = ' ';
				title += c;
			}else{
				title += c;
			}
		}
		return title;
	}

}
