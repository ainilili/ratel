package org.nico.ratel.landlords.server.event;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.entity.ServerTransferData;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.RoomStatus;
import org.nico.ratel.landlords.server.ServerContains;

import io.netty.channel.Channel;

public class ServerEventListener_CODE_JOIN_ROOM implements ServerEventListener<Integer>{

	@Override
	public void call(Channel channel, ServerTransferData<Integer> serverTransferData) {
		
		Room room = ServerContains.ROOM_MAP.get(serverTransferData.getData());
		
		if(room == null) {
			ChannelUtils.pushToClient(channel, ClientEventCode.CODE_SHOW_OPTIONS, ServerContains.ROOM_MAP, "Room inexistence !");
		}else if(room.getClientSideMap().size() == 3) {
			ChannelUtils.pushToClient(channel, ClientEventCode.CODE_SHOW_OPTIONS, ServerContains.ROOM_MAP, "The room is full !");
		}else {
			ClientSide clientSide = ServerContains.CLIENT_SIDE_MAP.get(serverTransferData.getClientId());
			room.getClientSideMap().put(clientSide.getId(), clientSide);
			if(room.getClientSideMap().size() == 3) {
				room.setStatus(RoomStatus.STARTING);
				
				for(ClientSide client: room.getClientSideMap().values()) {
					ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_ROOM_STARTING, room);
				}
			}else {
				room.setStatus(RoomStatus.WAIT);
				ChannelUtils.pushToClient(channel, ClientEventCode.CODE_JOIN_ROOM_SUCCESS, room);
				for(ClientSide client: room.getClientSideMap().values()) {
					if(client.getId() != clientSide.getId()) {
						ChannelUtils.pushToClient(client.getChannel(), null, null, clientSide.getNickname() + " join room !");
					}
				}
			}
		}
	}

	



}
