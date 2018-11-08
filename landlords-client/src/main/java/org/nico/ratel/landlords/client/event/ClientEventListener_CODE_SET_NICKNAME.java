package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.entity.ClientTransferData;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_SET_NICKNAME extends ClientEventListener{

	@Override
	public void call(Channel channel, ClientTransferData clientTransferData) {
		SimplePrinter.println("You has been join Nico-Landlords, please set your nickname: ");
		String nickname = SimpleWriter.write();
		
		
		pushToServer(channel, ServerEventCode.CODE_SET_NICKNAME, nickname);
	}



}
