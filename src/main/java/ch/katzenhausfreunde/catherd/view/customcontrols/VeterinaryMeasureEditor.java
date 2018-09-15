package ch.katzenhausfreunde.catherd.view.customcontrols;

import ch.katzenhausfreunde.catherd.model.VeterinaryMeasure;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;

public class VeterinaryMeasureEditor {
	@FXML
	private DatePicker date;
	
	@FXML
	private AnchorPane reasonContainer;
	private LengthLimitedTextArea reason;
	
	@FXML
	private AnchorPane measuresContainer;
	private LengthLimitedTextArea measures;
	
	private VeterinaryMeasureEditor() {
		reason = new LengthLimitedTextArea(92);
		measures = new LengthLimitedTextArea(54);
	}
	
	@FXML
	public void initialize() {
		reasonContainer.getChildren().add(reason);
		measuresContainer.getChildren().add(measures);
	}
	
	public void setMeasure(VeterinaryMeasure vm) {
		vm.dateProperty().bind(date.valueProperty());
		vm.causeProperty().bind(reason.textProperty());
		vm.measuresProperty().bind(measures.textProperty());
	}
}
