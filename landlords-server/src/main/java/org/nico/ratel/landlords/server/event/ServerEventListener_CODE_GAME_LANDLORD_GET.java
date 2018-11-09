package org.nico.ratel.landlords.server.event;

import org.nico.noson.Noson;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.helper.PokerHelper;
import org.nico.ratel.landlords.helper.TimeHelper;
import org.nico.ratel.landlords.server.ServerContains;

public class ServerEventListener_CODE_GAME_LANDLORD_GET implements ServerEventListener{

	@Override
	public void call(ClientSide clientSide, String data) {
		
		Room room = ServerContains.ROOM_MAP.get(clientSide.getRoomId());
		ClientSide startGrabClient = ServerContains.CLIENT_SIDE_MAP.get(room.getLandlordId()); 
		
		String result = MapHelper.newInstance()
				.put("roomId", room.getId())
				.put("roomOwner", room.getRoomOwner())
				.put("roomClientCount", room.getClientSideList().size())
				.put("turnClientNickname", startGrabClient.getNickname())
				.put("turnClientId", startGrabClient.getId())
				.json();
		for(ClientSide client: room.getClientSideList()){
			ChannelUtils.pushToClient(client.getChannel(), ClientEventCode.CODE_GAME_LANDLORD_ELECT, result);
		}

	}





}
