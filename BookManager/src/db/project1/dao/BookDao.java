package db.project1.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import db.project1.domain.*;
import db.project1.tools.*;

//BookDao class receives objects passed from BookService class and consults the 'bookmanager'
//database via the QueryRunner object to fulfill user request
public class BookDao {

	// QueryRunner object used to consult database and sanitize data
	private QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());

	// Issues SQL statement to MySQL 'bookmanager' database for book to be deleted.
	// Book to be deleted is identified by user-entered ISBN, ISBN is checked for
	// integrity constraint violations before this method is invoked
	public void deleteBook(String ISBN) {
		try {
			// SQL statement with placeholder
			String sql = "DELETE FROM Book WHERE ISBN=?";
			qr.update(sql, ISBN);
		} catch (SQLException ex) {
			System.out.println(ex);
			throw new RuntimeException("Delete Exception!");
		}
	}

	// Checks to see if an ISBN sent for deletion is another book's previous
	// edition. This is done to prevent an integrity constraint violation. SQL
	// statement is
	// issued to MySQL 'bookmanager' database and results (if any) are returned.
	public List<Book> validatePrevEdition(String ISBN) {
		try {
			// SQL statement with placeholder
			String sql = "SELECT * FROM Book WHERE previous_edition = ?";
			// Put parameter in an array
			Object[] params = { ISBN };
			return qr.query(sql, new BeanListHandler<>(Book.class), params);
		} catch (SQLException ex) {
			System.out.println(ex);
			throw new RuntimeException("Validate Exception!");
		}
	}

	// Issues SQL statement to MySQL 'bookmanager' database for book to be edited.
	// Book is identified by ISBN and all attributes must be updated by user
	public void editBook(Book book) {
		try {
			// SQL statement with placeholder
			String sql = "UPDATE Book SET title=?,year=?,published_by=?,previous_edition=?,price=? WHERE ISBN=?";
			// Put parameters in an array
			Object[] params = { book.getTitle(), book.getYear(), book.getPublished_by(), book.getPrevious_edition(),
					book.getPrice(), book.getISBN() };
			qr.update(sql, params);
		} catch (SQLException ex) {
			System.out.println(ex);
			throw new RuntimeException("Edit Exception!");
		}
	}

	// Issues SQL statement to MySQL 'bookmanager' database for publisher to be
	// added.
	// Publisher to be added is identified by user-entered name for publisher,
	// publisher name
	// is checked for integrity constraint violations before this method is invoked
	public void addPublisher(Publisher publisher) {
		try {
			// SQL statement with placeholder
			String sql = "INSERT INTO Publisher (name,phone,city) VALUES(?,?,?)";
			// Put parameters in an array
			Object[] params = { publisher.getName(), publisher.getPhone(), publisher.getCity() };
			qr.update(sql, params);
		} catch (SQLException ex) {
			System.out.println(ex);
			throw new RuntimeException("Add Exception!");
		}
	}

	// Issues SQL statement to MySQL 'bookmanager' database for book to be added.
	// Book to be added is identified by user-entered ISBN, ISBN is checked for
	// integrity constraint violations before this method is invoked
	public void addBook(Book book) {
		try {
			// SQL statement with placeholder
			String sql = "INSERT INTO Book (ISBN,title,year,published_by,previous_edition,price) VALUES(?,?,?,?,?,?)";
			// Put parameters in an array
			Object[] params = { book.getISBN(), book.getTitle(), book.getYear(), book.getPublished_by(),
					book.getPrevious_edition(), book.getPrice() };
			qr.update(sql, params);
		} catch (SQLException ex) {
			System.out.println(ex);
			throw new RuntimeException("Add Exception!");
		}
	}

	// Checks to see if an ISBN sent for addition is another book's ISBN.
	// This is done to prevent an integrity constraint violation. SQL statement is
	// issued
	// to MySQL 'bookmanager' database and a boolean value is returned.
	public boolean validateISBN(String ISBN) {

		List<Book> list = null;

		try {
			// SQL statement with placeholder
			String sql = "SELECT * FROM Book WHERE ISBN = ?";
			// Put the parameter in an array
			Object[] params = { ISBN };
			list = qr.query(sql, new BeanListHandler<>(Book.class), params);
		} catch (SQLException ex) {
			System.out.println(ex);
			throw new RuntimeException("Validate Exception!");
		}
		return list.size() == 0;
	}

	// Checks to see if a publisher sent for addition is a duplicate.
	// This is done to prevent an integrity constraint violation. SQL statement is
	// issued
	// to MySQL 'bookmanager' database and a boolean value is returned.
	public boolean validatePublisher(String publisher) {

		List<Publisher> list = null;

		try {
			// SQL statement with placeholder
			String sql = "SELECT * FROM Publisher WHERE name = ?";
			// Put the parameter in an array
			Object[] params = { publisher };
			list = qr.query(sql, new BeanListHandler<>(Publisher.class), params);
		} catch (SQLException ex) {
			System.out.println(ex);
			throw new RuntimeException("Validate Exception!");
		}
		return list.size() > 0;
	}

	// Issues SQL statement to MySQL 'bookmanager' database for books to be returned
	// that meet price range specifications stipulated by user.
	public List<Book> selectRange(String minPrice, String maxPrice) {
		try {
			// SQL statement with placeholder
			String sql = "SELECT * FROM Book WHERE price BETWEEN ? AND ?";
			// Put the two parameters in an array
			Object[] params = { minPrice, maxPrice };
			return qr.query(sql, new BeanListHandler<>(Book.class), params);
		} catch (SQLException ex) {
			System.out.println(ex);
			throw new RuntimeException("Select Exception!");
		}
	}

	// Issues SQL statement to MySQL 'bookmanager' database for books to be returned
	// that match a single criteria specified by user (title, ISBN, publisher, year)
	public List<Book> selectSingleCriteria(String attribute, String value) {
		try {
			// SQL statement with placeholder. Attribute variable is sanitized
			// before sql variable is passed to database, attribute variable can only
			// be title, ISBN, publisher, or year
			String sql = "SELECT * FROM Book WHERE " + attribute + " = ?";
			// Put the parameter in an array
			Object[] params = { value };
			return qr.query(sql, new BeanListHandler<>(Book.class), params);
		} catch (SQLException ex) {
			System.out.println(ex);
			throw new RuntimeException("Select Exception!");
		}
	}

	// Issues SQL statement to MySQL 'bookmanager' database for books to be returned
	// that match two criteria specified by user (title AND publisher), both must be
	// true for
	// results to be returned
	public List<Book> selectTitleAndPub(String title, String publisher) {
		try {
			// SQL statement with placeholder
			String sql = "SELECT * FROM Book WHERE title = ? AND published_by = ?";
			// Put the two parameters in an array
			Object[] params = { title, publisher };
			return qr.query(sql, new BeanListHandler<>(Book.class), params);
		} catch (SQLException ex) {
			System.out.println(ex);
			throw new RuntimeException("Select Exception!");
		}
	}

	// Issues SQL statement to MySQL 'bookmanager' database for all book objects in
	// database
	// to be returned
	public List<Book> selectAll() {
		try {
			// SQL statement
			String sql = "SELECT * FROM Book";
			List<Book> list = qr.query(sql, new BeanListHandler<>(Book.class));
			return list;
		} catch (SQLException ex) {
			System.out.println(ex);
			throw new RuntimeException("Select All Exception!");
		}
	}
}