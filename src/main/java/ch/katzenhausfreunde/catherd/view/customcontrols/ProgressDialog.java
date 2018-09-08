package ch.katzenhausfreunde.catherd.view.customcontrols;

import java.io.IOException;

import ch.katzenhausfreunde.catherd.CatHerdMain;
import javafx.beans.binding.Bindings;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;

public class ProgressDialog extends BorderPane {
	@FXML
	private ProgressBar progressBar;
	
	@FXML
	private Label progressLabel;
	
	@FXML
	private Button cancelButton;
	
	@FXML
	private Label messageLabel;
	
	private FloatProperty progress = new SimpleFloatProperty();
	
	private StringProperty message = new SimpleStringProperty(null);
	
	public ProgressDialog() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(CatHerdMain.class.getResource("view/customcontrols/ProgressDialog.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		
		loader.load();
    }
	
	@FXML
	public void initialize() {
		messageLabel.textProperty().bind(message);
		progressBar.progressProperty().bind(progress);
		progressLabel.textProperty().bind(Bindings.createStringBinding(() -> { return Math.floor(100*progress.get()) + "%"; }, progress));
	}
	
	public String getMessage() {
		return this.message.get();
	}
	
	public void setMessage(String message) {
		this.message.set(message);
	}
	
	public StringProperty messageProperty() {
		return this.message;
	}
	
	public float getProgress() {
		return this.progress.get();
	}
	
	public void setProgress(float progress) {
		this.progress.set(progress);
	}
	
	public FloatProperty progressProperty() {
		return this.progress;
	}
}
