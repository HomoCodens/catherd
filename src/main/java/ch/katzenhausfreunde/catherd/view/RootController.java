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
import ch.katzenhausfreunde.catherd.util.DocumentRenderer;
import ch.katzenhausfreunde.catherd.view.customcontrols.ProgressDialog;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class RootController {	
	@FXML
	private BorderPane leftPane;
	
	@FXML
	private BorderPane rightPane;
	
	@FXML
	private BorderPane rootPane;
	
	private CatHerdMain main;
	
	public RootController() {
		
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
	        		TreeItem<Nameable> catNode = new TreeItem<Nameable>(c);
	        		group.getChildren().add(catNode);
	        	}
	        	fosterHome.getChildren().add(group);
	        }
        }
        
		// Custom TreeCell implementation with context menus
        final class CatCell extends TreeCell<Nameable> {
        	       	
        	/**
        	 * Confirmation dialog used when deleting items
        	 */
        	Alert confirmation = new Alert(AlertType.CONFIRMATION);
        	Alert notification = new Alert(AlertType.INFORMATION);
        	
            public CatCell() {
            	confirmation.setHeaderText(null);
            	confirmation.setTitle("Bestätigen");
            	
            	notification.setHeaderText(null);
            	notification.setTitle("Erfolg");
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
            		MenuItem addMenuItem = new MenuItem();
            		MenuItem removeMenuItem = new MenuItem();
            		MenuItem renderMenuItem = new MenuItem();
            		
            		// Number of items for generating numbered default names
            		int n_items = getTreeItem().getChildren().size();
            		
            		// Register option to add new group if item is a FosterHome
            		if(item instanceof FosterHome) {
            			addMenuItem.setText("Neue Gruppe");
                		addMenuItem.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent e) {
								// Create new group
								CatGroup newGroup = new CatGroup("Gruppe " + (n_items + 1));
								
								// Add group to this item's FosterHome
								TreeItem<Nameable> parent = getTreeItem();
								((FosterHome)item).addCatGroup(newGroup);
								
								// Add the group to the tree
                				TreeItem<Nameable> newGroupItem = new TreeItem<Nameable>((Nameable)newGroup);
                				parent.getChildren().add(newGroupItem);
                				
                				// Expand item that was just added to
                				parent.setExpanded(true);
							}
                		});
                		
                		// Register menu item
                		menu.getItems().add(addMenuItem);
                		
                	// Register add cat and remove group on group items
                	} else if(item instanceof CatGroup) {
                		
                		// Set the add MenuItem
                		addMenuItem.setText("Neue Katze");
                		addMenuItem.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent e) {
								// Create new cat
								Cat newCat = new Cat("Katze " + (n_items + 1));
								
								// Add cat to item's group
								TreeItem<Nameable> parent = getTreeItem();
								((CatGroup)item).addCat(newCat);
								
								// Add cat to tree
                				TreeItem<Nameable> newCatItem = new TreeItem<Nameable>((Nameable)newCat);
                				parent.getChildren().add(newCatItem);
                				
                				// Expand item that was just added to
                				parent.setExpanded(true);
							}
                		});
                		
                		// Set the remove MenuItem
                		removeMenuItem.setText("Gruppe entfernen");
                		removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                			@Override
                			public void handle(ActionEvent e) {
                				// Ask for confirmation from the user
                				confirmation.setContentText("Gruppe " + item.getName() + " wirklich entfernen?");
                				Optional<ButtonType> result = confirmation.showAndWait();
                				
                				// If they really want to remove the group
                				if (result.get() == ButtonType.OK){
                					// Remove the group from the FosterHome
                					FosterHome parent = (FosterHome)getTreeItem().getParent().getValue();
                					parent.removeCatGroup((CatGroup)item);
                					
                					// Remove the group from the tree
                    				TreeItem<Nameable> node = getTreeView().getSelectionModel().getSelectedItem();
                    				node.getParent().getChildren().remove(node);
                				}
                			}
                		});
                		
                		// Set the "render documents" item
                		renderMenuItem.setText("Dokumente für Gruppe erstellen");
                		renderMenuItem.setOnAction((ActionEvent e) -> {
                			DirectoryChooser dirChooser = new DirectoryChooser();
                			File outDir = dirChooser.showDialog(main.getPrimaryStage());
                			
                			
                			CatGroup group = (CatGroup)item;
                			DocumentRenderer renderer = new DocumentRenderer();
            			
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
            				//progressStage.initOwner(primaryStage);
            				//progressStage.initModality(Modality.WINDOW_MODAL);
                			
            				progressStage.show();
                			renderer.renderDocuments(group, outDir);
                		});
                		
                		// Register menu items
                		menu.getItems().add(addMenuItem);
                		menu.getItems().add(removeMenuItem);
                		
                		CatGroup group = (CatGroup)item;
                		if(group.getCats().size() > 0) {
                			menu.getItems().add(renderMenuItem);
                		}
                		
                	// Add remove Cat MenuItem if item is a Cat
                	} else if(item instanceof Cat) {
                		
                		// Create MenuItem
                		removeMenuItem.setText("Katze entfernen");
                		removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                			@Override
                			public void handle(ActionEvent e) {
                				// Ask for confirmation
                				confirmation.setContentText("Katze " + item.getName() + " wirklich entfernen?");
                				Optional<ButtonType> result = confirmation.showAndWait();
                				if (result.get() == ButtonType.OK) {
                					// Remove the cat from its gruop
                					CatGroup parent = (CatGroup)getTreeItem().getParent().getValue();
	                				parent.removeCat((Cat)item);
	                				
	                				// Remove the cat from the tree
	                				TreeItem<Nameable> node = getTreeView().getSelectionModel().getSelectedItem();
	                				node.getParent().getChildren().remove(node);
                				}
                			}
                		});
                		
                		renderMenuItem.setText("Dokumente für Katze erstellen");
                		renderMenuItem.setOnAction((ActionEvent e) -> {
                			DirectoryChooser dirChooser = new DirectoryChooser();
                			File outDir = dirChooser.showDialog(main.getPrimaryStage());
                			
                			
                			Cat cat = (Cat)item;
                			DocumentRenderer renderer = new DocumentRenderer();
                			renderer.renderDocuments(cat, outDir);
                		});
                		
                		// Register the menu items
                		menu.getItems().add(removeMenuItem);
                		menu.getItems().add(renderMenuItem);
                	}
            		
            		// Register the context menu
            		setContextMenu(menu);
                } else {
                	// If the item is to be emptied, unbind and reset the text property
                	this.textProperty().unbind();
                	setText(null);
                }
            }
        }
		
        // Create the tree from the rootItem created above
        TreeView<Nameable> tree = new TreeView<Nameable>(rootItem);
        
        // Hide the root item
        tree.setShowRoot(false);
        
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
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(CatHerdMain.class.getResource("view/MainMenu.fxml"));
			AnchorPane mainMenu = (AnchorPane) loader.load();
			
			MainMenuController menuController = loader.getController();
			menuController.setMain(main);
			
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
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(CatHerdMain.class.getResource("view/CatEditor.fxml"));
			AnchorPane catEditor = (AnchorPane) loader.load();
			rightPane.setCenter(catEditor);
			
			CatEditorController controller = loader.getController();
			controller.setCat(cat);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void showCatGroupEditor(CatGroup group) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(CatHerdMain.class.getResource("view/CatGroupEditor.fxml"));
			AnchorPane catEditor = (AnchorPane) loader.load();
			rightPane.setCenter(catEditor);
			
			CatGroupEditorController controller = loader.getController();
			controller.setCatGroup(group);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void showFosterHomeEditor(FosterHome home) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(CatHerdMain.class.getResource("view/FosterHomeEditor.fxml"));
			AnchorPane catEditor = (AnchorPane) loader.load();
			rightPane.setCenter(catEditor);
			
			FosterHomeEditorController controller = loader.getController();
			controller.setFosterHome(home);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
