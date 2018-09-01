package ch.katzenhausfreunde.catherd.view;

import ch.katzenhausfreunde.catherd.model.Cat;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CatEditorController {
	@FXML
	private TextField catName;
	
	public CatEditorController() {
		
	}
	
	@FXML
	private void initialize() {
		
	}
	
	public void setCat(Cat cat) {
		// Set initial text
		catName.setText(cat.getName());
		
		// Bind Cat's name property to the text in the text field.
		cat.nameProperty().bind(catName.textProperty());
	}
}
