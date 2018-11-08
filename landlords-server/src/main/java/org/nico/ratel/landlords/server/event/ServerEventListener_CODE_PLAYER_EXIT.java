package org.nico.ratel.landlords.server.event;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.entity.ServerTransferData;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.server.ServerContains;

import io.netty.channel.Channel;

public class ServerEventListener_CODE_PLAYER_EXIT implements ServerEventListener{

	@Override
	public void call(ClientSide client, String data) {
		
		Room room = ServerContains.ROOM_MAP.get(client.getRoomId());
		
		if(room != null) {
			for(ClientSide roomClient: room.getClientSideList()) {
				if(client.getId() != roomClient.getId()) {
					ChannelUtils.pushToClient(roomClient.getChannel(), ClientEventCode.CODE_SHOW_OPTIONS, null, client.getNickname() + " exited, room dissolve!!");
				}else {
					ChannelUtils.pushToClient(roomClient.getChannel(), ClientEventCode.CODE_SHOW_OPTIONS, null, "you exited, room dissolve!!");
				}
				roomClient.init();
			}
			ServerContains.ROOM_MAP.remove(room.getId());
		}
	}

}
