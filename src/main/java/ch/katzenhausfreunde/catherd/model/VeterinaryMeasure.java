package ch.katzenhausfreunde.catherd.model;

import java.time.LocalDate;

import ch.katzenhausfreunde.catherd.util.CatHerdState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public class VeterinaryMeasure {
	ObjectProperty<LocalDate> date;
	StringProperty cause;
	StringProperty measures;
	
	public void arm() {
		date.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		cause.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		measures.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
	}
	
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
