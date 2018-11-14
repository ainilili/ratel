package org.nico.ratel.landlords.server.robot;

import java.util.Map;

import org.nico.ratel.landlords.client.SimpleClient;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.print.SimpleWriter;

public class RobotEventListener_CODE_GAME_LANDLORD_ELECT implements RobotEventListener{

	@Override
	public void call(ClientSide player, ClientSide robot, String data) {
		
		Map<String, Object> map = MapHelper.parser(data);
		int turnClientId = (int) map.get("nextClientId");
		
		if(turnClientId == robot.getId()) {
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
