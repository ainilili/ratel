package org.nico.ratel.landlords.enums;

/**
 * Poker type Spade、 Heart、 Diamond、 Club
 * 
 * @author nico
 */
public enum PokerType{

	BLANK(" "),
	
	DIAMOND("♦"),
	
	CLUB("♣"),
	
	SPADE("♠"),
	
	HEART("♥")
	;
	
	private String name;

	private PokerType(String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}
}
