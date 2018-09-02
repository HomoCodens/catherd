package ch.katzenhausfreunde.catherd.model;

import javax.xml.bind.annotation.XmlElement;

import ch.katzenhausfreunde.catherd.util.CatHerdState;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
	StringProperty firstName;
	StringProperty lastName;
	StringProperty street;
	StringProperty postalAndTown;
	StringProperty phone;
	StringProperty mobile;
	StringProperty eMail;
	
	public Person() {
		this.firstName = new SimpleStringProperty(null);
		this.lastName = new SimpleStringProperty(null);
		this.street = new SimpleStringProperty(null);
		this.postalAndTown = new SimpleStringProperty(null);
		this.phone = new SimpleStringProperty(null);
		this.mobile = new SimpleStringProperty(null);
		this.eMail = new SimpleStringProperty(null);
	}
	
	public Person(String firstName, String lastName, String street,
			String postalAndTown, String phone, String mobile, String eMail) {
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.street = new SimpleStringProperty(street);
		this.postalAndTown = new SimpleStringProperty(postalAndTown);
		this.phone = new SimpleStringProperty(phone);
		this.mobile = new SimpleStringProperty(mobile);
		this.eMail = new SimpleStringProperty(eMail);
	}

	@XmlElement(name = "firstName")
	public final String getFirstName() {
		return firstName.get();
	}

	public final void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}
	
	public final StringProperty firstNameProperty() {
		return firstName;
	}

	@XmlElement(name = "lastName")
	public final String getLastName() {
		return lastName.get();
	}

	public final void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public final StringProperty lastNameProperty() {
		return lastName;
	}
	
	@XmlElement(name = "street")
	public final String getStreet() {
		return street.get();
	}

	public final void setStreet(String street) {
		this.street.set(street);
	}
	
	public final StringProperty streetProperty() {
		return street;
	}

	@XmlElement(name = "postalAndTown")
	public final String getPostalAndTown() {
		return postalAndTown.get();
	}

	public final void setPostalAndTown(String postalAndTown) {
		this.postalAndTown.set(postalAndTown);
	}

	public final StringProperty postalAndTownProperty() {
		return postalAndTown;
	}
	
	@XmlElement(name = "phone")
	public final String getPhone() {
		return phone.get();
	}

	public final void setPhone(String phone) {
		this.phone.set(phone);
	}

	public final StringProperty phoneProperty() {
		return phone;
	}
	
	@XmlElement(name = "mobile")
	public final String getMobile() {
		return mobile.get();
	}

	public final void setMobile(String mobile) {
		this.mobile.set(mobile);
	}

	public final StringProperty mobileProperty() {
		return mobile;
	}
	
	@XmlElement(name = "eMail")
	public final String getEMail() {
		return eMail.get();
	}

	public final void setEMail(String eMail) {
		this.eMail.set(eMail);
	}
	
	public final StringProperty eMailProperty() {
		return eMail;
	}
	
	public void arm() {
		lastName.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		firstName.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		street.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		postalAndTown.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		phone.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		mobile.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		eMail.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
	}
}
