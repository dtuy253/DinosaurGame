package game;

import controller.GameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Game extends Application {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	@Override
	public void start(Stage stage) {
		Pane root = new Pane();
		Scene scene = new Scene(root, WIDTH, HEIGHT, Color.WHITE);
		GameController controller = new GameController(root);

		scene.setOnKeyPressed(e -> controller.handleKeyPress(e.getCode()));
		scene.setOnKeyReleased(e -> controller.handleKeyRelease(e.getCode()));

		stage.setTitle("Dinosaur");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
