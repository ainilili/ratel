package org.nico.ratel.landlords.server.event;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ClientRole;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.server.ServerContains;
import org.nico.ratel.landlords.server.robot.RobotEventListener;

public class ServerEventListener_CODE_GAME_POKER_PLAY_PASS implements ServerEventListener{

	@Override
	public void call(ClientSide clientSide, String data) {
		Room room = ServerContains.getRoom(clientSide.getRoomId());

		if(room != null) {
			if(room.getCurrentSellClient() == clientSide.getId()) {
				if(clientSide.getId() != room.getLastSellClient()) {
					ClientSide turnClient = clientSide.getNext();

					room.setCurrentSellClient(turnClient.getId());

					for(ClientSide client: room.getClientSideList()) {
						String result = MapHelper.newInstance()
								.put("clientId", clientSide.getId())
								.put("clientNickname", clientSide.getNickname())
								.put("nextClientId", turnClient.getId())
								.put("nextClientNickname", turnClient.getNickname())
								.json();
						if(client.getRole() == ClientRole.PLAYER) {
							ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY_PASS, result);
						}else {
							if(client.getId() == turnClient.getId()) {
								RobotEventListener.get(ClientEventCode.CODE_GAME_POKER_PLAY).call(turnClient, data);
							}
						}
					}
				}else {
					ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY_CANT_PASS, null);
				}
			}else {
				ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY_ORDER_ERROR, null);
			}
		}else {
//			ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_ROOM_PLAY_FAIL_BY_INEXIST, null);
		}
	}

}
