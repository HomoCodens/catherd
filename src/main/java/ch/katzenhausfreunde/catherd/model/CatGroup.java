package ch.katzenhausfreunde.catherd.model;

import javax.xml.bind.annotation.XmlElement;

import ch.katzenhausfreunde.catherd.util.CatHerdState;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CatGroup extends Nameable {
	@XmlElement(name = "cats")
	private ObservableList<Cat> cats = FXCollections.observableArrayList();
	
	public CatGroup() {
		this("Gruppe");
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
	
	public void removeCat(Cat cat) {
		cats.remove(cat);
	}
	
	public void arm() {
		super.arm();
		cats.addListener((Observable o) -> CatHerdState.touchStore());
		cats.forEach((ct) -> ct.arm());
	}
}
