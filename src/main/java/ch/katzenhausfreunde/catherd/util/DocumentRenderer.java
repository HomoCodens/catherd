package ch.katzenhausfreunde.catherd.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import ch.katzenhausfreunde.catherd.model.Cat;
import ch.katzenhausfreunde.catherd.model.CatGroup;
import ch.katzenhausfreunde.catherd.model.FosterHome;

/**
 * @author thoenis
 * 
 * May be subject to change in the future.
 *
 */
public class DocumentRenderer {
	private List<PDField> fieldsToFlatten = new ArrayList<PDField>();
	private FosterHome home;
	private CatGroup group;
	private Cat cat;
	
	public DocumentRenderer(FosterHome home, CatGroup group) {
		this.home = home;
		this.group = group;
	}
	
	public DocumentRenderer(FosterHome home, CatGroup group, Cat cat) {
		this(home, group);
		this.cat = cat;
	}
	
	public void renderDocuments(Path destination) {
		renderDocuments(cat, destination);
	}
	
	public void renderDocuments(Cat cat, Path destination) {
		File outFile = destination.resolve(cat.getName().toLowerCase().replace(" ", "_") + "_vertrag.pdf").toFile();
				
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL resource = loader.getResource("resources/pdf/contract.pdf");
		File inFile = new File(resource.getFile());
		
		try {
			PDDocument contract = PDDocument.load(inFile);
			PDAcroForm form = contract.getDocumentCatalog().getAcroForm();
			
			// Fill in all foster home related info
			fillField("Textfeld 1", form, home.getName());
			
			// Fill in all cat related info
			fillField("Textfeld 17", form, cat.getName());
			
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
	}
	
	public void renderAllGroupDocuments(Path destination) {
		for(Cat c : group.getCats()) {
			renderDocuments(c, destination);
			fieldsToFlatten.clear();
		}
	}
	
	private void fillField(String fieldName, PDAcroForm form, String value) throws IOException {
		PDField field = form.getField(fieldName);
		field.setValue(value);
		fieldsToFlatten.add(field);
	}
}
