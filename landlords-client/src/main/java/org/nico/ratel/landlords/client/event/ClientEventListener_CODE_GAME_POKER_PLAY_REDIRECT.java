package org.nico.ratel.landlords.client.event;

import java.util.List;
import java.util.Map;

import org.nico.ratel.landlords.client.SimpleClient;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_POKER_PLAY_REDIRECT extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		
		Map<String, Object> map = MapHelper.parser(data);
		
		int sellClientId = (int) map.get("sellClientId");
		
		SimplePrinter.printNotice("\nEveryone's number of cards:\n");
		List<Map<String, Object>> clientInfos = (List<Map<String, Object>>) map.get("clientInfos");
		for(Map<String, Object> clientInfo: clientInfos) {
			SimplePrinter.printNotice(clientInfo.get("clientNickname") + "\t(" + clientInfo.get("type") + "): \t : " + clientInfo.get("surplus") + " cards");
		}
		SimplePrinter.printNotice("");
		
		if(sellClientId == SimpleClient.id) {
			get(ClientEventCode.CODE_GAME_POKER_PLAY).call(channel, data);
		}else {
			SimplePrinter.printNotice("Next player is " + map.get("sellClinetNickname") + ". Please wait for him to play his cards.");
		}
	}

}
