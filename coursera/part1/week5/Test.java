import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Iterator;

/**
* this is not a part of assignment.
*/
public class Test {

    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        PointSET pointSET = new PointSET();
        In in = new In(args[0]);
        int n = in.readInt();
        for (int i = 0; i < n; i++) {
            Point2D input = new Point2D(in.readDouble(), in.readDouble());
            kdTree.insert(input);
            pointSET.insert(input);
        }

        RectHV rectHV = new RectHV(0, 0.125,0.625, 0.875);
        Iterator<Point2D> iterator = kdTree.range(rectHV).iterator();
       // while (iterator.hasNext()) {
      //      System.out.println(iterator.next());
      //  }

        Point2D nearest = kdTree.nearest(new Point2D(0.476, 0.537));
        System.out.println(nearest);
       // nearest = pointSET.nearest(new Point2D(0.75, 0.5));
       // System.out.println(nearest);
    }
}
