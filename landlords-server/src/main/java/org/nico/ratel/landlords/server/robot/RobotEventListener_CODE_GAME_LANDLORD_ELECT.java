package org.nico.ratel.landlords.server.robot;

import java.util.Map;

import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.robot.RobotDecisionMakers;
import org.nico.ratel.landlords.server.ServerContains;
import org.nico.ratel.landlords.server.event.ServerEventListener;

public class RobotEventListener_CODE_GAME_LANDLORD_ELECT implements RobotEventListener{

	@Override
	public void call(ClientSide robot, String data) {
		
		Room room = ServerContains.ROOM_MAP.get(robot.getRoomId());
		
		Map<String, Object> map = MapHelper.parser(data);
		int turnClientId = (int) map.get("nextClientId");
		
		if(turnClientId == robot.getId()) {
			ServerEventListener.get(ServerEventCode.CODE_GAME_LANDLORD_ELECT).call(robot, String.valueOf(RobotDecisionMakers.howToChooseLandlord(robot.getPre().getPokers(), robot.getNext().getPokers(), room.getLandlordPokers(), robot.getPokers())));
		}
		
	}
	
}
