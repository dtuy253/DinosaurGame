package controller;

public class GameConfig {
	private static double gameSpeed = 900;
	private static int lastScoreMilestone = 0;

	public static double getGameSpeed() {
		return gameSpeed;
	}

	public static void resetSpeed() {
		gameSpeed = 900; // reset khi restart game
		lastScoreMilestone = 0;
	}

	public static void increaseSpeed(int score) {
		if (score >= lastScoreMilestone + 100) {
			gameSpeed += 100;
			lastScoreMilestone += 100;
		}

	}

}
