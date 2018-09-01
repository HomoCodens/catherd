package ch.katzenhausfreunde.catherd.util;

import ch.katzenhausfreunde.catherd.model.Cat;
import ch.katzenhausfreunde.catherd.model.CatGroup;
import ch.katzenhausfreunde.catherd.model.CatHerdStore;
import ch.katzenhausfreunde.catherd.model.FosterHome;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Static/Singleton class that holds the applications state.
 * Currently "state" means the data store and a flag indicating whether
 * data in said store have changed.
 * 
 * @author thoenis
 *
 */
public class CatHerdState {
	private static CatHerdState instance;
	private static CatHerdStore store;
	/**
	 * A BooleanProperty indicating if the data in the store have changed since
	 * the app was launched. 
	 */
	private static BooleanProperty dirty = new SimpleBooleanProperty(false);
	
	private CatHerdState() {
	}
	
	public static CatHerdState getInstance() {
		if(instance == null) {
			instance = new CatHerdState();
		}
		
		return instance;
	}
	
	/**
	 * Set the dirty flag to true.
	 * This function will be bound to the properties of the elements in the store.
	 */
	public static void touchStore() {
		if(!isDirty()) {
			System.out.println("As of now I am a dirty dirty state");
		}
		dirty.set(true);
	}
	
	public static void cleanStore() {
		dirty.set(false);
	}
	
	public static boolean isDirty() {
		return dirty.get();
	}
	
	public static BooleanProperty dirtyProperty() {
		return dirty;
	}
	
	public static void setStore(CatHerdStore s) {
		store = s;
		cleanStore();
	}
	
	public static CatHerdStore getStore() {
		return store;
	}
	
	public static FosterHome getCatsHome(Cat cat) {
		for(FosterHome fh : store.getFosterHomes()) {
			for(CatGroup gr : fh.getGroups()) {
				if(gr.getCats().contains(cat)) {
					return fh;
				}
			}
		}
		return null;
	}
	
	public static CatGroup getCatsGroup(Cat cat) {
		for(FosterHome fh : store.getFosterHomes()) {
			for(CatGroup gr : fh.getGroups()) {
				if(gr.getCats().contains(cat)) {
					return gr;
				}
			}
		}
		return null;
	}
	
	/**
	 * Makes all elements in the state's store register touchStore as listener on their properties.
	 */
	public static void arm() {
		store.arm();
	}
}
