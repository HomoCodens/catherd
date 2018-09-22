package ch.katzenhausfreunde.catherd.view.customcontrols;

import java.io.IOException;

import ch.katzenhausfreunde.catherd.model.VeterinaryMeasure;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class VeterinaryMeasureEditor extends AnchorPane {
	@FXML
	private DatePicker date;
	
	@FXML
	private AnchorPane reasonContainer;
	private LengthLimitedTextArea reason;
	
	@FXML
	private AnchorPane measuresContainer;
	private LengthLimitedTextArea measures;
	
	@FXML
	private Label label;
	private StringProperty labelText;
	
	@FXML
	private Button remove;
	
	public VeterinaryMeasureEditor(String label) {
		this.labelText = new SimpleStringProperty(label);
		
		reason = new LengthLimitedTextArea(92);
		reason.setPrefHeight(75);
		measures = new LengthLimitedTextArea(54);
		measures.setPrefHeight(50);
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("VeterinaryMeasureEditor.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		
		try {
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void initialize() {
		reasonContainer.getChildren().add(reason);
		AnchorPane.setTopAnchor(reason, 0.0);
		AnchorPane.setRightAnchor(reason, 0.0);
		AnchorPane.setLeftAnchor(reason, 0.0);
		
		measuresContainer.getChildren().add(measures);
		AnchorPane.setTopAnchor(measures, 0.0);
		AnchorPane.setRightAnchor(measures, 0.0);
		AnchorPane.setLeftAnchor(measures, 0.0);
		label.textProperty().bind(labelText);
	}
	
	public void setMeasure(VeterinaryMeasure vm) {
		date.setValue(vm.getDate());
		vm.dateProperty().bind(date.valueProperty());
		reason.setText(vm.getCause());
		vm.causeProperty().bind(reason.textProperty());
		measures.setText(vm.getMeasures());
		vm.measuresProperty().bind(measures.textProperty());
	}
	
	public void setLabel(String label) {
		this.labelText.set(label);
	}
	
	public void setOnRemove(EventHandler<ActionEvent> e) {
		remove.setOnAction(e);
	}
}
