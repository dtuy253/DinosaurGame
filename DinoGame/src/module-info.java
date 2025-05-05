module DinoGame {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.media;

	opens game to javafx.graphics, javafx.fxml;

	exports objects;
}
