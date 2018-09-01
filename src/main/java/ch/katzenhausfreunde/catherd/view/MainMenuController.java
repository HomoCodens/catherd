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
		FileChooser fileChooser = getJSONChooser();
		File inFile = fileChooser.showOpenDialog(main.getPrimaryStage());
		
		if(inFile != null) {
			CatHerdStore store = CatHerdDiskStorage.loadFromFile(inFile);
			CatHerdState.setStore(store);
			main.storeLoaded();
		}
	}
	
	@FXML
	private void handleSave() {
		File outFile = CatHerdDiskStorage.getSavePath();
		if(outFile != null) {
			CatHerdDiskStorage.saveToFile(outFile);
		} else {
			handleSaveAs();
		}
	}
	
	@FXML
	private void handleSaveAs() {
		FileChooser fileChooser = getJSONChooser();	
		File outFile = fileChooser.showSaveDialog(main.getPrimaryStage());

		if(outFile != null) {
			CatHerdDiskStorage.saveToFile(outFile);
		}
	}
	
	private FileChooser getJSONChooser() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON Dateien", "*.json");
		fileChooser.getExtensionFilters().add(extFilter);
		return fileChooser;
	}
}
