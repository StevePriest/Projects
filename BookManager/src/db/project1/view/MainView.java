package db.project1.view;

/*

 */

import java.util.List;
import java.util.Scanner;

import db.project1.controller.*;
import db.project1.domain.*;

//MainView class receives input from user and selects the correct
//BookController method to handle the request
public class MainView {

	// BookController object used to call appropriate method
	private BookController controller = new BookController();

	/*
	 * The control flow in run() is 1.print the menu 2.get the input from user
	 * 3.call the corresponding method to update or query the database 4.print
	 * results to user 5.go back to step 1 until the input is exit.
	 */
	public void run() {

		Scanner sc = new Scanner(System.in);
		while (true) {
			// print the menu
			System.out.println("---------------Book Manager Software---------------");
			System.out.println("1.Add Publisher 2.Add Book 3.Edit Book 4.Delete Book 5.Search Book 6.Exit");
			System.out.println("Please select the function, type [1-6] and press enter:");

			// Try, catch block to validate user-input. Input should be numeric
			int choose = 0;
			try {
				choose = Integer.parseInt(sc.nextLine());

			} catch (NumberFormatException e) {
				System.out.println("Numbers only please, let's try again\n");
				run();
			}

			// Method call based on user selection
			switch (choose) {
			case 1:
				addPublisher();
				break;
			case 2:
				addBook();
				break;
			case 3:
				editBook();
				break;
			case 4:
				deleteBook();
				break;
			case 5:
				selectBook();
				break;
			case 6:
				System.exit(0);
				break;
			}
		}
	}

	// When user selects 'Add Publisher'
	public void addPublisher() {

		// User is prompted for necessary information
		System.out.println("Please enter the following information:");
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter name of Publisher:");
		String name = sc.nextLine();

		// Check to see if a publisher with this name already exists.
		// (guard against integrity constraint violation)
		if (controller.validatePublisher(name)) {
			System.out.println("Relax, your work is done! A publisher with this name is already on"
					+ " file... or try again using a different name :)");
			run();
		}
		System.out.println("Enter Publisher's 10 digit phone number in this format ##########:");
		String phone = sc.nextLine();
		System.out.println("Enter city where Publisher is located:");
		String city = sc.nextLine();

		// Publisher object is created from user input and passed
		// to be added to database
		Publisher publisher = new Publisher(name, phone, city);

		// controller object passes publisher object to be added to database
		controller.addPublisher(publisher);

		// Confirmation message notifying user publisher was added
		System.out.println("Publisher Added Successfully!");
	}

	// When user selects "Add Book"
	public void addBook() {

		// User is prompted for necessary information
		System.out.println("Please enter the following information:");
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter ISBN of the book:");
		String ISBN = sc.nextLine();

		// Check for duplicate ISBN (guard against integrity constraint violation)
		if (!controller.validateISBN(ISBN)) {
			System.out.println("Whoops! This ISBN already belongs to a book on file, a different ISBN"
					+ " must be used to add your book. Please try again!");
			run();
		}
		System.out.println("Enter title of book:");
		String title = sc.nextLine();
		System.out.println("Enter year of publication in this format ####:");

		// Try, catch block to validate user-input. Input should be numeric
		int year = 0;
		try {
			year = Integer.parseInt(sc.nextLine());

		} catch (NumberFormatException e) {
			System.out.println("Numbers only please, let's try again\n");
			run();
		}
		System.out.println("Enter name of publisher (enter 'none' if N/A):");
		String publisher = sc.nextLine();
		
		// Convert 'none' to null value
		publisher = (publisher.equalsIgnoreCase("none") ? null : publisher);

		// Check to see if publisher is on file (guard against integrity constraint
		// violation)
		if (publisher != null && !controller.validatePublisher(publisher)) {
			System.out.println("Whoops! This publisher is not on file, please add this publisher first"
					+ " or try again using a publisher already on file.");
			run();
		}
		System.out.println("Enter ISBN of previous edition (enter 'none' if N/A):");
		String previousEdition = sc.nextLine();

		// Convert 'none' to null value
		previousEdition = (previousEdition.equalsIgnoreCase("none") ? null : previousEdition);

		// Ensure previous edition ISBN exists (guard against integrity constraint
		// violation)
		if (previousEdition != null && controller.validateISBN(previousEdition)) {
			System.out.println("Whoops! This previous edition is not yet on file, please add"
					+ " previous edition as a new book first then try again.");
			run();
		}
		System.out.println("Enter price of book (numbers only, please!):");

		// Try, catch block to validate user-input. Input should be numeric
		double price = 0;
		try {
			price = Double.parseDouble(sc.nextLine());

		} catch (NumberFormatException e) {
			System.out.println("Numbers only please, let's try again\n");
			run();
		}

		// Book object is created from user input and passed to be added to database
		Book book = new Book(ISBN, title, year, publisher, previousEdition, price);

		// controller object passes book object to be added to database
		controller.addBook(book);

		// Confirmation message notifying user book was added
		System.out.println("Book Added Successfully!");
	}

