package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class Graph {
	/*
	 * Graph gerichtet, ungewichtet. Veränderbar
	 */
	
	//Konstruktoren
	public Graph() {
		knoten = new LinkedList<>();
	}
	
	//Variablen
	private List<Knoten> knoten;
	
	//Methoden
	
	//static
	public static Graph fromFile(String file) {
		Graph g = new Graph();
		String fileInhalt = "";
		String line;
		
		//Inhalt einlesen
		try(BufferedReader br = new BufferedReader(new FileReader(file));){
			while((line = br.readLine()) != null) {
				fileInhalt += line;
			}
		}catch(Exception e) {
			System.out.println("Fehler beim Graph einlesen");
			e.printStackTrace();
		}
		
		//Inhalt verarbeiten
//		System.out.println(fileInhalt);
//		Bsp. Input: 1{2, 3, 4}, 2{3, 4}, 3{4}, 4{}
		
		return g;
	}
	

}
