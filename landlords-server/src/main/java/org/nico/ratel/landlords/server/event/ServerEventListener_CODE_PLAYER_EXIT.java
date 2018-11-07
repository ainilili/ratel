package org.nico.ratel.landlords.server.event;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.entity.ServerTransferData;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.server.ServerContains;

import io.netty.channel.Channel;

public class ServerEventListener_CODE_PLAYER_EXIT implements ServerEventListener<String>{

	@Override
	public void call(Channel channel, ServerTransferData<String> serverTransferData) {
		
		ClientSide clientSide = ServerContains.CLIENT_SIDE_MAP.get(serverTransferData.getClientId());
		Room room = ServerContains.ROOM_MAP.get(clientSide.getRoomId());
		
		if(room != null) {
			for(ClientSide client: room.getClientSideList()) {
				if(client.getId() != clientSide.getId()) {
					ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_SHOW_OPTIONS, null, clientSide.getNickname() + " exited, room dissolve!!");
				}else {
					ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_SHOW_OPTIONS, null, "you exited, room dissolve!!");
				}
				client.init();
			}
		}
		
		ServerContains.ROOM_MAP.remove(room.getId());
	}

}
