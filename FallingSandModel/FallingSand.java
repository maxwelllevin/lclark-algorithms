
//Falling sand with appropriate modifications by Journie Ma-Johnson and Maxwell Levin
import java.awt.Color;

/** GUI for the Falling Sand toy. */
public class FallingSand {

	/** Names of the buttons. */
	private static final String[] BUTTON_NAMES = { "Empty", "Metal", "Sand", "Water", "Mercury", "Lava" };

	/** Colors of the various particle types. */
	private static final Color[] COLORS = { StdDraw.WHITE, StdDraw.LIGHT_GRAY, StdDraw.YELLOW, StdDraw.BLUE,
			StdDraw.MAGENTA, StdDraw.RED, StdDraw.BLACK };

	/** Number of simulation steps per screen redraw. */
	private static final int SPEED = 1000;

	public static void main(String[] args) {
		new FallingSand().run();
	}

	/** The model behind this GUI. */
	private FallingSandModel model;

	public FallingSand() {
		model = new FallingSandModel();
	}

	/** Sets the window size. */
	public void configureWindow() {
		StdDraw.enableDoubleBuffering();
		StdDraw.setCanvasSize(600, 400);
		StdDraw.setXscale(0, FallingSandModel.MODEL_SIZE * 1.5);
		StdDraw.setYscale(0, FallingSandModel.MODEL_SIZE);
	}

	/** Draws the current state of the simulation. */
	public void draw() {
		StdDraw.clear();
		drawDiagram();
		drawButtons();
		StdDraw.show();
	}

	/** Draws the buttons. */
	public void drawButtons() {
		int n = BUTTON_NAMES.length;
		int s = FallingSandModel.MODEL_SIZE;
		for (int i = 0; i < BUTTON_NAMES.length; i++) {
			int y = s * (i + 1) / (n + 1);
			StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
			StdDraw.filledRectangle(s * 1.25, y, s / 5, s / (3 * (n + 1)));
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.text(s * 1.25, y, BUTTON_NAMES[i]);
			if (i == model.getMode()) {
				StdDraw.rectangle(s * 1.25, y, s / 5, s / (3 * (n + 1)));
			}
		}
	}

	/** Draws the diagram showing the current state of the model. */
	public void drawDiagram() {
		StdDraw.setPenColor(StdDraw.WHITE);
		int s = FallingSandModel.MODEL_SIZE;
		StdDraw.filledRectangle(s / 2, s / 2, s / 2, s / 2);
		for (int x = 0; x < s; x++) {
			for (int y = 0; y < s; y++) {
				int particle = model.get(x, y);
				if (particle > 0) {
					StdDraw.setPenColor(COLORS[particle]);
					StdDraw.filledRectangle(x, y, 0.5, 0.5);

				}
			}
		}
	}

	/** Runs the simulation. */

	public void run() {
		configureWindow();
		while (true) {
			int x = (int) Math.round(StdDraw.mouseX());
			int y = (int) Math.round(StdDraw.mouseY());
			if (StdDraw.isMousePressed()) {
				if (model.inGrid(x, y)) {
					model.placeParticle(x, y);
				} else {
					model.setMode((int) (y * BUTTON_NAMES.length / (double) FallingSandModel.MODEL_SIZE));
				}
			}
			for (int i = 0; i < SPEED; i++) {
				model.step();
			}
			draw();
		}
	}

}
