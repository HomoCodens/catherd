package ch.katzenhausfreunde.catherd;

import java.io.IOException;

import ch.katzenhausfreunde.catherd.model.Cat;
import ch.katzenhausfreunde.catherd.model.CatGroup;
import ch.katzenhausfreunde.catherd.model.FosterHome;
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
	private FosterHome fosterHome;
	private ObservableList<CatGroup> groups = FXCollections.observableArrayList();
	
	public CatHerdMain() {
		System.out.println("Hi meow.");
		
		this.fosterHome = new FosterHome();
		this.groups.add(new CatGroup("1"));
		this.groups.get(0).getCats().add(new Cat("Mittens"));
		this.groups.get(0).getCats().add(new Cat("Spot"));
		this.groups.add(new CatGroup("2"));
		this.groups.get(1).getCats().add(new Cat("Fluffy"));
		this.groups.add(new CatGroup("3"));
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
	
	public FosterHome getFosterHome() {
		return this.fosterHome;
	}
	
	public ObservableList<CatGroup> getGroups() {
		return this.groups;
	}
	
	public void addCatGroup(CatGroup group) {
		groups.add(group);
	}
	
	public void addCatToGroup(Cat cat, CatGroup group) {
		for(CatGroup g : groups) {
			if(g.getId() == group.getId()) {
				g.addCat(cat);
			}
		}
	}
}
