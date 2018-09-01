package ch.katzenhausfreunde.catherd.model;

import javax.xml.bind.annotation.XmlElement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author thoenis
 *
 */
public class FosterHome extends Nameable {
	/**
	 * A list of CatGroup object belonging to this home.
	 */
	@XmlElement(name = "groups")
	private ObservableList<CatGroup> groups = FXCollections.observableArrayList();
	
	/**
	 * Generate a FosterHome with the default name "Meine Pflegestelle"
	 */
	public FosterHome() {
		this("Meine Pflegestelle");
	}
	
	/**
	 * @param name Generate a FosterHome with the given properties.
	 */
	public FosterHome(String name) {
		super(name);
	}

	/**
	 * @return
	 */
	public ObservableList<CatGroup> getGroups() {
		return this.groups;
	}
	
	/**
	 * Add the given CatGroup to this FosterHome
	 * 
	 * @param group The group to add
	 */
	public void addCatGroup(CatGroup group) {
		groups.add(group);
	}
	
	/**
	 * Remove the given CatGroup from this FosterHome
	 * 
	 * @param group The group to be removed
	 */
	public void removeCatGroup(CatGroup group) {
		groups.remove(group);
	}
	
	/**
	 * Add the Cat to the given CatGroup
	 * 
	 * @param cat The Cat to add
	 * @param group The CatGroup to which to add the Cat
	 */
	public void addCatToGroup(Cat cat, CatGroup group) {
		for(CatGroup g : groups) {
			if(g.getId() == group.getId()) {
				g.addCat(cat);
			}
		}
	}
	
	/**
	 * Removes the given Cat from any groups it belongs to.
	 * 
	 * @param cat The Cat to remove.
	 */
	public void removeCat(Cat cat) {
		for(CatGroup g : groups) {
			g.removeCat(cat);
		}
	}
	
	public void arm() {
		super.arm();
		groups.forEach((gr) -> gr.arm());
	}
	
	/**
	 * Add some dummy testingdata to the home. For development purposes only.
	 */
	public void populateDummies() {
		this.groups.add(new CatGroup("1"));
		this.groups.get(0).getCats().add(new Cat("Mittens"));
		this.groups.get(0).getCats().add(new Cat("Spot"));
		this.groups.add(new CatGroup("2"));
		this.groups.get(1).getCats().add(new Cat("Fluffy"));
		this.groups.add(new CatGroup("3"));
	}
}
