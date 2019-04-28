package ch.katzenhausfreunde.catherd.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ch.katzenhausfreunde.catherd.util.CatHerdState;
import ch.katzenhausfreunde.catherd.util.LocalDateAdapter;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author thoenis
 *
 * 600 lines of properties *sigh*
 *
 */
public class Cat extends Nameable {
	private StringProperty newName;
	private ObjectProperty<LocalDate> dateOfBirth;
	private StringProperty coat;
	private StringProperty sex;
	private ObjectProperty<LocalDate> castratedDate;
	private BooleanProperty castrationPending;
	private StringProperty breed;
	private BooleanProperty lineage;
	private StringProperty outside;
	private StringProperty contact;
	private StringProperty chipNo;
	private ObjectProperty<LocalDate> chipImplantedDate;
	private ObjectProperty<LocalDate> leucosisVaccination1Date;
	private ObjectProperty<LocalDate> leucosisVaccination2Date;
	private ObjectProperty<LocalDate> FVRVaccination1Date;
	private ObjectProperty<LocalDate> FVRVaccination2Date;
	private StringProperty otherVaccination;
	private ObjectProperty<LocalDate> otherVaccinationDate;
	private StringProperty leucosisTestResult;
	private ObjectProperty<LocalDate> leucosisTestDate;
	private StringProperty otherTests;
	private ObjectProperty<LocalDate> otherTestsDate;
	private ObjectProperty<LocalDate> vermifuge1Date; // Vermifuge... what a word!
	private ObjectProperty<LocalDate> vermifuge2Date;
	private StringProperty illnesses;
	@XmlElement(name = "parasiteMeasures")
	private ObservableList<VeterinaryMeasure> parasiteMeasures;
	@XmlElement(name = "vetMeasures")
	private ObservableList<VeterinaryMeasure> vetMeasures;
	private Person buyer;
	private StringProperty characterTraits;
	private FinancialItem charge;
	private FinancialItem downPayment;
	private ObjectProperty<LocalDate> movedInDate;
	private ObjectProperty<LocalDate> reservedDate;
	private FinancialItem remainingPayment;
	private ObjectProperty<LocalDate> soldDate;
	private FinancialItem chipDonation;
	private FinancialItem donation;
	private StringProperty notes;
	
	/**
	 * 
	 */
	public Cat() {
		this("Mittens");
	}
	
	/**
	 * @param name
	 */
	public Cat(String name) {
		super(name);
		
		newName = new SimpleStringProperty(null);
		dateOfBirth = new SimpleObjectProperty<LocalDate>(null);
		coat = new SimpleStringProperty(null);
		sex = new SimpleStringProperty(null);
		castratedDate = new SimpleObjectProperty<LocalDate>(null);
		castrationPending = new SimpleBooleanProperty(false);
		breed = new SimpleStringProperty(null);
		lineage = new SimpleBooleanProperty(false);
		outside = new SimpleStringProperty(null);
		contact = new SimpleStringProperty(null);
		chipNo = new SimpleStringProperty();
		chipImplantedDate = new SimpleObjectProperty<LocalDate>(null);
		leucosisVaccination1Date = new SimpleObjectProperty<LocalDate>(null);
		leucosisVaccination2Date = new SimpleObjectProperty<LocalDate>(null);
		FVRVaccination1Date = new SimpleObjectProperty<LocalDate>(null);
		FVRVaccination2Date = new SimpleObjectProperty<LocalDate>(null);
		otherVaccination = new SimpleStringProperty(null);
		otherVaccinationDate = new SimpleObjectProperty<LocalDate>(null);
		leucosisTestResult = new SimpleStringProperty(null);
		leucosisTestDate = new SimpleObjectProperty<LocalDate>(null);
		otherTests = new SimpleStringProperty(null);;
		otherTestsDate = new SimpleObjectProperty<LocalDate>(null);
		vermifuge1Date = new SimpleObjectProperty<LocalDate>(null); // Vermifuge... what a word!
		vermifuge2Date = new SimpleObjectProperty<LocalDate>(null);
		illnesses = new SimpleStringProperty(null);
		parasiteMeasures = FXCollections.observableArrayList();
		vetMeasures = FXCollections.observableArrayList();
		buyer = new Person();
		characterTraits = new SimpleStringProperty(null);
		charge = new FinancialItem();
		downPayment = new FinancialItem();
		movedInDate = new SimpleObjectProperty<LocalDate>(null);
		reservedDate = new SimpleObjectProperty<LocalDate>(null);
		remainingPayment = new FinancialItem();
		soldDate = new SimpleObjectProperty<LocalDate>(null);
		chipDonation = new FinancialItem();
		donation = new FinancialItem();
		notes = new SimpleStringProperty();
	}
	
