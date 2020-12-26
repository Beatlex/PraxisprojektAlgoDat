package zyklusFinder;

import java.util.Stack;

import model.Knoten;

public class JohnsonStack{
	/*
	 * Prinzip eines Stacks bekannt. Angepasst auf die Bedürfnisse von Johnson (für Knoten)
	 */
	
	//Konstruktoren
	public JohnsonStack() {
		stack = new Stack<>();
	}
	
	//non-static Members
	Stack<Knoten> stack;
	
	//non-static Funktions
	
	public void add(Knoten k) {
		stack.push(k);
	}
	
	public Knoten getLast() {
		return stack.pop();
	}
	
	public boolean isEmpty() {
		return stack.empty();
	}
}
