package org.nico.ratel.landlords.enums;

public enum RoomStatus {

	BLANK("空闲"),

	WAIT("等待"),

	STARTING("开始"),


	;

	private String msg;

	RoomStatus(String msg) {
		this.msg = msg;
	}

	public final String getMsg() {
		return msg;
	}

	public final void setMsg(String msg) {
		this.msg = msg;
	}

}
