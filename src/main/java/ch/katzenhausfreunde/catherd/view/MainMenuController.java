package ch.katzenhausfreunde.catherd.view;

import java.io.IOException;

import ch.katzenhausfreunde.catherd.CatHerdMain;
import ch.katzenhausfreunde.catherd.util.CatHerdState;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
	public void showAbout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(CatHerdMain.class.getResource("view/About.fxml"));
			AnchorPane aboutPane = (AnchorPane) loader.load();
			
			AboutController controller = loader.getController();
			controller.versionProperty().bind(main.versionProperty());
			
			Stage aboutStage = new Stage();
			aboutStage.setScene(new Scene(aboutPane));
			aboutStage.setResizable(false);
			aboutStage.initOwner(main.getPrimaryStage());
			aboutStage.initModality(Modality.WINDOW_MODAL);
			
			controller.setOnOK((e) -> aboutStage.close());
			
			aboutStage.showAndWait();
		} catch(IOException e) {
			e.printStackTrace();
		}
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
