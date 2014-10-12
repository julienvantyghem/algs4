import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class KdTree {

	private static class KdNode {
		final RectHV container;
		Point2D key;
		KdNode left;
		KdNode right;
		int size;

		KdNode(RectHV container) {
			this.container = container;
		}
	}

	private static enum Dimension {
		X, Y;
		Dimension next() {
			switch (this) {
			case X:
				return Y;
			case Y:
				return X;
			}
			return null;
		}

		int compare(Point2D p1, Point2D p2) {
			switch (this) {
			case X:
				return ((Double) p1.x()).compareTo((Double) p2.x());
			case Y:
				return ((Double) p1.y()).compareTo((Double) p2.y());
			}
			return 0;
		}

		RectHV getNextLeftContainer(RectHV previousRectangle, Point2D p) {
			switch (this) {
			case X:
				return new RectHV(previousRectangle.xmin(),
						previousRectangle.ymin(), p.x(),
						previousRectangle.ymax());
			case Y:
				return new RectHV(previousRectangle.xmin(),
						previousRectangle.ymin(), previousRectangle.xmax(),
						p.y());
			}
			return null;
		}

		RectHV getNextRightContainer(RectHV previousRectangle, Point2D p) {
			switch (this) {
			case X:
				return new RectHV(p.x(), previousRectangle.ymin(),
						previousRectangle.xmax(), previousRectangle.ymax());
			case Y:
				return new RectHV(previousRectangle.xmin(), p.y(),
						previousRectangle.xmax(), previousRectangle.ymax());
			}
			return null;
		}
	}

	private KdNode root;
	private Point2D nearestPoint;
	private double minSquaredDistance;

	public KdTree() {
		root = new KdNode(new RectHV(0, 0, 1, 1));
	}

	public boolean isEmpty() {
		return isEmpty(root);
	}

	private boolean isEmpty(KdNode node) {
		return node.key == null;
	}

	public int size() {
		return size(root);
	}

	private int size(KdNode node) {
		if (isEmpty(node))
			return 0;
		return node.size;
	}

	public void insert(Point2D p) {
		root = insertInto(root, p, Dimension.X);
	}

	private KdNode insertInto(KdNode node, Point2D key, Dimension d) {
		if (!isEmpty(node)) {
			int cmp = d.compare(key, node.key);
			if (cmp < 0)
				node.left = insertInto(node.left, key, d.next());
			else if (cmp > 0 || !key.equals(node.key))
				node.right = insertInto(node.right, key, d.next());
		} else {
			node.key = key;
			node.left = new KdNode(d.getNextLeftContainer(node.container, key));
			node.right = new KdNode(
					d.getNextRightContainer(node.container, key));
		}
		node.size = 1 + size(node.left) + size(node.right);
		return node;
	}

	public boolean contains(Point2D p) {
		return contains(root, p, Dimension.X);
	}

	private boolean contains(KdNode node, Point2D p, Dimension d) {
		if (isEmpty(node))
			return false;
		int cmp = d.compare(p, node.key);
		if (cmp < 0)
			return contains(node.left, p, d.next());
		if (cmp > 0 || !p.equals(node.key))
			return contains(node.right, p, d.next());

		return true;
	}

	public void draw() {
		StdDraw.rectangle(0.5, 0.5, 0.5, 0.5);
		drawNodeAndLineWithinContainer(root, Dimension.X);
	}

	private void drawNodeAndLineWithinContainer(KdNode node, Dimension d) {
		if (isEmpty(node))
			return;
		StdDraw.setPenRadius(0.01);
		StdDraw.setPenColor(Color.BLACK);
		double x = node.key.x();
		double y = node.key.y();
		StdDraw.point(x, y);
		RectHV rect = node.container;
		switch (d) {
		case X:
			StdDraw.setPenRadius(0.005);
			StdDraw.setPenColor(Color.RED);
			StdDraw.line(x, rect.ymin(), x, rect.ymax());
			drawNodeAndLineWithinContainer(node.left, Dimension.Y);
			drawNodeAndLineWithinContainer(node.right, Dimension.Y);
			break;
		case Y:
			StdDraw.setPenRadius(0.005);
			StdDraw.setPenColor(Color.BLUE);
			StdDraw.line(rect.xmin(), y, rect.xmax(), y);
			drawNodeAndLineWithinContainer(node.left, Dimension.X);
			drawNodeAndLineWithinContainer(node.right, Dimension.X);
			break;
		}
	}

	public Iterable<Point2D> range(RectHV rect) {
		Queue<Point2D> q = new Queue<>();
		accumulateIntersectionWithNode(rect, root, q);
		return q;
	}

	private void accumulateIntersectionWithNode(RectHV rect, KdNode node,
			Queue<Point2D> q) {
		if (!rect.intersects(node.container) || isEmpty(node))
			return;
		accumulateIntersectionWithNode(rect, node.left, q);
		if (rect.contains(node.key))
			q.enqueue(node.key);
		accumulateIntersectionWithNode(rect, node.right, q);
	}

	public Point2D nearest(Point2D p) {
		updateNearest(p, root.key);
		updateNearestWithPointsInNode(p, root, Dimension.X);
		return nearestPoint;
	}

	private void updateNearest(Point2D p, Point2D nearest) {
		nearestPoint = nearest;
		minSquaredDistance = nearest.distanceSquaredTo(p);
	}

	private void updateNearestWithPointsInNode(Point2D p, KdNode node,
			Dimension d) {
		if (isEmpty(node))
			return;
		if (node.key.distanceSquaredTo(p) < minSquaredDistance) {
			updateNearest(p, node.key);
		}
		int cmp = d.compare(p, node.key);
		if (cmp < 0) {
			if (node.left.container.distanceSquaredTo(p) < minSquaredDistance) {
				updateNearestWithPointsInNode(p, node.left, d.next());
			}
			if (node.right.container.distanceSquaredTo(p) < minSquaredDistance) {
				updateNearestWithPointsInNode(p, node.right, d.next());
			}
		} else {
			if (node.right.container.distanceSquaredTo(p) < minSquaredDistance) {
				updateNearestWithPointsInNode(p, node.right, d.next());
			}
			if (node.left.container.distanceSquaredTo(p) < minSquaredDistance) {
				updateNearestWithPointsInNode(p, node.left, d.next());
			}
		}
	}

	public static void main(String[] args) {
		String filename = args[0];
		In in = new In(filename);
		// initialize the data structures with N points from standard input
		KdTree kdtree = new KdTree();
		List<Point2D> points = new ArrayList<>();
		while (!in.isEmpty()) {
			double x = in.readDouble();
			double y = in.readDouble();
			Point2D p = new Point2D(x, y);
			kdtree.insert(p);
			points.add(p);
		}
		System.out.println(kdtree.nearest(new Point2D(0.5, 0.95)));
		System.out.println(kdtree.size());
		for(Point2D p : points){
			kdtree.insert(p);
		}
		System.out.println(kdtree.size());
		kdtree.insert(new Point2D(StdRandom.uniform(),StdRandom.uniform()));
		System.out.println(kdtree.size());
	}

}
