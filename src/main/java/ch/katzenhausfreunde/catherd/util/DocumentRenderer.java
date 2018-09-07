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

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.fdf.FDFDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import ch.katzenhausfreunde.catherd.model.Cat;
import ch.katzenhausfreunde.catherd.model.CatGroup;
import ch.katzenhausfreunde.catherd.model.FosterHome;
import ch.katzenhausfreunde.catherd.model.Person;

/**
 * @author thoenis
 * 
 * May be subject to change in the future.
 *
 */
public class DocumentRenderer {
	private List<PDField> fieldsToFlatten = new ArrayList<PDField>();
	
	public boolean renderDocuments(Cat cat, File destination) {
		renderContract(cat, destination);
		return true;
	}
	
	public boolean renderDocuments(CatGroup group, File destination) {
		for(Cat c : group.getCats()) {
			renderDocuments(c, destination);
			fieldsToFlatten.clear();
		}
		return true;
	}
	
	public boolean renderContract(Cat cat, File destination) {
		Path dest = Paths.get(destination.getAbsolutePath());
		
		FosterHome home = CatHerdState.getCatsHome(cat);
		
		File outFile = dest.resolve(cat.getName().toLowerCase().replace(" ", "_") + "_vertrag.pdf").toFile();
				
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL resource = loader.getResource("pdf/contract.pdf");
		File inFile = new File(resource.getFile());
		
		try {
			PDDocument contract = PDDocument.load(inFile);
			PDAcroForm form = contract.getDocumentCatalog().getAcroForm();
			
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
			fillField("Textfeld 173", form, cat.getColor());
			fillField("Geschlecht", form, cat.getSex() != "weiblich" ? "Auswahl1" : "Auswahl2");
			fillField("Textfeld 172", form, cat.getCastratedDate());
			fillField("Textfeld 18", form, cat.getBreed());
			fillField("Stammbaum", form, cat.getLineage() ? "Auswahl1" : "Auswahl2");
			fillField("Freilauf", form, cat.getRunFree() ? "Auswahl1" : "Auswahl2");
			fillField("Einzelkatze", form, cat.getContact() ? "Auswahl2" : "Auswahl1");
			fillField("Textfeld 21", form, cat.getChipNo());
			fillField("Textfeld 22", form, cat.getChipImplentedDate());
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
			if(cat.getCastratedDate() != null) {
				// render castration doc
			}
			fillField("Textfeld 44", form, cat.getNotes());
			fillField("Textfeld 42", form, cat.getSoldDate());
			// Whee weeny oneliners
			fillField("Textfeld 45", form, (fosterParent.getPostalAndTown() != null ? fosterParent.getPostalAndTown() : "").replaceAll("[0-9,. ]", "") + formatDate(cat.getSoldDate()));
			
			
			//form.flatten(fieldsToFlatten, true);
			
			contract.save(outFile);
			contract.close();
		} catch (InvalidPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
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
		return date.format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
	}
}
