import edu.princeton.cs.algs4.StdDraw;

public class Point2D implements Comparable<Point2D> {

    double x;
    double y;
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public  double x() {
        return x;
    }                             // x-coordinate
    public  double y() {
        return y;
    }                             // y-coordinate
    public  double distanceTo(Point2D that) {
        return Math.sqrt((x - that.x)*(x - that.x)) + Math.sqrt((y - that.y)*(y - that.y));
    }         // Euclidean distance between two points
    public  double distanceSquaredTo(Point2D that) {
        return (x - that.x)*(x - that.x) +(y - that.y)*(y - that.y);
    } // square of Euclidean distance between two points
    public     int compareTo(Point2D that) {
        if (that == null) throw new IllegalArgumentException();
        if (x == that.x && y == that.y) return 0;
        if (x > that.x) return 1;
        if (x < that.x) return -1;
        if (y > that.y) return 1;
        if (y < that.y) return -1;
        return 0;
    }         // for use in an ordered symbol table
    public boolean equals(Object that) {
        if (that == null) throw new IllegalArgumentException();
        Point2D t = (Point2D) that;
        if (compareTo(t) > 0) return false;
        return true;
    }             // does this point equal that object?
    public    void draw() {
        StdDraw.setCanvasSize(100, 100);
        StdDraw.enableDoubleBuffering();
        StdDraw.mouseX();
        StdDraw.mouseY();
        StdDraw.point(x, y);
        StdDraw.show();
    }                          // draw to standard draw
    public  String toString() {
        return "(" + String.valueOf(x) + ", " + String.valueOf(y) +")";
    }                      // string representation
    public static void main(String[] args) {
        Point2D p = new Point2D(2,3);
        p.draw();
    }
}
