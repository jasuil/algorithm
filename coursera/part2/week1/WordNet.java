import java.util.TreeMap;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

public class WordNet {
    private final HashMap<String, TreeSet<Integer>> nounMap;
    private final TreeMap<Integer, String> synsetMap;
    private final Digraph graph;
    private int synonymSize;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        nounMap = new HashMap<>();
        synsetMap = new TreeMap<>();
        In in1 = new In(synsets);

        while (!in1.isEmpty()) {
            String[] line = in1.readLine().split(",");
            int index = Integer.parseInt(line[0]);
            synsetMap.put(index, line[1]);
            TreeSet<String> words = new TreeSet<>(Arrays.asList(line[1].split(" ")));
            for (String word : words) {
                if (nounMap.get(word) == null) {
                    nounMap.put(word, new TreeSet<>());
                }
                TreeSet<Integer> wordIndices = nounMap.get(word);
                wordIndices.add(index);
                nounMap.put(word, wordIndices);
            }
            synonymSize++;
        }

        in1 = new In(hypernyms);
        graph = new Digraph(synonymSize);

        while (!in1.isEmpty()) {

            String[] line = in1.readString().split(",");
            if (line.length < 2) continue;
            for (int i = 1; i < line.length; i++) {
                graph.addEdge(Integer.parseInt(line[0]), Integer.parseInt(line[i]));
            }

        }

        if (!isRootedDAG()) throw new IllegalArgumentException();
        DirectedCycle directedCycle = new DirectedCycle(graph);
        if (directedCycle.hasCycle()) throw new IllegalArgumentException();
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        TreeSet<String> nouns = new TreeSet<>();

        for (String word : nounMap.keySet()) {
            nouns.add(word);
        }

        return nouns;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        if (nounMap.get(word) != null) return true;
        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        if (nounA.equals(nounB)) return 0;
        int [][] distanceMap = new int[synonymSize][3];

        for (int aIndex : nounMap.get(nounA)) {
            ArrayList<Integer> indices = new ArrayList<>();
            indices.add(aIndex);
            distanceMap[aIndex][0] = 1;

            int count = 1;
            while (!indices.isEmpty()) {
                ArrayList<Integer> nextIndices = new ArrayList<>();
                for (int index : indices) {
                    Iterator<Integer> iterable = graph.adj(index).iterator();
                    while (iterable.hasNext()) {
                        int nextIndex = iterable.next();
                        // attention : 0 is never visited
                        if (distanceMap[nextIndex][0] == 0 || distanceMap[nextIndex][0] > count+1) {
                            distanceMap[nextIndex][0] = count+1;
                            nextIndices.add(nextIndex);
                        }
                    }
                }
                count++;
                indices = nextIndices;
            }
        }

        for (int bIndex : nounMap.get(nounB)) {
            ArrayList<Integer> indices = new ArrayList<>();
            indices.add(bIndex);
            distanceMap[bIndex][1] = 1;

            int count = 1;
            while (!indices.isEmpty()) {
                ArrayList<Integer> nextIndices = new ArrayList<>();
                for (int index : indices) {
                    Iterator<Integer> iterable = graph.adj(index).iterator();
                    while (iterable.hasNext()) {
                        int nextIndex = iterable.next();
                        // attention : 0 is never visited
                        if (distanceMap[nextIndex][1] == 0 || distanceMap[nextIndex][1] > count+1) {
                            distanceMap[nextIndex][1] = count+1;
                            nextIndices.add(nextIndex);
                        }
                    }
                }
                count++;
                indices = nextIndices;
            }
        }

        int minDistance = -1;
        for (int i = 0; i < distanceMap.length; i++) {
            int totalDistance = distanceMap[i][0] + distanceMap[i][1];
            if (distanceMap[i][0] > 0 && distanceMap[i][1] > 0 && (minDistance == -1 || minDistance > totalDistance)) {
                minDistance = totalDistance;
            }
        }
        return minDistance - 2;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        // if (nounA.equals(nounB)) return nounA;
        int [][] distanceMap = new int[synonymSize][4];

        for (int aIndex : nounMap.get(nounA)) {
            ArrayList<Integer> indices = new ArrayList<>();
            indices.add(aIndex);
            distanceMap[aIndex][0] = 1;

            int count = 1;
            while (!indices.isEmpty()) {
                ArrayList<Integer> nextIndices = new ArrayList<>();
                for (int index : indices) {
                    Iterator<Integer> iterable = graph.adj(index).iterator();
                    while (iterable.hasNext()) {
                        int nextIndex = iterable.next();
                        // attention : 0 is never visited
                        if (distanceMap[nextIndex][0] == 0 || distanceMap[nextIndex][0] > count+1) {
                            distanceMap[nextIndex][0] = count+1;
                            distanceMap[nextIndex][2] = index;
                            nextIndices.add(nextIndex);
                        }
                    }
                }
                count++;
                indices = nextIndices;
            }
        }

        for (int bIndex : nounMap.get(nounB)) {
            ArrayList<Integer> indices = new ArrayList<>();
            indices.add(bIndex);
            distanceMap[bIndex][1] = 1;

            int count = 1;
            while (!indices.isEmpty()) {
                ArrayList<Integer> nextIndices = new ArrayList<>();
                for (int index : indices) {
                    Iterator<Integer> iterable = graph.adj(index).iterator();
                    while (iterable.hasNext()) {
                        int nextIndex = iterable.next();
                        // attention : 0 is never visited
                        if (distanceMap[nextIndex][1] == 0 || distanceMap[nextIndex][1] > count+1) {
                            distanceMap[nextIndex][1] = count+1;
                            distanceMap[nextIndex][3] = index;
                            nextIndices.add(nextIndex);
                        }
                    }
                }
                count++;
                indices = nextIndices;
            }
        }

        int minDistance = -1;
        int minIndex = -1;
        for (int i = 0; i < distanceMap.length; i++) {
            int totalDistance = distanceMap[i][0] + distanceMap[i][1];
            if (distanceMap[i][0] > 0 && distanceMap[i][1] > 0 && (minDistance == -1 || minDistance > totalDistance)) {
                minDistance = totalDistance;
                minIndex = i;
            }
        }

        // find reverse path toward nounA
        return synsetMap.get(minIndex);
    }

    private boolean isRootedDAG() {
        int verticesSize = graph.V();
        int rootCount = 0;
        for (int i = 0; i < verticesSize; i++) {
            if (graph.outdegree(i) == 0 && graph.indegree(i) > 0) rootCount++;
            if (rootCount > 1) return false;
        }
        return true;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
        System.out.println(wordNet.sap("a", "d"));
    }
}