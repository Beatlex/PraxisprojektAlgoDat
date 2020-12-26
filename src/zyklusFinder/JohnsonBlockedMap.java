package zyklusFinder;

import java.util.ArrayList;
import java.util.HashMap;

import model.Knoten;

public class JohnsonBlockedMap {
	/*
	 * 
	 */
	
	//Konstruktoren
	public JohnsonBlockedMap() {
		map = new ArrayList<>();
	}
	
	//non-static Members
	ArrayList<Knoten[]> map;

	//non-static Funktions
	public void add(Knoten k1, Knoten k2) {
		map.add(new Knoten[] {k1, k2});
	}
	
	public Knoten getValue(Knoten key) {
		Knoten v = null;
		for(Knoten[] arr : map) {
			if(arr[0].equals(key)) {
				v = arr[1];
			}
		}
		return v;
	}
	
	public boolean isEmpty() {
		return map.isEmpty();
	}
}
