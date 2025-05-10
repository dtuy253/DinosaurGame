package objects;

import controller.GameConfig;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class EndlessBackground extends GameObject {
	private Image bgImage;
	private double bgWidth;
	private double scrollOffset = 0; // tốc độ cuộn (pixel/giây)
	private int tiles;
	private double posY;

	private static final int WIDTH = 800; // Bạn đổi WIDTH theo màn hình game của bạn

	public EndlessBackground(Pane root, String path, double posY) {
		super(root);

		this.bgImage = new Image(path);
		this.bgWidth = bgImage.getWidth();
		this.posY = posY;

		// Số lượng background cần vẽ để phủ kín màn hình
		this.tiles = (int) Math.ceil(WIDTH / bgWidth) + 1;
	}

	@Override
	public void update(double deltaTime) {
		scrollOffset -= GameConfig.getGameSpeed() * deltaTime / 1000.0; // dùng deltaTime truyền vào
		if (Math.abs(scrollOffset) > bgWidth) {
			scrollOffset = 0;
		}
	}

	@Override
	public void draw() {
		for (int i = 0; i < tiles; i++) {
			ImageView imageView = new ImageView(bgImage);
			imageView.setLayoutX(i * bgWidth + scrollOffset);
			imageView.setLayoutY(posY);
			root.getChildren().add(imageView);
		}
	}
}
