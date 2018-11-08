package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_LANDLORD_ELECT extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		SimplePrinter.print("It's your turn to rob the landlord[Y/N]：");
		String line = SimpleWriter.write();
		if(line.equalsIgnoreCase("Y")){
			pushToServer(channel, ServerEventCode.CODE_GAME_LANDLORD_ELECT, "TRUE");
		}else if(line.equalsIgnoreCase("N")){
			pushToServer(channel, ServerEventCode.CODE_GAME_LANDLORD_ELECT, "FALSE");
		}else{
			SimplePrinter.println("Invalid options");
			call(channel, data);
		}
	}

}
