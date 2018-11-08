package org.nico.ratel.landlords.client.event;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;

import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.ratel.landlords.entity.ClientTransferData;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_SHOW_ROOM_LIST extends ClientEventListener{

	@Override
	public void call(Channel channel, ClientTransferData clientTransferData) {
		ConcurrentSkipListMap<Integer, Room> roomMap = Noson.convert(clientTransferData.getData(), new NoType<ConcurrentSkipListMap<Integer, Room>>() {});
		if(roomMap != null && ! roomMap.isEmpty()){
			SimplePrinter.println("Room list: ");
			for(Entry<Integer, Room> roomEntry: roomMap.entrySet()) {
				SimplePrinter.println(roomEntry.getKey() + "\t|\t" + roomEntry.getValue());
			}
			get(ClientEventCode.CODE_SHOW_OPTIONS).call(channel, new ClientTransferData());
		}else {
			SimplePrinter.println("No available room, please create a room ！");
			get(ClientEventCode.CODE_SHOW_OPTIONS).call(channel, new ClientTransferData());
		}
	}



}
