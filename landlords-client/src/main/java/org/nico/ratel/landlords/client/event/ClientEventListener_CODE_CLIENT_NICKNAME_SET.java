package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_CLIENT_NICKNAME_SET extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		SimplePrinter.printNotice("Please set your nickname");
		String nickname = SimpleWriter.write("nickname");
		pushToServer(channel, ServerEventCode.CODE_CLIENT_NICKNAME_SET, nickname);
	}



}
