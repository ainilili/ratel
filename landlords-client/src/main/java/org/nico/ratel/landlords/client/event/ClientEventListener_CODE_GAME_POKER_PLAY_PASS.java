package org.nico.ratel.landlords.client.event;

import java.util.Map;

import org.nico.ratel.landlords.client.SimpleClient;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_POKER_PLAY_PASS extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		Map<String, Object> map = MapHelper.parser(data);
		
		SimplePrinter.printNotice(map.get("clientNickname") + " dont out, turn next player " + map.get("nextClientNickname") + " out pokers");
		
		int turnClientId = (int) map.get("nextClientId");
		if(SimpleClient.id == turnClientId) {
			get(ClientEventCode.CODE_GAME_POKER_PLAY).call(channel, data);
		}
	}

}
