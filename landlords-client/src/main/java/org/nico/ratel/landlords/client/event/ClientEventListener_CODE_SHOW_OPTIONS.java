package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;
import org.nico.ratel.landlords.utils.OptionsUtils;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_SHOW_OPTIONS extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		SimplePrinter.printNotice("Options: ");
		SimplePrinter.printNotice("1. Create Room");
		SimplePrinter.printNotice("2. Room List");
		SimplePrinter.printNotice("3. Join Room");
		SimplePrinter.printNotice("Please enter the number of options");
		String line = SimpleWriter.write("options");
		while(line == null || (! line.equals("1") && ! line.equals("2") && ! line.equals("3"))) {
			SimplePrinter.printNotice("Invalid options, please choose again: ");
			line = SimpleWriter.write("options");
		}
		
		int choose = Integer.valueOf(line);
		
		if(choose == 1) {
			pushToServer(channel, ServerEventCode.CODE_ROOM_CREATE, null);
		}else if(choose == 2){
			pushToServer(channel, ServerEventCode.CODE_GET_ROOMS, null);
		}else {
			SimplePrinter.printNotice("Please enter the room id you want to join (enter [BACK] return options list)");
			line = SimpleWriter.write("roomid");
			
			if(line.equalsIgnoreCase("BACK")) {
				call(channel, data);
			}else {
				int option = OptionsUtils.getOptions(line);
				if(line == null || option < 1) {
					SimplePrinter.printNotice("Invalid options, please choose again: ");
					call(channel, data);
				}else{
					pushToServer(channel, ServerEventCode.CODE_ROOM_JOIN, String.valueOf(option));
				}
			}
		}
		
	}



}
