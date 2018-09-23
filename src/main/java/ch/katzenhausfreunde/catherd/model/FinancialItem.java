package ch.katzenhausfreunde.catherd.model;

import ch.katzenhausfreunde.catherd.util.CatHerdState;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;

public class FinancialItem {
	FloatProperty amount;
	BooleanProperty sentToKHF;
	
	public FinancialItem() {
		this(0.0f, false);
	}
	
	public FinancialItem(float amount, boolean done) {
		this.amount = new SimpleFloatProperty(amount);
		this.sentToKHF = new SimpleBooleanProperty(done);
	}
	
	public void arm() {
		amount.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		sentToKHF.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
	}
	
	public float getAmount() {
		return amount.get();
	}
	
	public void setAmount(float amount) {
		this.amount.set(amount);
	}
	
	public FloatProperty amountProperty() {
		return amount;
	}
	
	public boolean getSentToKHF() {
		return sentToKHF.get();
	}
	
	public void setSentToKHF(boolean sentToKHF) {
		this.sentToKHF.set(sentToKHF);
	}
	
	public BooleanProperty sentToKHFProperty() {
		return sentToKHF;
	}
}
