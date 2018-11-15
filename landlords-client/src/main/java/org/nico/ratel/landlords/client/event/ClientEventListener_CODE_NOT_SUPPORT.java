package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_NOT_SUPPORT extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		SimplePrinter.printNotice(data);
	}



}
