package zyklusFinder;

import java.util.ArrayList;

import model.Knoten;

public class JohnsonBlockedSet {
	/*
	 * 
	 */
	
	//Konstruktoren
	public JohnsonBlockedSet() {
		liste = new ArrayList<>();
	}
	
	//non-static Members
	ArrayList<Knoten> liste;
	
	//non-static Funktions
	public void add(Knoten k) {
		liste.add(k);
	}
	
	public boolean remove(Knoten k){
		return liste.remove(k);
	}
	
	public boolean isEmpty() {
		return liste.isEmpty();
	}

}
