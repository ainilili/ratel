package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;
import org.nico.ratel.landlords.utils.OptionsUtils;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_SHOW_OPTIONS_PVE extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		SimplePrinter.printNotice("PVE: ");
		SimplePrinter.printNotice("1. Easy Mode");
		SimplePrinter.printNotice("2. Medium Mode");
		SimplePrinter.printNotice("3. Hard Mode");
		SimplePrinter.printNotice("Please select an option above (enter [back|b] to return to options list)");
		String line = SimpleWriter.write("pve");
		
		if(line.equalsIgnoreCase("back") ||  line.equalsIgnoreCase("b")) {
			get(ClientEventCode.CODE_SHOW_OPTIONS).call(channel, data);
		}else {
			int choose = OptionsUtils.getOptions(line);
			
			if(0 < choose && choose < 4) {
				initLastSellInfo();
				pushToServer(channel, ServerEventCode.CODE_ROOM_CREATE_PVE, String.valueOf(choose));
			}else {
				SimplePrinter.printNotice("Invalid option, please choose again：");
				call(channel, data);
			}
		}
		
	}



}
