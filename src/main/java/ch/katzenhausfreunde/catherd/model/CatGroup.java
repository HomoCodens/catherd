package ch.katzenhausfreunde.catherd.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CatGroup extends Nameable {
	private ObservableList<Cat> cats = FXCollections.observableArrayList();
	
	public CatGroup() {
		this("Grippe");
	}
	
	public CatGroup(String name) {
		super(name);
	}
	
	public ObservableList<Cat> getCats() {
		return this.cats;
	}
	
	public void setCats(ObservableList<Cat> cats) {
		this.cats = cats;
	}
	
	public void addCat(Cat cat) {
		cats.add(cat);
	}
}
