package org.nico.ratel.landlords.enums;

import java.io.Serializable;

public enum ServerEventCode implements Serializable{

	CODE_RENAME("修改昵称"),
	
	CODE_CREATE_ROOM("创建房间"),
	
	CODE_JOIN_ROOM("加入房间"),
	;
	
	private String msg;

	private ServerEventCode(String msg) {
		this.msg = msg;
	}

	public final String getMsg() {
		return msg;
	}

	public final void setMsg(String msg) {
		this.msg = msg;
	}
	
}
