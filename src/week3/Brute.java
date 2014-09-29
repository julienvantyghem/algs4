import java.util.Arrays;

public class Brute {

    private static Point[] readPoints(String file){
        In in = new In(file);
        int n = in.readInt();
        Point[] points = new Point[n];
        int i = 0;
        while(i < n){
            points[i++] = new Point(in.readInt(), in.readInt());
        }
        return points;
    }

    private static boolean areAligned(Point p, Point q, Point r, Point s){
        double p_q = p.slopeTo(q);
        double p_r = p.slopeTo(r);
        double p_s = p.slopeTo(s);
        if(p_q != p_r) return false;
        if(p_r != p_s) return false;
        return true;
    }

    public static void main(String[] args){
        Point[] points = readPoints(args[0]);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for(Point p : points){
            p.draw();
        }
        int n = points.length;
        for(int i = 0; i<n-3; i++){
            for(int j = i+1; j<n-2; j++){
                for(int k = j+1; k<n-1; k++){
                    for(int l = k+1; l<n; l++){
                        if(areAligned(points[i], points[j], points[k], points[l])){
                            Point[] alignedPoints = {points[i], points[j], points[k], points[l]};
                            Arrays.sort(alignedPoints);
                            System.out.println(alignedPoints[0] + " -> " + alignedPoints[1] + " -> " + alignedPoints[2] + " -> " + alignedPoints[3]);
                            alignedPoints[0].drawTo(alignedPoints[3]);
                        }
                    }
                }
            }
        }
    }
}
