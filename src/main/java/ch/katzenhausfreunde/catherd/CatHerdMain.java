package ch.katzenhausfreunde.catherd;

import java.io.File;
import java.io.IOException;

import ch.katzenhausfreunde.catherd.model.CatHerdStore;
import ch.katzenhausfreunde.catherd.util.CatHerdDiskStorage;
import ch.katzenhausfreunde.catherd.util.CatHerdState;
import ch.katzenhausfreunde.catherd.view.RootController;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author thoenis
 *
 */
public class CatHerdMain extends Application {
	private Stage primaryStage;
	private RootController rootController;
	private CatHerdState state;
	
	public CatHerdMain() {
		System.out.println("Hi meow.");
		
		this.state = CatHerdState.getInstance();
		
		// Attempt loading stored state and use default if not available
		File inFile = CatHerdDiskStorage.getSavePath();
		CatHerdStore loadedStore = CatHerdDiskStorage.loadFromFile(inFile);
		if(loadedStore != null) {
			CatHerdState.setStore(loadedStore);
		} else {
			CatHerdStore store = new CatHerdStore();
			store.populateDummies();
			CatHerdState.setStore(store);
		}
		
		// Start listening for changes in the state objects
		CatHerdState.arm();
	}
	
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.titleProperty().bind(Bindings.createStringBinding(() -> {
			String loaded = CatHerdDiskStorage.getLoadedPath();
			String out = "CatHerd";
			if(loaded != null) {
				out = out + " - " + loaded;
			}
			return out;
		}, CatHerdDiskStorage.loadedPathProperty()));
		//this.primaryStage.getIcons().add(new Image("file:resources/images/if_Address_Book_86957.png"));
			
		initRootLayout();
	}

	public static void main(String[] args) {
		// A warning told me to do that xD
		System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
		launch(args);
	}
	
	/**
	 * Loads and shows the application root layout.
	 */
	private void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(CatHerdMain.class.getResource("view/Root.fxml"));
			BorderPane rootLayout = (BorderPane) loader.load();
			rootController = loader.getController();
			rootController.setMain(this);
			rootController.initTree();
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void storeLoaded() {
		rootController.initTree();
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
}
