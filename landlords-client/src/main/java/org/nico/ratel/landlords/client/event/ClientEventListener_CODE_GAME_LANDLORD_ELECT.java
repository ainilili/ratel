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
		int turnClientId = (int) map.get("nextClientId");
		
		if(map.containsKey("preClientNickname")) {
			SimplePrinter.printNotice(map.get("preClientNickname") + " don't rob the landlord!");
		}
		
		if(turnClientId == SimpleClient.id) {
			SimplePrinter.printNotice("It's your turn. Do you want to rob the landlord? [Y/N] (enter [EXIT] to exit current room)");
			String line = SimpleWriter.write("Y/N");
			if(line.equalsIgnoreCase("EXIT")) {
				pushToServer(channel, ServerEventCode.CODE_CLIENT_EXIT);
			}else if(line.equalsIgnoreCase("Y")){
				pushToServer(channel, ServerEventCode.CODE_GAME_LANDLORD_ELECT, "TRUE");
			}else if(line.equalsIgnoreCase("N")){
				pushToServer(channel, ServerEventCode.CODE_GAME_LANDLORD_ELECT, "FALSE");
			}else{
				SimplePrinter.printNotice("Invalid options");
				call(channel, data);
			}
		}else {
			SimplePrinter.printNotice("It's " + map.get("nextClientNickname") + "'s turn. Please wait patiently for his/her confirmation !");
		}
		
	}

}
