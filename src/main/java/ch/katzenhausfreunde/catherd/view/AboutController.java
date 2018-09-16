package ch.katzenhausfreunde.catherd.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AboutController {
	@FXML
	private Label versionLabel;
	
	@FXML
	private Button okButton;
	
	private StringProperty version = new SimpleStringProperty(null);
	
	@FXML
	public void initialize() {
		versionLabel.textProperty().bind(version);
	}
	
	public String getVersion() {
		return version.get();
	}
	
	public void setVersion(String v) {
		version.set(v);
	}
	
	public StringProperty versionProperty() {
		return version;
	}
	
	public void setOnOK(EventHandler<ActionEvent> handler) {
		okButton.setOnAction(handler);
	}
}
