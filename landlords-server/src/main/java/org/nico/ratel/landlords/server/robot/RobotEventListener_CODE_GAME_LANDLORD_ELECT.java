package org.nico.ratel.landlords.server.robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.helper.PokerHelper;
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
			List<Poker> landlordPokers = new ArrayList<>(20);
			landlordPokers.addAll(robot.getPokers());
			landlordPokers.addAll(room.getLandlordPokers());
			
			List<Poker> leftPokers = new ArrayList<>(17);
			leftPokers.addAll(robot.getPre().getPokers());
			
			List<Poker> rightPokers = new ArrayList<>(17);
			leftPokers.addAll(robot.getNext().getPokers());
			
			PokerHelper.sortPoker(landlordPokers);
			PokerHelper.sortPoker(leftPokers);
			PokerHelper.sortPoker(rightPokers);
			
			ServerEventListener.get(ServerEventCode.CODE_GAME_LANDLORD_ELECT).call(robot, String.valueOf(RobotDecisionMakers.howToChooseLandlord(room.getDifficultyCoefficient(), leftPokers, rightPokers, landlordPokers)));
		}
		
	}
	
}
