package objects;

import java.util.List;
import java.util.Random;

import javafx.scene.layout.Pane;

public class SmallCactus extends Obstacle {
	private static final Random random = new Random();

	public SmallCactus(Pane root, List<Obstacle> obstacles, String[] imgPaths) {
		super(root, obstacles, imgPaths, randomType());

		// Điều chỉnh lại vị trí y
		this.setBottom(510);
	}

	// Hàm phụ để random type (0, 1, 2)
	private static int randomType() {
		return (int) (Math.random() * 3); // Math.random() * 3 sẽ ra 0, 1 hoặc 2
	}

	// Hàm phụ để set bottom như Python `self.rect.bottom = 500`
	private void setBottom(double bottomY) {
		double height = this.imageView.getImage().getHeight();
		this.y = bottomY - height;
		this.imageView.setY(this.y);
		this.bounds.setY(this.y);
	}

	@Override
	public void update(double deltaTime) {
		// Cập nhật vị trí của SmallCactus (kế thừa từ Obstacle)
		super.update(deltaTime);
	}

	@Override
	public void draw() {
		// Vẽ SmallCactus (sử dụng phương thức từ lớp cha)
		super.draw();
	}
}
