package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.client.ClientContains;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.ClientTransferData;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_CONNECT extends ClientEventListener<ClientSide>{

	@Override
	public void call(Channel channel, ClientTransferData<ClientSide> clientTransferData) {
		SimplePrinter.println("You has been join Nico-Landlords, please set your nickname: ");
		String nickname = SimpleWriter.write();
		
		ClientSide clientSide = clientTransferData.getData();
		clientSide.setNickname(nickname);
		
		ClientContains.clientSide = clientSide;
		
		pushToServer(channel, ServerEventCode.CODE_RENAME, clientSide);
	}



}
