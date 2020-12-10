package model;

import java.util.LinkedList;
import java.util.List;

public class Knoten {
	
	//Konstruktoren
	public Knoten(int id) {
		this.id = id;
		verbundeneKnoten = new LinkedList<>();
	}
	
	public Knoten(int id, List<Knoten> vb) {
		this.id = id;
		this.verbundeneKnoten = vb;
	}
	
	//Variablen
	private int id;
	private List<Knoten> verbundeneKnoten;
	
	//Methoden
	public int getId() {
		return id;
	}
	
	public boolean hasConnectedKnoten() {
		if(verbundeneKnoten.size() > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public List<Knoten> getVerbundeneKnoten(){
		return this.verbundeneKnoten;
	}
	
	public void setVerbundeneKnoten(List<Knoten> vb) {
		verbundeneKnoten = vb;
	}
	
	public boolean addKanteTo(Knoten k) {
		return verbundeneKnoten.add(k);
	}
	
	public boolean removeKanteTo(Knoten k) {
		return verbundeneKnoten.remove(k);
	}
	
	@Override
	public String toString(){
		return "KnotenID: " + id;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == this) {
			return true;
		}
		if(!(o instanceof Knoten)) {
			return false;
		}
		
		Knoten k = (Knoten) o;
		
		if(k.id == this.id) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override 
	public int hashCode() {
		return id;
	}
	
	

}
