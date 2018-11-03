package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.entity.ClientTransferData;
import org.nico.ratel.landlords.entity.Room;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_ROOM_STARTING implements ClientEventListener<Room>{

	@Override
	public void call(Channel channel, ClientTransferData<Room> clientTransferData) {
		System.out.println(clientTransferData.getData() + " game starting !");
	}



}
