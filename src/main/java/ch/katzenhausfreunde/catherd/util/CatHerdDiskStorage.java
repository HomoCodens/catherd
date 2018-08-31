package ch.katzenhausfreunde.catherd.util;

import java.io.File;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.persistence.jaxb.MarshallerProperties;

import ch.katzenhausfreunde.catherd.CatHerdMain;
import ch.katzenhausfreunde.catherd.model.CatHerdStore;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CatHerdDiskStorage {
	public static File getSavePath() {
		Preferences prefs = Preferences.userNodeForPackage(CatHerdMain.class);
		String filePath = prefs.get("filePath",  null);
		if(filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}
	
	public static void setSavePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(CatHerdMain.class);
		if(file != null) {
			prefs.put("filePath", file.getPath());
		} else {
			prefs.remove("filePath");
		}
	}
	
	public static CatHerdStore loadFromFile(File file) {
		CatHerdStore store = null;
		try {
			JAXBContext context = JAXBContext.newInstance(CatHerdStore.class);
			Unmarshaller um = context.createUnmarshaller();
			um.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
			um.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
			
			store = (CatHerdStore)um.unmarshal(file);
		} catch (Exception e) {
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not load data");
	        alert.setContentText("Could not load data from file:\n" + file.getPath());

	        alert.showAndWait();
		}
		return store;
	}
	
	public static void saveToFile(File file, CatHerdStore store) {
		try {
			JAXBContext context = JAXBContext.newInstance(CatHerdStore.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
			m.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
			
			
			m.marshal(store,  file);
		} catch (Exception e) { // catches ANY exception
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Could not save data");
	        alert.setContentText("Could not save data to file:\n" + file.getPath());

	        e.printStackTrace();
	        
	        alert.showAndWait();
	    }
	}
}
