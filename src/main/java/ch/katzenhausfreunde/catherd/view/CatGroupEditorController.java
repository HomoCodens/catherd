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
		catGroupName.setText(group.getName());
		group.nameProperty().bind(catGroupName.textProperty());
	}
}
