package org.nico.ratel.landlords.client.event;

import java.util.Map;

import org.nico.ratel.landlords.client.SimpleClient;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_ROOM_JOIN_SUCCESS extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		Map<String, Object> map = MapHelper.parser(data);
		
		int joinClientId = (int) map.get("clientId");
		if(SimpleClient.id == joinClientId) {
			SimplePrinter.printNotice("You has been join room：" + map.get("roomId") + ", there are " + map.get("roomClientCount") + " player in the room now");
			SimplePrinter.printNotice("Please wait for other players to join, start a good game when the room player reaches three !");
		}else {
			SimplePrinter.printNotice(map.get("clientNickname") + " join room, the current number of rooms is " + map.get("roomClientCount"));
		}
		
		
		
	}



}
