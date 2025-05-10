package model;

import java.util.List;
import java.util.Random;

import javafx.scene.layout.Pane;

public class LargeCactus extends Obstacle {

	public LargeCactus(Pane root, List<Obstacle> obstacles, String[] imgPaths) {
		super(root, obstacles, imgPaths, new Random().nextInt(3)); // random từ 0 đến 2

		// Cập nhật lại vị trí y để set bottom = 500
		double bottom = 510;
		this.y = bottom - this.imageView.getImage().getHeight();
		this.imageView.setY(y);
		this.bounds.setY(y);
	}
}
