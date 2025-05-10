package model;

import java.util.Random;

import game.Game;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Cloud extends GameObject {
	private double x, y;
	private double width;
	private double speed;
	private ImageView cloudImageView;
	private Random random;

	public Cloud(Pane root) {
		super(root); // Gọi constructor của lớp cha GameObject
		this.random = new Random();
		this.x = Game.WIDTH + random.nextInt(Game.WIDTH);
		this.y = random.nextInt(350 - 50 + 1) + 50;

		// Tải hình ảnh đám mây và tạo ImageView
		Image cloudImage = new Image("file:Assets/Other/Cloud.png"); // Đường dẫn đến ảnh
		this.cloudImageView = new ImageView(cloudImage);
		this.width = cloudImage.getWidth();

		// Thiết lập vị trí ban đầu của đám mây
		this.cloudImageView.setLayoutX(x);
		this.cloudImageView.setLayoutY(y);

		// Thêm ImageView vào root của game
		root.getChildren().add(cloudImageView);
	}

	@Override
	public void update(double deltaTime) {
		// Cập nhật vị trí của đám mây
		speed = 200;
		this.x -= speed * deltaTime / 1000;
		if (this.x < -this.width) {
			// Đặt lại vị trí của đám mây khi nó ra khỏi màn hình
			this.x = Game.WIDTH + random.nextInt(1001) + 2000;
			this.y = random.nextInt(350 - 50 + 1) + 50;
		}

		// Cập nhật lại vị trí của ImageView
		this.cloudImageView.setLayoutX(this.x);
	}

	@Override
	public void draw() {
		// Đám mây đã được vẽ thông qua ImageView, không cần vẽ lại ở đây
		root.getChildren().add(cloudImageView);
	}
}
