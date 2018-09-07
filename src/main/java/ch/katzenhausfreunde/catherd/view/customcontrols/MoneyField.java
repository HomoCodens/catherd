package ch.katzenhausfreunde.catherd.view.customcontrols;

import java.text.NumberFormat;
import java.util.Locale;

import javafx.beans.binding.Bindings;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;

public class MoneyField extends TextField {
	private FloatProperty amount;
	private StringProperty nullSafeTextProperty;
	
	public MoneyField() {
		super();
		System.out.println("Yay, money!");
		
		this.amount = new SimpleFloatProperty();
		
		this.nullSafeTextProperty = new SimpleStringProperty(null);
		this.nullSafeTextProperty.bind(Bindings.when(textProperty().isNotNull()).then(textProperty()).otherwise("0.00"));
		
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
		
		// 'cause GERMAN doesn't recognize . as decimal. And we lurvs us some CANADA.
		// Nüp, can't handle NULL apparently...
		this.textProperty().bindBidirectional(amountProperty(), NumberFormat.getNumberInstance(Locale.CANADA));
		//this.textProperty().bindBidirectional(this.nullSafeTextProperty());
		//this.nullSafeTextProperty().bindBidirectional(amountProperty(), NumberFormat.getNumberInstance(Locale.CANADA));
		/*StringConverter<Float> bla = new StringConverter<Float>() {

			@Override
			public Float fromString(String string) {
				if(string == null) {
					return null;
				} else {
					return Float.parseFloat(string);
				}
			}

			@Override
			public String toString(Float object) {
				return object.toString();
			}
			
		};
		
		this.textProperty().bindBidirectional(amountProperty()., bla);*/
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
	
	public StringProperty nullSafeTextProperty() {
		return nullSafeTextProperty;
	}
}
