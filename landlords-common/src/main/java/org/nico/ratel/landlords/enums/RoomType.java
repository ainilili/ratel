package org.nico.ratel.landlords.enums;

public enum RoomType {

	PVP("玩家对战"),

	PVE("人机对战"),

	;
	private String msg;

	RoomType(String msg) {
		this.msg = msg;
	}

	public final String getMsg() {
		return msg;
	}

	public final void setMsg(String msg) {
		this.msg = msg;
	}

}
