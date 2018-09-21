package ch.katzenhausfreunde.catherd.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import ch.katzenhausfreunde.catherd.CatHerdMain;
import ch.katzenhausfreunde.catherd.model.Cat;
import ch.katzenhausfreunde.catherd.model.CatGroup;
import ch.katzenhausfreunde.catherd.model.FosterHome;
import ch.katzenhausfreunde.catherd.model.Person;
import ch.katzenhausfreunde.catherd.model.VeterinaryMeasure;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

/**
 * @author thoenis
 * 
 * May be subject to change in the future.
 *
 */
public class DocumentRenderer {
	private IntegerProperty totalDocs = new SimpleIntegerProperty();
	private IntegerProperty doneDocs = new SimpleIntegerProperty();
	private BooleanProperty renderInProgress = new SimpleBooleanProperty(false);
	
	private Task<Void> currentTask;
	private StringProperty currentTaskMessage = new SimpleStringProperty();
	
	private List<PDField> fieldsToFlatten = new ArrayList<PDField>();
	
	public DocumentRenderer() {
		this.renderInProgress.bind(doneDocs.isNotEqualTo(totalDocs));
	}
	
	public void renderDocuments(Cat cat, File destination, ContractType type) {
		_renderDocuments(cat, destination, type, true, true);
	}
	
	public void renderContract(Cat cat, File destination, ContractType type) {
		_renderDocuments(cat, destination, type, true, false);
	}
	
	public void renderDatasheet(Cat cat, File destination) {
		_renderDocuments(cat, destination, null, false, true);
	}
	
