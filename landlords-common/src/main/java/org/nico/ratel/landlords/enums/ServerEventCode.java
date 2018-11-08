package org.nico.ratel.landlords.enums;

import java.io.Serializable;

public enum ServerEventCode implements Serializable{

	CODE_SET_NICKNAME("设置昵称"),
	
	CODE_CREATE_ROOM("创建房间"),
	
	CODE_GET_ROOM_LIST("获取房间列表"),
	
	CODE_JOIN_ROOM("加入房间"),
	
	CODE_GRAB_LANDLORD("抢地主"),
	
	CODE_PLAY_ROUND("出牌环节"),
	
	CODE_PLAYER_EXIT("玩家退出"),
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
