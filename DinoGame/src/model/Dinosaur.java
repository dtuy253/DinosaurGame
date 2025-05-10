package objects;

import graphics.SpriteAnimated;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;

public class Dinosaur extends GameObject {
	private static final double X = 120;
	private static final double Y = 420;
	private static final double Y_DUCK = 450;
	private static final double JUMP_SPEED = 1000; // pixel / s
	private static final double GRAVITY = 25;

	private static final String[] DUCKING = { "file:assets/Dino/DinoDuck1.png", "file:assets/Dino/DinoDuck2.png" };
	private static final String[] RUNNING = { "file:assets/Dino/DinoRun1.png", "file:assets/Dino/DinoRun2.png" };
	private static final String[] JUMPING = { "file:assets/Dino/DinoJump.png" };
	private static final String DEAD = "file:assets/Dino/DinoDead.png";

	private SpriteAnimated duckAnim;
	private SpriteAnimated runAnim;
	private SpriteAnimated jumpAnim;

	private boolean duck;
	private boolean run;
	private boolean jump;

	private double jumpV;
	private ImageView imageView;

	private double y;

	private AudioClip jumpSound;
	private AudioClip deathSound;

	// Constructor
	public Dinosaur(Pane root) {
		super(root);

		this.duckAnim = new SpriteAnimated(root, DUCKING, 70.0);
		this.runAnim = new SpriteAnimated(root, RUNNING, 70.0);
		this.jumpAnim = new SpriteAnimated(root, JUMPING, 0.4);

		this.jumpSound = new AudioClip("file:assets/sounds/fap.wav");
		this.deathSound = new AudioClip("file:assets/sounds/fall.wav");

		this.duck = false;
		this.run = true;
		this.jump = false;

		this.jumpV = JUMP_SPEED;

		this.imageView = new ImageView(runAnim.getImage());
		this.imageView.setLayoutX(X);
		this.imageView.setLayoutY(Y);
		this.y = Y;
		root.getChildren().add(imageView);
	}

	@Override
	public void update(double deltaTime) {
		// Xử lý sự kiện phím ngay trong update()
		if (jump) {
			jumpAnim.update(deltaTime);
			imageView.setImage(jumpAnim.getImage());

			y -= jumpV * deltaTime / 1000;
			jumpV -= GRAVITY;

			imageView.setLayoutY(y);

			// Nếu đã nhảy xong, trở về trạng thái chạy
			if (y >= Y) {
				jump = false;
				jumpV = JUMP_SPEED;
				imageView.setLayoutY(Y);
				run = true; // Quay lại trạng thái chạy khi nhảy xong
			}
		} else if (duck) {
			duckAnim.update(deltaTime);
			imageView.setImage(duckAnim.getImage());
			imageView.setLayoutY(Y_DUCK);
			y = Y_DUCK;

		} else if (run) {
			runAnim.update(deltaTime);
			imageView.setImage(runAnim.getImage());
			imageView.setLayoutY(Y);
			y = Y;
		}

	}

	// Phương thức xử lý sự kiện phím (UP/DOWN) trong update()
	public void handleKeyPress(KeyCode key) {
		// Chỉ xử lý khi không đang nhảy và không đang cúi
		if (!jump && !duck) {
			if (key == KeyCode.UP || key == KeyCode.SPACE) {
				// Bắt đầu nhảy
				jump = true;
				run = false;
				if (jumpSound != null) {
					jumpSound.play();
				}
			} else if (key == KeyCode.DOWN) {
				// Bắt đầu cúi
				duck = true;
				run = false;
				if (jumpSound != null) {
					jumpSound.play();
				}
			}
		}

	}

	// Phương thức xử lý khi thả phím
	public void handleKeyRelease(KeyCode key) {
		if (key == KeyCode.DOWN) {
			// Khi thả phím DOWN, trở lại trạng thái chạy
			duck = false;
			run = true;
		}
	}

	@Override
	public void draw() {
		imageView.setLayoutX(this.imageView.getLayoutX());
		imageView.setLayoutY(this.imageView.getLayoutY());
		root.getChildren().add(imageView);
	}

	public void setPos(double x, double y) {
		imageView.setLayoutX(x);
		imageView.setLayoutY(y);
	}

	public javafx.scene.image.Image getImage() {
		return imageView.getImage();
	}

	public javafx.geometry.Bounds getBounds() {
		return imageView.getBoundsInParent();
	}

	public javafx.geometry.Point2D getPos() {
		return new javafx.geometry.Point2D(imageView.getLayoutX(), imageView.getLayoutY());
	}

	// Thêm phương thức getWidth và getHeight
	public double getWidth() {
		return imageView.getBoundsInParent().getWidth();
	}

	public double getHeight() {
		return imageView.getBoundsInParent().getHeight();
	}

	public void setDeadImage() {
		imageView.setImage(new javafx.scene.image.Image(DEAD));
	}

	public void getDeadSound() {
		if (deathSound != null) {
			deathSound.play();
		}
	}
}
