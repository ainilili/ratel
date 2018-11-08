package org.nico.ratel.landlords.server.event;

import org.nico.noson.Noson;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.RoomStatus;
import org.nico.ratel.landlords.server.ServerContains;

public class ServerEventListener_CODE_ROOM_CREATE implements ServerEventListener{

	@Override
	public void call(ClientSide clientSide, String data) {
		
		Room room = new Room(ServerContains.getServerId());
		room.setStatus(RoomStatus.BLANK);
		room.setRoomOwner(clientSide.getNickname());
		room.getClientSideMap().put(clientSide.getId(), clientSide);
		room.getClientSideList().add(clientSide);
		
		clientSide.setRoomId(room.getId());
		ServerContains.ROOM_MAP.put(room.getId(), room);
		
		ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_ROOM_CREATE_SUCCESS, Noson.reversal(room));
	}

	



}
