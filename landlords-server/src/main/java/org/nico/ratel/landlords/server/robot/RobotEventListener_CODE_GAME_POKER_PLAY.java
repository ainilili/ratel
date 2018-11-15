package org.nico.ratel.landlords.server.robot;

import java.util.List;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.entity.PokerSell;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ClientRole;
import org.nico.ratel.landlords.enums.SellType;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.robot.RobotDecisionMakers;
import org.nico.ratel.landlords.server.ServerContains;

public class RobotEventListener_CODE_GAME_POKER_PLAY implements RobotEventListener{

	@Override
	public void call(ClientSide robot, String data) {
		Room room = ServerContains.ROOM_MAP.get(robot.getRoomId());

		PokerSell lastPokerShell = null;
		if(room.getLastSellClient() != robot.getId()) {
			lastPokerShell = room.getLastPokerShell();

			PokerSell pokerSell = RobotDecisionMakers.howToPlayPokers(room.getDifficultyCoefficient(), lastPokerShell, robot.getPokers());
			if(pokerSell == null || pokerSell.getSellType() == SellType.ILLEGAL) {
				ClientSide turnClient = robot.getNext();
				for(ClientSide client: room.getClientSideList()) {
					String result = MapHelper.newInstance()
							.put("clientId", robot.getId())
							.put("clientNickname", robot.getNickname())
							.put("nextClientId", turnClient.getId())
							.put("nextClientNickname", turnClient.getNickname())
							.json();
					if(client.getRole() == ClientRole.PLAYER) {
						ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY_PASS, result);
					}
				}
			}else {

			}
		}
	}
}
