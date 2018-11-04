package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.entity.ClientTransferData;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_JOIN_ROOM_SUCCESS extends ClientEventListener<Room>{

	@Override
	public void call(Channel channel, ClientTransferData<Room> clientTransferData) {
		SimplePrinter.println("You has been join room：" + clientTransferData.getData());
		SimplePrinter.println("Please wait for other players to join !");
	}



}
