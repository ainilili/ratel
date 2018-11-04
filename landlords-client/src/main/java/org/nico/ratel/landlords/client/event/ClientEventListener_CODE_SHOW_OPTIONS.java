package org.nico.ratel.landlords.client.event;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.client.ClientContains;
import org.nico.ratel.landlords.entity.ClientTransferData;
import org.nico.ratel.landlords.entity.Room;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;
import org.nico.ratel.landlords.utils.OptionsUtils;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_SHOW_OPTIONS extends ClientEventListener<String>{

	@Override
	public void call(Channel channel, ClientTransferData<String> clientTransferData) {
		SimplePrinter.println("Options: ");
		SimplePrinter.println("1. Create Room");
		SimplePrinter.println("2. Room List");
		SimplePrinter.println("3. Join Room");
		SimplePrinter.println("Your choose options number：");
		String line = SimpleWriter.write();
		while(line == null || (! line.equals("1") && ! line.equals("2") && ! line.equals("3"))) {
			SimplePrinter.println("Invalid options, please choose again：");
			line = SimpleWriter.write();
		}
		
		int choose = Integer.valueOf(line);
		
		if(choose == 1) {
			pushToServer(channel, ServerEventCode.CODE_CREATE_ROOM, null);
		}else if(choose == 2){
			pushToServer(channel, ServerEventCode.CODE_GET_ROOM_LIST, null);
		}else {
			SimplePrinter.print("Your choose rooms number：");
				line = SimpleWriter.write();
				int option = OptionsUtils.getOptions(line);
				if(line == null || option < 1) {
					SimplePrinter.println("Invalid options, please choose again：");
					call(channel, clientTransferData);
				}else{
					pushToServer(channel, ServerEventCode.CODE_JOIN_ROOM, option);
				}
		}
		
	}



}
