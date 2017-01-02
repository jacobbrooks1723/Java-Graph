package assignment3;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Data {
	
	private HashTable src;
	private HashTable dest;
	PrintWriter out = new PrintWriter("edges1.txt");
	
	public Data() throws IOException{
		src = new HashTable();
		dest = new HashTable();
	}
	
	public void parse(String rootURL) throws IOException{
		wikipediaParse(rootURL, 0);
		out.close();
	}
	
	
	private void wikipediaParse(String rootURL, int parseCount) throws IOException {
		ArrayList<String> urlList = new ArrayList<String>();
		Document doc = Jsoup.connect(rootURL).get();
		String sourceText = doc.body().text();
		Scanner stringScanner = new Scanner(sourceText);
		src = new HashTable();
		while (stringScanner.hasNext()) {
			String s = stringScanner.next();
			src.insert(s);
		}
		Elements links = doc.select("p");
		for (Element e : links) {
			Elements anchor = e.select("a");
			for (Element e1 : anchor) {
				if (e1.hasAttr("href")) {
					String URL = e1.attr("href");
					if (URL.startsWith("/wiki")) {
						String finalURL = "https://en.wikipedia.org" + URL;
						urlList.add(finalURL);
					}
				}
			}
		}
		if(urlList.size() >= 10){
			parseText(10, urlList, stringScanner, doc, rootURL);
			if(parseCount < 6){
				parseCount++;
				for(int i = 0; i < 10; i++){
					wikipediaParse(urlList.get(i), parseCount);
				}
			}
		}else{
			parseText(urlList.size(), urlList, stringScanner, doc, rootURL);
			if(parseCount < 6){
				parseCount++;
				for(int i = 0; i < urlList.size(); i++){
					wikipediaParse(urlList.get(i), parseCount);
				}
			}
		}
	}
	
	private void parseText(int bound, ArrayList<String> urlList, Scanner stringScanner, Document doc, String rootURL) throws IOException{
		for(int i = 0; i < bound; i++){
			doc = Jsoup.connect(urlList.get(i)).get();
			String destText = doc.body().text();
			stringScanner = new Scanner(destText);
			dest = new HashTable();
			while (stringScanner.hasNext()) {
				String s = stringScanner.next();
				dest.insert(s);
			}
			double d = cosineSimilarity(src, dest);
			String s = rootURL + " " + urlList.get(i) + " " + 1;
			out.println(s);
		}
	}
	
	private static double cosineSimilarity(HashTable h, HashTable h2) {
        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;
        double cosineSimilarity = 0.0;
        
        for(int i = 0; i < h.getArray().length; i++){
        	h.getArray()[i].selectionSort();
        	h2.getArray()[i].selectionSort();
        }
     
        for (int i = 0; i < h.getArray().length; i++) {
        	if(h.getArray()[i].size() >= h2.getArray()[i].size()){
        		for(int j = 0; j < h.getArray()[i].size(); j++){
        			if(j < h2.getArray()[i].size()){
        				dotProduct += h.getArray()[i].get(j).frequency * h2.getArray()[i].get(j).frequency;  
        				magnitude1 += Math.pow(h.getArray()[i].get(j).frequency, 2);  
        				magnitude2 += Math.pow(h2.getArray()[i].get(j).frequency, 2);
        			}
        			else{
        				dotProduct += 0;  
                        magnitude1 += Math.pow(h.getArray()[i].get(j).frequency, 2);  
                        magnitude2 += Math.pow(0, 2);
        			}
                }
        	}
        	else{
        		for(int j = 0; j < h2.getArray()[i].size(); j++){
        			if(j < h.getArray()[i].size()){
        				dotProduct += h2.getArray()[i].get(j).frequency * h.getArray()[i].get(j).frequency;  
        				magnitude1 += Math.pow(h2.getArray()[i].get(j).frequency, 2);  
        				magnitude2 += Math.pow(h.getArray()[i].get(j).frequency, 2);
        			}
        			else{
        				dotProduct += 0;  
                        magnitude1 += Math.pow(h2.getArray()[i].get(j).frequency, 2);  
                        magnitude2 += Math.pow(0, 2);
        			}
                }
        	}
        }  
        magnitude1 = Math.sqrt(magnitude1);
        magnitude2 = Math.sqrt(magnitude2);
 
        if (magnitude1 != 0.0 | magnitude2 != 0.0) {
            cosineSimilarity = dotProduct / (magnitude1 * magnitude2);
        } 
        else {
            return 0.0;
        }
        return cosineSimilarity;
    }
	
}
