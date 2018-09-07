package ch.katzenhausfreunde.catherd.view;

import java.util.concurrent.Callable;

import ch.katzenhausfreunde.catherd.model.Cat;
import ch.katzenhausfreunde.catherd.view.customcontrols.MoneyField;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CatEditorController {
	@FXML
	private TextField catName;
	
	@FXML
	private TextField newName; 
	
	@FXML
	private DatePicker dateOfBirth;
	
	@FXML
	private TextField color;
	
	@FXML
	private ChoiceBox<String> sex;
	
	@FXML
	private DatePicker castratedDate;
	
	@FXML
	private TextField breed;
	
	@FXML
	private CheckBox lineage;
	
	@FXML
	private CheckBox runFree;
	
	@FXML
	private CheckBox contact;
	
	@FXML
	private TextField chipNo;

	@FXML
	private DatePicker chipImplantedDate;
	
	@FXML
	private DatePicker leucosisVaccination1Date;
	
	@FXML
	private DatePicker leucosisVaccination2Date;
	
	@FXML
	private DatePicker FVRVaccination1Date;
	
	@FXML
	private DatePicker FVRVaccination2Date;
	
	@FXML
	private TextField otherVaccination;
	
	@FXML
	private DatePicker otherVaccinationDate;
	
	@FXML
	private ChoiceBox<String> leucosisTestResult;
	
	@FXML
	private DatePicker leucosisTestDate;
	
	@FXML
	private TextField otherTests;
	
	@FXML
	private DatePicker otherTestsDate;
	
	@FXML
	private DatePicker vermifuge1Date; // Vermifuge... what a word!
	
	@FXML
	private DatePicker vermifuge2Date;
	
	@FXML
	private TextArea illnesses;
	
	@FXML
	private TextArea characterTraits;
	
	@FXML
	private MoneyField charge;
	private BooleanProperty subAmountChanging;
	private BooleanProperty mainAmountChanging;
	
	@FXML
	private MoneyField downPayment;
	
	@FXML
	private DatePicker reservedDate;
	
	@FXML
	private MoneyField remainingPayment;
	
	@FXML
	private DatePicker soldDate;
	
	@FXML
	private CheckBox chipDonation;
	
	@FXML
	private MoneyField donation;
	
	@FXML
	private TextArea notes;
	
	public CatEditorController() {
	}
	
	@FXML
	private void initialize() {
		sex.getItems().setAll("maennlich", "weiblich");
		leucosisTestResult.getItems().setAll("negativ", "positiv");
		
		subAmountChanging = new SimpleBooleanProperty(false);
		mainAmountChanging = new SimpleBooleanProperty(false);
		
		ChangeListener<String> textFieldNumberifyer = (observable, oldValue, newValue) -> {
			if(newValue == null) {
				return;
			} else {
				if(!newValue.matches("[0-9]*")) {
					StringProperty field = (StringProperty)observable;
					field.set(oldValue);
				}
			}
		};
			
		chipNo.textProperty().addListener(textFieldNumberifyer);
	}
	
	public void setCat(Cat cat) {
		// Could just use two way binding for simplicity but since the objects properties never change...
		
		// Set initial text
		this.catName.setText(cat.getName());
		
		// Bind Cat's name property to the text in the text field.
		cat.nameProperty().bind(this.catName.textProperty());
		
		// Rinse, repeat
		this.newName.setText(cat.getNewName());
		cat.newNameProperty().bind(this.newName.textProperty());
		
		this.dateOfBirth.setValue(cat.getDateOfBirth());
		cat.dateOfBirthProperty().bind(this.dateOfBirth.valueProperty());
		
		this.color.setText(cat.getColor());
		cat.colorProperty().bind(this.color.textProperty());
		
		this.sex.setValue(cat.getSex());
		cat.sexProperty().bind(this.sex.valueProperty());
		
		this.castratedDate.setValue(cat.getCastratedDate());
		cat.castratedDateProperty().bind(this.castratedDate.valueProperty());
		
		this.lineage.setSelected(cat.getLineage());
		cat.lineageProperty().bind(this.lineage.selectedProperty());
		
		this.runFree.setSelected(cat.getRunFree());
		cat.runFreeProperty().bind(this.runFree.selectedProperty());
		
		this.contact.setSelected(cat.getContact());
		cat.contactProperty().bind(this.contact.selectedProperty());
		
		
		this.chipNo.setText(cat.getChipNo());
		cat.chipNoProperty().bind(this.chipNo.textProperty());
		
		this.chipImplantedDate.setValue(cat.getChipImplentedDate());
		cat.chipImplantedDateProperty().bind(this.chipImplantedDate.valueProperty());
		
		this.leucosisVaccination1Date.setValue(cat.getLeucosisVaccination1Date());
		cat.leucosis1DateProperty().bind(this.leucosisVaccination1Date.valueProperty());
		
		this.leucosisVaccination2Date.setValue(cat.getLeucosisVaccination2Date());
		cat.leucosis2DateProperty().bind(this.leucosisVaccination2Date.valueProperty());
		
		this.FVRVaccination1Date.setValue(cat.getFVRVaccination1Date());
		cat.FRVVaccination1DateProperty().bind(this.FVRVaccination1Date.valueProperty());
		
		this.FVRVaccination2Date.setValue(cat.getFVRVaccination2Date());
		cat.FRVVaccination2DateProperty().bind(this.FVRVaccination2Date.valueProperty());
		
		this.otherVaccination.setText(cat.getOtherVaccination());
		cat.otherVaccinationProperty().bind(this.otherVaccination.textProperty());
		
		this.otherVaccinationDate.setValue(cat.getOtherVaccinationDate());
		cat.otherVaccinationDateProperty().bind(this.otherVaccinationDate.valueProperty());
		
		this.leucosisTestResult.setValue(cat.getLeucosisTestResult());
		cat.leucosisTestResultProperty().bind(this.leucosisTestResult.valueProperty());
		
		this.leucosisTestDate.setValue(cat.getLeucosisTestDate());
		cat.leucosisTestDateProperty().bind(this.leucosisTestDate.valueProperty());
		
		this.otherTests.setText(cat.getOtherTests());
		cat.otherTestsProperty().bind(this.otherTests.textProperty());
		
		this.otherTestsDate.setValue(cat.getOtherTestsDate());
		cat.otherTestsDateProperty().bind(this.otherTestsDate.valueProperty());
		
		this.vermifuge1Date.setValue(cat.getVermifuge1Date());
		cat.vermifuge1DateProperty().bind(this.vermifuge1Date.valueProperty());
		
		this.vermifuge2Date.setValue(cat.getVermifuge2Date());
		cat.vermifuge2DateProperty().bind(this.vermifuge2Date.valueProperty());
		
		this.illnesses.setText(cat.getIllnesses());
		cat.illnessesProperty().bind(this.illnesses.textProperty());
		
		this.characterTraits.setText(cat.getCharacterTraits());
		cat.characterTraitsProperty().bind(this.characterTraits.textProperty());
		
		this.charge.setAmount(cat.getCharge());
		this.charge.amountProperty().addListener((observable, oldValue, newValue) -> {
			if(!subAmountChanging.get() && newValue != null) {
				mainAmountChanging.set(true);
				downPayment.setAmount((float)newValue/2);
				remainingPayment.setAmount((float)newValue/2);
				mainAmountChanging.set(false);
			}
		});
		/*
		 * Ah'll be Bach! -Arnie
		this.charge.amountProperty().bind(Bindings.when(subAmountsHot).then(this.downPayment.amountProperty().add(this.remainingPayment.amountProperty())).otherwise(3.0f));
		Bindings.when(subAmountsHot).then(3.0f);
		this.charge.amountProperty().bind(Bindings.createFloatBinding(() -> {
			return 3.0f;
		}, this.downPayment.amountProperty(), this.remainingPayment.amountProperty()));*/
		cat.chargeProperty().bind(charge.amountProperty());

		this.downPayment.setAmount(cat.getDownPayment());
		this.downPayment.amountProperty().addListener((observable, oldValue, newValue) -> {
			if(!mainAmountChanging.get() && newValue != null) {
				subAmountChanging.set(true);
				this.charge.setAmount((float)newValue + remainingPayment.getAmount());
				subAmountChanging.set(false);
			}
		});
		cat.downPaymentProperty().bind(downPayment.amountProperty());
		
		this.reservedDate.setValue(cat.getReservedDate());;
		cat.reservedDateProperty().bind(this.reservedDate.valueProperty());
		
		this.remainingPayment.setAmount(cat.getRemainingPayment());
		this.remainingPayment.amountProperty().addListener((observable, oldValue, newValue) -> {
			if(!mainAmountChanging.get() && newValue != null) {
				subAmountChanging.set(true);
				this.charge.setAmount((float)newValue + downPayment.getAmount());
				subAmountChanging.set(false);
			}
		});
		cat.remainingPaymentProperty().bind(remainingPayment.amountProperty());
		
		this.soldDate.setValue(cat.getSoldDate());
		cat.soldDateProperty().bind(soldDate.valueProperty());
		
		this.chipDonation.setSelected(cat.getChipDonation());
		cat.chipDonationProperty().bind(this.chipDonation.selectedProperty());
		
		this.donation.setAmount(cat.getDonation());
		cat.donationProperty().bind(donation.amountProperty());
		
		this.notes.setText(cat.getNotes());
		cat.notesProperty().bind(this.notes.textProperty());
	}
}
