package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.GameObject;

public class SpriteAnimated extends GameObject {
	private ImageView imageView; // Sử dụng ImageView để hiển thị hình ảnh
	private Image[] images; // Danh sách các hình ảnh
	private int numFrames;
	private int currentFrame;

	private double interval; // Thời gian mỗi frame (giây)
	private double prevTick; // Thời gian của frame trước
	private double currentTick; // Thời gian của frame hiện tại

	public SpriteAnimated(Pane root, String[] pathList, double timePerFrame) {
		super(root);

		// Tải các hình ảnh vào mảng
		this.images = new Image[pathList.length];
		for (int i = 0; i < pathList.length; i++) {
			this.images[i] = new Image(pathList[i]);
		}

		this.numFrames = images.length;
		this.currentFrame = 0;

		// Khởi tạo ImageView và thêm vào root pane
		this.imageView = new ImageView(images[currentFrame]);
		root.getChildren().add(imageView);

		this.interval = timePerFrame;

		// Thiết lập thời gian ban đầu
		this.prevTick = 0;
		this.currentTick = 0;
	}

	@Override
	public void update(double deltaTime) {
		// Cập nhật thời gian
		currentTick += deltaTime;

		// Nếu thời gian giữa các frame đã đủ, chuyển sang frame tiếp theo
		if (currentTick - prevTick >= interval) {
			currentFrame = (currentFrame + 1) % numFrames;
			imageView.setImage(images[currentFrame]); // Cập nhật hình ảnh hiển thị

			prevTick = currentTick; // Cập nhật thời gian của frame trước
		}
	}

	@Override
	public void draw() {
		// Vẽ đối tượng bằng ImageView đã được cập nhật
		// Không cần vẽ lại vì ImageView tự động cập nhật hình ảnh
	}

	public void setPos(double x, double y) {
		imageView.setLayoutX(x);
		imageView.setLayoutY(y);
	}

	public Image getImage() {
		return imageView.getImage();
	}
}
