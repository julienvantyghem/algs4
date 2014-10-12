public class PointSET {

	private final RedBlackBST<Point2D, Point2D> rbBST;

	public PointSET() {
		this.rbBST = new RedBlackBST<>();
	}

	public boolean isEmpty() {
		return rbBST.isEmpty();
	}

	public int size() {
		return rbBST.size();
	}

	public void insert(Point2D p) {
		rbBST.put(p, p);
	}

	public boolean contains(Point2D p) {
		return rbBST.contains(p);
	}

	public void draw() {
		StdDraw.setScale(0, 1);
		StdDraw.setPenRadius(0.01);
		for (Point2D p : rbBST.keys()) {
			StdDraw.point(p.x(), p.y());
		}
	}

	public Iterable<Point2D> range(RectHV rect) {
		Queue<Point2D> q = new Queue<>();
		for (Point2D p : rbBST.keys()) {
			if (p.x() >= rect.xmin() && p.x() <= rect.xmax() && p.y() >= rect.ymin() && p.y() <= rect.ymax()) {
				q.enqueue(p);
			}
		}
		return q;
	}

	public Point2D nearest(Point2D p) {
		double minDistance = Double.POSITIVE_INFINITY;
		Point2D nearest = null;
		for (Point2D current : rbBST.keys()) {
			double currentSquaredDistance = current.distanceTo(p);
			if (currentSquaredDistance < minDistance) {
				minDistance = currentSquaredDistance;
				nearest = current;
			}
		}
		return nearest;
	}

	public static void main(String[] args) {
		PointSET ps = new PointSET();
		ps.insert(new Point2D(0.1, 0.1));
		ps.insert(new Point2D(0.2, 0.2));
		ps.insert(new Point2D(0.7, 0.1));
		ps.draw();
	}

}
