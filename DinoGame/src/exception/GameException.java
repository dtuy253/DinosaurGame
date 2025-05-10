package exception;

//Lớp GameException dùng để đại diện cho các lỗi trong quá trình chạy game.

public class GameException extends Exception {

	public GameException() {
		super("Lỗi không xác định trong game.");
	}

	public GameException(String message) {
		super(message);
	}

	public GameException(String message, Throwable cause) {
		super(message, cause);
	}

	public GameException(Throwable cause) {
		super(cause);
	}
}
