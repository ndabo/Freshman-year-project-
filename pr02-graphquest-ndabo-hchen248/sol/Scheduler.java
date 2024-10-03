package sol;

import java.util.*;

import src.NoScheduleException;

/**
 * This class represents a schedule creator for labs, with each node representing a lab.
 */
public class Scheduler {

    /**
     * Method which checks if a given allocation of labs adheres to
     *     the scheduling constraints of the graph. Assumes that
     *     all lab names in proposedAlloc are valid labels in theGraph.
     *
     * @param theGraph the graph to try to schedule
     * @param proposedAlloc the proposed allocation of labs between Kathi and Elijah
     * @return boolean indicating whether the proposed allocation is valid
     */
    public static boolean checkValidity(IGraph theGraph, ArrayList<HashSet<String>> proposedAlloc) {
        //check if there's only two hashSet in the list
        if(proposedAlloc.size()!= 2){
            return false;
        }

        HashSet<String> kLabs = proposedAlloc.get(0);
        HashSet<String> eLabs = proposedAlloc.get(1);

        //check if all labs are schedule
        int totalLabs = theGraph.getTotalLabs();
        if (kLabs.size() + eLabs.size() != totalLabs) {
            return false;
        }

        //check if the nodes are not overlapping
        for (String lab: kLabs){
            List<String> kNei = theGraph.getNeighbors(lab);
            for(String n: kNei){
                if(!eLabs.contains(n)){
                    return false;
                }
            }
        }

        //Check if every node in neighbors are opposite to each other
        for (String kathiLab : kLabs) {
            List<String> kathiNeighbors = theGraph.getNeighbors(kathiLab);
            for (String neighbor : kathiNeighbors) {
                if (kLabs.contains(neighbor)) {
                    return false;
                }
            }
        }

        for (String elijahLab : eLabs) {
            List<String> elijahNeighbors = theGraph.getNeighbors(elijahLab);
            for (String neighbor : elijahNeighbors) {
                if (eLabs.contains(neighbor)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Method to compute a valid split of the graph nodes
     *     without violating scheduling constraints,
     *     if such a split exists
     * Throws a NoScheduleException if no such split exists
     *
     * @param theGraph the graph to try to schedule
     * @return an ArrayList of HashSets of node labels that constitute a
     *         valid split of the graph
     * @throws NoScheduleException if no such split exists
     */
    public static ArrayList<HashSet<String>> findSchedule(IGraph theGraph)
            throws NoScheduleException {
        ArrayList<HashSet<String>> schedule = new ArrayList<>();
        HashSet<String> visited = new HashSet<>();
        HashSet<String> kLabs = new HashSet<>();
        HashSet<String> eLabs = new HashSet<>();

        for (String lab : theGraph.allNodes()) {
            if (!visited.contains(lab)) {

                LinkedList<String> queue = new LinkedList<>();
                queue.add(lab);
                visited.add(lab);

                while(!queue.isEmpty()) {
                    String l = queue.poll();
                    // Assign lab to Kathi or Elijah
                    if (kLabs.contains(l)) {
                        List<String> neighbors = theGraph.getNeighbors(l);
                        for (String neighbor : neighbors) {
                            if (!visited.contains(neighbor)) {
                                if (!eLabs.contains(neighbor)) {
                                    eLabs.add(neighbor);
                                    queue.add(neighbor);
                                }

                                visited.add(neighbor);
                            }else{
                                if(kLabs.contains(neighbor)){
                                    throw new NoScheduleException();
                                }
                            }
                        }
                    }
                    if (eLabs.contains(l) || (!kLabs.contains(lab) && !eLabs.contains(lab))) {
                        List<String> neighbors = theGraph.getNeighbors(l);
                        eLabs.add(l);
                        for (String neighbor : neighbors) {
                            if (!visited.contains(neighbor)) {
                                if (!kLabs.contains(neighbor)) {
                                    kLabs.add(neighbor);
                                    queue.add(neighbor);
                                }
                                visited.add(neighbor);
                            }else{
                                if(eLabs.contains(neighbor)){
                                    throw new NoScheduleException();
                                }
                            }

                        }
                    }
                }
            }
        }
        schedule.add(kLabs);
        schedule.add(eLabs);

        return schedule;
    }
}
