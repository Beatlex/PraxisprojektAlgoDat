package zyklusFinder;

import java.util.*;

import model.Graph;
import model.Knoten;

/**
 * Date 11/16/2015
 * @author Tushar Roy
 *
 * Find all cycles in directed graph using Johnson's algorithm
 *
 * Time complexity - O(E + V).(c+1) where c is number of cycles found
 * Space complexity - O(E + V + s) where s is sum of length of all cycles.
 *
 * Link to youtube video - https://youtu.be/johyrWospv0
 *
 * References
 * https://github.com/jgrapht/jgrapht/blob/master/jgrapht-core/src/main/java/org/jgrapht/alg/cycle/JohnsonSimpleCycles.java
 */
public class AllCyclesInDirectedGraphJohnson {
    Set<Knoten> blockedSet;
    Map<Knoten, Set<Knoten>> blockedMap;
    Deque<Knoten> stack;
    List<List<Knoten>> allCycles;

    /**
     * Main function to find all simple cycles (each node only occurs once in a cycle)
     */
    public List<List<Knoten>> simpleCyles(Graph graph) {

        //stores nodes, that have been visited on the current path --> cannot be visited again = BLOCKED
        blockedSet = new HashSet<>();
        //if a node n has no more unexplored neighbors and is not part of an existing cycle, n is added to the value set of all neighbors in the blockedMap
        //blockedMap stores a "conditional" mapping of nodes --> if key is unblocked, the value has to be unblocked as well
        blockedMap = new HashMap<>();
        stack = new LinkedList<>();
        allCycles = new ArrayList<>();
        long startIndex = 1;
        TarjanStronglyConnectedComponent tarjan = new TarjanStronglyConnectedComponent();
        while(startIndex <= graph.getKnoten().size()) {
            //get subGraph to ignore every node smaller than startIndex
            Graph subGraph = Graph.createSubGraph(startIndex, graph);
            //get all strongly connected components (sccs) in the subGraph
            List<Set<Knoten>> sccs = tarjan.scc(subGraph);
            //create a graph containing only the scc that contains the node with the lowest index
            //get the node with the lowest index from that new graph
            Knoten lowestKnoten = lowestIndexSCC(sccs, subGraph);
            if(lowestKnoten == null) {
                break;
            } else {
                blockedSet.clear();
                blockedMap.clear();
                //start searching for cycles with the node with the lowest index
                findCyclesInSCG(lowestKnoten, lowestKnoten);
                startIndex = lowestKnoten.getId() + 1;
            }
        }
        return allCycles;
    }
    
    //this function picks the strongly connected component (scc), that contains the node with the lowest index from a list of sccs
    //if the scc consists of only one node, it will be ignored
    //it then creates a graph that only consists of that strongly connected component
    //it then returns the node with the lowest index from that new graph.
    private Knoten lowestIndexSCC(List<Set<Knoten>> sccs, Graph subGraph) {
        long min = Integer.MAX_VALUE;
        Knoten minKnoten = null;
        Set<Knoten> minScc = null;
        for(Set<Knoten> scc : sccs) {
            if(scc.size() == 1) {
                continue;
            }
            for(Knoten knoten : scc) {
                if(knoten.getId() < min) {
                    min = knoten.getId();
                    minKnoten = knoten;
                    minScc = scc;
                }
            }
        }

        if(minKnoten == null) {
            return null;
        }
        Graph graphScc = Graph.createSubGraph(minScc, subGraph);
        return graphScc.getKnoten(minKnoten.getId());
    }

    private void unblock(Knoten u) {
        blockedSet.remove(u);
        if(blockedMap.get(u) != null) {
            for (Knoten k : blockedMap.get(u)) {
                if(blockedSet.contains(k)){
                    unblock(k);
                }
            }
            blockedMap.remove(u);
        }
    }

    private boolean findCyclesInSCG(
            Knoten startKnoten,
            Knoten currentKnoten) {
        boolean foundCycle = false;
        stack.push(currentKnoten);
        blockedSet.add(currentKnoten);

        for (Knoten neighbor : currentKnoten.getVerbundeneKnoten()) {
            //if neighbor is same as start node means cycle is found.
            //Store contents of stack in final result.
            if (neighbor == startKnoten) {
                List<Knoten> cycle = new ArrayList<>();
                stack.push(startKnoten);
                cycle.addAll(stack);
                Collections.reverse(cycle);
                stack.pop();
                allCycles.add(cycle);
                foundCycle = true;
            } //explore this neighbor only if it is not in blockedSet.
            else if (!blockedSet.contains(neighbor)) {
                boolean gotCycle =
                        findCyclesInSCG(startKnoten, neighbor);
                foundCycle = foundCycle || gotCycle;
            }
        }
        //if cycle is found with current node then recursively unblock node and all vertices which are dependent on this node.
        if (foundCycle) {
            //remove from blockedSet  and then remove all the other vertices dependent on this node from blockedSet
            unblock(currentKnoten);
        } else {
            //if no cycle is found with current node then don't unblock it. But find all its neighbors and add this
            //node to their blockedMap. If any of those neighbors ever get unblocked then unblock current node as well.
            for (Knoten k : currentKnoten.getVerbundeneKnoten()) {
                if(!blockedMap.containsKey(k)){
                    blockedMap.put(k, new HashSet<>());
                }
                blockedMap.get(k).add(currentKnoten);
            }
        }
        //remove node from the stack.
        stack.pop();
        return foundCycle;
    }
}