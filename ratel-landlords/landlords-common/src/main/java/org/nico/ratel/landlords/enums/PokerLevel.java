package org.nico.ratel.landlords.enums;

import java.io.Serializable;

/**
 * Poker level
 * 
 * @author nico
 */
public enum PokerLevel implements Serializable{

	LEVEL_3(3, "3"),
	
	LEVEL_4(4, "4"),
	
	LEVEL_5(5, "5"),
	
	LEVEL_6(6, "6"),
	
	LEVEL_7(7, "7"),
	
	LEVEL_8(8, "8"),
	
	LEVEL_9(9, "9"),
	
	LEVEL_10(10, "10"),
	
	LEVEL_J(11, "J"),
	
	LEVEL_Q(12, "Q"),
	
	LEVEL_K(13, "K"),
	
	LEVEL_A(14, "A"),
	
	LEVEL_2(15, "2"),
	
	LEVEL_SMALL_KING(16, "SMALL JOKER"),
	
	LEVEL_BIG_KING(17, "BIG JOKER"),
	;
	
	private int level;
	
	private String name;
	
	

	private PokerLevel(int level, String name) {
		this.name = name;
		this.level = level;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final int getLevel() {
		return level;
	}

	public final void setLevel(int level) {
		this.level = level;
	}
}
