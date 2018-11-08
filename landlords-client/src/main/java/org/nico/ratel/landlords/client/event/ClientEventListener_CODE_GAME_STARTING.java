package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_STARTING extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		SimplePrinter.println(data + " game starting !");
		SimplePrinter.println("Wait for the server to issue the license...");
	}

}