	// When user selects 'Edit Book'
	public void editBook() {

		// We first display all books to the user
		selectAll();

		// User is prompted for necessary information
		System.out.println("\nWhich book do you want to edit?");
		Scanner sc = new Scanner(System.in);
		System.out.println("Type ISBN and press enter: ");
		String ISBN = sc.nextLine();

		// Ensure book to be edited exists (guard against integrity constraint
		// violation)
		if (controller.validateISBN(ISBN)) {
			System.out.println("Whoops! This book cannot be edited because it is not on file, please"
					+ " add book first, then edit later if necessary :)");
			run();
		}
		System.out.println("Enter new title:");
		String title = sc.nextLine();
		System.out.println("Enter new year of publication in this format ####: ");

		// Try, catch block to validate user-input. Input should be numeric
		int year = 0;
		try {
			year = Integer.parseInt(sc.nextLine());

		} catch (NumberFormatException e) {
			System.out.println("Numbers only please, let's try again\n");
			run();
		}
		System.out.println("Enter new name of publisher (enter 'none' if N/A): ");
		String publisher = sc.nextLine();
		publisher = (publisher.equalsIgnoreCase("none") ? null : publisher);

		// Check to see if publisher is on file (guard against integrity constraint
		// violation)
		if (publisher != null && !controller.validatePublisher(publisher)) {
			System.out.println("Whoops! This publisher is not on file, please add this publisher first"
					+ " or try again using a publisher already on file.");
			run();
		}
		System.out.println("Enter ISBN of previous edition (enter 'none' if N/A): ");
		String previousEdition = sc.nextLine();
		previousEdition = (previousEdition.equalsIgnoreCase("none") ? null : previousEdition);

		// Ensure previous edition ISBN exists (guard against integrity constraint
		// violation)
		if (previousEdition != null && controller.validateISBN(previousEdition)) {
			System.out.println("Whoops! This previous edition is not yet on file, please add"
					+ " previous edition as a new book first then try again.");
			run();
		}
		System.out.println("Enter price of book (numbers only, please!): ");

		// Try, catch block to validate user-input. Input should be numeric
		double price = 0;
		try {
			price = Double.parseDouble(sc.nextLine());

		} catch (NumberFormatException e) {
			System.out.println("Numbers only please, let's try again\n");
			run();
		}

		// Book object is created from user input and passed to be added to database
		Book book = new Book(ISBN, title, year, publisher, previousEdition, price);

		// controller object passes book object to be added to database
		controller.editBook(book);

		// Confirmation message notifying user book was added
		System.out.println("Edit Successful!");
	}

	// When user selects 'Search Book'
	public void selectBook() {

		// User is prompted to select search type
		System.out.println("1. Search All Books 2. Search Books Based on Criteria");
		Scanner sc = new Scanner(System.in);

		// Try, catch block to validate user-input. Input should be numeric
		int selectChooser = 0;
		try {
			selectChooser = Integer.parseInt(sc.nextLine());

		} catch (NumberFormatException e) {
			System.out.println("Numbers only please, let's try again\n");
			run();
		}

		// Method call based on user input
		switch (selectChooser) {
		case 1:
			// select all books from DB
			selectAll();
			break;
		case 2:
			// select based on certain criteria
			select();
			break;
		}
	}

	// When user chooses 'Search All Books'
	public void selectAll() {
		// Call BookController's selectAll method
		List<Book> list = controller.selectAll();
		if (list.size() != 0)
			print(list);
		else
			System.out.println("No Books found!");
	}