	public void arm() {
		super.arm();
				
		newName.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore()); 
		dateOfBirth.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		coat.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		sex.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		castratedDate.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		castrationPending.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore()); 
		breed.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		lineage.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		outside.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		contact.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		chipNo.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		chipImplantedDate.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		leucosisVaccination1Date.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		leucosisVaccination2Date.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		FVRVaccination1Date.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		FVRVaccination2Date.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		otherVaccination.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		otherVaccinationDate.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore()); 
		leucosisTestResult.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		leucosisTestDate.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		otherTests.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		otherTestsDate.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		vermifuge1Date.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore()); // Vermifuge... what a word!
		vermifuge2Date.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		illnesses.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		parasiteMeasures.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				CatHerdState.touchStore();
			}
		});
		for(VeterinaryMeasure vm : parasiteMeasures) {
			vm.arm();
		}
		vetMeasures.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				CatHerdState.touchStore();
			}
		});
		for(VeterinaryMeasure vm : vetMeasures) {
			vm.arm();
		}
		buyer.arm();
		characterTraits.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		charge.arm();
		downPayment.arm();
		movedInDate.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		reservedDate.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		remainingPayment.arm();
		soldDate.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
		chipDonation.arm();
		donation.arm();
		notes.addListener((observable, oldValue, newValue) -> CatHerdState.touchStore());
	}

	public final String getNewName() {
		return newName.get();
	}

	public final void setNewName(String newName) {
		this.newName.set(newName);
	}

	public final StringProperty newNameProperty() {
		return newName;
	}
	
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	@XmlElement(name = "dateOfBirth")
	public final LocalDate getDateOfBirth() {
		return dateOfBirth.get();
	}

	public final void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth.set(dateOfBirth);
	}

	public final ObjectProperty<LocalDate> dateOfBirthProperty() {
		return dateOfBirth;
	}
	
	public final String getCoat() {
		return coat.get();
	}

	public final void setCoat(String coat) {
		this.coat.set(coat);
	}

	public final StringProperty coatProperty() {
		return coat;
	}
	
	public final String getSex() {
		return sex.get();
	}

	public final void setSex(String sex) {
		this.sex.set(sex);
	}

	public final StringProperty sexProperty() {
		return sex;
	}
	
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	@XmlElement(name = "castratedDate")
	public final LocalDate getCastratedDate() {
		return castratedDate.get();
	}

	public final void setCastratedDate(LocalDate castratedDate) {
		this.castratedDate.set(castratedDate);
	}

	public final ObjectProperty<LocalDate> castratedDateProperty() {
		return castratedDate;
	}
	
	public final boolean getCastrationPending() {
		return castrationPending.get();
	}
	
	public final void setCastrationPending(boolean castrationPending) {
		this.castrationPending.set(castrationPending);
	}
	
	public final BooleanProperty castrationPendingProperty() {
		return castrationPending;
	}
	
	public final String getBreed() {
		return breed.get();
	}

	public final void setBreed(String breed) {
		this.breed.set(breed);
	}

	public final StringProperty breedProperty() {
		return breed;
	}
	
	public final boolean getLineage() {
		return lineage.get();
	}

	public final void setLineage(boolean lineage) {
		this.lineage.set(lineage);
	}

	public final BooleanProperty lineageProperty() {
		return lineage;
	}
	
	public final String getOutside() {
		return outside.get();
	}

	public final void setOutside(String outside) {
		this.outside.set(outside);
	}

	public final StringProperty outsideProperty() {
		return outside;
	}
	
	public final String getContact() {
		return contact.get();
	}

	public final void setContact(String contact) {
		this.contact.set(contact);
	}
	
	public final StringProperty contactProperty() {
		return contact;
	}
	
	public final String getChipNo() {
		return chipNo.get();
	}

	public final void setChipNo(String chipNo) {
		this.chipNo.set(chipNo);
	}
	
	public final StringProperty chipNoProperty() {
		return chipNo;
	}
	
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	@XmlElement(name = "chipImplantedDate")
	public final LocalDate getChipImplantedDate() {
		return chipImplantedDate.get();
	}

	public final void setChipImplantedDate(LocalDate chipImplantedDate) {
		this.chipImplantedDate.set(chipImplantedDate);
	}
	
	public final ObjectProperty<LocalDate> chipImplantedDateProperty() {
		return chipImplantedDate;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	@XmlElement(name = "leucosisVaccination1Date")
	public final LocalDate getLeucosisVaccination1Date() {
		return leucosisVaccination1Date.get();
	}

	public final void setLeucosisVaccination1Date(LocalDate leucosisVaccination1Date) {
		this.leucosisVaccination1Date.set(leucosisVaccination1Date);
	}
	
	public final ObjectProperty<LocalDate> leucosis1DateProperty() {
		return this.leucosisVaccination1Date;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	@XmlElement(name = "leucosisVaccination2Date")
	public final LocalDate getLeucosisVaccination2Date() {
		return leucosisVaccination2Date.get();
	}

	public final void setLeucosisVaccination2Date(LocalDate leucosisVaccination2Date) {
		this.leucosisVaccination2Date.set(leucosisVaccination2Date);
	}

	public final ObjectProperty<LocalDate> leucosis2DateProperty() {
		return this.leucosisVaccination2Date;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	@XmlElement(name = "FVRVaccination1Date")
	public final LocalDate getFVRVaccination1Date() {
		return FVRVaccination1Date.get();
	}

	public final void setFVRVaccination1Date(LocalDate FVRVaccination1Date) {
		this.FVRVaccination1Date.set(FVRVaccination1Date);
	}
	
	public final ObjectProperty<LocalDate> FRVVaccination1DateProperty() {
		return this.FVRVaccination1Date;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	@XmlElement(name = "FVRVaccination2Date")
	public final LocalDate getFVRVaccination2Date() {
		return FVRVaccination2Date.get();
	}

	public final void setFVRVaccination2Date(LocalDate FVRVaccination2Date) {
		this.FVRVaccination2Date.set(FVRVaccination2Date);
	}

	public final ObjectProperty<LocalDate> FRVVaccination2DateProperty() {
		return this.FVRVaccination2Date;
	}
	
	public final String getOtherVaccination() {
		return otherVaccination.get();
	}
	
	public final void setOtherVaccination(String otherVaccination) {
		this.otherVaccination.set(otherVaccination);
	}
	
	public final StringProperty otherVaccinationProperty() {
		return this.otherVaccination;
	}
	
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	@XmlElement(name = "otherVaccinationDate")
	public final LocalDate getOtherVaccinationDate() {
		return otherVaccinationDate.get();
	}

	public final void setOtherVaccinationDate(LocalDate otherVaccinationDate) {
		this.otherVaccinationDate.set(otherVaccinationDate);
	}

	public final ObjectProperty<LocalDate> otherVaccinationDateProperty() {
		return otherVaccinationDate;
	}
	
	public final String getLeucosisTestResult() {
		return leucosisTestResult.get();
	}

	public final void setLeucosisTestResult(String leucosisTestResult) {
		this.leucosisTestResult.set(leucosisTestResult);
	}

	public final StringProperty leucosisTestResultProperty() {
		return leucosisTestResult;
	}
	
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	@XmlElement(name = "leucosisTestDate")
	public final LocalDate getLeucosisTestDate() {
		return leucosisTestDate.get();
	}

	public final void setLeucosisTestDate(LocalDate leucosisTestDate) {
		this.leucosisTestDate.set(leucosisTestDate);
	}

	public final ObjectProperty<LocalDate> leucosisTestDateProperty() {
		return leucosisTestDate;
	}
	
	public final String getOtherTests() {
		return otherTests.get();
	}

	public final void setOtherTests(String otherTests) {
		this.otherTests.set(otherTests);
	}
	
	public final StringProperty otherTestsProperty() {
		return otherTests;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	@XmlElement(name = "otherTestsDate")
	public final LocalDate getOtherTestsDate() {
		return otherTestsDate.get();
	}

	public final void setOtherTestsDate(LocalDate otherTestsDate) {
		this.otherTestsDate.set(otherTestsDate);
	}
	
	public final ObjectProperty<LocalDate> otherTestsDateProperty() {
		return otherTestsDate;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	@XmlElement(name = "vermifuge1Date")
	public final LocalDate getVermifuge1Date() {
		return vermifuge1Date.get();
	}

	public final void setVermifuge1Date(LocalDate vermifuge1Date) {
		this.vermifuge1Date.set(vermifuge1Date);
	}

	public final ObjectProperty<LocalDate> vermifuge1DateProperty() {
		return vermifuge1Date;
	}
	
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	@XmlElement(name = "vermifuge2Date")
	public final LocalDate getVermifuge2Date() {
		return vermifuge2Date.get();
	}

	public final void setVermifuge2Date(LocalDate vermifuge2Date) {
		this.vermifuge2Date.set(vermifuge2Date);
	}

	public final ObjectProperty<LocalDate> vermifuge2DateProperty() {
		return vermifuge2Date;
	}
	
	public final String getIllnesses() {
		return illnesses.get();
	}

	public final void setIllnesses(String illnesses) {
		this.illnesses.set(illnesses);
	}
	
	public final StringProperty illnessesProperty() {
		return illnesses;
	}

	public final ObservableList<VeterinaryMeasure> parasiteMeasures() {
		return parasiteMeasures;
	}
	
	public final ObservableList<VeterinaryMeasure> vetMeasures() {
		return vetMeasures;
	}
	
	public final Person getBuyer() {
		return buyer;
	}
	
	public final void setBuyer(Person buyer) {
		this.buyer = buyer;
	}
	
	public final String getCharacterTraits() {
		return characterTraits.get();
	}

	public final void setCharacterTraits(String characterTraits) {
		this.characterTraits.set(characterTraits);
	}
	
	public final StringProperty characterTraitsProperty() {
		return characterTraits;
	}

	public final FinancialItem getCharge() {
		return charge;
	}
	
	public final void setCharge(FinancialItem charge) {
		this.charge = charge;
	}
	
	public final FinancialItem getDownPayment() {
		return downPayment;
	}
	
	public final void setDownPayment(FinancialItem downPayment) {
		this.downPayment = downPayment;
	}
	
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	@XmlElement(name = "movedInDate")
	public final LocalDate getMovedInDate() {
		return movedInDate.get();
	}
	
	public final void setMovedInDate(LocalDate reservedDate) {
		this.movedInDate.set(reservedDate);
	}
	
	public final ObjectProperty<LocalDate> movedInDateProperty() {
		return movedInDate;
	}
	
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	@XmlElement(name = "reservedDate")
	public final LocalDate getReservedDate() {
		return reservedDate.get();
	}

	public final void setReservedDate(LocalDate reservedDate) {
		this.reservedDate.set(reservedDate);
	}
	
	public final ObjectProperty<LocalDate> reservedDateProperty() {
		return reservedDate;
	}

	public final FinancialItem getRemainingPayment() {
		return remainingPayment;
	}
	
	public final void setRemainingPayment(FinancialItem remainingPayment) {
		this.remainingPayment = remainingPayment;
	}
	
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	@XmlElement(name = "soldDate")
	public final LocalDate getSoldDate() {
		return soldDate.get();
	}

	public final void setSoldDate(LocalDate soldDate) {
		this.soldDate.set(soldDate);
	}
	
	public final ObjectProperty<LocalDate> soldDateProperty() {
		return soldDate;
	}

	public final FinancialItem getChipDonation() {
		return chipDonation;
	}

	public final void setChipDonation(FinancialItem chipDonation) {
		this.chipDonation = chipDonation;
	}
	
	public final FinancialItem getDonation() {
		return donation;
	}
	
	public final void setDonation(FinancialItem donation) {
		this.donation = donation;
	}
	
	public final String getNotes() {
		return notes.get();
	}

	public final void setNotes(String notes) {
		this.notes.set(notes);
	}

	public final StringProperty notesProperty() {
		return notes;
	}

	public void addParasiteMeasure(VeterinaryMeasure vm) {
		parasiteMeasures.add(vm);
	}
	
	public void removeParasiteMeasure(VeterinaryMeasure vm) {
		parasiteMeasures.remove(vm);
	}
	
	public void addVetMeasure(VeterinaryMeasure vm) {
		vetMeasures.add(vm);
	}
	
	public void removeVetMeasure(VeterinaryMeasure vm) {
		vetMeasures.remove(vm);
	}
}
