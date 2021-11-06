package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_POKER_PLAY_LESS extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {
		SimplePrinter.printNotice("Your combination has lower rank than the previous. You cannot play this combination!");

		if(lastPokers != null) {
			SimplePrinter.printNotice(lastSellClientNickname + "[" + lastSellClientType + "] played:");
			SimplePrinter.printPokers(lastPokers);
		}

		pushToServer(channel, ServerEventCode.CODE_GAME_POKER_PLAY_REDIRECT);
	}

}
