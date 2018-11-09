package org.nico.ratel.landlords.server.event;

import org.nico.noson.Noson;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.enums.ClientEventCode;

public class ServerEventListener_CODE_GET_POKERS implements ServerEventListener{

	@Override
	public void call(ClientSide clientSide, String data) {
		
		ChannelUtils.pushToClient(clientSide.getChannel(), ClientEventCode.CODE_SHOW_POKERS, Noson.reversal(clientSide.getPokers()));
	}

}
