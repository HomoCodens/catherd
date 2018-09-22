package ch.katzenhausfreunde.catherd;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;

import ch.katzenhausfreunde.catherd.model.CatHerdStore;
import ch.katzenhausfreunde.catherd.model.FosterHome;
import ch.katzenhausfreunde.catherd.util.CatHerdDiskStorage;
import ch.katzenhausfreunde.catherd.util.CatHerdState;
import ch.katzenhausfreunde.catherd.view.RootController;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author thoenis
 *
 */
public class CatHerdMain extends Application {
	private Stage primaryStage;
	private RootController rootController;
	private CatHerdState state;
	private StringProperty version;
	
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
			store.addHome(new FosterHome());
			CatHerdState.setStore(store);
		}
		
		// Start listening for changes in the state objects
		CatHerdState.arm();
		
		this.version = new SimpleStringProperty("unbekannt");
		final Properties properties = new Properties();
		try {
			properties.load(this.getClass().getClassLoader().getResourceAsStream("project.properties"));
			version.set(properties.getProperty("version"));
		} catch (IOException e) {
		}
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
		
		// Ask for confirmation when closing with unsaved changes
		this.primaryStage.setOnCloseRequest((e) -> {
			if(CatHerdState.isDirty()) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Beenden");
				alert.setHeaderText("Ungespeicherte Änderungen");
				alert.setContentText("Trotzdem beenden?");
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().addAll(getPrimaryStage().getIcons());
				alert.initOwner(getPrimaryStage());
				
				ButtonType confirm = new ButtonType("Beenden");
				ButtonType save = new ButtonType("Speichern");
				ButtonType cancel = new ButtonType("Abbrechen");
				
				alert.getButtonTypes().setAll(save, confirm, cancel);
				Button cancelButton = (Button)alert.getDialogPane().lookupButton(cancel);
				cancelButton.setDefaultButton(true);
				
				Optional<ButtonType> result = alert.showAndWait();
				if(result.get() == cancel) {
					e.consume();
				} else if(result.get() == save) {
					if(handleSave() == null) {
						e.consume();
					}
				}
			}
		});
		
		initRootLayout();
	}

	public static void main(String[] args) {
		// A warning told me to do that xD
		// It does speed up pdf rendering though
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
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			
			ClassLoader ld = Thread.currentThread().getContextClassLoader();
			Image mainIcon = new Image(ld.getResource("icons/house.png").toString());
			primaryStage.getIcons().add(mainIcon);

			rootController.initTree();
			
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void handleOpen() {
		FileChooser fileChooser = getJSONChooser();
		File inFile = fileChooser.showOpenDialog(getPrimaryStage());
		
		if(inFile != null) {
			CatHerdStore store = CatHerdDiskStorage.loadFromFile(inFile);
			CatHerdState.setStore(store);
			storeLoaded();
		}
	}
	
	public File handleSave() {
		File outFile = CatHerdDiskStorage.getSavePath();
		if(outFile != null) {
			CatHerdDiskStorage.saveToFile(outFile);
			return outFile;
		} else {
			return handleSaveAs();
		}
	}
	
	public File handleSaveAs() {
		FileChooser fileChooser = getJSONChooser();
		File outFile = fileChooser.showSaveDialog(getPrimaryStage());

		if(outFile != null) {
			CatHerdDiskStorage.saveToFile(outFile);
		}
		
		return outFile;
	}
	
	public void quit() {
		primaryStage.close();
	}
	
	private FileChooser getJSONChooser() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON Dateien", "*.json");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setInitialDirectory(new File(CatHerdDiskStorage.getSavePath().getParent()));
		return fileChooser;
	}
	
	public void storeLoaded() {
		rootController.initTree();
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public String getVersion() {
		return version.get();
	}
	
	public StringProperty versionProperty() {
		return version;
	}
}
