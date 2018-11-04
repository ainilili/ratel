package org.nico.ratel.landlords.client.event;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.client.ClientContains;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.ClientTransferData;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_SHOW_ROOM_LIST extends ClientEventListener<ConcurrentSkipListMap<Integer, Room>>{

	@Override
	public void call(Channel channel, ClientTransferData<ConcurrentSkipListMap<Integer, Room>> clientTransferData) {
		ConcurrentSkipListMap<Integer, Room> roomMap = clientTransferData.getData();
		if(roomMap != null && ! roomMap.isEmpty()){
			SimplePrinter.println("Room list: ");
			for(Entry<Integer, Room> roomEntry: roomMap.entrySet()) {
				SimplePrinter.println(roomEntry.getKey() + "\t|\t" + roomEntry.getValue());
			}
			get(ClientEventCode.CODE_SHOW_OPTIONS).call(channel, new ClientTransferData<>());
		}else {
			SimplePrinter.println("No available room, please create a room ！");
			get(ClientEventCode.CODE_SHOW_OPTIONS).call(channel, new ClientTransferData<>());
		}
	}



}
