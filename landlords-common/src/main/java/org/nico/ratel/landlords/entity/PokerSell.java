package org.nico.ratel.landlords.entity;

import org.nico.ratel.landlords.enums.SellType;
import org.nico.ratel.landlords.helper.PokerHelper;

import java.util.List;

public class PokerSell {

	private int score;

	private SellType sellType;

	private List<Poker> sellPokers;

	private int coreLevel;

	public PokerSell(SellType sellType, List<Poker> sellPokers, int coreLevel) {
		this.score = PokerHelper.parseScore(sellType, coreLevel);
		this.sellType = sellType;
		this.sellPokers = sellPokers;
		this.coreLevel = coreLevel;
	}

	public final int getCoreLevel() {
		return coreLevel;
	}

	public final void setCoreLevel(int coreLevel) {
		this.coreLevel = coreLevel;
	}

	public final int getScore() {
		return score;
	}

	public final void setScore(int score) {
		this.score = score;
	}

	public final SellType getSellType() {
		return sellType;
	}

	public final void setSellType(SellType sellType) {
		this.sellType = sellType;
	}

	public final List<Poker> getSellPokers() {
		return sellPokers;
	}

	public final void setSellPokers(List<Poker> sellPokers) {
		this.sellPokers = sellPokers;
	}

	@Override
	public String toString() {
		return sellType + "\t| " + score + "\t|" + sellPokers;
	}

}
