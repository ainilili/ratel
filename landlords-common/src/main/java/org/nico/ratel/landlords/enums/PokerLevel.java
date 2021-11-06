package org.nico.ratel.landlords.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Poker level
 *
 * @author nico
 */
public enum PokerLevel {

	LEVEL_3(3, "3", new Character[]{'3'}),

	LEVEL_4(4, "4", new Character[]{'4'}),

	LEVEL_5(5, "5", new Character[]{'5'}),

	LEVEL_6(6, "6", new Character[]{'6'}),

	LEVEL_7(7, "7", new Character[]{'7'}),

	LEVEL_8(8, "8", new Character[]{'8'}),

	LEVEL_9(9, "9", new Character[]{'9'}),

	LEVEL_10(10, "10", new Character[]{'T', 't', '0'}),

	LEVEL_J(11, "J", new Character[]{'J', 'j'}),

	LEVEL_Q(12, "Q", new Character[]{'Q', 'q'}),

	LEVEL_K(13, "K", new Character[]{'K', 'k'}),

	LEVEL_A(14, "A", new Character[]{'A', 'a', '1'}),

	LEVEL_2(15, "2", new Character[]{'2'}),

	LEVEL_SMALL_KING(16, "S", new Character[]{'S', 's'}),

	LEVEL_BIG_KING(17, "X", new Character[]{'X', 'x'}),
	;

	private int level;

	private String name;

	private Character[] alias;

	private static Set<Character> aliasSet = new HashSet<>();

	static {
		for (PokerLevel level : PokerLevel.values()) {
			PokerLevel.aliasSet.addAll(Arrays.asList(level.getAlias()));
		}
	}

	private PokerLevel(int level, String name, Character[] alias) {
		this.level = level;
		this.name = name;
		this.alias = alias;
	}

	public static boolean aliasContains(char key) {
		return aliasSet.contains(key);
	}

	public final Character[] getAlias() {
		return alias;
	}

	public final void setAlias(Character[] alias) {
		this.alias = alias;
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
	
	public static final PokerLevel parseByName(String name) {
		if(name == null) {
			return null;
		}
		for(PokerLevel level: PokerLevel.values()) {
			if(level.name.equals(name.toUpperCase())) {
				return level;
			}
		}
		return null;
	}
	
	public static final PokerLevel parseByLevel(int l) {
		for(PokerLevel level: PokerLevel.values()) {
			if(level.level == l) {
				return level;
			}
		}
		return null;
	}
}
