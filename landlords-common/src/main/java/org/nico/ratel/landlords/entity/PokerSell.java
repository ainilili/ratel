package org.nico.ratel.landlords.entity;

import java.util.List;

import org.nico.ratel.landlords.enums.SellType;

public class PokerSell {

	private int score;
	
	private SellType sellType;
	
	private List<Poker> sellPokers;

	public PokerSell(int score, SellType sellType, List<Poker> sellPokers) {
		super();
		this.score = score;
		this.sellType = sellType;
		this.sellPokers = sellPokers;
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
	
}
