package ch.katzenhausfreunde.catherd.view.customcontrols;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.scene.control.TextField;

public class MoneyField extends TextField {
	private FloatProperty amount;
	
	public MoneyField() {
		super();
		
		this.amount = new SimpleFloatProperty();
		
		this.amountProperty().addListener((observable, oldValue, newValue) -> {
			if(!this.isFocused()) {
				setText(String.format("%.2f", newValue));
			}
		});
		
		this.textProperty().addListener((observable, oldValue, newValue) -> {			
			if(oldValue == newValue) {
				return;
			}
			
			if(!newValue.matches("^\\d*(\\.\\d{0,2})?$")) {
				setText(oldValue);
				return;
			}
			
			if(newValue == null || newValue.isEmpty()) {
				setAmount(0.0f);
			} else {
				setAmount(Float.parseFloat(newValue));
			}
		});
		
		this.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue) {
			} else {
				String t = getText();
				if(t == null || t.isEmpty()) {
					setAmount(0.0f);
				} else {
					setText(String.format("%.2f", getAmount()));
				}
			}
		});
	}
	
	public float getAmount() {
		return amount.get();
	}
	
	public void setAmount(float newAmount) {
		amount.set(newAmount);
	}
	
	public FloatProperty amountProperty() {
		return amount;
	}
}
