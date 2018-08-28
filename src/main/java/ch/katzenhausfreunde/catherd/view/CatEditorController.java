package ch.katzenhausfreunde.catherd.view;

import ch.katzenhausfreunde.catherd.model.Cat;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CatEditorController {
	@FXML
	private TextField catName;
	
	private Cat cat;
	
	public CatEditorController() {
		
	}
	
	@FXML
	private void initialize() {
		
	}
	
	public void setCat(Cat cat) {
		this.cat = cat;
		catName.setText(cat.getName());
	}
	
	public void onChangeCatName() {
		System.out.println("onChangeCatName");
		this.cat.setName(catName.getText());
	}
}
