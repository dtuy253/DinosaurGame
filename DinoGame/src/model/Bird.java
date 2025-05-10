package model;

import java.util.List;

import javafx.scene.layout.Pane;
import view.SpriteAnimated;

public class Bird extends Obstacle {
	private SpriteAnimated anim;

	public Bird(Pane root, List<Obstacle> obstacles, String[] imgPaths) {
		super(root, obstacles, imgPaths, 0); // type = 0 cố định

		// Khởi tạo hoạt ảnh
		anim = new SpriteAnimated(root, imgPaths, 100.0);

		// Điều chỉnh random vị trí y
		double[] heights = { 470, 430, 500 }; // 3 độ cao
		int index = (int) (Math.random() * heights.length);
		double bottom = heights[index];

		this.y = bottom - this.imageView.getImage().getHeight();
		this.imageView.setY(y);
		this.bounds.setY(y);
	}

	@Override
	public void update(double deltaTime) {
		super.update(deltaTime);
		anim.update(deltaTime);
	}

	@Override
	public void draw() {
		// Thay đổi frame ảnh động mỗi lần vẽ
		this.imageView.setImage(anim.getImage());
		super.draw();
	}

	// Thêm phương thức getWidth và getHeight
	public double getWidth() {
		return imageView.getBoundsInParent().getWidth() - 5;
	}

	public double getHeight() {
		return imageView.getBoundsInParent().getHeight();
	}
}
