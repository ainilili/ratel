package org.nico.ratel.landlords.enums;

import java.io.Serializable;

public enum ServerEventCode implements Serializable {

	CODE_CLIENT_EXIT("玩家退出"),

	CODE_CLIENT_OFFLINE("玩家离线"),

	CODE_CLIENT_INFO_SET("设置客户端信息"),

	CODE_CLIENT_NICKNAME_SET("设置昵称"),

	CODE_CLIENT_HEAD_BEAT("不出"),

	CODE_ROOM_CREATE("创建PVP房间"),

	CODE_ROOM_CREATE_PVE("创建PVE房间"),

	CODE_GET_ROOMS("获取房间列表"),

	CODE_ROOM_JOIN("加入房间"),

	CODE_GAME_STARTING("游戏开始"),

	CODE_GAME_READY("玩家准备"),

	CODE_GAME_LANDLORD_ELECT("抢地主"),

	CODE_GAME_POKER_PLAY("出牌环节"),

	CODE_GAME_POKER_PLAY_REDIRECT("出牌重定向"),

	CODE_GAME_POKER_PLAY_PASS("不出"),

	CODE_GAME_WATCH("观战"),

	CODE_GAME_WATCH_EXIT("退出观战");


	private String msg;

	ServerEventCode(String msg) {
		this.msg = msg;
	}

	public final String getMsg() {
		return msg;
	}

	public final void setMsg(String msg) {
		this.msg = msg;
	}

}
