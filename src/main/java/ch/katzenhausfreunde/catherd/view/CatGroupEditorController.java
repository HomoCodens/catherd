package ch.katzenhausfreunde.catherd.view;

import ch.katzenhausfreunde.catherd.model.CatGroup;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CatGroupEditorController {
	@FXML
	private TextField catGroupName;
	
	public CatGroupEditorController() {
		
	}
	
	@FXML
	private void initialize() {
		
	}
	
	public void setCatGroup(CatGroup group) {
		// Set inital text
		catGroupName.setText(group.getName());

		// Bind group's name to the field
		group.nameProperty().bind(catGroupName.textProperty());
	}
}
