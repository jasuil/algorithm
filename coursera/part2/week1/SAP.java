import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private final Digraph graph;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException();
        graph = new Digraph(G);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        int left = 58962;
        int right = 27758;
        ArrayList<Integer> l1 = new ArrayList<>();
        l1.add(58962);
        ArrayList<Integer> l2 = new ArrayList<>();
        l2.add(27758);
        int length   = sap.length(l1, l2);
        int ancestor = sap.ancestor(left, right);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        /*
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }

         */
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v == w) return 0;

        HashMap<Integer, int[]> distMap = new HashMap<>();

        int count = 1;
        ArrayList<Integer> indices = new ArrayList<>();
        indices.add(v);

        if (distMap.get(v) == null) {
            distMap.put(v, new int[] {0, -1});
        }
        distMap.put(v, new int[] {0, distMap.get(v)[1]});

        while (!indices.isEmpty()) {
            ArrayList<Integer> newIndices = new ArrayList<>();
            for (Integer index : indices) {
                Iterator<Integer> iteratorV = graph.adj(index).iterator();
                while (iteratorV.hasNext()) {
                    Integer nextIndex = iteratorV.next();
                    if (distMap.get(nextIndex) == null) {
                        distMap.put(nextIndex, new int[] { -1, -1});
                    }
                    if (distMap.get(nextIndex)[0] == -1 || distMap.get(nextIndex)[0] > count) {
                        distMap.put(nextIndex, new int[] {count, distMap.get(nextIndex)[1]});
                        newIndices.add(nextIndex);
                    }
                }
            }
            indices = newIndices;
            count++;
        }

        count = 1;
        indices = new ArrayList<>();
        indices.add(w);

        if (distMap.get(w) == null) {
            distMap.put(w, new int[] {-1, 0});
        }
        distMap.put(w, new int[] {distMap.get(w)[0], 0});

        while (!indices.isEmpty()) {
            ArrayList<Integer> newIndices = new ArrayList<>();
            for (Integer index : indices) {
                Iterator<Integer> iteratorV = graph.adj(index).iterator();
                while (iteratorV.hasNext()) {
                    Integer nextIndex = iteratorV.next();
                    if (distMap.get(nextIndex) == null) {
                        distMap.put(nextIndex, new int[] {-1, -1});
                    }
                    if (distMap.get(nextIndex)[1] == -1 || distMap.get(nextIndex)[1] > count) {
                        distMap.put(nextIndex, new int[] {distMap.get(nextIndex)[0], count});
                        newIndices.add(nextIndex);
                    }
                }
            }
            indices = newIndices;
            count++;
        }


        int minDist = -1;
        for (Integer key : distMap.keySet()) {
            int[] distPair = distMap.get(key);
            int totalDist = distPair[0] + distPair[1];
            if (distPair[0] > -1 && distPair[1] > -1 && (minDist == -1 || minDist > totalDist)) {
                minDist = totalDist;
            }
        }

        return minDist;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v == w) return v;

        HashMap<Integer, int[]> distMap = new HashMap<>();

        int count = 1;
        ArrayList<Integer> indices = new ArrayList<>();
        indices.add(v);

        if (distMap.get(v) == null) {
            distMap.put(v, new int[] {0, -1});
        }
        distMap.put(v, new int[] {0, distMap.get(v)[1]});

        while (!indices.isEmpty()) {
            ArrayList<Integer> newIndices = new ArrayList<>();
            for (int index : indices) {
                Iterator<Integer> iteratorV = graph.adj(index).iterator();
                while (iteratorV.hasNext()) {
                    Integer nextIndex = iteratorV.next();
                    if (nextIndex == null) throw new IllegalArgumentException();
                    if (distMap.get(nextIndex) == null) {
                        distMap.put(nextIndex, new int[] {-1, -1});
                    }
                    if (distMap.get(nextIndex)[0] == -1 || distMap.get(nextIndex)[0] > count) {
                        distMap.put(nextIndex, new int[] {count, distMap.get(nextIndex)[1]});
                        newIndices.add(nextIndex);
                    }
                }
            }
            indices = newIndices;
            count++;
        }

        count = 1;
        indices = new ArrayList<>();
        indices.add(w);

        if (distMap.get(w) == null) {
            distMap.put(w, new int[] {-1, 0});
        }
        distMap.put(w, new int[] { distMap.get(w)[0], 0});

        while (!indices.isEmpty()) {
            ArrayList<Integer> newIndices = new ArrayList<>();
            for (int index : indices) {
                Iterator<Integer> iteratorV = graph.adj(index).iterator();
                if (iteratorV == null) throw new IllegalArgumentException();
                while (iteratorV.hasNext()) {
                    Integer nextIndex = iteratorV.next();
                    if (distMap.get(nextIndex) == null) {
                        distMap.put(nextIndex, new int[] { -1, -1});
                    }
                    if (distMap.get(nextIndex)[1] == -1 || distMap.get(nextIndex)[1] > count) {
                        distMap.put(nextIndex, new int[] {distMap.get(nextIndex)[0], count});
                        newIndices.add(nextIndex);
                    }
                }
            }
            indices = newIndices;
            count++;
        }


        int minDist = -1;
        int midIndex = -1;
        for (Integer key : distMap.keySet()) {
            int[] distPair = distMap.get(key);
            int totalDist = distPair[0] + distPair[1];
            if (distPair[0] > -1 && distPair[1] > -1 && (minDist == -1 || minDist > totalDist)) {
                minDist = totalDist;
                midIndex = key;
            }
        }

        return midIndex;
    }

    private int[] ancestorInfo(int v, int w) {
        if (v == w) return new int[] {v, 0};

        HashMap<Integer, int[]> distMap = new HashMap<>();

        int count = 1;
        ArrayList<Integer> indices = new ArrayList<>();
        indices.add(v);

        if (distMap.get(v) == null) {
            distMap.put(v, new int[] {0, -1});
        }
        distMap.put(v, new int[] {0, distMap.get(v)[1]});

        while (!indices.isEmpty()) {
            ArrayList<Integer> newIndices = new ArrayList<>();
            for (Integer index : indices) {
                Iterator<Integer> iteratorV = graph.adj(index).iterator();
                while (iteratorV.hasNext()) {
                    Integer nextIndex = iteratorV.next();
                    if (nextIndex == null) throw new IllegalArgumentException();
                    if (distMap.get(nextIndex) == null) {
                        distMap.put(nextIndex, new int[] {-1, -1});
                    }
                    if (distMap.get(nextIndex)[0] == -1 || distMap.get(nextIndex)[0] > count) {
                        distMap.put(nextIndex, new int[] {count, distMap.get(nextIndex)[1]});
                        newIndices.add(nextIndex);
                    }
                }
            }
            indices = newIndices;
            count++;
        }

        count = 1;
        indices = new ArrayList<>();
        indices.add(w);

        if (distMap.get(w) == null) {
            distMap.put(w, new int[] {-1, 0});
        }
        distMap.put(w, new int[] { distMap.get(w)[0], 0});

        while (!indices.isEmpty()) {
            ArrayList<Integer> newIndices = new ArrayList<>();
            for (Integer index : indices) {
                Iterator<Integer> iteratorV = graph.adj(index).iterator();
                while (iteratorV.hasNext()) {
                    Integer nextIndex = iteratorV.next();
                    if (nextIndex == null) throw new IllegalArgumentException();
                    if (distMap.get(nextIndex) == null) {
                        distMap.put(nextIndex, new int[] {-1, -1});
                    }
                    if (distMap.get(nextIndex)[1] == -1 || distMap.get(nextIndex)[1] > count) {
                        distMap.put(nextIndex, new int[] {distMap.get(nextIndex)[0], count});
                        newIndices.add(nextIndex);
                    }
                }
            }
            indices = newIndices;
            count++;
        }


        int minDist = -1;
        int midIndex = 0;
        for (Integer key : distMap.keySet()) {
            int[] distPair = distMap.get(key);
            int totalDist = distPair[0] + distPair[1];
            if (distPair[0] > -1 && distPair[1] > -1 && (minDist == -1 || minDist > totalDist)) {
                minDist = totalDist;
                midIndex = key;
            }
        }

        return new int[] {midIndex, minDist};
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();

        int minDist = -1;
        Iterator<Integer> vIterator =  v.iterator();
        while (vIterator.hasNext()) {
            Integer vIndex = vIterator.next();
            if (vIndex == null) throw new IllegalArgumentException();

            Iterator<Integer> wIterator = w.iterator();
            while (wIterator.hasNext()) {
                Integer nextIndex = wIterator.next();
                if (nextIndex == null) throw new IllegalArgumentException();
                int result = length(vIndex, nextIndex);
                if (result == 0) return 0;
                else if (minDist == -1 || minDist > result) {
                    minDist = result;
                }
            }
        }

        return minDist;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();

        int[] minInfo = {-1, -1};
        Iterator<Integer> vIterator =  v.iterator();
        while (vIterator.hasNext()) {
            Integer vIndex = vIterator.next();
            if (vIndex == null) throw new IllegalArgumentException();

            Iterator<Integer> wIterator = w.iterator();
            while (wIterator.hasNext()) {
                Integer nextIndex = wIterator.next();
                if (nextIndex == null) throw new IllegalArgumentException();
                int[] result = ancestorInfo(vIndex, nextIndex);
                if (result[1] == 0) return result[0];
                else if (minInfo[1] == -1 || minInfo[1] > result[1]) {
                    minInfo = result;
                }
            }
        }

        return minInfo[0];
    }
}
