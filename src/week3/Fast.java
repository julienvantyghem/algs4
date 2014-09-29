import java.util.Arrays;

public class Fast {

    private static Point[] readPoints(String file) {
        In in = new In(file);
        int n = in.readInt();
        Point[] points = new Point[n];
        int i = 0;
        while (i < n) {
            points[i++] = new Point(in.readInt(), in.readInt());
        }
        return points;
    }

    public static void main(String[] args) {
        Point[] points = readPoints(args[0]);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        int n = points.length;
        Point[] aux = new Point[n];
        for (int i = 0; i < n; i++) {
            Point p = points[i];
            copyArray(points, aux);
            Arrays.sort(aux, points[i].SLOPE_ORDER);
            int j = 0;
            while (j < n) {
                int firstInSeq = j;
                double currentSlope = p.slopeTo(aux[j++]);
                while (j < n && p.slopeTo(aux[j]) == currentSlope) {
                    j++;
                }
                int lastInSeq = j - 1;
                if (lastInSeq - firstInSeq >= 3) {
                    outputAlignedPoints(aux, firstInSeq, lastInSeq);
                }
            }
        }
    }

    private static void outputAlignedPoints(Point[] pointsSortedBySlope, int first, int last) {
        int sequenceLength = last - first + 1;
        Point[] alignedPoints = new Point[sequenceLength];
        for (int k = 0; k < sequenceLength; k++) {
            alignedPoints[k] = pointsSortedBySlope[first + k];
        }
        Arrays.sort(alignedPoints);
        StringBuilder sb = new StringBuilder("" + alignedPoints[0]);
        for (int k = 1; k < sequenceLength; k++) {
            sb.append(" -> " + alignedPoints[k]);
        }
        System.out.println(sb.toString());
        alignedPoints[0].drawTo(alignedPoints[sequenceLength - 1]);
    }

    private static void copyArray(Point[] src, Point[] dst) {
        for (int i = 0; i < src.length; i++) {
            dst[i] = src[i];
        }
    }

}
