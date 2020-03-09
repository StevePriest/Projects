package db.project1.controller;

import java.util.List;

import db.project1.domain.*;
import db.project1.service.*;

//BookController class receives objects passed from MainView class and selects the correct
//BookService method to handle the request
public class BookController {

	// BookService object used to call appropriate method
	private BookService service = new BookService();

	// ISBN passed by service object for deletion
	public void deleteBook(String ISBN) {
		service.deleteBook(ISBN);
	}

	// service object invokes method that checks if ISBN passed is that of
	// a book's previous edition (guard against integrity constraint violation)
	public List<Book> validatePrevEdition(String ISBN) {
		return service.validatePrevEdition(ISBN);
	}

	// service object passes book object for editing
	public void editBook(Book book) {
		service.editBook(book);
	}

	// service object passes publisher object to be added to database
	public void addPublisher(Publisher publisher) {
		service.addPublisher(publisher);
	}

	// service object passes book object to be added to database
	public void addBook(Book book) {
		service.addBook(book);
	}

	// service object invokes method that checks for duplicate ISBN
	// (guard against integrity constraint violation)
	public boolean validateISBN(String ISBN) {
		return service.validateISBN(ISBN);
	}

	// service object invokes method that checks for duplicate publisher
	// (guard against integrity constraint violation)
	public boolean validatePublisher(String publisher) {
		return service.validatePublisher(publisher);
	}

	// service object invokes method that returns book objects within
	// designated price range
	public List<Book> selectRange(String minPrice, String maxPrice) {
		return service.selectRange(minPrice, maxPrice);
	}

	// service object invokes method that returns book objects matching a single
	// criteria specified by user (title, ISBN, publisher, year)
	public List<Book> selectSingleCriteria(String attribute, String value) {
		return service.selectSingleCriteria(attribute, value);
	}

	// service object invokes method that returns book objects matching two
	// criteria specified by user (title AND publisher) both must be true for
	// results to be returned
	public List<Book> selectTitleAndPub(String title, String publisher) {
		return service.selectTitleAndPub(title, publisher);
	}

	// service object invokes method that returns all books objects in database
	public List<Book> selectAll() {
		return service.selectAll();
	}
}