package model;

import java.util.List;

import zyklusFinder.AllCyclesInDirectedGraphJohnson;

public class ModelTest {
	public static void main(String[] args) {
//		Graph g = Graph.fromFile("graph1Test.txt");
		Graph g = Graph.fromFile("GraphDreiZyklen.txt");
		System.out.println("Graph dargestellt:");
		System.out.println(g);

		AllCyclesInDirectedGraphJohnson johnson = new AllCyclesInDirectedGraphJohnson();
		List<List<Knoten>> cycles = johnson.simpleCyles(g);
		System.out.println(cycles.size());

		// System.out.println("SubGraph dargestellt:");
		// System.out.println(Graph.createSubGraph(3, g));
	}
}
