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

        for (Knoten neighbor : knoten.getVerbundeneKnoten()) {
            //if neighbor is not visited then visit it and see if it has link back to node's ancestor. In that case update
            //lowLink to ancestor's index
            // --> DFS until a visited node is hit 
            if (!visited.contains(neighbor)) {
                sccUtil(neighbor);
                //updates lowLink[Knoten] = min(lowLink[Knoten], lowLink[neighbor]);
                lowLink.put(knoten, Math.min(lowLink.get(knoten), lowLink.get(neighbor)));
            } //if neighbor is on stack then see if its index is lower than node's lowLink. If yes then update node's lowLink to that.
            else if (stack.contains(neighbor)) {
                //If neighbor is not on stack, then the edge to neighbor is pointing to an SCC already found and must be ignored
                //DFS found a leaf/end --> "backtrack" one step and update lowLink value
                //updates lowLink[Knoten] = min(lowLink[Knoten], indices[neighbor]);
                lowLink.put(knoten, Math.min(lowLink.get(knoten), indices.get(neighbor)));
            }
        }

        //End of DFS:
        //if nodes lowLink value is the same as index then this is the root node for a strongly connected component.
        //keep taking nodes off the stack until the current node is found. They are all part of one strongly connected component.
        if (indices.get(knoten) == lowLink.get(knoten)) {
            Set<Knoten> stronglyConnectedComponent = new HashSet<>();
            Knoten v;
            do {
                v = stack.pollFirst();  // pollFirst() is analogue to pop(), but throws no exception
                stronglyConnectedComponent.add(v);
            } while (!knoten.equals(v));
            result.add(stronglyConnectedComponent);
        }
    }
}