package ch.katzenhausfreunde.catherd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;

import ch.katzenhausfreunde.catherd.CatHerdMain;
import ch.katzenhausfreunde.catherd.model.CatHerdStore;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Static class that handles loading and saving the application data store from/to disk.
 * We chose JSON to represent the data for readability and possible future use on the web.
 * 
 * @author thoenis
 *
 */
public class CatHerdDiskStorage {
	private static StringProperty loadedPath = new SimpleStringProperty(null);
	
	/**
	 * Get the Path last used to save data.
	 * 
	 * @return Path to the catherd data file
	 */
	public static File getSavePath() {
		Preferences prefs = Preferences.userNodeForPackage(CatHerdMain.class);
		String filePath = prefs.get("filePath",  null);
		if(filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}
	
	/**
	 * Store the last used data Path to the application preferences.
	 * 
	 * @param file The Path to store on the system.
	 */
	public static void setSavePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(CatHerdMain.class);
		if(file != null) {
			prefs.put("filePath", file.getPath());
		} else {
			prefs.remove("filePath");
		}
	}
	
	/**
	 * Load the marshalled CatHerdStore from the given file.
	 * 
	 * @param file File pointing to the JSON file to be loaded
	 * @return The CatHerdStore read from the file.
	 */
	public static CatHerdStore loadFromFile(File file) {
		CatHerdStore store = null;
		try {
			// Create a JSON Unmarshaller
			JAXBContext context = JAXBContext.newInstance(CatHerdStore.class);
			Unmarshaller um = context.createUnmarshaller();
			um.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
			um.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
			
			InputStream is = new FileInputStream(file);
			Reader r = new InputStreamReader(is, "UTF-8");
			
			// Load the data
			store = (CatHerdStore)um.unmarshal(r);
			
			// Remember the path used
			setSavePath(file);
			loadedPath.set(file.toString());
		} catch (Exception e) {
			// If loading failed, show an alert and then return null so the app will use an empty store.
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Fehler");
	        alert.setHeaderText("Konnte Daten nicht laden");
	        alert.setContentText("Fehler beim Laden der Datei\n" + file.getPath());

	        setSavePath(null);
	        
	        e.printStackTrace();
	        
	        alert.showAndWait();
		}
		return store;
	}
	
	/**
	 * Save the given CatHerdStore to the file.
	 * 
	 * @param file File in which to store the data.
	 * @param store The CatHerdStore to be saved.
	 */
	public static void saveToFile(File file) {
		CatHerdStore store = CatHerdState.getStore();
		try {
			// Create a JSON Marshaller
			JAXBContext context = JAXBContext.newInstance(CatHerdStore.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
			m.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
			m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			
			// Store the data.
			m.marshal(store,  file);
			
			// Store the last used path for future reference
			CatHerdState.cleanStore();
			setSavePath(file);
			loadedPath.set(file.toString());
		} catch (Exception e) { // catches ANY exception
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not save data");
	        alert.setContentText("Could not save data to file:\n" + file.getPath());

	        e.printStackTrace();
	        
	        alert.showAndWait();
	    }
	}
	
	public static String getLoadedPath() {
		return loadedPath.get();
	}
	
	public static StringProperty loadedPathProperty() {
		return loadedPath;
	}
}
