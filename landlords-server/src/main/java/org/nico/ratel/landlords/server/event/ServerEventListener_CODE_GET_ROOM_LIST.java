package org.nico.ratel.landlords.server.event;

import org.nico.noson.Noson;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.server.ServerContains;

public class ServerEventListener_CODE_GET_ROOM_LIST implements ServerEventListener{

	@Override
	public void call(ClientSide clientSide, String data) {
		
		ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_SHOW_ROOM_LIST, Noson.reversal(ServerContains.ROOM_MAP));
	}

	



}
