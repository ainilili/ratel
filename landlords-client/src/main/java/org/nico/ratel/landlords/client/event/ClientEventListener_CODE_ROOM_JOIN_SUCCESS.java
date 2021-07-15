package org.nico.ratel.landlords.client.event;

import java.util.Map;

import org.nico.ratel.landlords.client.SimpleClient;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_ROOM_JOIN_SUCCESS extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {
		Map<String, Object> map = MapHelper.parser(data);

		initLastSellInfo();

		int joinClientId = (int) map.get("clientId");
		if (SimpleClient.id == joinClientId) {
			SimplePrinter.printNotice("You have joined room：" + map.get("roomId") + ". There are " + map.get("roomClientCount") + " players in the room now.");
			SimplePrinter.printNotice("Please wait for other players to join. The game would start at three players!");
		} else {
			SimplePrinter.printNotice(map.get("clientNickname") + " joined room, there are currently " + map.get("roomClientCount") + " in the room.");
		}

	}
}