	private void _renderDocuments(Cat cat, File destination, ContractType type, boolean renderContract, boolean renderDatasheet) {
		initProgress(renderContract && renderDatasheet ? 2 : 1);
		currentTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				if(renderContract) {
					Platform.runLater(() -> setCurrentTaskMessage("Vertrag für " + cat.getName() + "..."));
					_renderContract(cat, destination, type);
					Platform.runLater(() -> incrementProgress());
				}
				
				if(renderDatasheet) {
					Platform.runLater(() -> setCurrentTaskMessage("Datenblatt für " + cat.getName() + "..."));
					_renderDatasheet(cat, destination);
					Platform.runLater(() -> incrementProgress());
				}
				if(isCancelled()) {
	        		 Platform.runLater(() -> setCurrentTaskMessage("Abgebrochen"));
	        	 } else {
	        		 Platform.runLater(() -> setCurrentTaskMessage("Fertig!"));
	        	 }
				return null;
			}
		};
		Thread t = new Thread(currentTask);
		t.start();
		setDocsPath(destination);
	}
	
	public void renderDocuments(CatGroup group, File destination, ContractType type) {
		_renderDocuments(group, destination, type, true, true);
	}
	
	public void renderContracts(CatGroup group, File destination, ContractType type) {
		_renderDocuments(group, destination, type, true, false);
	}
	
	public void renderDatasheets(CatGroup group, File destination) {
		_renderDocuments(group, destination, null, false, true);
	}
	
	private void _renderDocuments(CatGroup group, File destination, ContractType type, boolean renderContract, boolean renderDatasheet) {
		initProgress((renderContract && renderDatasheet ? 2 : 1)*group.getCats().size());
		currentTask = new Task<Void>() {
	         @Override 
	         protected Void call() throws Exception {
	        	 for(Cat c : group.getCats()) {
	        		 if(isCancelled()) {
	        			 Platform.runLater(() -> doneDocs.set(totalDocs.get()));
	        			 break;
	        		 }
	        		 if(renderContract) {
		        		 Platform.runLater(() -> setCurrentTaskMessage("Vertrag für " + c.getName() + "..."));
		        		 _renderContract(c, destination, type);
		        		 Platform.runLater(() -> incrementProgress());
	        		 }
	        		 
	        		 if(renderDatasheet) {
		        		 Platform.runLater(() -> setCurrentTaskMessage("Datenblatt für " + c.getName() + "..."));
		        		 _renderDatasheet(c, destination);
		        		 Platform.runLater(() -> incrementProgress());
	        		 }
	        	 }
	        	 if(isCancelled()) {
	        		 Platform.runLater(() -> setCurrentTaskMessage("Abgebrochen"));
	        	 } else {
	        		 Platform.runLater(() -> setCurrentTaskMessage("Fertig!"));
	        	 }
	        	 return null;
	         }
	     };
	     Thread t = new Thread(currentTask);
	     t.start();
	     setDocsPath(destination);
	}
	
	private void _renderContract(Cat cat, File destination, ContractType type) {
		fieldsToFlatten.clear();
		
		Path dest = Paths.get(destination.getAbsolutePath());
		
		FosterHome home = CatHerdState.getCatsHome(cat);
		CatGroup group = CatHerdState.getCatsGroup(cat);
		
		File outFile = dest.resolve(fileNameSafeString(group.getName()) + "_" + fileNameSafeString(cat.getName()) + "_vertrag.pdf").toFile();
				
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL resource = loader.getResource("pdf/contract.pdf");
		File inFile = new File(resource.getFile());
		
		try {
			PDDocument contract = PDDocument.load(inFile);
			PDAcroForm form = contract.getDocumentCatalog().getAcroForm();
			
			fillField("Kombinationsfeld 1", form, type == ContractType.RESERVATION ? "(Reservation)" : "(Verkauf)");
			
			// Fill in all foster home related info
			fillField("Textfeld 1", form, home.getName());
			
			Person fosterParent = home.getFosterParent();
			fillField("Textfeld 2", form, fosterParent.getLastName());
			fillField("Textfeld 3", form, fosterParent.getFirstName());
			fillField("Textfeld 4", form, fosterParent.getStreet());
			fillField("Textfeld 5", form, fosterParent.getPostalAndTown());
			fillField("Textfeld 6", form, fosterParent.getPhone());
			fillField("Textfeld 7", form, fosterParent.getMobile());
			fillField("Textfeld 8", form, fosterParent.getEMail());
			
			// Fill in buyer info
			Person buyer = cat.getBuyer();
			fillField("Textfeld 9", form, buyer.getLastName());
			fillField("Textfeld 10", form, buyer.getFirstName());
			fillField("Textfeld 11", form, buyer.getStreet());
			fillField("Textfeld 12", form, buyer.getPostalAndTown());
			fillField("Textfeld 13", form, buyer.getPhone());
			fillField("Textfeld 14", form, buyer.getMobile());
			fillField("Textfeld 15", form, buyer.getEMail());
			
			// Fill in all cat related info
			fillField("Textfeld 16", form, cat.getNewName());
			fillField("Textfeld 17", form, cat.getName());
			fillField("Textfeld 171", form, cat.getDateOfBirth());
			fillField("Textfeld 173", form, cat.getCoat());
			fillField("Geschlecht", form, cat.getSex() != "weiblich" ? "Auswahl1" : "Auswahl2");
			fillField("Textfeld 172", form, cat.getCastratedDate());
			fillField("Textfeld 18", form, cat.getBreed());
			fillField("Stammbaum", form, cat.getLineage() ? "Auswahl1" : "Auswahl2");
			fillField("Freilauf", form, cat.getOutside() ? "Auswahl1" : "Auswahl2");
			fillField("Einzelkatze", form, cat.getContact() ? "Auswahl2" : "Auswahl1");
			fillField("Textfeld 21", form, cat.getChipNo());
			fillField("Textfeld 22", form, cat.getChipImplantedDate());
			fillField("1. SS", form, cat.getFVRVaccination1Date() != null ? "Auswahl1" : "Off");
			fillField("Textfeld 24", form, cat.getFVRVaccination1Date());
			fillField("2. SS", form, cat.getFVRVaccination2Date() != null ? "Auswahl1" : "Off");
			fillField("Textfeld 25", form, cat.getFVRVaccination2Date());
			fillField("1. L", form, cat.getLeucosisVaccination1Date() != null ? "Auswahl1" : "Off");
			fillField("Textfeld 26", form, cat.getLeucosisVaccination1Date());
			fillField("2. L", form, cat.getLeucosisVaccination2Date() != null ? "Auswahl1" : "Off");
			fillField("Textfeld 27", form, cat.getLeucosisVaccination2Date());
			fillField("Textfeld 28", form, cat.getOtherVaccination());
			fillField("Textfeld 29", form, cat.getOtherVaccinationDate());
			fillField("Leuk", form, cat.getLeucosisTestResult() != null ? "Auswahl1" : "Off");
			fillField("Leukose", form, cat.getLeucosisTestResult() == "negativ" ? "Auswahl1" : "Auswahl2");
			fillField("Textfeld 30", form, cat.getLeucosisTestDate());
			fillField("Textfeld 31", form, cat.getOtherTests());
			fillField("Textfeld 32", form, cat.getOtherTestsDate());
			fillField("Textfeld 33", form, cat.getVermifuge1Date());
			fillField("Textfeld 34", form, cat.getVermifuge2Date());
			fillField("Textfeld 35", form, cat.getIllnesses());
			fillField("Textfeld 36", form, cat.getCharacterTraits());
			fillField("Textfeld 37", form, cat.getCharge());
			fillField("Textfeld 38", form, cat.getDownPayment());
			fillField("Textfeld 39", form, cat.getReservedDate());
			fillField("Textfeld 40", form, cat.getRemainingPayment());
			fillField("Textfeld 41", form, cat.getSoldDate());
			fillField("Chipspende", form, cat.getChipDonation() ? "Auswahl1" : "Off");
			fillField("Textfeld 43", form, cat.getDonation());
			fillField("Kastration", form, cat.getCastratedDate() != null ? "Auswahl1" : "Off");
			fillField("Textfeld 44", form, cat.getNotes());
			fillField("Textfeld 42", form, cat.getSoldDate());
			// Whee weeny oneliners
			fillField("Textfeld 45", form, 
					(fosterParent.getPostalAndTown() != null ? fosterParent.getPostalAndTown() : "").replaceAll("[0-9,. ]", "") + 
					formatDate(type == ContractType.RESERVATION ? cat.getReservedDate() : cat.getSoldDate()));
			
			
			form.flatten(fieldsToFlatten, true);
			
			if(cat.getCastratedDate() == null) {
				PDPage castrationPage = _renderCastration(cat, destination);
				contract.addPage(castrationPage);
			}
			
			contract.save(outFile);
			contract.close();
		} catch (InvalidPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private PDPage _renderCastration(Cat cat, File destination) throws InvalidPasswordException, IOException {
		fieldsToFlatten.clear();
		
		FosterHome home = CatHerdState.getCatsHome(cat);
				
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL resource = loader.getResource("pdf/castration.pdf");
		File inFile = new File(resource.getFile());
		
		PDDocument castration = PDDocument.load(inFile);
		PDAcroForm form = castration.getDocumentCatalog().getAcroForm();

		fillField("Textfeld 195", form, castrationDate(cat.getDateOfBirth()));
		
		Person buyer = cat.getBuyer();
		fillField("Textfeld 176", form, buyer.getLastName() + " " + buyer.getFirstName());
		fillField("Textfeld 178", form, buyer.getStreet());
		fillField("Textfeld 179", form, buyer.getPostalAndTown());
		
		fillField("Textfeld 180", form, cat.getNewName());
		fillField("Textfeld 181", form, cat.getName());
		fillField("Textfeld 182", form, cat.getDateOfBirth());
		fillField("Webseite", form, cat.getSex() != "weiblich" ? "Auswahl1" : "Auswahl2"); // Website???
		fillField("Textfeld 183", form, cat.getChipNo());
		// Just to flatten it
		fillField("Textfeld 209", form, "");
		fillField("Textfeld 196", form, home.getName());
		Person fosterParent = home.getFosterParent();
		fillField("Textfeld 197", form, (fosterParent.getPostalAndTown() != null ? fosterParent.getPostalAndTown() : "").replaceAll("[0-9,. ]", "") + formatDate(cat.getSoldDate()));
		
		form.flatten(fieldsToFlatten, true);
		
		return castration.getPage(0);
	}
	
	private void _renderDatasheet(Cat cat, File destination) {
		fieldsToFlatten.clear();
		
		Path dest = Paths.get(destination.getAbsolutePath());
		
		FosterHome home = CatHerdState.getCatsHome(cat);
		CatGroup group = CatHerdState.getCatsGroup(cat);
		
		File outFile = dest.resolve(fileNameSafeString(group.getName()) + "_" + fileNameSafeString(cat.getName()) + "_datenblatt.pdf").toFile();
				
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL resource = loader.getResource("pdf/datasheet.pdf");
		File inFile = new File(resource.getFile());
		
		try {
			PDDocument datasheet = PDDocument.load(inFile);
			PDAcroForm form = datasheet.getDocumentCatalog().getAcroForm();
			
			fillField("Textfeld 1", form, home.getName());
			
			Person fosterParent = home.getFosterParent();
			fillField("Textfeld 2", form, fosterParent.getLastName());
			fillField("Textfeld 3", form, fosterParent.getFirstName());
			
			fillField("Textfeld 215", form, cat.getName());
			fillField("Textfeld 214", form, group.getName());
			fillField("Textfeld 213", form, cat.getDateOfBirth());
			fillField("Geschlecht", form, cat.getSex() != "weiblich" ? "Auswahl1" : "Auswahl2");
			fillField("Textfeld 211", form, cat.getCastratedDate());
			fillField("Textfeld 209", form, cat.getChipNo());
			fillField("Textfeld 208", form, cat.getChipImplantedDate());
			fillField("Textfeld 217", form, cat.getMovedInDate());
			fillField("Textfeld 216", form, cat.getSoldDate());
			
			// Bit ugly due to the confusedly named form fields
			for(int i = 0; i < cat.parasiteMeasures().size(); i++) {
				VeterinaryMeasure vm = cat.parasiteMeasures().get(i);
				if(i == 0) {
						fillField("Textfeld 218", form, vm.getDate());
						fillField("Textfeld 184", form, vm.getCause());
						fillField("Textfeld 195", form, vm.getMeasures());
				} else {
						fillField("Textfeld " + (222 + i - 1), form, vm.getDate());
						fillField("Textfeld " + (219 + i - 1), form, vm.getCause());
						fillField("Textfeld " + (225 + i - 1), form, vm.getMeasures());
				}
			}
			
			for(int i = 0; i < cat.vetMeasures().size(); i++) {
				VeterinaryMeasure vm = cat.vetMeasures().get(i);
				int dateFieldIndex;
				int causeFieldIndex;
				int measureFieldIndex;
				if(i < 4) {
					dateFieldIndex = 239 - 3*i;
					causeFieldIndex = 238 - 3*i;
					measureFieldIndex = 237 - 3*i;
				} else {
					dateFieldIndex = 242 + 3*(i - 4);
					causeFieldIndex = 241 + 3*(i - 4);
					measureFieldIndex = 240 + 3*(i - 4);
				}
				fillField("Textfeld " + dateFieldIndex, form, vm.getDate());
				fillField("Textfeld " + causeFieldIndex, form, vm.getCause());
				fillField("Textfeld " + measureFieldIndex, form, vm.getMeasures());
			}
			
			datasheet.save(outFile);
			datasheet.close();
		} catch (InvalidPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void fillField(String fieldName, PDAcroForm form, String value) throws IOException {
		if(value != null) {
			PDField field = form.getField(fieldName);
			field.setValue(value);
			fieldsToFlatten.add(field);
		}
	}
	
	private void fillField(String fieldName, PDAcroForm form, LocalDate value) throws IOException {
		if(value != null) {
			fillField(fieldName, form, formatDate(value));
		}
	}
	
	private void fillField(String fieldName, PDAcroForm form, float value) throws IOException {
		fillField(fieldName, form, new Float(value).toString());
	}
	
	private String formatDate(LocalDate date) {
		if(date == null) {
			return null;
		}
		return date.format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
	}
	
	private String castrationDate(LocalDate date) {
		if(date == null) {
			return null;
		}
		return formatDate(date.plusMonths(7));
	}
	
	private void initProgress(int nDocs) {
		if(!renderInProgress.get()) {
			totalDocs.set(nDocs);
			doneDocs.set(0);
		}
	}
	
	private void incrementProgress() {
		doneDocs.set(doneDocs.get()+1);
	}
	
	public void cancel() {
		this.currentTask.cancel();
	}
	
	private String fileNameSafeString(String s) {
		if(s == null) {
			return null;
		}
		return s.toLowerCase().replaceAll("[^a-zA-Z0-9]", "").replaceAll(" ", "_");
	}
	
	public static File getDocsPath() {
		Preferences prefs = Preferences.userNodeForPackage(CatHerdMain.class);
		String docsPath = prefs.get("docsPath",  null);
		if(docsPath != null) {
			return new File(docsPath);
		} else {
			return null;
		}
	}
	
	public static void setDocsPath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(CatHerdMain.class);
		if(file != null) {
			prefs.put("docsPath", file.getPath());
		} else {
			prefs.remove("docsPath");
		}
	}
	
	public IntegerProperty doneDocsProperty() {
		return this.doneDocs;
	}
	
	public IntegerProperty totalDocsProperty() {
		return this.totalDocs;
	}
	
	public void setCurrentTaskMessage(String newMessage) {
		this.currentTaskMessage.set(newMessage);
	}
	
	public StringProperty currentTaskMessageProperty() {
		return this.currentTaskMessage;
	}
}
