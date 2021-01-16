package model;

import java.util.List;

import zyklusFinder.AllCyclesInDirectedGraphJohnson;

public class ModelTest {
	public static void main(String[] args) {
		String[] alleFiles = {	"GraphOhneZyklus.txt",
								"GraphEinZyklus.txt",
								"GraphZweiZyklen.txt",
								"GraphDreiZyklen.txt",
								"BeispielGraphAlsGerichtet.txt",
								"GraphVideo.txt"
		};
		//Über alle Testgraphen iterieren und Ergebnisse ausgeben
		for(String s : alleFiles) {
			Graph g = Graph.fromFile(s);
			System.out.println("Graph von: " + s);
			System.out.println(g);
			AllCyclesInDirectedGraphJohnson johnson = new AllCyclesInDirectedGraphJohnson();
			List<List<Knoten>> cycles = johnson.simpleCyles(g);
			System.out.println("Anzahl an Zyklen:");
			System.out.println(cycles.size());

		}
	}
}
