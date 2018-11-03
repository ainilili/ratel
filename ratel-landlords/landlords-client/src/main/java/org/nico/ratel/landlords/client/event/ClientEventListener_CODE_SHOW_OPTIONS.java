package org.nico.ratel.landlords.client.event;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.client.ClientContains;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.entity.ClientTransferData;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;
import org.nico.ratel.landlords.utils.OptionsUtils;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_SHOW_OPTIONS implements ClientEventListener<ConcurrentSkipListMap<Integer, Room>>{

	@Override
	public void call(Channel channel, ClientTransferData<ConcurrentSkipListMap<Integer, Room>> clientTransferData) {
		SimplePrinter.println("Options: ");
		SimplePrinter.println("1. Create Room");
		SimplePrinter.println("2. Join Room");
		SimplePrinter.print("Your choose options number：");
		String line = SimpleWriter.write();
		while(line == null || (! line.equals("1") && ! line.equals("2"))) {
			SimplePrinter.println("Invalid options, please choose again：");
			line = SimpleWriter.write();
		}
		
		Map<Integer, Room> roomMap = clientTransferData.getData();
		
		int choose = Integer.valueOf(line);
		
		if(choose == 1) {
			ChannelUtils.pushToServer(channel, ServerEventCode.CODE_CREATE_ROOM, ClientContains.clientSide.getId());
		}else {
			if(roomMap != null && ! roomMap.isEmpty()) {
				SimplePrinter.println("Room list: ");
				for(Entry<Integer, Room> roomEntry: roomMap.entrySet()) {
					SimplePrinter.println(roomEntry.getKey() + "\t|\t" + roomEntry.getValue());
				}
				SimplePrinter.print("Your choose rooms number：");
				line = SimpleWriter.write();
				while(line == null || ! roomMap.keySet().contains(OptionsUtils.getOptions(line))) {
					SimplePrinter.println("Invalid options, please choose again：");
					line = SimpleWriter.write();
				}
				ChannelUtils.pushToServer(channel, ClientContains.clientSide.getId(), ServerEventCode.CODE_JOIN_ROOM, OptionsUtils.getOptions(line));
			}else {
				SimplePrinter.println("No available room, please create a room ！");
				call(channel, clientTransferData);
			}
		}
		
	}



}
