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
 */
public class TarjanStronglyConnectedComponent {

    private Map<Knoten, Integer> visitedTime;
    private Map<Knoten, Integer> lowTime;
    private Set<Knoten> onStack;
    private Deque<Knoten> stack;
    private Set<Knoten> visited;
    private List<Set<Knoten>> result;
    private int time;

    public List<Set<Knoten>> scc(Graph graph) {

        //keeps the time when every Knoten is visited
        time = 0;
        //keeps map of Knoten to time it was visited
        visitedTime = new HashMap<>();

        //keeps map of Knoten and time of first Knoten visited in current DFS
        lowTime = new HashMap<>();

        //tells if a Knoten is in stack or not
        onStack = new HashSet<>();

        //stack of visited Knoten
        stack = new LinkedList<>();

        //tells if Knoten has ever been visited or not. This is for DFS purpose.
        visited = new HashSet<>();

        //stores the strongly connected components result;
        result = new ArrayList<>();

        //start from any Knoten in the graph.
        for (Knoten Knoten : graph.getKnoten()) {
            if(visited.contains(Knoten)) {
                continue;
            }
            sccUtil(Knoten);
        }

        return result;
    }

    private void sccUtil(Knoten Knoten) {

        visited.add(Knoten);
        visitedTime.put(Knoten, time);
        lowTime.put(Knoten, time);
        time++;
        stack.addFirst(Knoten);
        onStack.add(Knoten);

        for (Knoten child : Knoten.getVerbundeneKnoten()) {
            //if child is not visited then visit it and see if it has link back to Knoten's ancestor. In that case update
            //low time to ancestor's visit time
            if (!visited.contains(child)) {
                sccUtil(child);
                //sets lowTime[Knoten] = min(lowTime[Knoten], lowTime[child]);
                lowTime.compute(Knoten, (v, low) ->
                    Math.min(low, lowTime.get(child))
                );
            } //if child is on stack then see if it was visited before Knoten's low time. If yes then update Knoten's low time to that.
            else if (onStack.contains(child)) {
                //sets lowTime[Knoten] = min(lowTime[Knoten], visitedTime[child]);
                lowTime.compute(Knoten, (v, low) -> Math.min(low, visitedTime.get(child))
                );
            }
        }

        //if Knoten low time is same as visited time then this is start Knoten for strongly connected component.
        //keep popping Knoten out of stack still you find current Knoten. They are all part of one strongly
        //connected component.
        if (visitedTime.get(Knoten) == lowTime.get(Knoten)) {
            Set<Knoten> stronglyConnectedComponenet = new HashSet<>();
            Knoten v;
            do {
                v = stack.pollFirst();
                onStack.remove(v);
                stronglyConnectedComponenet.add(v);
            } while (!Knoten.equals(v));
            result.add(stronglyConnectedComponenet);
        }
    }
}