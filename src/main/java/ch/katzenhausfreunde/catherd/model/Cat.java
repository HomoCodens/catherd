package ch.katzenhausfreunde.catherd.model;

/**
 * @author Severin
 *
 */
public class Cat extends Nameable {
	/**
	 * 
	 */
	public Cat() {
		this("Mittens");
	}
	
	/**
	 * @param name
	 */
	public Cat(String name) {
		super(name);
	}
	
	public void arm() {
		super.arm();
	}
}
