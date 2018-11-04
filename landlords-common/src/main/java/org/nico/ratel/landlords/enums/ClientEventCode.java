package org.nico.ratel.landlords.enums;

import java.io.Serializable;

public enum ClientEventCode implements Serializable{

	CODE_CONNECT("进入系统"),
	
	CODE_SHOW_OPTIONS("展示选项"),
	
	CODE_SHOW_ROOM_LIST("展示房间列表"),
	
	CODE_JOIN_ROOM_SUCCESS("加入房间成功"),
	
	CODE_ROOM_STARTING("开始游戏"),
	
	CODE_SHOW_POKERS("展示Poker"),
	
	CODE_GRAB_LANDLORD("抢地主"),
	
	CODE_PLAY_ROUND("出牌回合"),
	;
	
	private String msg;

	private ClientEventCode(String msg) {
		this.msg = msg;
	}

	public final String getMsg() {
		return msg;
	}

	public final void setMsg(String msg) {
		this.msg = msg;
	}
	
}
