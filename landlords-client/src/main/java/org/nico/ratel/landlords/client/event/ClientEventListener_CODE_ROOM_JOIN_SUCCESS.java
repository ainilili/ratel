package org.nico.ratel.landlords.client.event;

import java.util.Map;

import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_ROOM_JOIN_SUCCESS extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		Map<String, Object> dataMap = MapHelper.parser(data);
		
		SimplePrinter.println("You has been join room：" + dataMap.get("roomId") + ", There are " + dataMap.get("roomClientCount") + " people in the room now");
		SimplePrinter.println("Please wait for other players to join !");
	}



}
