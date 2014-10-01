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
            copyArray(points, aux);
            Point p = points[i];
            Arrays.sort(aux, p.SLOPE_ORDER);
            int j = 1;
            while (j < n) {
                int firstInSeq = j;
                double currentSlope = p.slopeTo(aux[j++]);
                int sequenceLength = 1;
                while (j < n && p.slopeTo(aux[j]) == currentSlope) {
                    sequenceLength++;
                    j++;
                }
                if (sequenceLength >= 3) {
                    outputAlignedPoints(aux, firstInSeq, sequenceLength);
                }
            }
        }
    }

    private static void outputAlignedPoints(Point[] pointsSortedBySlope, int first, int sequenceLength) {
        int nAlignedPoints = sequenceLength + 1;
        Point[] alignedPoints = new Point[nAlignedPoints];
        Point originPoint = pointsSortedBySlope[0];
        alignedPoints[0] = originPoint;
        for (int k = 0; k < sequenceLength; k++) {
            alignedPoints[1 + k] = pointsSortedBySlope[first + k];
        }
        Arrays.sort(alignedPoints);
        if (originPoint.compareTo(alignedPoints[0]) != 0) return;
        StringBuilder sb = new StringBuilder("" + alignedPoints[0]);
        for (int k = 1; k < nAlignedPoints; k++) {
            sb.append(" -> " + alignedPoints[k]);
        }
        System.out.println(sb.toString());
        alignedPoints[0].drawTo(alignedPoints[nAlignedPoints - 1]);
    }

    private static void copyArray(Point[] src, Point[] dst) {
        for (int i = 0; i < src.length; i++) {
            dst[i] = src[i];
        }
    }

}
