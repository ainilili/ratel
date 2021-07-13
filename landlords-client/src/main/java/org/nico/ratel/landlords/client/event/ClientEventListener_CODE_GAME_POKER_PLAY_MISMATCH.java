package org.nico.ratel.landlords.client.event;

import java.util.Map;

import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_POKER_PLAY_MISMATCH extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {
		Map<String, Object> map = MapHelper.parser(data);

		SimplePrinter.printNotice("Your combination is " + map.get("playType") + " (" + map.get("playCount") + "), but the previous combination is " + map.get("preType") + " (" + map.get("preCount") + "). Mismatch!");

		if(lastPokers != null) {
			SimplePrinter.printNotice(lastSellClientNickname + "[" + lastSellClientType + "] played:");
			SimplePrinter.printPokers(lastPokers);
		}

		pushToServer(channel, ServerEventCode.CODE_GAME_POKER_PLAY_REDIRECT);
	}

}
