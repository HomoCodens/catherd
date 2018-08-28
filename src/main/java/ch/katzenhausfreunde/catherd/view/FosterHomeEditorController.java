package ch.katzenhausfreunde.catherd.view;

import ch.katzenhausfreunde.catherd.model.FosterHome;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FosterHomeEditorController {
	@FXML
	private TextField fosterHomeName;
	
	private FosterHome fosterHome;
	
	public FosterHomeEditorController() {
		
	}
	
	@FXML
	private void initialize() {
		
	}
	
	public void setFosterHome(FosterHome home) {
		this.fosterHome = home;
		fosterHomeName.setText(home.getName());
	}
	
	public void onChangeFosterHomeName() {
		System.out.println("onChangeFosterHomeName");
		this.fosterHome.setName(fosterHomeName.getText());
	}
}
