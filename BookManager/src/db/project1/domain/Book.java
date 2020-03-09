package db.project1.domain;

import java.io.Serializable;

/**
 * Class Book is called JavaBean, it corresponds to the table Book in
 * 'bookmanager' Database. Each attribute in JavaBean corresponds to each field
 * or column in the table.
 */
public class Book implements Serializable {

	private String ISBN;

	private String title;

	private int year;

	private String published_by;

	private String previous_edition;

	private double price;

	public Book(String ISBN, String title, int year, String published_by, String previous_edition, double price) {

		this.ISBN = ISBN;
		this.title = title;
		this.year = year;
		this.published_by = published_by;
		this.previous_edition = previous_edition;
		this.price = price;

	}

	public Book() {

	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getPublished_by() {
		return published_by;
	}

	public void setPublished_by(String published_by) {
		this.published_by = published_by;
	}

	public String getPrevious_edition() {
		return previous_edition;
	}

	public void setPrevious_edition(String previous_edition) {
		this.previous_edition = previous_edition;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}