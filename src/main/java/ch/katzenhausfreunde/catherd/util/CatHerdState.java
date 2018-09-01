package ch.katzenhausfreunde.catherd.util;

import ch.katzenhausfreunde.catherd.model.CatHerdStore;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class CatHerdState {
	private static CatHerdState instance;
	private static CatHerdStore store;
	private static BooleanProperty dirty = new SimpleBooleanProperty(false);
	
	private CatHerdState() {
	}
	
	public static CatHerdState getInstance() {
		if(instance == null) {
			instance = new CatHerdState();
		}
		
		return instance;
	}
	
	public static void touchStore() {
		if(!isDirty()) {
			System.out.println("As of now I am a dirty dirty state");
		}
		dirty.set(true);
	}
	
	public static boolean isDirty() {
		return dirty.get();
	}
	
	public static BooleanProperty dirtyProperty() {
		return dirty;
	}
	
	public static void setStore(CatHerdStore s) {
		store = s;
	}
	
	public static CatHerdStore getStore() {
		return store;
	}
	
	public static void arm() {
		store.arm();
	}
}
