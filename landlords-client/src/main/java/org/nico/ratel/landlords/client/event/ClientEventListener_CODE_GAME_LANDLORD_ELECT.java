package org.nico.ratel.landlords.client.event;

import java.util.Map;

import org.nico.ratel.landlords.client.SimpleClient;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_LANDLORD_ELECT extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		Map<String, Object> map = MapHelper.parser(data);
		int turnClientId = (int) map.get("turnClientId");
		
		if(turnClientId == SimpleClient.id) {
			SimplePrinter.printNotice("It's your turn to rob the landlord[Y/N]");
			String line = SimpleWriter.write("Y/N");
			if(line.equalsIgnoreCase("Y")){
				pushToServer(channel, ServerEventCode.CODE_GAME_LANDLORD_ELECT, "TRUE");
			}else if(line.equalsIgnoreCase("N")){
				pushToServer(channel, ServerEventCode.CODE_GAME_LANDLORD_ELECT, "FALSE");
			}else{
				SimplePrinter.printNotice("Invalid options");
				call(channel, data);
			}
		}else {
			SimplePrinter.printNotice("It's turn " + map.get("turnClientNickname") + " to confirm, please wait patiently !");
		}
		
	}

}
