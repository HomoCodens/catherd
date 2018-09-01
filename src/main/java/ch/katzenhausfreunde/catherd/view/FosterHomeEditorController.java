package ch.katzenhausfreunde.catherd.view;

import ch.katzenhausfreunde.catherd.model.FosterHome;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FosterHomeEditorController {
	@FXML
	private TextField fosterHomeName;
	
	public FosterHomeEditorController() {
		
	}
	
	@FXML
	private void initialize() {
		
	}
	
	public void setFosterHome(FosterHome home) {
		// Set initial Text
		fosterHomeName.setText(home.getName());
		
		// Bind home's name to field
		home.nameProperty().bind(fosterHomeName.textProperty());
	}
}
