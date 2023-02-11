import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import edu.princeton.cs.algs4.Point2D;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> points;

    public PointSET() {
        points = new TreeSet<>();
    }                              // construct an empty set of points

    public static void main(String[] args) {

    }                 // unit testing of the methods (optional)

    public boolean isEmpty() {
        return points.isEmpty();
    }                      // is the set empty?

    public int size() {
        return points.size();
    }                        // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        points.add(p);
    }             // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return points.contains(p);
    }          // does the set contain point p?

    public void draw() {
        for (Point2D point : points) {
            StdDraw.point(point.x(), point.y());
        }
    }                        // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        TreeSet<Point2D> residents = new TreeSet<>();
        for (Point2D point : points) {
            if (rect.contains(point)) residents.add(point);
        }
        return residents;
    }            // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (points.isEmpty()) return null;
        if (points.contains(p)) return p;

        double dist = -1;
        Point2D result = null;
        for (Point2D point : points.descendingSet()) {
            double newDist = point.distanceSquaredTo(p);
            if (dist == -1 || dist >= newDist) {
                dist = newDist;
                result = point;
            }
        }

        return result;
    }            // a nearest neighbor in the set to point p; null if the set is empty
}