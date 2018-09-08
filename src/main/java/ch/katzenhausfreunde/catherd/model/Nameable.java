package ch.katzenhausfreunde.catherd.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ch.katzenhausfreunde.catherd.util.CatHerdState;
import ch.katzenhausfreunde.catherd.util.UUIDAdapter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author thoenis
 *
 */
public class Nameable {
	/**
	 * The name of the object
	 */
	private StringProperty name;
	
	/**
	 * The id of the object. Mainly here in case it's needed in the future.
	 */
	@XmlJavaTypeAdapter(UUIDAdapter.class)
	@XmlElement(name = "id")
	private UUID id = UUID.randomUUID();;
	
	/**
	 * Creates a Nameable with an empty String ("") for its name.
	 */
	public Nameable() {
		this("");
	}
	
	/**
	 * Creates a nameable with the given name.
	 * 
	 * @param name The name of the object.
	 */
	public Nameable(String name) {
		this.name = new SimpleStringProperty(name);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getName();
	}

	/**
	 * @return The id of the object.
	 */
	public UUID getId() {
		return id;
	}
	
	public void setID(UUID id) {
		this.id = id;
	}
	
	/**
	 * @return The name property of the object.
	 */
	public StringProperty nameProperty() {
		return this.name;
	}
	
	/**
	 * @return The name of the object.
	 */
	@XmlElement(name = "name")
	public String getName() {
		return this.name.get();
	}
	
	/**
	 * @param name The new name of the object.
	 */
	public void setName(String name) {
		this.name.set(name);
	}
	
	/**
	 * Registers a listener to invalidate the application state when the name property changes.
	 */
	public void arm() {
		System.out.println(getName() + " locked'n'loaded");
		this.name.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
	}
}
