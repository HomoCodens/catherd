package ch.katzenhausfreunde.catherd.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ch.katzenhausfreunde.catherd.util.CatHerdState;
import ch.katzenhausfreunde.catherd.util.LocalDateAdapter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VeterinaryMeasure {
	ObjectProperty<LocalDate> date;
	StringProperty cause;
	StringProperty measures;
	
	public VeterinaryMeasure() {
		date = new SimpleObjectProperty<LocalDate>(null);
		cause = new SimpleStringProperty(null);
		measures = new SimpleStringProperty(null);
	}
	
	public void arm() {
		date.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		cause.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		measures.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
	}
	
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	@XmlElement(name = "date")
	public final LocalDate getDate() {
		return date.get();
	}
	
	public final void setDate(LocalDate date) {
		this.date.set(date);
	}
	
	public final ObjectProperty<LocalDate> dateProperty() {
		return this.date;
	}
	
	public final String getCause() {
		return cause.get();
	}
	
	public final void setCause(String cause) {
		this.cause.set(cause);
	}
	
	public final StringProperty causeProperty() {
		return this.cause;
	}
	
	public final String getMeasures() {
		return measures.get();
	}
	
	public final void setMeasures(String measures) {
		this.measures.set(measures);
	}
	
	public final StringProperty measuresProperty() {
		return this.measures;
	}
}
