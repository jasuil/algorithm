import edu.princeton.cs.algs4.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class KdTreeOld {

    private BST2d points;            // construct an empty set of points
    public KdTreeOld() {
        points = new BST2d();
    }

    public static void main(String[] args) {

    }                 // unit testing of the methods (optional)

    public boolean isEmpty() {
        return points.size() > 0 ? false : true;
    }                      // is the set empty?

    public int size() {
        return points.size();
    }                        // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        points.put(p);
    }             // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        return points.contains(p);
    }          // does the set contain point p?

    public void draw() {
        Iterator<Point2D> iterator = points.keys().iterator();
        while (iterator.hasNext()) {
            Point2D point = iterator.next();
            StdDraw.point(point.x(), point.y());
        }
    }                        // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        BST2d residents = new BST2d();
        Iterator<Point2D> iterator = points.keys().iterator();
        while (iterator.hasNext()) {
            Point2D point = iterator.next();
            if (rect.contains(point)) residents.put(point);
        }
        return residents.keys();
    }            // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (points.size() == 0) return null;
        return points.ceiling(p);
    }

    private class BST2d {
        private Node root;             // root of BST

        /**
         * Initializes an empty symbol table.
         */
        public BST2d() {
        }

        public int size() {
            return size(root);
        }

        // return number of key-value pairs in BST rooted at x
        private int size(Node x) {
            if (x == null) return 0;
            else return x.size;
        }

        public boolean contains(Point2D key) {
            if (key == null) throw new IllegalArgumentException("argument to contains() is null");
            return get(key);
        }

        public boolean get(Point2D key) {
            return get(root, key);
        }

        private boolean get(Node x, Point2D key) {
            if (key == null) throw new IllegalArgumentException("calls get() with a null key");
            if (x == null) return false;
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) return get(x.left, key);
            else if (cmp > 0) return get(x.right, key);
            else              return true;
        }

        public void put(Point2D key) {
            if (key == null) throw new IllegalArgumentException("calls put() with a null key");
            root = put(root, key);
            assert check();
        }

        private Node put(Node x, Point2D key) {
            if (x == null) return new Node(key, 1);
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) x.left  = put(x.left,  key);
            else if (cmp > 0) x.right = put(x.right, key);
            x.size = 1 + size(x.left) + size(x.right);
            return x;
        }

        private boolean check() {
            if (!isBST())            StdOut.println("Not in symmetric order");
            if (!isSizeConsistent()) StdOut.println("Subtree counts not consistent");
            if (!isRankConsistent()) StdOut.println("Ranks not consistent");
            return isBST() && isSizeConsistent() && isRankConsistent();
        }

        // does this binary tree satisfy symmetric order?
        // Note: this test also ensures that data structure is a binary tree since order is strict
        private boolean isBST() {
            return isBST(root, null, null);
        }

        // is the tree rooted at x a BST with all keys strictly between min and max
        // (if min or max is null, treat as empty constraint)
        // Credit: elegant solution due to Bob Dondero
        private boolean isBST(Node x, Point2D min, Point2D max) {
            if (x == null) return true;
            if (min != null && x.key.compareTo(min) <= 0) return false;
            if (max != null && x.key.compareTo(max) >= 0) return false;
            return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
        }

        private boolean isSizeConsistent() { return isSizeConsistent(root); }

        private boolean isSizeConsistent(Node x) {
            if (x == null) return true;
            if (x.size != size(x.left) + size(x.right) + 1) return false;
            return isSizeConsistent(x.left) && isSizeConsistent(x.right);
        }

        // check that ranks are consistent
        private boolean isRankConsistent() {
            for (int i = 0; i < size(); i++)
                if (i != rank(select(i))) return false;
            for (Point2D key : keys())
                if (key.compareTo(select(rank(key))) != 0) return false;
            return true;
        }

        public Point2D select(int rank) {
            if (rank < 0 || rank >= size()) {
                throw new IllegalArgumentException("argument to select() is invalid: " + rank);
            }
            return select(root, rank);
        }

        // Return key in BST rooted at x of given rank.
        // Precondition: rank is in legal range.
        private Point2D select(Node x, int rank) {
            if (x == null) return null;
            int leftSize = size(x.left);
            if      (leftSize > rank) return select(x.left,  rank);
            else if (leftSize < rank) return select(x.right, rank - leftSize - 1);
            else                      return x.key;
        }

        /**
         * Return the number of keys in the symbol table strictly less than {@code key}.
         *
         * @param  key the key
         * @return the number of keys in the symbol table strictly less than {@code key}
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public int rank(Point2D key) {
            if (key == null) throw new IllegalArgumentException("argument to rank() is null");
            return rank(key, root);
        }

        // Number of keys in the subtree less than key.
        private int rank(Point2D key, Node x) {
            if (x == null) return 0;
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) return rank(key, x.left);
            else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
            else              return size(x.left);
        }

        /**
         * Returns all keys in the symbol table in ascending order,
         * as an {@code Iterable}.
         * To iterate over all of the keys in the symbol table named {@code st},
         * use the foreach notation: {@code for (Key key : st.keys())}.
         *
         * @return all keys in the symbol table in ascending order
         */
        public Iterable<Point2D> keys() {
            if (isEmpty()) return new Queue<Point2D>();
            return keys(min(), max());
        }

        public Point2D min() {
            if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
            return min(root).key;
        }

        private Node min(Node x) {
            if (x.left == null) return x;
            else                return min(x.left);
        }

        /**
         * Returns the largest key in the symbol table.
         *
         * @return the largest key in the symbol table
         * @throws NoSuchElementException if the symbol table is empty
         */
        public Point2D max() {
            if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
            return max(root).key;
        }

        private Node max(Node x) {
            if (x.right == null) return x;
            else                 return max(x.right);
        }

        public Iterable<Point2D> keys(Point2D lo, Point2D hi) {
            if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
            if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

            Queue<Point2D> queue = new Queue<Point2D>();
            keys(root, queue, lo, hi);
            return queue;
        }

        private void keys(Node x, Queue<Point2D> queue, Point2D lo, Point2D hi) {
            if (x == null) return;
            int cmplo = lo.compareTo(x.key);
            int cmphi = hi.compareTo(x.key);
            if (cmplo < 0) keys(x.left, queue, lo, hi);
            if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
            if (cmphi > 0) keys(x.right, queue, lo, hi);
        }

        /**
         * Returns the number of keys in the symbol table in the given range.
         *
         * @param  lo minimum endpoint
         * @param  hi maximum endpoint
         * @return the number of keys in the symbol table between {@code lo}
         *         (inclusive) and {@code hi} (inclusive)
         * @throws IllegalArgumentException if either {@code lo} or {@code hi}
         *         is {@code null}
         */
        public int size(Point2D lo, Point2D hi) {
            if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
            if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

            if (lo.compareTo(hi) > 0) return 0;
            if (contains(hi)) return rank(hi) - rank(lo) + 1;
            else              return rank(hi) - rank(lo);
        }

        public Point2D ceiling(Point2D key) {
            if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
            if (isEmpty()) throw new NoSuchElementException("calls ceiling() with empty symbol table");
            Node x = ceiling(root, key);
            if (x == null) throw new NoSuchElementException("argument to ceiling() is too large");
            else return x.key;
        }

        private Node ceiling(Node x, Point2D key) {
            if (x == null) return null;
            int cmp = key.compareTo(x.key);
            if (cmp == 0) return x;
            if (cmp < 0) {
                Node t = ceiling(x.left, key);
                if (t != null) return t;
                else return x;
            }
            return ceiling(x.right, key);
        }

        private class Node {
            private Point2D key;           // sorted by key
            private Node left, right;  // left and right subtrees
            private int size;          // number of nodes in subtree

            public Node(Point2D key, int size) {
                this.key = key;
                this.size = size;
            }
        }

    }
}
