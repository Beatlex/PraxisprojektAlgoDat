package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class Graph {
	/*
	 * Graph gerichtet, ungewichtet. Veränderbar
	 */

	// Konstruktoren
	public Graph() {
		knoten = new LinkedList<>();
	}

	// Variablen
	private List<Knoten> knoten;

	// Methoden

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
			// Abbruchbedingung für das Einlesen
			if (fileInhalt.length() <= 1) {
				break;
			}
			// führendes , entfernen
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

	// Methode um Knoten einfügen zu können
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

	public boolean remove(Knoten k) {
		for (Knoten k1 : this.knoten) {
			k1.removeKanteTo(k);
		}
		return this.knoten.remove(k);
	}

}