	// When user chooses 'Search Books Based on Criteria'
	public void select() {

		// User is prompted for selection criteria
		System.out.println("---------------Selection Criteria---------------");
		System.out.println("1.Title 2.ISBN 3.Publisher 4.Price Range 5.Year 6.Title and Publisher");
		System.out.println("Please select criteria, type [1-6] and press enter:");

		Scanner sc = new Scanner(System.in);

		// Try, catch block to validate user-input. Input should be numeric
		int choose = 0;
		try {
			choose = Integer.parseInt(sc.nextLine());

		} catch (NumberFormatException e) {
			System.out.println("Numbers only please, let's try again\n");
			run();
		}

		// Method call based on user input
		switch (choose) {
		case 1:
		case 2:
		case 3:
		case 5:
			selectSingleCriteria(choose);
			break;
		case 4:
			selectRange();
			break;
		case 6:
			selectTitleAndPub();
			break;
		}
	}

	// When user selects 'Title', 'ISBN', Publisher', or 'Year' (1, 2, 3, or 5)
	public void selectSingleCriteria(int choice) {

		// User choice is retrieved from String array
		String[] criteria = { "title", "ISBN", "published_by", "price", "year" };
		Scanner sc = new Scanner(System.in);
		String value = "";

		// If statement only used to print 'publisher' to user instead of
		// 'published_by'
		if (criteria[choice - 1].equalsIgnoreCase("published_by")) {
			System.out.print("Enter publisher: ");
			value = sc.nextLine();
		} else {
			System.out.print("Enter " + criteria[choice - 1] + ": ");
			value = sc.nextLine();
		}

		// User choice is passed from String array as a means of sanitizing data
		// controller object passes arguments for data retrieval
		List<Book> list = controller.selectSingleCriteria(criteria[choice - 1], value);
		if (list.size() != 0)
			print(list);
		else
			System.out.println("No Book found matching that criteria!");
	}

	// When user selects 'Price Range'
	public void selectRange() {

		// User is prompted for necessary information
		System.out.println("Numbers only, please!");
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter minimum price: ");
		String minPrice = sc.nextLine();
		System.out.print("Enter maximum price: ");
		String maxPrice = sc.nextLine();

		// controller object passes arguments for data retrieval
		List<Book> list = controller.selectRange(minPrice, maxPrice);
		if (list.size() != 0)
			print(list);
		else
			System.out.println("No Book found in that price range!");
	}

	// When user selects 'Title and Publisher'
	public void selectTitleAndPub() {

		// User is prompted for necessary information
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter title: ");
		String title = sc.nextLine();
		System.out.print("Enter publisher: ");
		String publisher = sc.nextLine();

		// controller object passes arguments for data retrieval
		List<Book> list = controller.selectTitleAndPub(title, publisher);
		if (list.size() != 0)
			print(list);
		else
			System.out.println("No Book found with that title and publisher!");
	}

	// When user selects 'Delete Book'
	public void deleteBook() {
		// First, we display all existing books to user
		selectAll();

		// User is prompted for necessary information
		System.out.println("\nEnter the ISBN of the book you want to delete and press enter:");
		String ISBN = new Scanner(System.in).nextLine();

		// Check to see if book to be deleted is another book's previous edition. If so,
		// user
		// is prompted to edit the book that contains the book to be deleted as its
		// previous edition
		// (guard against integrity constraint violation)
		List<Book> list = controller.validatePrevEdition(ISBN);
		if (list.size() != 0) {
			System.out.println("Whoops! The book you want to delete is on file as the previous edition"
					+ " for the book(s) below. \nIf you still want to delete this book, please edit the"
					+ " previous edition of the book(s) below \nusing the 'Edit Book' option first. You"
					+ " may enter 'none' for previous edition when prompted and then delete this book.");
			print(list);
			run();
			// Check to see if book exists
		} else if (controller.validateISBN(ISBN)) {
			System.out.println("Book doesn't exist! No need to delete :)");
			run();
		}

		// controller object passes ISBN of book object to be deleted from database
		controller.deleteBook(ISBN);

		// Confirmation message notifying user book was deleted.
		System.out.println("Book was deleted.");
	}

	// Prints data to console
	private void print(List<Book> list) {
		System.out.printf("%-15s %-25s %-10s%-25s%-25s%-10s%n", "ISBN", "Title", "Year", "Published By",
				"Previous Edition", "Price");
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------");
		// Iterate the list and print each item to console
		for (Book book : list) {
			System.out.printf("%-15s %-25s %-10s%-25s%-25s%-10s%n", book.getISBN(), book.getTitle(), book.getYear(),
					book.getPublished_by(), book.getPrevious_edition(), book.getPrice());
		}
	}
}