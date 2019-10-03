
public class OperationNotAllowedException extends Exception {

	// Override the default getMessage function
	public String getMessage() {
		return "Add book operation not allowed if not admin.";
	}
	
	public Object getOperation() {
		// TODO Auto-generated method stub
		return "Add book";
	}

}
