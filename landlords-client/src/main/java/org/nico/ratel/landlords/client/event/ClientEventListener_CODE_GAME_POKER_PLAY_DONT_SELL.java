package org.nico.ratel.landlords.client.event;

import java.util.Map;

import org.nico.ratel.landlords.client.SimpleClient;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_POKER_PLAY_DONT_SELL extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		Map<String, Object> map = MapHelper.parser(data);
		
		SimplePrinter.printNotice(map.get("currentClientNickname") + " dont out, turn next player " + map.get("turnClientNickname") + " out pokers");
		
		int turnClientId = (int) map.get("turnClientId");
		if(SimpleClient.id == turnClientId) {
			get(ClientEventCode.CODE_GAME_POKER_PLAY).call(channel, data);
		}
	}

}
