package ch.katzenhausfreunde.catherd.view;

import java.io.File;

import ch.katzenhausfreunde.catherd.CatHerdMain;
import ch.katzenhausfreunde.catherd.model.CatHerdStore;
import ch.katzenhausfreunde.catherd.util.CatHerdDiskStorage;
import ch.katzenhausfreunde.catherd.util.CatHerdState;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

public class MainMenuController {
	@FXML
	MenuItem saveItem;
	
	private CatHerdMain main;
	
	@FXML
	private void initialize() {
		// Funky
		saveItem.disableProperty().bind(CatHerdState.dirtyProperty().not());
	}
	
	public void setMain(CatHerdMain main) {
		this.main = main;
	}
	
	@FXML
	public void handleOpen() {
		main.handleOpen();
	}
	
	@FXML
	private void handleSave() {
		main.handleSave();
	}
	
	@FXML
	private void handleSaveAs() {
		main.handleSaveAs();
	}
}
