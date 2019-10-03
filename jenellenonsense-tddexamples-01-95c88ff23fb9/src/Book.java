
public class Book {

	// properties
	private String signature;
	private String title;
	private String author;
	
	public Book(String signature, String title, String author) {
		this.signature = signature;
		this.title = title;
		this.author = author;
	}

	// Automatically generate getters and setters
	//		1. RIGHT CLICK > SOURCE > GENERATE GETTERS AND SETTERS
	//		2. CLICK THE CHECKBOXES
	//		3. PRESS ENTER
	//		4. DONE
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
}
