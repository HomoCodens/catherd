package ch.katzenhausfreunde.catherd.view;

import ch.katzenhausfreunde.catherd.model.CatGroup;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CatGroupEditorController {
	@FXML
	private TextField catGroupName;
	
	private CatGroup catGroup;
	
	public CatGroupEditorController() {
		
	}
	
	@FXML
	private void initialize() {
		
	}
	
	public void setCatGroup(CatGroup group) {
		this.catGroup = group;
		catGroupName.setText(group.getName());
	}
	
	public void onChangeCatGroupName() {
		System.out.println("onChangeCatGroupName");
		this.catGroup.setName(catGroupName.getText());
	}
}
