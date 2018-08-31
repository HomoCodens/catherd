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
		catName.setText(cat.getName());
		cat.nameProperty().bind(catName.textProperty());
	}
}
