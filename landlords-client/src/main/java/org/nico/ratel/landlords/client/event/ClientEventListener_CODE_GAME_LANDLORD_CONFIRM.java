package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_LANDLORD_CONFIRM extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		SimplePrinter.print(data + " grabed landlord, he got three pokers");
	}

}
