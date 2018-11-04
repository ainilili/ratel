package org.nico.ratel.landlords.server.event;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.entity.ServerTransferData;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.RoomStatus;
import org.nico.ratel.landlords.server.ServerContains;

import io.netty.channel.Channel;

public class ServerEventListener_CODE_CREATE_ROOM implements ServerEventListener<Integer>{

	@Override
	public void call(Channel channel, ServerTransferData<Integer> serverTransferData) {
		
		ClientSide clientSide = ServerContains.CLIENT_SIDE_MAP.get(serverTransferData.getClientId());
		
		Room room = new Room(ServerContains.getServerId());
		room.setStatus(RoomStatus.BLANK);
		room.getClientSideMap().put(clientSide.getId(), clientSide);
		room.getClientSideList().add(clientSide);
		
		clientSide.setRoomId(room.getId());
		ServerContains.ROOM_MAP.put(room.getId(), room);
		
		ChannelUtils.pushToClient(channel, ClientEventCode.CODE_JOIN_ROOM_SUCCESS, room);
	}

	



}
