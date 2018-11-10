package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_POKER_PLAY_CANT_PASS extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		SimplePrinter.printNotice("The last time you play the card, you can't pass");
		get(ClientEventCode.CODE_GAME_POKER_PLAY).call(channel, data);
	}

}
