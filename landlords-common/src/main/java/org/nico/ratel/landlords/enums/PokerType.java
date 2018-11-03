package org.nico.ratel.landlords.enums;

import java.io.Serializable;

/**
 * Poker type Spade、 Heart、 Diamond、 Club
 * 
 * @author nico
 */
public enum PokerType implements Serializable{

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
