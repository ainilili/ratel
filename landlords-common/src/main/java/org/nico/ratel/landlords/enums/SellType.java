package org.nico.ratel.landlords.enums;

public enum SellType {

	ILLEGAL("非合法"),

	BOMB("炸弹"),

	KING_BOMB("王炸"),

	SINGLE("单个牌"),

	DOUBLE("对子牌"),

	THREE("三张牌"),

	THREE_ZONES_SINGLE("三带单"),

	THREE_ZONES_DOUBLE("三带对"),

	FOUR_ZONES_SINGLE("四带单"),

	FOUR_ZONES_DOUBLE("四带对"),

	SINGLE_STRAIGHT("单顺子"),

	DOUBLE_STRAIGHT("双顺子"),

	THREE_STRAIGHT("三顺子"),

	FOUR_STRAIGHT("四顺子"),

	THREE_STRAIGHT_WITH_SINGLE("飞机带单牌"),

	THREE_STRAIGHT_WITH_DOUBLE("飞机带对牌"),

	FOUR_STRAIGHT_WITH_SINGLE("四顺子带单"),

	FOUR_STRAIGHT_WITH_DOUBLE("四顺子带对"),
	;

	private String msg;

	SellType(String msg) {
		this.msg = msg;
	}

	public final String getMsg() {
		return msg;
	}

	public final void setMsg(String msg) {
		this.msg = msg;
	}

}
