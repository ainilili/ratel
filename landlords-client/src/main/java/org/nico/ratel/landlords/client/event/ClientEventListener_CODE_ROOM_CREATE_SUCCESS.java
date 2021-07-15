package org.nico.ratel.landlords.client.event;

import org.nico.noson.Noson;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_ROOM_CREATE_SUCCESS extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {
		Room room = Noson.convert(data, Room.class);
		initLastSellInfo();
		SimplePrinter.printNotice("You have created a room with id " + room.getId());
		SimplePrinter.printNotice("Please wait for other players to join !");
	}

}
