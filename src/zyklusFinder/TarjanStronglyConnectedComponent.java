package zyklusFinder;

import java.util.*;

import model.Graph;
import model.Knoten;

/**
 * Date 08/16/2015
 * @author Tushar Roy
 *
 * Find strongly connected components of directed graph.
 *
 * Time complexity is O(E + V)
 * Space complexity  is O(V)
 *
 * Reference - https://en.wikipedia.org/wiki/Tarjan%27s_strongly_connected_components_algorithm
 * Good Explanation of the Algorithm: https://www.youtube.com/watch?v=wUgWX0nc4NY
 */
public class TarjanStronglyConnectedComponent {

    private Map<Knoten, Integer> indices;
    private Map<Knoten, Integer> lowLink;
    private Set<Knoten> onStack;
    private Deque<Knoten> stack;
    private Set<Knoten> visited;
    private List<Set<Knoten>> result;
    private int index;

    public List<Set<Knoten>> scc(Graph graph) {

        //keeps the sequence order when every node is visited
        index = 0;
        //keeps map of node -> index it was visited
        indices = new HashMap<>();

        //keeps map of node and index of first node visited in current DFS
        lowLink = new HashMap<>();

        //tells if a node is in stack or not
        // redundant?
        onStack = new HashSet<>();

        //stack of visited nodes
        stack = new LinkedList<>();

        //tells if node has ever been visited or not. This is for DFS purpose.
        visited = new HashSet<>();

        //stores the strongly connected components result;
        result = new ArrayList<>();

        //start from any node in the graph.
        for (Knoten knoten : graph.getKnoten()) {
            if(!visited.contains(knoten)) {
                sccUtil(knoten);
            }
        }

        return result;
    }

    private void sccUtil(Knoten knoten) {

        visited.add(knoten);
        indices.put(knoten, index);
        lowLink.put(knoten, index);
        index++;
        stack.push(knoten);
        // stack.addFirst(Knoten);
        onStack.add(knoten);

        for (Knoten child : knoten.getVerbundeneKnoten()) {
            //if child is not visited then visit it and see if it has link back to node's ancestor. In that case update
            //lowLink to ancestor's index
            // --> DFS until a visited node is hit 
            if (!visited.contains(child)) {
                sccUtil(child);
                //updates lowLink[Knoten] = min(lowLink[Knoten], lowLink[child]);
                lowLink.put(knoten, Math.min(lowLink.get(knoten), lowLink.get(child)));
            } //if child is on stack then see if its index is lower than node's lowLink. If yes then update node's lowLink to that.
            else if (onStack.contains(child)) {
                //If child is not on stack, then th edge to child is pointing to an SCC already found and must be ignored
                //DFS found a leaf/end --> "backtrack" one step and update lowLink value
                //updates lowLink[Knoten] = min(lowLink[Knoten], indices[child]);
                lowLink.put(knoten, Math.min(lowLink.get(knoten), indices.get(child)));
            }
        }

        //End of DFS:
        //if nodes lowLink value is the same as index then this is the root node for a strongly connected component.
        //keep popping node out of stack until you find the current node. They are all part of one strongly connected component.
        if (indices.get(knoten) == lowLink.get(knoten)) {
            Set<Knoten> stronglyConnectedComponenet = new HashSet<>();
            Knoten v;
            do {
                v = stack.pollFirst();  // pollFirst() is analogue to pop(), but throws no exception
                onStack.remove(v);
                stronglyConnectedComponenet.add(v);
            } while (!knoten.equals(v));
            result.add(stronglyConnectedComponenet);
        }
    }
}