package org.nico.ratel.landlords.enums;

import java.io.Serializable;

public enum ServerEventCode implements Serializable{

	CODE_CLIENT_EXIT("玩家退出"),
	
	CODE_CLIENT_NICKNAME_SET("设置昵称"),
	
	CODE_ROOM_CREATE("创建房间"),
	
	CODE_ROOM_LIST_GET("获取房间列表"),
	
	CODE_ROOM_JOIN("加入房间"),
	
	CODE_GAME_STARTING("游戏开始"),
	
	CODE_GAME_LANDLORD_ELECT("抢地主"),
	
	CODE_GAME_POKER_PLAY("出牌环节"),
	
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
