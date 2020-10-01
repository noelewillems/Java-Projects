// subclass of Java Exception class
// Thrown when attempting to remove int from empty queue
public class QueueEmptyException extends Exception {

	public QueueEmptyException() {
	}

	public QueueEmptyException(String arg0) {
		super(arg0);
	}

	public QueueEmptyException(Throwable arg0) {
		super(arg0);
	}

	public QueueEmptyException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public QueueEmptyException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
