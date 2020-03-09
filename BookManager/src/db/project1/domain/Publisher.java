package db.project1.domain;

import java.io.Serializable;

/**
 * Class Publisher is called JavaBean, it corresponds to the table Publisher in
 * 'bookmanager' Database. Each attribute in JavaBean corresponds to each field
 * or column in the table.
 */
public class Publisher implements Serializable {

	private String name;

	private String phone;

	private String city;

	public Publisher(String name, String phone, String city) {

		this.name = name;
		this.phone = phone;
		this.city = city;

	}

	public Publisher() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}