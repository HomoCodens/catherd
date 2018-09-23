package ch.katzenhausfreunde.catherd.view;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import ch.katzenhausfreunde.catherd.CatHerdMain;
import ch.katzenhausfreunde.catherd.model.Cat;
import ch.katzenhausfreunde.catherd.model.CatGroup;
import ch.katzenhausfreunde.catherd.model.CatHerdStore;
import ch.katzenhausfreunde.catherd.model.FosterHome;
import ch.katzenhausfreunde.catherd.model.Nameable;
import ch.katzenhausfreunde.catherd.util.CatHerdState;
import ch.katzenhausfreunde.catherd.util.ContractType;
import ch.katzenhausfreunde.catherd.util.DocumentRenderer;
import ch.katzenhausfreunde.catherd.view.customcontrols.ProgressDialog;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class RootController {	
	@FXML
	private BorderPane leftPane;
	
	@FXML
	private BorderPane rightPane;
	
	@FXML
	private BorderPane rootPane;
	
	MenuItem newGroup;
	MenuItem removeGroup;
	MenuItem newCat;
	MenuItem removeCat;
	
	private CatHerdMain main;
	
	TreeView<Nameable> tree;
	
	Image catIcon;
	
	EventHandler<ActionEvent> newGroupHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
        	TreeItem<Nameable> homeItem = tree.getSelectionModel().getSelectedItem();
        	String newGroupName = "Gruppe " + (homeItem.getChildren().size() + 1);
        	
        	TextInputDialog nameDialog = new TextInputDialog(newGroupName);
			nameDialog.setTitle("Neue Gruppe");
			nameDialog.setContentText("Name der neuen Gruppe");
			nameDialog.setHeaderText(null);
			Stage stage = (Stage) nameDialog.getDialogPane().getScene().getWindow();
			stage.getIcons().addAll(main.getPrimaryStage().getIcons());
			nameDialog.initOwner(main.getPrimaryStage());
        	
			Optional<String> result = nameDialog.showAndWait();
			
			if(result.isPresent()) {
				// Create new group
				CatGroup newGroup = new CatGroup(result.get());
				
				// Add group to this item's FosterHome
				FosterHome home = (FosterHome)homeItem.getValue();
				home.addCatGroup(newGroup);
				
				// Add the group to the tree
				TreeItem<Nameable> newGroupItem = new TreeItem<Nameable>((Nameable)newGroup);
				homeItem.getChildren().add(newGroupItem);
				
				// Expand item that was just added to
				homeItem.setExpanded(true);
			}
		}
	};
	
	EventHandler<ActionEvent> removeGroupHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			TreeItem<Nameable> groupItem = tree.getSelectionModel().getSelectedItem();
			CatGroup group = (CatGroup)groupItem.getValue();
			
        	Alert confirmation = new Alert(AlertType.CONFIRMATION);
        	confirmation.setHeaderText(null);
        	confirmation.setTitle("Bestätigen");
        	Stage stage = (Stage) confirmation.getDialogPane().getScene().getWindow();
			stage.getIcons().addAll(main.getPrimaryStage().getIcons());
			confirmation.initOwner(main.getPrimaryStage());
			
			// Ask for confirmation from the user
			confirmation.setContentText("Gruppe " + group.getName() + " wirklich entfernen?");
			Optional<ButtonType> result = confirmation.showAndWait();
			
			// If they really want to remove the group
			if (result.get() == ButtonType.OK){
				// Remove the group from the FosterHome
				FosterHome parent = (FosterHome)groupItem.getParent().getValue();
				parent.removeCatGroup((CatGroup)group);
				
				// Remove the group from the tree
				groupItem.getParent().getChildren().remove(groupItem);
			}
		}			
	};
	
	EventHandler<ActionEvent> newCatHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
        	TreeItem<Nameable> groupItem = tree.getSelectionModel().getSelectedItem();
			String newCatName = "Katze " + (groupItem.getChildren().size() + 1);
			
        	TextInputDialog nameDialog = new TextInputDialog(newCatName);
        	nameDialog.setTitle("Neue Katze");
			nameDialog.setContentText("Name der neuen Katze");
			nameDialog.setHeaderText(null);
			Stage stage = (Stage) nameDialog.getDialogPane().getScene().getWindow();
			stage.getIcons().addAll(main.getPrimaryStage().getIcons());
			nameDialog.initOwner(main.getPrimaryStage());
			
			Optional<String> result = nameDialog.showAndWait();
			if (result.isPresent()){
				// Create new cat
				Cat newCat = new Cat(result.get());
				
				// Add cat to item's group
				CatGroup group = (CatGroup)groupItem.getValue();
				group.addCat(newCat);
				
				// Add cat to tree
				TreeItem<Nameable> newCatItem = new TreeItem<Nameable>((Nameable)newCat, new ImageView(catIcon));
				groupItem.getChildren().add(newCatItem);
				
				// Expand item that was just added to
				groupItem.setExpanded(true);
			}
		}
	};
    
	EventHandler<ActionEvent> removeCatHandler = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
        	TreeItem<Nameable> catItem = tree.getSelectionModel().getSelectedItem();
			Cat cat = (Cat)catItem.getValue();
			
        	Alert confirmation = new Alert(AlertType.CONFIRMATION);
        	confirmation.setHeaderText(null);
        	confirmation.setTitle("Bestätigen");
        	Stage stage = (Stage) confirmation.getDialogPane().getScene().getWindow();
			stage.getIcons().addAll(main.getPrimaryStage().getIcons());
			confirmation.initOwner(main.getPrimaryStage());
        	
			// Ask for confirmation
			confirmation.setContentText("Katze " + cat.getName() + " wirklich entfernen?");
			Optional<ButtonType> result = confirmation.showAndWait();
			if (result.get() == ButtonType.OK) {
				// Remove the cat from its gruop
				CatGroup parent = (CatGroup)catItem.getParent().getValue();
				parent.removeCat(cat);
				
				// Remove the cat from the tree
				catItem.getParent().getChildren().remove(catItem);
			}
		}
		
	};

	
	public RootController() {
		newGroup = new MenuItem("Neue Gruppe...");
		removeGroup = new MenuItem("Gruppe entfernen");
		newCat = new MenuItem("Neue Katze...");
		removeCat = new MenuItem("Katze entfernen");
		
		tree = new TreeView<Nameable>();
		
		catIcon = new Image("file:icons/cat.png");
	}
	
	@FXML
	private void initialize() {
	}
	
	public void setMain(CatHerdMain main) {
		this.main = main;
		initMenu();
	}
	
	/**
	 * Initialize the TreeView on the left used to display groups/cats
	 */
	public void initTree() {		
		CatHerdStore store = CatHerdState.getStore();
		
		// Initialize an invisible root item
		// This is to keep the option open of managing multiple foster homes
		// with CatHerd. Currently only one is possible though.
		TreeItem<Nameable> rootItem = new TreeItem<Nameable>(new Nameable("root"));
		
		// Build the tree
		for(FosterHome h : store.getFosterHomes()) {
			TreeItem<Nameable> fosterHome = new TreeItem<Nameable>(h);
			fosterHome.setExpanded(true);
			rootItem.getChildren().add(fosterHome);
        
	        for(CatGroup g: h.getGroups()) {
	        	TreeItem<Nameable> group = new TreeItem<Nameable>(g);
	        	for(Cat c : g.getCats()) {
	        		TreeItem<Nameable> catNode = new TreeItem<Nameable>(c, new ImageView(catIcon));
	        		group.getChildren().add(catNode);
	        	}
	        	fosterHome.getChildren().add(group);
	        }
        }
				
        // Create the tree from the rootItem created above
		tree.setRoot(rootItem);
        
        // Hide the root item
        tree.setShowRoot(false);
		
				
		// Custom TreeCell implementation with context menus
        final class CatCell extends TreeCell<Nameable> {
        	public CatCell() {
            }
     
            /* (non-Javadoc)
             * @see javafx.scene.control.Cell#updateItem(java.lang.Object, boolean)
             */
            @Override
            public void updateItem(Nameable item, boolean empty) {
                super.updateItem(item, empty);
                
                if(!empty) {
                	// Bind the name displayed in the tree to the item's name property
                	this.textProperty().bind(item.nameProperty());
                	
                	// Context menu with the proper add/remove options
            		ContextMenu menu = new ContextMenu();
            		Menu renderMenu = new Menu();
            		MenuItem renderAllMenuItem = new MenuItem();
            		MenuItem renderContractMenuItem = new MenuItem();
            		MenuItem renderDatasheetMenuItem = new MenuItem();
            		renderMenu.getItems().add(renderAllMenuItem);
            		renderMenu.getItems().add(renderContractMenuItem);
            		renderMenu.getItems().add(renderDatasheetMenuItem);
            		
            		// Register option to add new group if item is a FosterHome
            		if(item instanceof FosterHome) {
            			ImageView homeIcon = new ImageView(main.getPrimaryStage().getIcons().get(0));
            			homeIcon.setFitWidth(16);
            			homeIcon.setFitHeight(16);
            			homeIcon.setSmooth(true);
            			setGraphic(homeIcon);
                		
                		// Register menu item
            			MenuItem newGroupItem = new MenuItem("Neue Gruppe...");
            			newGroupItem.setOnAction(newGroupHandler);
                		menu.getItems().add(newGroupItem);
                		
                	// Register add cat and remove group on group items
                	} else if(item instanceof CatGroup) {
                		ClassLoader ld = Thread.currentThread().getContextClassLoader();
                		Image groupIcon = new Image(ld.getResourceAsStream("icons/group.png"));
                		setGraphic(new ImageView(groupIcon));
                		
                		// Set the "render documents" items
                		renderMenu.setText("Dokumente für Gruppe erstellen");
                		
                		renderAllMenuItem.setText("Alle");
                		renderAllMenuItem.setOnAction((ActionEvent e) -> {
                			ContractType type = askForContractType();

                			if(type != null) {
	                			File outDir = askForDocDir();
	                			
	                			if(outDir != null) {
		                			CatGroup group = (CatGroup)item;
		                			DocumentRenderer renderer = new DocumentRenderer();
		            			
		                			showProgress(renderer);
		                			renderer.renderDocuments(group, outDir, type);
	                			}
                			}
                		});
                		
                		renderContractMenuItem.setText("Verträge");
                		renderContractMenuItem.setOnAction((ActionEvent e) -> {
                			ContractType type = askForContractType();

                			if(type != null) {
	                			File outDir = askForDocDir();
	                			
	                			if(outDir != null) {
		                			CatGroup group = (CatGroup)item;
		                			DocumentRenderer renderer = new DocumentRenderer();
		            			
		                			showProgress(renderer);
		                			renderer.renderContracts(group, outDir, type);
	                			}
                			}
                		});
                		
                		renderDatasheetMenuItem.setText("Datenblätter");
                		renderDatasheetMenuItem.setOnAction((ActionEvent e) -> {
                			File outDir = askForDocDir();
                			
                			if(outDir != null) {
	                			CatGroup group = (CatGroup)item;
	                			DocumentRenderer renderer = new DocumentRenderer();
	            			
	                			showProgress(renderer);
	                			renderer.renderDatasheets(group, outDir);
                			}
                		});
                		
                		// Register menu items
                		MenuItem removeGroupItem = new MenuItem("Gruppe entfernen");
                		removeGroupItem.setOnAction(removeGroupHandler);
                		
                		MenuItem newCatItem = new MenuItem("Neue Katze...");
                		newCatItem.setOnAction(newCatHandler);
                		
                		menu.getItems().add(newCatItem);
                		menu.getItems().add(removeGroupItem);
                		
                		CatGroup group = (CatGroup)item;
                		if(group.getCats().size() > 0) {
                			menu.getItems().add(renderMenu);
                		}
                		
                	// Add remove Cat MenuItem if item is a Cat
                	} else if(item instanceof Cat) {
                		ClassLoader ld = Thread.currentThread().getContextClassLoader();
                		Image groupIcon = new Image(ld.getResourceAsStream("icons/cat.png"));
                		setGraphic(new ImageView(groupIcon));
                		
                		renderMenu.setText("Dokumente für Katze erstellen");
                		
                		renderAllMenuItem.setText("Alle");
                		renderAllMenuItem.setOnAction((ActionEvent e) -> {
                			ContractType type = askForContractType();
                			if(type != null) {
	                			File outDir = askForDocDir();
	                			
	                			if(outDir != null) {
		                			Cat cat = (Cat)item;
		                			DocumentRenderer renderer = new DocumentRenderer();
		                			showProgress(renderer);
		                			renderer.renderDocuments(cat, outDir, type);
	                			}
                			}
                		});
                		
                		renderContractMenuItem.setText("Vertrag");
                		renderContractMenuItem.setOnAction((ActionEvent e) -> {
                			ContractType type = askForContractType();
                			if(type != null) {
	                			File outDir = askForDocDir();
	                			
	                			if(outDir != null) {
		                			Cat cat = (Cat)item;
		                			DocumentRenderer renderer = new DocumentRenderer();
		                			showProgress(renderer);
		                			renderer.renderContract(cat, outDir, type);
	                			}
                			}
                		});
                		
                		renderDatasheetMenuItem.setText("Datenblatt");
                		renderDatasheetMenuItem.setOnAction((ActionEvent e) -> {
            				File outDir = askForDocDir();
                			
                			if(outDir != null) {
	                			Cat cat = (Cat)item;
	                			DocumentRenderer renderer = new DocumentRenderer();
	                			showProgress(renderer);
	                			renderer.renderDatasheet(cat, outDir);
                			}
                		});
                		
                		// Register the menu items
                		MenuItem removeCatItem = new MenuItem("Katze entfernen");
                		removeCatItem.setOnAction(removeCatHandler);
                		
                		menu.getItems().add(removeCatItem);
                		menu.getItems().add(renderMenu);
                	}
            		
            		// Register the context menu
            		setContextMenu(menu);
                } else {
                	// If the item is to be emptied, unbind and reset the text property
                	this.textProperty().unbind();
                	setText(null);
                	setGraphic(null);
                }
            }
        }
                
        // Add listener that displays the appropriate editor on the right side of the 
        // screen when items are selected.
        tree.getSelectionModel().selectedItemProperty().addListener((observable, oldItem, newItem) -> {
        	Nameable value = newItem.getValue();
        	if(value instanceof Cat) {
        		showCatEditor((Cat)value);
        	} else if(value instanceof CatGroup) {
        		showCatGroupEditor((CatGroup)value);
        	} else if(value instanceof FosterHome) {
        		showFosterHomeEditor((FosterHome)value);
        	}
        });
        
        // Set the cell factory to use CatCells in this tree
        tree.setCellFactory(new Callback<TreeView<Nameable>,TreeCell<Nameable>>(){
            @Override
            public TreeCell<Nameable> call(TreeView<Nameable> p) {
                return new CatCell();
            }
        });
        
        // Add the tree to the left side of the screen
        leftPane.setCenter(tree);
        
        // Show welcome screen when tree is redrawn because nothing will be selected
        showWelcomeScreen();
	}
	
	private void initMenu() {
		newGroup.disableProperty().bind(
        		Bindings.createBooleanBinding(() -> {
        			if(tree.getSelectionModel().getSelectedItem() == null) {
        				return true;
        			}
        			return !(tree.getSelectionModel().getSelectedItem().getValue() instanceof FosterHome);
        		}, tree.getSelectionModel().selectedItemProperty())
        );
        newGroup.setOnAction(newGroupHandler);
        
        removeGroup.disableProperty().bind(
        		Bindings.createBooleanBinding(() -> {
        			if(tree.getSelectionModel().getSelectedItem() == null) {
        				return true;
        			}
        			return !(tree.getSelectionModel().getSelectedItem().getValue() instanceof CatGroup);
        		}, tree.getSelectionModel().selectedItemProperty())
        );
		removeGroup.setOnAction(removeGroupHandler);
        
        newCat.disableProperty().bind(
        		Bindings.createBooleanBinding(() -> {
        			if(tree.getSelectionModel().getSelectedItem() == null) {
        				return true;
        			}
        			return !(tree.getSelectionModel().getSelectedItem().getValue() instanceof CatGroup);
        		}, tree.getSelectionModel().selectedItemProperty())
        );
        newCat.setOnAction(newCatHandler);
        
        removeCat.disableProperty().bind(
        		Bindings.createBooleanBinding(() -> {
        			if(tree.getSelectionModel().getSelectedItem() == null) {
        				return true;
        			}
        			return !(tree.getSelectionModel().getSelectedItem().getValue() instanceof Cat);
        		}, tree.getSelectionModel().selectedItemProperty())
        );
        removeCat.setOnAction(removeCatHandler);
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(CatHerdMain.class.getResource("view/MainMenu.fxml"));
			AnchorPane mainMenu = (AnchorPane) loader.load();
			
			MainMenuController menuController = loader.getController();
			menuController.setMain(main);

			menuController.pupulateEditMenu(newGroup, removeGroup, newCat, removeCat);

			rootPane.setTop(mainMenu);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void showWelcomeScreen() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(CatHerdMain.class.getResource("view/WelcomeScreen.fxml"));
			AnchorPane welcomeScreen = (AnchorPane) loader.load();
			rightPane.setCenter(welcomeScreen);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void showCatEditor(Cat cat) {
		showEditorLoading();
		
		Thread editorLoader = new Thread(() -> {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(CatHerdMain.class.getResource("view/CatEditor.fxml"));
				AnchorPane catEditor = (AnchorPane) loader.load();
				CatEditorController controller = loader.getController();
				
				Platform.runLater(() -> {
					rightPane.setCenter(catEditor);
					controller.setCat(cat);
				});
				
			} catch(IOException e) {
				e.printStackTrace();
			}
		});
		editorLoader.start();
	}
	
	private void showCatGroupEditor(CatGroup group) {
		showEditorLoading();
				
		Thread editorLoader = new Thread(() -> {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(CatHerdMain.class.getResource("view/CatGroupEditor.fxml"));
				AnchorPane groupEditor = (AnchorPane) loader.load();
				CatGroupEditorController controller = loader.getController();
				
				Platform.runLater(() -> {
					rightPane.setCenter(groupEditor);
					controller.setCatGroup(group);
				});
			} catch(IOException e) {
				e.printStackTrace();
			}
		});
		editorLoader.start();
	}
	
	private void showFosterHomeEditor(FosterHome home) {
		showEditorLoading();
		
		Thread editorLoader = new Thread(() -> {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(CatHerdMain.class.getResource("view/FosterHomeEditor.fxml"));
				AnchorPane homeEditor = (AnchorPane) loader.load();
				FosterHomeEditorController controller = loader.getController();
				
				Platform.runLater(() -> {
					rightPane.setCenter(homeEditor);
					controller.setFosterHome(home);
				});
			} catch(IOException e) {
				e.printStackTrace();
			}
		});
		editorLoader.start();
	}
	
	private void showEditorLoading() {
		ProgressIndicator pi = new ProgressIndicator();
		pi.setProgress(-1.0);
		pi.setMaxWidth(32.0);
		pi.setMaxHeight(32.0);
		
		rightPane.setCenter(pi);
	}
	
	private void showProgress(DocumentRenderer renderer) {
		ProgressDialog progressDialog = new ProgressDialog();
		progressDialog.progressProperty().bind(
				Bindings.createFloatBinding(() -> { 
					return renderer.doneDocsProperty().floatValue()/renderer.totalDocsProperty().floatValue(); 
					}, renderer.doneDocsProperty(), renderer.totalDocsProperty()));
		progressDialog.messageProperty().bind(renderer.currentTaskMessageProperty());
		progressDialog.cancelledProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue == true) {
				renderer.cancel();
			}
		});
		
		Stage progressStage = new Stage();
		progressStage.setScene(new Scene(progressDialog));
		progressStage.setResizable(false);
		progressStage.setOnCloseRequest((e) -> {
			renderer.cancel();
			e.consume();
		});
		progressStage.initOwner(main.getPrimaryStage());
		progressStage.initModality(Modality.WINDOW_MODAL);
		
		progressStage.show();
	}
	
	private ContractType askForContractType() {
		ButtonType res = new ButtonType("Reservation");
		ButtonType sal = new ButtonType("Verkauf");
		ButtonType canc = new ButtonType("Abbrechen", ButtonData.CANCEL_CLOSE);
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.getButtonTypes().setAll(res, sal, canc);
		alert.setTitle("Verträge erstellen");
		alert.setHeaderText("Welche Art Vertrag soll erstellt werden?");

		Optional<ButtonType> out = alert.showAndWait();
		
		System.out.println(out.get());
		
		if(out.isPresent()) {
			if(out.get() == res) {
				return ContractType.RESERVATION;
			} else if(out.get() == sal) {
				return ContractType.SALE;
			}
		}
		
		return null;
	}
	
	private File askForDocDir() {
		DirectoryChooser dirChooser = new DirectoryChooser();
		dirChooser.setInitialDirectory(DocumentRenderer.getDocsPath());
		return dirChooser.showDialog(main.getPrimaryStage());
	}
}
