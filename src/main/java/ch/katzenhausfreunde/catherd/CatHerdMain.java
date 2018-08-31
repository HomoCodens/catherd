package ch.katzenhausfreunde.catherd;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import ch.katzenhausfreunde.catherd.model.Cat;
import ch.katzenhausfreunde.catherd.model.CatGroup;
import ch.katzenhausfreunde.catherd.model.CatHerdStore;
import ch.katzenhausfreunde.catherd.model.FosterHome;
import ch.katzenhausfreunde.catherd.util.CatHerdDiskStorage;
import ch.katzenhausfreunde.catherd.view.RootController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CatHerdMain extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	private CatHerdStore store;
	
	public CatHerdMain() {
		System.out.println("Hi meow.");
		
		CatHerdStore loadedStore = CatHerdDiskStorage.loadFromFile(new File("E:\\blason.json"));
		if(loadedStore != null) {
			this.store = loadedStore;
		} else {
			this.store = new CatHerdStore();
			this.store.populateDummies();
		}
		CatHerdDiskStorage.saveToFile(new File("E:\\blason.json"), store);
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("CatHerd");
		//this.primaryStage.getIcons().add(new Image("file:resources/images/if_Address_Book_86957.png"));
		
		
		initRootLayout();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(CatHerdMain.class.getResource("view/Root.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			
			RootController rootController = loader.getController();
			rootController.setMain(this);
			
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public CatHerdStore getStore() {
		return store;
	}
}
