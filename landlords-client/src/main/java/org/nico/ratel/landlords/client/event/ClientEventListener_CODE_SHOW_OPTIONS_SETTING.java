package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.helper.PokerHelper;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;
import org.nico.ratel.landlords.utils.OptionsUtils;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_SHOW_OPTIONS_SETTING extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		SimplePrinter.printNotice("Setting: ");
		SimplePrinter.printNotice("1. Sniper Mode (Poker style camouflage)");
		SimplePrinter.printNotice("2. Normal Mode");
		SimplePrinter.printNotice("Please enter the number of setting (enter [BACK] return options list)");
		String line = SimpleWriter.write("setting");
		
		if(line.equalsIgnoreCase("BACK")) {
			get(ClientEventCode.CODE_SHOW_OPTIONS).call(channel, data);
		}else {
			while(line == null || OptionsUtils.getOptions(line) == -1) {
				SimplePrinter.printNotice("Invalid setting, please choose again：");
				line = SimpleWriter.write("setting");
			}
			
			int choose = Integer.valueOf(line);
			
			if(choose == 1) {
				PokerHelper.disguise = true;
				SimplePrinter.printNotice("Game mode switch to [Sniper Model]");
				get(ClientEventCode.CODE_SHOW_OPTIONS).call(channel, data);
			}else if(choose == 2){
				PokerHelper.disguise = false;
				SimplePrinter.printNotice("Game mode switch to [Normal Model]");
				get(ClientEventCode.CODE_SHOW_OPTIONS).call(channel, data);
			}else {
				SimplePrinter.printNotice("Invalid setting, please choose again：");
				call(channel, data);
			}
		}
	}



}
