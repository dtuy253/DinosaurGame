package model;

import java.util.List;

import controller.GameConfig;
import game.Game;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Obstacle extends GameObject {
	private Image[] images;
	private int type;
	protected double x, y;
	protected ImageView imageView;
	protected Rectangle bounds;
	private List<Obstacle> obstacles; // Danh sách obstacles

	public Obstacle(Pane root, List<Obstacle> obstacles, String[] imgPaths, int type) {
		super(root);

		this.obstacles = obstacles; // Lưu danh sách obstacles để tự xóa mình
		this.images = new Image[imgPaths.length];
		for (int i = 0; i < imgPaths.length; i++) {
			images[i] = new Image(imgPaths[i]);
		}

		this.type = type;
		this.x = Game.WIDTH;
		this.y = Game.HEIGHT - images[type].getHeight();

		this.imageView = new ImageView(images[type]);
		this.imageView.setX(x);
		this.imageView.setY(y);
		root.getChildren().add(this.imageView);

		this.bounds = new Rectangle(x, y, images[type].getWidth(), images[type].getHeight());
		bounds.setVisible(false); // nếu muốn debug va chạm thì setVisible(true)
		root.getChildren().add(bounds);
	}

	@Override
	public void update(double deltaTime) {
		// Di chuyển
		double speed = GameConfig.getGameSpeed();
		this.x -= speed * deltaTime / 1000;
		this.imageView.setX(x);
		this.bounds.setX(x);

		// Nếu ra ngoài màn hình, tự xóa khỏi root và danh sách obstacles
		if (this.x < -this.imageView.getImage().getWidth()) {
			root.getChildren().removeAll(imageView, bounds);
			obstacles.remove(this); // <--- tự xóa khỏi danh sách!
		}
	}

	@Override
	public void draw() {
		// Không cần code gì thêm, ImageView đã tự hiển thị
		root.getChildren().add(imageView);
	}

	public boolean checkCollision(Rectangle otherBounds) {
		return this.bounds.getBoundsInParent().intersects(otherBounds.getBoundsInParent());
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
}
