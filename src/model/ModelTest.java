package model;

public class ModelTest {
	public static void main(String[] args) {
		Graph g = Graph.fromFile("graph1Test.txt");
		System.out.println("Graph dargestellt:");
		System.out.println(g);
	}

}
