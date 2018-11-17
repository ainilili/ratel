package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;
import org.nico.ratel.landlords.utils.OptionsUtils;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_SHOW_OPTIONS_PVP extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		SimplePrinter.printNotice("PVP: ");
		SimplePrinter.printNotice("1. Create Room");
		SimplePrinter.printNotice("2. Room List");
		SimplePrinter.printNotice("3. Join Room");
		SimplePrinter.printNotice("Please enter the number of options (enter [BACK] return options list)");
		String line = SimpleWriter.write("pvp");
		
		if(line.equalsIgnoreCase("BACK")) {
			get(ClientEventCode.CODE_SHOW_OPTIONS).call(channel, data);
		}else {
			int choose = OptionsUtils.getOptions(line);
			
			if(choose == 1) {
				pushToServer(channel, ServerEventCode.CODE_ROOM_CREATE, null);
			}else if(choose == 2){
				pushToServer(channel, ServerEventCode.CODE_GET_ROOMS, null);
			}else if(choose == 3){
				SimplePrinter.printNotice("Please enter the room id you want to join (enter [BACK] return options list)");
				line = SimpleWriter.write("roomid");
				
				if(line.equalsIgnoreCase("BACK")) {
					call(channel, data);
				}else {
					int option = OptionsUtils.getOptions(line);
					if(line == null || option < 1) {
						SimplePrinter.printNotice("Invalid options, please choose again：");
						call(channel, data);
					}else{
						pushToServer(channel, ServerEventCode.CODE_ROOM_JOIN, String.valueOf(option));
					}
				}
			}else {
				SimplePrinter.printNotice("Invalid option, please choose again：");
				call(channel, data);
			}
		}
		
	}



}
