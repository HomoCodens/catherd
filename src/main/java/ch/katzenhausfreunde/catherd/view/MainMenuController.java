package ch.katzenhausfreunde.catherd.view;

import java.io.IOException;

import ch.katzenhausfreunde.catherd.CatHerdMain;
import ch.katzenhausfreunde.catherd.util.CatHerdDiskStorage;
import ch.katzenhausfreunde.catherd.util.CatHerdState;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainMenuController {
	@FXML
	MenuItem saveItem;
	
	@FXML
	Menu editMenu;
	
	private CatHerdMain main;
	
	@FXML
	private void initialize() {
		// Funky
		saveItem.disableProperty().bind(
				Bindings.createBooleanBinding(
						() -> CatHerdState.dirtyProperty().not().get() ||
						CatHerdDiskStorage.getSavePath() == null, 
						CatHerdState.dirtyProperty()));
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
			aboutStage.getIcons().addAll(main.getPrimaryStage().getIcons());
			aboutStage.initModality(Modality.WINDOW_MODAL);
			
			controller.setOnOK((e) -> aboutStage.close());
			
			aboutStage.showAndWait();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void pupulateEditMenu(MenuItem... items) {
		editMenu.getItems().setAll(items);
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
	
	@FXML
	private void handleQuit() {
		main.quit();
	}
}
