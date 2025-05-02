package objects;

import javafx.scene.layout.Pane;

// Class GameObject dùng để làm lớp cha cho tất cả đối tượng trong game
public abstract class GameObject {
	protected Pane root; // Root pane để thêm hình ảnh hoặc đối tượng vào màn hình

	public GameObject(Pane root) {
		this.root = root;
	}

	// Các phương thức abstract để các lớp con override
	public abstract void update(double deltaTime);

	public void draw() {
		// Nếu cần thì lớp con có thể override, mặc định không làm gì
	}

	public void eventHandling(javafx.scene.input.KeyEvent e) {

	}
}
