package ch.katzenhausfreunde.catherd.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Nameable {
	private StringProperty name;
	private UUID id;
	
	public Nameable() {
		this("");
	}
	
	public Nameable(String name) {
		this.name = new SimpleStringProperty(name);
		this.id = UUID.randomUUID();
	}
	
	public String toString() {
		return getName();
	}
	
	public UUID getId() {
		return id;
	}
	
	public StringProperty nameProperty() {
		return this.name;
	}
	
	@XmlElement(name = "name")
	public String getName() {
		return this.name.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
}
