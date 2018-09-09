package ch.katzenhausfreunde.catherd.view.customcontrols;

import java.text.NumberFormat;
import java.util.Locale;

import javafx.beans.binding.Bindings;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class MoneyField extends TextField {
	private FloatProperty amount;
	private StringProperty nullSafeTextProperty;
	
	public MoneyField() {
		super();
		
		this.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue) {
				System.out.println("We need to fuckus!");
			} else {
				String t = getText();
				if(t == null || t.isEmpty()) {
					setAmount(0.0f);
				}
				System.out.println("MoneyField out, over.");
			}
		});
		
		this.amount = new SimpleFloatProperty();
		
		this.nullSafeTextProperty = new SimpleStringProperty(null);
		this.nullSafeTextProperty.bind(Bindings.when(textProperty().isNotNull().and(textProperty().isNotEqualTo(""))).then(textProperty()).otherwise("0.00"));
		
		this.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue == null) {
				return;
			} else {
				if(!newValue.matches("^\\d*(\\.\\d{0,2})?$")) {
					StringProperty textProperty = (StringProperty)observable;
					textProperty.set(oldValue);
				}
			}
		});
		
		this.amountProperty().bind(Bindings.createFloatBinding(() -> {
			String s = getText();
			if(s == null || s.isEmpty()) {
				return 0.0f;
			} else {
				return Float.parseFloat(s);
			}
		}, textProperty()));
	}
	
	public float getAmount() {
		return amount.get();
	}
	
	public void setAmount(float newAmount) {
		//amount.set(newAmount);
		
		// Talk about hacks though...
		this.textProperty().set(new Float(Math.floor(100*newAmount)/100).toString());
	}
	
	public FloatProperty amountProperty() {
		return amount;
	}
	
	public StringProperty nullSafeTextProperty() {
		return nullSafeTextProperty;
	}
}
