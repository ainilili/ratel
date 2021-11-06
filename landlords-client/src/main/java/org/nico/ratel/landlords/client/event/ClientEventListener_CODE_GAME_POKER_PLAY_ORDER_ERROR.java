package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_POKER_PLAY_ORDER_ERROR extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {

		SimplePrinter.printNotice("It is not your turn yet. Please wait for other players!");
	}

}
