package org.nico.ratel.landlords.client.event;

import java.util.Map;

import org.nico.noson.Noson;
import org.nico.ratel.landlords.entity.ClientTransferData;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_CREATE_ROOM_SUCCESS extends ClientEventListener{

	@Override
	public void call(Channel channel, ClientTransferData clientTransferData) {
		
		Map<String, Object> room = Noson.parseMap(clientTransferData.getData());
		
		SimplePrinter.println("You has been create room：" + room);
		SimplePrinter.println("Please wait for other players to join !");
	}



}
