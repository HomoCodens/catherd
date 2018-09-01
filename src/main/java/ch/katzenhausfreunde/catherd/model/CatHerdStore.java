package ch.katzenhausfreunde.catherd.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@XmlRootElement(name = "catherd")
public class CatHerdStore {
	@XmlElement(name = "fosterhomes")
	private ObservableList<FosterHome> fosterHomes = FXCollections.observableArrayList();
	
	public CatHerdStore() {
	}
	
	public ObservableList<FosterHome> getFosterHomes() {
		return this.fosterHomes;
	}
	
	public void addCat(FosterHome home, CatGroup group, Cat cat) {
		for(FosterHome h : fosterHomes) {
			if(h.getId() == home.getId()) {
				h.addCatToGroup(cat, group);
			}
		}
	}
	
	public void addCatGroupToHome(FosterHome home, CatGroup group) {
		for(FosterHome h : fosterHomes) {
			if(h.getId() == home.getId()) {
				h.addCatGroup(group);
			}
		}
	}
	
	public void arm() {
		fosterHomes.forEach((fh) -> fh.arm());
	}
	
	public void populateDummies() {
		FosterHome home = new FosterHome();
		home.populateDummies();
		this.fosterHomes.add(home);
		
	}
}
