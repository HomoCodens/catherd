package ch.katzenhausfreunde.catherd.view;

import java.io.IOException;

import ch.katzenhausfreunde.catherd.CatHerdMain;
import ch.katzenhausfreunde.catherd.model.FosterHome;
import ch.katzenhausfreunde.catherd.view.customcontrols.PersonController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public class FosterHomeEditorController {
	@FXML
	private TextField fosterHomeName;
	
	@FXML
	private AnchorPane personContainer;
	
	private PersonController personController;
	
	public FosterHomeEditorController() {
		
	}
	
	@FXML
	private void initialize() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(CatHerdMain.class.getResource("view/customcontrols/Person.fxml"));
			AnchorPane catEditor = (AnchorPane) loader.load();
			
			personController = loader.getController();
			
			TitledPane personPortion = new TitledPane("Pflegeperson", catEditor);
			personPortion.prefWidthProperty().bind(personContainer.widthProperty());
			
			personContainer.getChildren().add(personPortion);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setFosterHome(FosterHome home) {
		// Set initial Text
		fosterHomeName.setText(home.getName());
		
		// Bind home's name to field
		home.nameProperty().bind(fosterHomeName.textProperty());
		
		personController.setPerson(home.getFosterParent());
	}
}
