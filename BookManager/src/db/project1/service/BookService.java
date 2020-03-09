package db.project1.service;

import java.util.List;

import db.project1.dao.*;
import db.project1.domain.*;

//BookService class receives objects passed from BookController class and selects the correct
//BookDao method to handle the request
public class BookService {

	// BookDao object used to call appropriate method
	private BookDao dao = new BookDao();

	// ISBN passed by dao object for deletion
	public void deleteBook(String ISBN) {
		dao.deleteBook(ISBN);
	}

	// dao object invokes method that checks if ISBN passed is that of
	// a book's previous edition (guard against integrity constraint violation)
	public List<Book> validatePrevEdition(String ISBN) {
		return dao.validatePrevEdition(ISBN);
	}

	// dao object passes book object for editing
	public void editBook(Book book) {
		dao.editBook(book);
	}

	// dao object passes publisher object to be added to database
	public void addPublisher(Publisher publisher) {
		dao.addPublisher(publisher);
	}

	// dao object passes book object to be added to database
	public void addBook(Book book) {
		dao.addBook(book);
	}

	// dao object invokes method that checks for duplicate ISBN
	// (guard against integrity constraint violation)
	public boolean validateISBN(String ISBN) {
		return dao.validateISBN(ISBN);
	}

	// dao object invokes method that checks for duplicate publisher
	// (guard against integrity constraint violation)
	public boolean validatePublisher(String publisher) {
		return dao.validatePublisher(publisher);
	}

	// dao object invokes method that returns book objects within
	// designated price range
	public List<Book> selectRange(String minPrice, String maxPrice) {
		return dao.selectRange(minPrice, maxPrice);
	}

	// dao object invokes method that returns book objects matching a single
	// criteria specified by user (title, ISBN, publisher, year)
	public List<Book> selectSingleCriteria(String attribute, String value) {
		return dao.selectSingleCriteria(attribute, value);
	}

	// dao object invokes method that returns book objects matching two
	// criteria specified by user (title AND publisher), both must be true for
	// results to be returned
	public List<Book> selectTitleAndPub(String title, String publisher) {
		return dao.selectTitleAndPub(title, publisher);
	}

	// dao object invokes method that returns all books objects in database
	public List<Book> selectAll() {
		return dao.selectAll();
	}
}