package ch.katzenhausfreunde.catherd.model;

import java.nio.file.Paths;

import javax.xml.bind.annotation.XmlElement;

import ch.katzenhausfreunde.catherd.util.DocumentRenderer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FosterHome extends Nameable {
	@XmlElement(name = "group")
	private ObservableList<CatGroup> groups = FXCollections.observableArrayList();
	
	public FosterHome() {
		this("Meine Pflegestelle");
	}
	
	public FosterHome(String name) {
		super(name);
	}

	public ObservableList<CatGroup> getGroups() {
		return this.groups;
	}
	
	public void addCatGroup(CatGroup group) {
		groups.add(group);
	}
	
	public void removeCatGroup(CatGroup group) {
		groups.remove(group);
	}
	
	public void addCatToGroup(Cat cat, CatGroup group) {
		for(CatGroup g : groups) {
			if(g.getId() == group.getId()) {
				g.addCat(cat);
			}
		}
	}
	
	public void removeCat(Cat cat) {
		for(CatGroup g : groups) {
			g.removeCat(cat);
		}
	}
	
	public void populateDummies() {
		this.groups.add(new CatGroup("1"));
		this.groups.get(0).getCats().add(new Cat("Mittens"));
		this.groups.get(0).getCats().add(new Cat("Spot"));
		this.groups.add(new CatGroup("2"));
		this.groups.get(1).getCats().add(new Cat("Fluffy"));
		this.groups.add(new CatGroup("3"));
		System.out.println("About to do it");
	}
}
