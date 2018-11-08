package org.nico.ratel.landlords.client.event;

import org.nico.ratel.landlords.entity.ClientTransferData;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GRAB_LANDLORD extends ClientEventListener{

	@Override
	public void call(Channel channel, ClientTransferData clientTransferData) {
		SimplePrinter.print("It's your turn to rob the landlord[Y/N]：");
		String line = SimpleWriter.write();
		if(line.equalsIgnoreCase("Y")){
			pushToServer(channel, ServerEventCode.CODE_GRAB_LANDLORD, "TRUE");
		}else if(line.equalsIgnoreCase("N")){
			pushToServer(channel, ServerEventCode.CODE_GRAB_LANDLORD, "FALSE");
		}else{
			SimplePrinter.println("Invalid options");
			call(channel, clientTransferData);
		}
	}

}
