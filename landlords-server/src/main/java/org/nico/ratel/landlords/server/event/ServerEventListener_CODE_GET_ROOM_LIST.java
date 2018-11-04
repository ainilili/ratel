package org.nico.ratel.landlords.server.event;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ServerTransferData;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.server.ServerContains;

import io.netty.channel.Channel;

public class ServerEventListener_CODE_GET_ROOM_LIST implements ServerEventListener<Boolean>{

	@Override
	public void call(Channel channel, ServerTransferData<Boolean> serverTransferData) {
		ChannelUtils.pushToClient(channel, ClientEventCode.CODE_SHOW_ROOM_LIST, ServerContains.ROOM_MAP);
	}

	



}
