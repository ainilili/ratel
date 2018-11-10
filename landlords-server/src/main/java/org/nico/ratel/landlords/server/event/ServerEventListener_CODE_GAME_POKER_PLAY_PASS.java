package org.nico.ratel.landlords.server.event;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.server.ServerContains;

public class ServerEventListener_CODE_GAME_POKER_PLAY_PASS implements ServerEventListener{

	@Override
	public void call(ClientSide clientSide, String data) {
		Room room = ServerContains.ROOM_MAP.get(clientSide.getRoomId());
		
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
					ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY_PASS, result);
				}
			}else {
				ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY_CANT_PASS, null);
			}
		}else {
			ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_GAME_POKER_PLAY_ORDER_ERROR, null);
		}
	}

}
