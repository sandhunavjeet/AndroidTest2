import java.util.ArrayList;
import java.util.List;

public class LibraryApp {
	
	private boolean isLoggedIn = false;

	private List<Book> books = new ArrayList<Book>();
	
	// empty constructor
	public LibraryApp() {
	}
	
	public boolean adminLoggedIn() {
		// Return some property that indicates if the person
		// is logged in or logged out
		return this.isLoggedIn;
	}
	
	public boolean adminLogin(String password) {
		// Function assumes:
		// 	1. There is only 1 administrator
		//  2. Admin's password = "adminadmin"
		//  3. If password is correct, then "log" the user in
		if (password.equals("adminadmin")) {
			this.isLoggedIn = true;
		}
		else {
			this.isLoggedIn = false;
		}
		return this.isLoggedIn;
	}

	public List<Book> getBooks() {
		return this.books;
	}
	
	public void addBook(Book book) throws OperationNotAllowedException {
		 // check if admin is logged in BEFORE adding a book
		 if (this.isLoggedIn == true) {
			 this.books.add(book);	 
		 }
		 else {
			 throw new OperationNotAllowedException();
		 }
		 
	}
	
	
	
	
	
	
	
	
	

}
