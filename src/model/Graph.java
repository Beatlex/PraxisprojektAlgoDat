package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.text.AsyncBoxView.ChildLocator;

public class Graph {
	/*
	* Graph gerichtet, ungewichtet. Ver�nderbar
	*/
	
	// Konstruktoren
	public Graph() {
		knoten = new LinkedList<>();
	}
	
	// Variablen
	private List<Knoten> knoten;
	
	// Methoden
	
	public Graph clone(){
		Graph clone = new Graph();
		for (Knoten k : this.knoten) {
			clone.addKnoten(new Knoten(k.getId()));
		}
		for (Knoten k : this.knoten) {
			for (Knoten child : k.getVerbundeneKnoten()) {
				clone.getKnoten(k.getId()).addKanteTo(clone.getKnoten(child.getId()));
			}
		}
		return clone;
	}

	public List<Knoten> getKnoten() {
		return knoten;
	}

	public void setKnoten(List<Knoten> knoten) {
		this.knoten = knoten;
	}
	
	// finde Anzahl an Zyklen
	public int findeZyklen() {

		return 0;
	}

	// static
	public static Graph fromFile(String file) {
		Graph g = new Graph();
		String fileInhalt = "";
		String line;

		// Inhalt einlesen
		try (BufferedReader br = new BufferedReader(new FileReader(file));) {
			while ((line = br.readLine()) != null) {
				fileInhalt += line;
			}
		} catch (Exception e) {
			System.out.println("Fehler beim Graph einlesen");
			e.printStackTrace();
		}

		// Inhalt verarbeiten
//		System.out.println(fileInhalt);
//		Bsp. Input: 1{2, 3, 4}, 2{3, 4}, 3{4}, 4{}

		// Alle Leerzeichen entfernen
		fileInhalt = fileInhalt.replace(" ", "");
//		System.out.println(fileInhalt);

		while (true) {
			// Abbruchbedingung f�r das Einlesen
			if (fileInhalt.length() <= 1) {
				break;
			}
			// f�hrendes , entfernen
			if (fileInhalt.charAt(0) == ',') {
				fileInhalt = fileInhalt.substring(1);
			}
			// Ausgangsknoten einlesen
			String k1 = fileInhalt.substring(0, fileInhalt.indexOf("{"));
//			System.out.println(k1);
			Knoten k = new Knoten(Integer.valueOf(k1));
			String verbindungen = fileInhalt.substring(fileInhalt.indexOf("{") + 1, fileInhalt.indexOf("}"));
//			System.out.println(verbindungen);
			if (verbindungen.length() > 0) {
				String[] vb = verbindungen.split(",");
				for (String s : vb) {
					k.addKanteTo(new Knoten(Integer.valueOf(s)));
				}
			}
			// Eingelesen Teil entfernen
			fileInhalt = fileInhalt.substring(fileInhalt.indexOf("}") + 1);
			g.addKnoten(k);
		}
		return g;
	}

	// creates a subgraph, that only includes nodes with ids greater than startKnoten
	public static Graph createSubGraph(long startKnoten, Graph graph) {
		Graph subGraph = graph.clone();
		for (Knoten k : subGraph.getKnoten()) {
			if(k.getId() < startKnoten){
				subGraph.remove(k);
			}
		}
		return subGraph;
	}
	
	//creates a subGraph, that only contains nodes, that are in knotenSet
	public static Graph createSubGraph(Set<Knoten> knotenSet, Graph graph){
		Graph subGraph = graph.clone();
		for (Knoten knoten : graph.getKnoten()) {
			if(!knotenSet.contains(knoten)){
				//remove node from the subGraph
				subGraph.remove(knoten.getId());
			}
		}
		return subGraph;
	}

	// Die toString methode um den Graphen darzustellen
	@Override
	public String toString() {
		String graph = "";
		for (Knoten k1 : this.knoten) {
			for (Knoten k2 : k1.getVerbundeneKnoten()) {
				graph += k1.toString() + " -> " + k2 + "\n";
			}
		}
		return graph;
	}

	// Methode um Knoten einf�gen zu k�nnen
	public void addKnoten(Knoten k) {
		this.knoten.add(k);
	}

	public Knoten getKnoten(Knoten k) {
		for (Knoten s : this.knoten) {
			if (s.equals(k)) {
				return s;
			}
		}
		return null;
	}

	public Knoten getKnoten(int id) {
		for (Knoten k : this.knoten) {
			if (k.getId() == id) {
				return k;
			}
		}
		return null;
	}

	public boolean remove(Knoten k) {
		for (Knoten k1 : this.knoten) {
			k1.removeKanteTo(k);
		}
		return this.knoten.remove(k);
	}

	public boolean remove(int id) {
		return remove(getKnoten(id));
	}
}
