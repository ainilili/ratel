package org.nico.ratel.landlords.entity;

import org.nico.ratel.landlords.enums.PokerLevel;
import org.nico.ratel.landlords.enums.PokerType;

/**
 * Poke, with {@link PokerLevel} and {@link PokerType}
 *
 * @author nico
 */
public class Poker {

	private PokerLevel level;

	private PokerType type;

	public Poker() {
	}

	public Poker(PokerLevel level, PokerType type) {
		this.level = level;
		this.type = type;
	}

	public final PokerLevel getLevel() {
		return level;
	}

	public final void setLevel(PokerLevel level) {
		this.level = level;
	}

	public final PokerType getType() {
		return type;
	}

	public final void setType(PokerType type) {
		this.type = type;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Poker other = (Poker) obj;
		if (level != other.level)
			return false;
		return type == other.type;
	}

	@Override
	public String toString() {
		return level.getLevel() + " ";
	}

}
