package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;
import org.nico.ratel.landlords.utils.OptionsUtils;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_SHOW_OPTIONS extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		SimplePrinter.printNotice("Options: ");
		SimplePrinter.printNotice("1. PvP");
		SimplePrinter.printNotice("2. PvE");
		SimplePrinter.printNotice("3. Setting");
		SimplePrinter.printNotice("Please enter the number of options (enter [EXIT] log out)");
		String line = SimpleWriter.write("options");
		
		if(line.equalsIgnoreCase("EXIT")) {
			System.exit(0);
		}else {
			int choose = OptionsUtils.getOptions(line);
			
			if(choose == 1) {
				get(ClientEventCode.CODE_SHOW_OPTIONS_PVP).call(channel, data);
			}else if(choose == 2){
				get(ClientEventCode.CODE_SHOW_OPTIONS_PVE).call(channel, data);
			}else if(choose == 3){
				get(ClientEventCode.CODE_SHOW_OPTIONS_SETTING).call(channel, data);
			}else {
				SimplePrinter.printNotice("Invalid option, please choose again：");
				call(channel, data);
			}
		}
	}



}
