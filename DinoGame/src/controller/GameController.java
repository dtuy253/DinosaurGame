package controller;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import objects.Bird;
import objects.Cloud;
import objects.Dinosaur;
import objects.EndlessBackground;
import objects.LargeCactus;
import objects.Obstacle;
import objects.SmallCactus;

public class GameController {

	private Pane root;
	private EndlessBackground bg;
	private Dinosaur dinosaur;
	private ArrayList<Cloud> clouds;
	private ArrayList<Obstacle> obstacles;
	private boolean death = false;
	private Text gameOverText;
	private double score;
	private Label scoreLabel;
	private Label highestscoreLabel;
	private long lastTime;
	private double deltaTime;

	private ImageView gameOverImageView;
	private ImageView restartImageView;
	int gameScore = 0;
	int highestScore = 0;

	private static final String[] SMALL_CACTUS = { "file:assets/Cactus/SmallCactus1.png",
			"file:assets/Cactus/SmallCactus2.png", "file:assets/Cactus/SmallCactus3.png" };

	private static final String[] LARGE_CACTUS = { "file:assets/Cactus/LargeCactus1.png",
			"file:assets/Cactus/LargeCactus2.png", "file:assets/Cactus/LargeCactus3.png" };

	private static final String[] BIRD = { "file:assets/Bird/Bird1.png", "file:assets/Bird/Bird2.png" };

	private boolean isGameStarted = false; // Trạng thái trò chơi

	// Các hằng số khác như SMALL_CACTUS, LARGE_CACTUS, BIRD

	public GameController(Pane root) {
		this.root = root;
		setupGame();
		startGameLoop();
	}

	private void setupGame() {
		showScore();
		showHighestScore();
		bg = new EndlessBackground(root, "file:Assets/Other/Track.png", 490);
		dinosaur = new Dinosaur(root);
		clouds = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			clouds.add(new Cloud(root));
		}
		obstacles = new ArrayList<>();

