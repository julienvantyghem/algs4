import com.sun.istack.internal.NotNull;

import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
            double slope1 = slopeTo(o1);
            double slope2 = slopeTo(o2);
            if (slope1 > slope2) {
                return 1;
            } else if (slope1 < slope2) {
                return -1;
            } else {
                return 0;
            }
        }
    };

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if(this.compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
        if(that.x == this.x) return Double.POSITIVE_INFINITY;
        return (that.y - this.y) / (double) (that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if(this.y > that.y) return 1;
        else if(this.y < that.y) return -1;
        else if(this.x > that.x) return 1;
        else if(this.x < that.x) return -1;
        else return 0;
    }

    // return string representation of this point
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        Point p0 = new Point(0,0);
        Point p1 = new Point(1,0);
        Point p2 = new Point(0,1);

        assert p0.compareTo(p1) < 0;
        assert p1.compareTo(p2) < 0;
        assert p0.compareTo(p2) < 0;

        assert p0.slopeTo(p1) == 0;
        assert p1.slopeTo(p2) == -1;
        assert p0.slopeTo(p2) == Double.POSITIVE_INFINITY;
        assert p1.slopeTo(p1) == Double.NEGATIVE_INFINITY;

        Point[] points = {p2, p1};
        Arrays.sort(points, p0.SLOPE_ORDER);
        assert points[0] == p1;
        assert points[1] == p2;

    }
}