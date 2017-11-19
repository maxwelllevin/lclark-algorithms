import edu.princeton.cs.algs4.*;


public class SpaceDisagreement {

	/** The number of stars in our system. */
	public int numStar;

	/** The x & y position of each star. */
	public double[][] pts;

	/** Preserve choke point information. */
	public boolean[] chokePointMarker;

	/** Mathematical representation of our system. */
	public Graph graph;

	
	/**	Constructor. Takes integer argument for number of stars in the system. Sets
	 * 	up graphics, generates random points, connects nearby stars, finds
	 * 	chokepoints, draws stars and edges to buffer, and draws the buffer to the
	 * 	screen.
	 */
	public SpaceDisagreement(int n) {
		numStar = n;
		pts = new double[2][numStar];
		graph = new Graph(numStar);
		chokePointMarker = new boolean[numStar];
		configureWindow();
		drawSpaceNoise(1200);
		generatePoints();
		drawNearbyEdges();
		findChokePoints();
		drawStars();
		StdDraw.show();
		//StdDraw.save("starmap.png");
	}

	
	/** Sets up the initial graphics */
	public void configureWindow() {
		StdDraw.enableDoubleBuffering();
		StdDraw.setCanvasSize(800, 800);
		StdDraw.setPenColor(0, 0, 10);
		StdDraw.filledRectangle(0.5, 0.5, 0.5, 0.5);

	}
	
	
	/** Draws small stars in the background to simulate distant stars and galaxies. */
	private void drawSpaceNoise(int numNoiseStars) {
		for (int i = 0; i < numNoiseStars; i++) {
			double radius = StdRandom.uniform(0.00001, 0.002);
			int r = StdRandom.uniform(120, 255);
			int g = StdRandom.uniform(60, 200);
			int b = StdRandom.uniform(90, 255);
			double x = StdRandom.uniform(0.01, .99);
			double y = StdRandom.uniform(0.01, .99);
			StdDraw.setPenColor(r, g, b);
			StdDraw.filledCircle(x, y, radius);
		}
	}

	
	/** Generate random X & Y coords for star locations */
	private void generatePoints() {
		for (int i = 0; i < numStar; i++) {
			pts[0][i] = StdRandom.uniform(0.01, .99);
			pts[1][i] = StdRandom.uniform(0.01, .99);
		}
	}

	
	/** Find nearby vertices and draw connecting edges. */
	private void drawNearbyEdges() {
		StdDraw.setPenColor(StdDraw.WHITE);
		for (int i = 0; i < numStar; i++) {
			for (int j = 0; j < numStar; j++) {
				if (i == j) {
					continue;
				} else if (Math.hypot((pts[0][i] - pts[0][j]), (pts[1][i] - pts[1][j])) < .15) {
					graph.addEdge(i, j);
					StdDraw.setPenRadius(0.001);
					StdDraw.line(pts[0][i], pts[1][i], pts[0][j], pts[1][j]);
				}
			}
		}
	}

	
	/** Find choke points using Biconnected.java */
	private void findChokePoints() {
		Biconnected ourGraph = new Biconnected(graph);
		for (int i = 0; i < numStar; i++) {
			if (ourGraph.isArticulation(i)) {
				chokePointMarker[i] = true;
			}
		}
	}

	
	/** Draws all stars. Colors star red if it is an articulation point, white
	 * 	otherwise. Does some fancy shading. */
	private void drawStars() {
		for (int i = 0; i < numStar; i++) {
			if (chokePointMarker[i]) {
				int c = 0;
				StdDraw.setPenColor(c, 0, c);
				for (double radius = 0.008, rot = 0; radius > 0; radius -= 0.0001, c += 5, rot += 2 * Math.PI / 90) {
					if (c > 255) {
						c = 255;
					}
					drawStarPoint(pts[0][i], pts[1][i], radius, rot);
					StdDraw.setPenColor(0, c, c);
				}
			} else {
				int c = 0;
				StdDraw.setPenColor(c, c, c);
				for (double radius = 0.008, rot = 0; radius > 0; radius -= 0.0001, c += 5, rot += 2 * Math.PI / 90) {
					if (c > 255) {
						c = 255;
					}
					drawStarPoint(pts[0][i], pts[1][i], radius, rot);
					StdDraw.setPenColor(c, c, c);
				}
			}
		}
	}

	
	/** Draws part of a star. Used by drawStars() */
	private void drawStarPoint(double x, double y, double r, double rot) {
		double xP[] = new double[3];
		double yP[] = new double[3];
		xP[0] = r * Math.cos(rot) + x;
		xP[1] = r * Math.cos(rot + 2 * Math.PI / 3) + x;
		xP[2] = r * Math.cos(rot + 4 * Math.PI / 3) + x;
		yP[0] = r * Math.sin(rot) + y;
		yP[1] = r * Math.sin(rot + 2 * Math.PI / 3) + y;
		yP[2] = r * Math.sin(rot + 4 * Math.PI / 3) + y;
		StdDraw.polygon(xP, yP);
	}

	
	/** Main program. Constructs SpaceDisagreement */
	public static void main(String[] args) {
		new SpaceDisagreement(50);
	}
}
