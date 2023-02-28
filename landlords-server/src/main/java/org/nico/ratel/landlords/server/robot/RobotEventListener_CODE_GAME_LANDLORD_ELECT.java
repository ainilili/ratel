package org.nico.ratel.landlords.server.robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.helper.PokerHelper;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.helper.TimeHelper;
import org.nico.ratel.landlords.robot.RobotDecisionMakers;
import org.nico.ratel.landlords.server.ServerContains;
import org.nico.ratel.landlords.server.event.ServerEventListener;

public class RobotEventListener_CODE_GAME_LANDLORD_ELECT implements RobotEventListener {

	@Override
	public void call(ClientSide robot, String data) {
		ServerContains.THREAD_EXCUTER.execute(() -> {
			Room room = ServerContains.getRoom(robot.getRoomId());

			List<Poker> landlordPokers = new ArrayList<>(20);
			landlordPokers.addAll(robot.getPokers());
			landlordPokers.addAll(room.getLandlordPokers());

			List<Poker> leftPokers = new ArrayList<>(17);
			leftPokers.addAll(robot.getPre().getPokers());

			List<Poker> rightPokers = new ArrayList<>(17);
			rightPokers.addAll(robot.getNext().getPokers());

			PokerHelper.sortPoker(landlordPokers);
			PokerHelper.sortPoker(leftPokers);
			PokerHelper.sortPoker(rightPokers);

			TimeHelper.sleep(300);

			Map<String, Object> map = MapHelper.parser(data);
			int expectedScore = RobotDecisionMakers.getLandlordScore(room.getDifficultyCoefficient(), leftPokers, rightPokers, landlordPokers);
			int highestScore = (Integer)map.get("highestScore");
			String result;
			if (expectedScore > highestScore) {
				result = MapHelper.newInstance()
					.put("highestScore", expectedScore)
					.put("currentLandlordId", robot.getId())
					.json();
			} else {
				result = data;
			}
   
			ServerEventListener.get(ServerEventCode.CODE_GAME_LANDLORD_ELECT).call(robot, result);
		});
	}

}