		// Chưa bắt đầu trò chơi nên không hiển thị các đối tượng như Dinosaur hay
		// Obstacle
		hideGameElements();
	}

	private void startGameLoop() {
		AnimationTimer gameLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (isGameStarted) {
					if (lastTime > 0) {
						deltaTime = (now - lastTime) / 1_000_000.0;
					}
					lastTime = now;
					update();
					draw();
				}
			}
		};
		gameLoop.start();
	}

	public void handleKeyPress(KeyCode code) {
		if (!isGameStarted && code == KeyCode.SPACE) {
			// Bắt đầu trò chơi khi nhấn Space
			isGameStarted = true;
			showGameElements();
		} else if (code == KeyCode.R && death) {
			reset();
		} else if (dinosaur != null) {
			dinosaur.handleKeyPress(code);
		}
	}

	public void handleKeyRelease(KeyCode code) {
		if (dinosaur != null) {
			dinosaur.handleKeyRelease(code);
		}
	}

	private void update() {
		if (death) {
			return;
		}

		bg.update(deltaTime);
		dinosaur.update(deltaTime);
		for (Cloud cloud : clouds)
			cloud.update(deltaTime);
		for (Obstacle obs : new ArrayList<>(obstacles))
			obs.update(deltaTime);

		if (obstacles.isEmpty()) {
			int randObs = (int) (Math.random() * 3);
			if (randObs == 0) {
				obstacles.add(new SmallCactus(root, obstacles, SMALL_CACTUS));
			} else if (randObs == 1) {
				obstacles.add(new LargeCactus(root, obstacles, LARGE_CACTUS));
			} else {
				obstacles.add(new Bird(root, obstacles, BIRD));
			}
		}
		for (Obstacle obs : obstacles) {
			if (checkCollision(dinosaur, obs)) {
				death = true;
				dinosaur.setDeadImage();
				dinosaur.getDeadSound();
				showGameOver();
			} else {
				score += 0.1 * (GameConfig.getGameSpeed() / 900);
			}
			gameScore = (int) score;
		}
		if (gameScore >= highestScore) {
			highestScore = gameScore;
			if (highestScore % 100 == 0 && highestScore != 0) {
				blinkHighestScore();
			}
		}
		GameConfig.increaseSpeed(gameScore);
		scoreLabel.setText("Score: " + gameScore);
		highestscoreLabel.setText("Highest Score: " + highestScore);
	}

	private void draw() {
		root.getChildren().clear();
		bg.draw();
		for (Cloud cloud : clouds)
			cloud.draw();
		for (Obstacle obs : obstacles)
			obs.draw();
		dinosaur.draw();
		root.getChildren().add(scoreLabel);
		root.getChildren().add(highestscoreLabel);

		if (death) {
			root.getChildren().clear();
			root.getChildren().add(highestscoreLabel);
			if (!root.getChildren().contains(gameOverImageView)) {
				root.getChildren().add(gameOverImageView);
			}
			if (!root.getChildren().contains(restartImageView)) {
				root.getChildren().add(restartImageView);
			}
			if (!root.getChildren().contains(gameOverText)) {
				root.getChildren().add(gameOverText);
			}
		}
	}

	private boolean checkCollision(Dinosaur dino, Obstacle obs) {
		return dino.getBounds().intersects(obs.getBounds());
	}

	private void showScore() {
		scoreLabel = new Label("Score: 0");
		scoreLabel.setLayoutX(700);
		scoreLabel.setLayoutY(50);
		scoreLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
	}

	private void showHighestScore() {
		highestscoreLabel = new Label("Highest Score: 0");
		highestscoreLabel.setLayoutX(1000);
		highestscoreLabel.setLayoutY(50);
		highestscoreLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
	}

	private void showGameOver() {
		root.getChildren().clear();
		// Tạo ảnh Game Over và Restart
		Image gameOverImage = new Image("file:assets/Other/GameOver.png");
		gameOverImageView = new ImageView(gameOverImage);

		Image restartImage = new Image("file:assets/Other/Reset.png");
		restartImageView = new ImageView(restartImage);

		// Chỉnh kích thước ảnh
		gameOverImageView.setFitWidth(400);
		gameOverImageView.setPreserveRatio(true);

		restartImageView.setFitWidth(200);
		restartImageView.setPreserveRatio(true);

		// Đặt vị trí ở giữa màn hình
		gameOverImageView.setX(root.getWidth() / 2 - gameOverImageView.getFitWidth() / 2);
		gameOverImageView.setY(root.getHeight() / 4 - gameOverImageView.getFitHeight() / 4);

		restartImageView.setX(root.getWidth() / 2 - restartImageView.getFitWidth() / 2);
		restartImageView.setY(root.getHeight() / 2.5 - restartImageView.getFitHeight() / 2.5);

		// Hiệu ứng nhấp nháy Game Over
		FadeTransition blink = new FadeTransition(Duration.seconds(0.5), gameOverImageView);
		blink.setFromValue(1.0);
		blink.setToValue(0.3);
		blink.setCycleCount(Animation.INDEFINITE);
		blink.setAutoReverse(true);
		blink.play();

		// Hiển thị dòng thông báo
		gameOverText = new Text("Game Over! Press 'R' to Restart");
		gameOverText.setX(445);
		gameOverText.setY(650);
		gameOverText.setStyle("-fx-font-size: 30px; -fx-fill: black;");

		// Thêm ảnh vào màn hình
		root.getChildren().addAll(gameOverImageView, restartImageView, gameOverText);
	}

	private void reset() {
		death = false;
		score = 0;
		GameConfig.resetSpeed();
		root.getChildren().clear();
		setupGame(); // showScore đã gọi add(scoreLabel)
	}

	// Thêm hiệu ứng cho highest score
	private void blinkHighestScore() {
		FadeTransition fade = new FadeTransition(Duration.seconds(0.2), highestscoreLabel);
		fade.setFromValue(1.0);
		fade.setToValue(0.3);
		fade.setCycleCount(6); // Hiệu ứng nhấp nháy
		fade.setAutoReverse(true); // Đảo ngược hiệu ứng
		fade.play();
	}

	// Ẩn các đối tượng game khi chưa bắt đầu
	private void hideGameElements() {
		root.getChildren().clear();
		Text startText = new Text("Press 'Space' to Start");
		startText.setX(root.getWidth() / 2 - 150);
		startText.setY(root.getHeight() / 2);
		startText.setStyle("-fx-font-size: 30px; -fx-fill: black;");
		root.getChildren().add(startText);
	}

	// Hiển thị các đối tượng game khi bắt đầu
	private void showGameElements() {
		root.getChildren().clear(); // Xóa màn hình chào mừng
		setupGame(); // Hiển thị các đối tượng trò chơi
	}
}
