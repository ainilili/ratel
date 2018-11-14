package org.nico.ratel.landlords.server.robot;

import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.PokerSell;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.server.ServerContains;

public class RobotEventListener_CODE_GAME_POKER_PLAY implements RobotEventListener{

	@Override
	public void call(ClientSide robot, String data) {
		Room room = ServerContains.ROOM_MAP.get(robot.getRoomId());
		
		if(room.getLastSellClient() == robot.getId()) {
			
		}else {
			PokerSell lastPokerShell = room.getLastPokerShell();
		}
	}
	
}
