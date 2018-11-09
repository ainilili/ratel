package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_POKER_PLAY_INVALID extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		
		SimplePrinter.println("Sell is invalid");
		get(ClientEventCode.CODE_GAME_POKER_PLAY).call(channel, data);
	}

}
