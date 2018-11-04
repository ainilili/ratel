package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.entity.ClientTransferData;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_ROOM_STARTING extends ClientEventListener<Room>{

	@Override
	public void call(Channel channel, ClientTransferData<Room> clientTransferData) {
		SimplePrinter.println(clientTransferData.getData() + " game starting !");
		SimplePrinter.println("Wait for the server to issue the license...");
	}

}
