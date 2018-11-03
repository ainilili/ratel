package org.nico.ratel.landlords.server.event;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.ServerTransferData;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.server.ServerContains;

import io.netty.channel.Channel;

public class ServerEventListener_CODE_RENAME implements ServerEventListener<ClientSide>{

	@Override
	public void call(Channel channel, ServerTransferData<ClientSide> serverTransferData) {
		
		ClientSide clientSide = serverTransferData.getData();
		
		ServerContains.CLIENT_SIDE_MAP.get(clientSide.getId()).setNickname(clientSide.getNickname());;
		
		ChannelUtils.pushToClient(channel, ClientEventCode.CODE_SHOW_OPTIONS, ServerContains.ROOM_MAP);
	}

}
