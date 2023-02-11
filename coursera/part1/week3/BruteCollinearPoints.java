import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points)  {
        if (points == null) throw new IllegalArgumentException();

        this.lineSegments = new LineSegment[points.length];
        int lineSegmentsIndex = 0;

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();

           // OrderInfo[] orderInfos = new OrderInfo[points.length-i-1];
            Point[] orderPoint = new Point[points.length-i-1];
            for (int i2 = i + 1; i2 < points.length; i2++) {
                if (points[i2] == null) throw new IllegalArgumentException();

               // OrderInfo orderInfo = new OrderInfo(points[i2]);
               // orderInfo.slope = points[i].slopeTo(points[i2]);
               // orderInfos[i2-i-1] = orderInfo;
                orderPoint[i2-i-1] = points[i2];
            }

           // Arrays.sort(orderInfos);
            Arrays.sort(orderPoint, points[i].slopeOrder());

            // line segment set consist of 4 point

            double slope = 0;
            int collinearCount = 0;
            Point[] pointBuffer = new Point[4];
            Point lastPoint = null;
            for (int i2 = 0; i2 < orderPoint.length; i2++) {
                if (orderPoint[i2].compareTo(points[i]) == 0 || (i2 > 0 && orderPoint[i2-1].compareTo(orderPoint[i2]) == 0)) {
                    throw new IllegalArgumentException();
                }
                double i2Slope = points[i].slopeTo(orderPoint[i2]);
                if (Double.compare(i2Slope, slope) != 0) {
                    // buffer clear
                    if (pointBuffer[pointBuffer.length-2] != null) {
                        pointBuffer[pointBuffer.length-1] = points[i];
                        Arrays.sort(pointBuffer);

                        lineSegments[lineSegmentsIndex++] = new LineSegment(pointBuffer[0], pointBuffer[pointBuffer.length-1]);
                        pointBuffer = new Point[4];
                    }
                    slope = i2Slope;
                    pointBuffer[0] = orderPoint[i2];
                    collinearCount = 1;
                } else {
                    pointBuffer[collinearCount] = orderPoint[i2];
                    collinearCount++;
                }
            }
            /*
            double slope = 0;
            int collinearCount = 0;
            Point[] pointBuffer = new Point[4];
            Point lastPoint = null;
            for (int i2 = 0; i2 < orderInfos.length; i2++) {
                if (orderInfos[i2].p.compareTo(points[i]) == 0 || (i2 > 0 && orderInfos[i2-1].p.compareTo(orderInfos[i2].p) == 0)) {
                    throw new IllegalArgumentException();
                }
                if (Double.compare(orderInfos[i2].slope, slope) != 0) {
                    // buffer clear
                    if (pointBuffer[pointBuffer.length-2] != null) {
                        pointBuffer[pointBuffer.length-1] = points[i];
                        Arrays.sort(pointBuffer);

                        lineSegments[lineSegmentsIndex++] = new LineSegment(pointBuffer[0], pointBuffer[pointBuffer.length-1]);
                        pointBuffer = new Point[4];
                    }
                    slope = orderInfos[i2].slope;
                    pointBuffer[0] = orderInfos[i2].p;
                    collinearCount = 1;
                } else {
                    pointBuffer[collinearCount] = orderInfos[i2].p;
                    collinearCount++;
                }
            }
*/
            // buffer clear
            if (pointBuffer[pointBuffer.length-2] != null) {
                pointBuffer[pointBuffer.length-1] = points[i];
                Arrays.sort(pointBuffer);

                lineSegments[lineSegmentsIndex++] = new LineSegment(pointBuffer[0], pointBuffer[pointBuffer.length-1]);
                       /* for (int j = 0; j < pointBuffer.length-1; j++) {
                            lineSegments[lineSegmentsIndex++] = new LineSegment(pointBuffer[j], pointBuffer[j+1]);
                            pointBuffer[j] = null;
                        }*/
            }

        }

        if (lineSegmentsIndex > 0 && lineSegmentsIndex < lineSegments.length-1) {
            LineSegment[] newSegment = new LineSegment[lineSegmentsIndex];
            for (int i = 0; i < newSegment.length; i++) {
                newSegment[i] = lineSegments[i];
            }
            lineSegments = newSegment;
        } else if (lineSegmentsIndex == 0) {
            lineSegments = new LineSegment[]{};
        }
    }   // finds all line segments containing 4 points

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.01);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    public  int numberOfSegments() {
        return lineSegments.length;
    }       // the number of line segments

    public LineSegment[] segments() {
        LineSegment[] copySegments = new LineSegment[lineSegments.length];
        for (int i = 0; i < lineSegments.length; i++) {
            copySegments[i] = lineSegments[i];
        }
        return copySegments;
    }               // the line segments

    /*
    private class OrderInfo implements Comparable<OrderInfo> {
        Point p;
        double slope;
        public OrderInfo (Point p) {
            this.p = p;
        }

        @Override
        public int compareTo(OrderInfo that) {
            int doubleCompare = Double.compare(slope, that.slope);
            if (doubleCompare == 0) {
                return p.compareTo(that.p);
            } else if (doubleCompare == -1) return -1;
            return 1;
        }
    }
    */
}