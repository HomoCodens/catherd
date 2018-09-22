package ch.katzenhausfreunde.catherd.view.customcontrols;

import ch.katzenhausfreunde.catherd.model.Person;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class PersonController {
	@FXML
	private TextField firstName;
	
	@FXML
	private TextField lastName;
	
	@FXML
	private TextField street;
	
	@FXML
	private TextField postalAndTown;
	
	@FXML
	private TextField phone;
	
	@FXML
	private TextField mobile;
	
	@FXML
	TextField eMail;
	
	@FXML 
	private void initialize() {
		ChangeListener<String> textFieldPhoneNumberifyer = (observable, oldValue, newValue) -> {
			if(newValue == null) {
				return;
			} else {
				if(!newValue.matches("^\\+?(?:\\d+ ?)*")) {
					StringProperty field = (StringProperty)observable;
					field.set(oldValue);
				}
			}
		};
			
		phone.textProperty().addListener(textFieldPhoneNumberifyer);
		mobile.textProperty().addListener(textFieldPhoneNumberifyer);
	}
	
	public void setPerson(Person person) {
		firstName.setText(person.getFirstName());
		person.firstNameProperty().bind(firstName.textProperty());
		
		lastName.setText(person.getLastName());
		person.lastNameProperty().bind(lastName.textProperty());
		
		street.setText(person.getStreet());
		person.streetProperty().bind(street.textProperty());
		
		postalAndTown.setText(person.getPostalAndTown());
		person.postalAndTownProperty().bind(postalAndTown.textProperty());
		
		phone.setText(person.getPhone());
		person.phoneProperty().bind(phone.textProperty());
		
		mobile.setText(person.getMobile());
		person.mobileProperty().bind(mobile.textProperty());
		
		eMail.setText(person.getEMail());
		person.eMailProperty().bind(eMail.textProperty());
	}
}
