package ch.katzenhausfreunde.catherd.view;

import java.io.IOException;
import java.util.ArrayList;

import ch.katzenhausfreunde.catherd.CatHerdMain;
import ch.katzenhausfreunde.catherd.model.Cat;
import ch.katzenhausfreunde.catherd.model.FinancialItem;
import ch.katzenhausfreunde.catherd.model.VeterinaryMeasure;
import ch.katzenhausfreunde.catherd.view.customcontrols.LengthLimitedTextArea;
import ch.katzenhausfreunde.catherd.view.customcontrols.MoneyField;
import ch.katzenhausfreunde.catherd.view.customcontrols.PersonController;
import ch.katzenhausfreunde.catherd.view.customcontrols.VeterinaryMeasureEditor;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
	private AnchorPane illnessesContainer;
	private LengthLimitedTextArea illnesses;
	
	@FXML
	private VBox parasiteMeasureContainer;
	
	@FXML
	private Button newParasiteMeasure;
	
	@FXML
	private VBox vetMeasureContainer;
	
	@FXML
	private Button newVetMeasure;
	
	@FXML
	AnchorPane buyerPane;
	private PersonController buyer;
	
	@FXML
	private AnchorPane characterTraitsContainer;
	private LengthLimitedTextArea characterTraits;
	
	@FXML
	private MoneyField charge;
	private BooleanProperty subAmountChanging;
	private BooleanProperty mainAmountChanging;
	
	@FXML
	private MoneyField downPayment;
	
	@FXML
	private DatePicker movedInDate;
	
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
	private AnchorPane notesContainer;
	private LengthLimitedTextArea notes;
	
	public CatEditorController() {
	}
	
	@FXML
	private void initialize() {
		sex.getItems().setAll("männlich", "weiblich");
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
		
		//Easier than fiddling around with scene builder and dependencies...
		characterTraits = new LengthLimitedTextArea(140);
		characterTraitsContainer.getChildren().add(characterTraits);
		AnchorPane.setTopAnchor(characterTraits, 0.0);
		AnchorPane.setLeftAnchor(characterTraits, 0.0);
		AnchorPane.setRightAnchor(characterTraits, 0.0);
		
		illnesses = new LengthLimitedTextArea(195);
		illnessesContainer.getChildren().add(illnesses);
		AnchorPane.setTopAnchor(illnesses, 0.0);
		AnchorPane.setLeftAnchor(illnesses, 0.0);
		AnchorPane.setRightAnchor(illnesses, 0.0);
		
		notes = new LengthLimitedTextArea(325);
		notesContainer.getChildren().add(notes);
		AnchorPane.setTopAnchor(notes, 0.0);
		AnchorPane.setLeftAnchor(notes, 0.0);
		AnchorPane.setRightAnchor(notes, 0.0);
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(CatHerdMain.class.getResource("view/customcontrols/Person.fxml"));
			VBox buyerEditor = (VBox) loader.load();
			
			buyer = loader.getController();
			
			buyerPane.getChildren().add(buyerEditor);
			AnchorPane.setTopAnchor(buyerEditor, 0.0);
			AnchorPane.setRightAnchor(buyerEditor, 0.0);
			AnchorPane.setLeftAnchor(buyerEditor, 0.0);
		} catch(IOException e) {
			e.printStackTrace();
		}
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
		
		this.color.setText(cat.getCoat());
		cat.coatProperty().bind(this.color.textProperty());
		
		this.sex.setValue(cat.getSex());
		cat.sexProperty().bind(this.sex.valueProperty());
		
		this.castratedDate.setValue(cat.getCastratedDate());
		cat.castratedDateProperty().bind(this.castratedDate.valueProperty());
		
		this.breed.setText(cat.getBreed());
		cat.breedProperty().bind(this.breed.textProperty());
		
		this.lineage.setSelected(cat.getLineage());
		cat.lineageProperty().bind(this.lineage.selectedProperty());
		
		this.runFree.setSelected(cat.getOutside());
		cat.outsideProperty().bind(this.runFree.selectedProperty());
		
		this.contact.setSelected(cat.getContact());
		cat.contactProperty().bind(this.contact.selectedProperty());
		
		
		this.chipNo.setText(cat.getChipNo());
		cat.chipNoProperty().bind(this.chipNo.textProperty());
		
		this.chipImplantedDate.setValue(cat.getChipImplantedDate());
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
		
		if(cat.parasiteMeasures().size() == 0) {
			VeterinaryMeasure vm = new VeterinaryMeasure();
			addParasiteMeasure(vm, cat);
			cat.addParasiteMeasure(vm);
		} else {
			ArrayList<VeterinaryMeasure> vms = new ArrayList<VeterinaryMeasure>();
			vms.addAll(cat.parasiteMeasures());
			for(int i = 0; i < vms.size() && i < 4; i++) {
				addParasiteMeasure(vms.get(i), cat);
			}
		}
		this.newParasiteMeasure.disableProperty().bind(Bindings.size(cat.parasiteMeasures()).greaterThanOrEqualTo(4));
		this.newParasiteMeasure.setOnAction((e) -> {
			VeterinaryMeasure vm = new VeterinaryMeasure();
			cat.addParasiteMeasure(vm);
			addParasiteMeasure(vm, cat);
		});

		if(cat.vetMeasures().size() == 0) {
			VeterinaryMeasure vm = new VeterinaryMeasure();
			addVetMeasure(vm, cat);
			cat.addVetMeasure(vm);
		} else {
			ArrayList<VeterinaryMeasure> vms = new ArrayList<VeterinaryMeasure>();
			vms.addAll(cat.vetMeasures());
			for(int i = 0; i < vms.size() && i < 8; i++) {
				addVetMeasure(vms.get(i), cat);
			}
		}
		this.newVetMeasure.disableProperty().bind(Bindings.size(cat.vetMeasures()).greaterThanOrEqualTo(8));
		this.newVetMeasure.setOnAction((e) -> {
			VeterinaryMeasure vm = new VeterinaryMeasure();
			cat.addVetMeasure(vm);
			addVetMeasure(vm, cat);
		});
		
		this.buyer.setPerson(cat.getBuyer());
		
		this.characterTraits.setText(cat.getCharacterTraits());
		cat.characterTraitsProperty().bind(this.characterTraits.textProperty());
		
		FinancialItem catCharge = cat.getCharge();
		this.charge.setAmount(catCharge.getAmount());
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
		catCharge.amountProperty().bind(charge.amountProperty());

		FinancialItem catDownPayment = cat.getDownPayment();
		this.downPayment.setAmount(catDownPayment.getAmount());
		this.downPayment.amountProperty().addListener((observable, oldValue, newValue) -> {
			if(!mainAmountChanging.get() && newValue != null) {
				subAmountChanging.set(true);
				this.charge.setAmount((float)newValue + remainingPayment.getAmount());
				subAmountChanging.set(false);
			}
		});
		catDownPayment.amountProperty().bind(downPayment.amountProperty());
		
		this.reservedDate.setValue(cat.getReservedDate());;
		cat.reservedDateProperty().bind(this.reservedDate.valueProperty());
		
		this.movedInDate.setValue(cat.getMovedInDate());
		cat.movedInDateProperty().bind(this.movedInDate.valueProperty());
		
		FinancialItem catRemainingPayment = cat.getRemainingPayment();
		this.remainingPayment.setAmount(catRemainingPayment.getAmount());
		this.remainingPayment.amountProperty().addListener((observable, oldValue, newValue) -> {
			if(!mainAmountChanging.get() && newValue != null) {
				subAmountChanging.set(true);
				this.charge.setAmount((float)newValue + downPayment.getAmount());
				subAmountChanging.set(false);
			}
		});
		catRemainingPayment.amountProperty().bind(remainingPayment.amountProperty());
		
		this.soldDate.setValue(cat.getSoldDate());
		cat.soldDateProperty().bind(soldDate.valueProperty());
		
		FinancialItem catChipDonation = cat.getChipDonation();
		this.chipDonation.setSelected(catChipDonation.getAmount() > 0);
		catChipDonation.amountProperty().bind(Bindings.createFloatBinding(() -> this.chipDonation.isSelected() ? 20.0f : 0.0f, this.chipDonation.selectedProperty()));
		
		FinancialItem catDonation = cat.getDonation();
		this.donation.setAmount(catDonation.getAmount());
		catDonation.amountProperty().bind(donation.amountProperty());
		
		this.notes.setText(cat.getNotes());
		cat.notesProperty().bind(this.notes.textProperty());
		
	}
	
	private void addParasiteMeasure(final VeterinaryMeasure vm, Cat cat) {
		vm.arm();
		
		int nMeasures = parasiteMeasureContainer.getChildren().size();
		VeterinaryMeasureEditor vme = new VeterinaryMeasureEditor("Eintrag " + (nMeasures + 1));
		vme.setMeasure(vm);
		vme.setOnRemove((re) -> {
			cat.removeParasiteMeasure(vm);
			parasiteMeasureContainer.getChildren().remove(vme);
			relabelParasiteMeasures();
		});
		parasiteMeasureContainer.getChildren().add(vme);
	}
	
	private void addVetMeasure(final VeterinaryMeasure vm, Cat cat) {
		vm.arm();

		int nMeasures = vetMeasureContainer.getChildren().size();
		VeterinaryMeasureEditor vme = new VeterinaryMeasureEditor("Eintrag " + (nMeasures + 1));
		vme.setMeasure(vm);
		vme.setOnRemove((re) -> {
			cat.removeVetMeasure(vm);
			vetMeasureContainer.getChildren().remove(vme);
			relabelVetMeasures();
		});
		vetMeasureContainer.getChildren().add(vme);
	}
	
	private void relabelParasiteMeasures() {
		for(int i = 0; i < parasiteMeasureContainer.getChildren().size(); i++) {
			VeterinaryMeasureEditor vme = (VeterinaryMeasureEditor)parasiteMeasureContainer.getChildren().get(i);
			vme.setLabel("Eintrag " + (i + 1));
		}
	}
	
	private void relabelVetMeasures() {
		for(int i = 0; i < vetMeasureContainer.getChildren().size(); i++) {
			VeterinaryMeasureEditor vme = (VeterinaryMeasureEditor)vetMeasureContainer.getChildren().get(i);
			vme.setLabel("Eintrag " + (i + 1));
		}
	}
}
