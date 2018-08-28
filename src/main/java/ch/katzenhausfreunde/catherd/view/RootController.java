package ch.katzenhausfreunde.catherd.view;

import java.io.IOException;

import ch.katzenhausfreunde.catherd.CatHerdMain;
import ch.katzenhausfreunde.catherd.model.Cat;
import ch.katzenhausfreunde.catherd.model.CatGroup;
import ch.katzenhausfreunde.catherd.model.FosterHome;
import ch.katzenhausfreunde.catherd.model.Nameable;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class RootController {
	private CatHerdMain main;
	
	@FXML
	private BorderPane leftPane;
	
	@FXML
	private BorderPane rightPane;
	
	public RootController() {
		
	}
	
	@FXML
	private void initialize() {
		showWelcomeScreen();
	}
	
	public void setMain(CatHerdMain main) {
		this.main = main;
		
		TreeItem<Nameable> rootItem = new TreeItem<Nameable>(new Nameable("root"));
		
		TreeItem<Nameable> fosterHome = new TreeItem<Nameable>(main.getFosterHome());
        rootItem.getChildren().add(fosterHome);
        
        for(CatGroup g: main.getGroups()) {
        	TreeItem<Nameable> group = new TreeItem<Nameable>(g);
        	for(Cat c : g.getCats()) {
        		TreeItem<Nameable> catNode = new TreeItem<Nameable>(c);
        		group.getChildren().add(catNode);
        	}
        	fosterHome.getChildren().add(group);
        }
        
        final class CatCell extends TreeCell<Nameable> {
       	
            public CatCell() {
            	
            }
     
            @Override
            public void updateItem(Nameable item, boolean empty) {
                super.updateItem(item, empty);
                
                if(!empty) {
                	this.textProperty().bind(getItem().getNameProperty());
                	
                	if(item instanceof FosterHome || item instanceof CatGroup) {
                		ContextMenu addMenu = new ContextMenu();
                		MenuItem addMenuItem = new MenuItem();
                		int n_items = getTreeItem().getChildren().size();
                		
                		if(item instanceof FosterHome) {
                			addMenuItem.setText("Neue Gruppe");
                    		addMenuItem.setOnAction(new EventHandler<ActionEvent>() {
    							@Override
    							public void handle(ActionEvent e) {
    								CatGroup newGroup = new CatGroup("Gruppe " + (n_items + 1));
    								TreeItem<Nameable> item = getTreeItem();
    								main.addCatGroup(newGroup);
                    				TreeItem<Nameable> newGroupItem = new TreeItem<Nameable>((Nameable)newGroup);
                    				item.getChildren().add(newGroupItem);
                    				item.setExpanded(true);
    							}
                    		});
                    	} else if(item instanceof CatGroup) {
                    		addMenuItem.setText("Neue Katze");
                    		addMenuItem.setOnAction(new EventHandler<ActionEvent>() {
    							@Override
    							public void handle(ActionEvent e) {
    								Cat newCat = new Cat("Katze " + (n_items + 1));
    								TreeItem<Nameable> item = getTreeItem();
    								main.addCatToGroup(newCat, (CatGroup)item.getValue());
                    				TreeItem<Nameable> newCatItem = new TreeItem<Nameable>((Nameable)newCat);
                    				item.getChildren().add(newCatItem);
                    				item.setExpanded(true);
    							}
                    		});                    	}
                		addMenu.getItems().add(addMenuItem);
                		setContextMenu(addMenu);
                	}
                }
            }
        }
        
        TreeView<Nameable> tree = new TreeView<Nameable>(rootItem);
        tree.setShowRoot(false);
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
        tree.setCellFactory(new Callback<TreeView<Nameable>,TreeCell<Nameable>>(){
            @Override
            public TreeCell<Nameable> call(TreeView<Nameable> p) {
                return new CatCell();
            }
        });
        leftPane.setCenter(tree);
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
