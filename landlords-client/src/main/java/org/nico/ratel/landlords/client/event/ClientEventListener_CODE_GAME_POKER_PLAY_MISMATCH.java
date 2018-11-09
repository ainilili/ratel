package org.nico.ratel.landlords.client.event;

import java.util.Map;

import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_POKER_PLAY_MISMATCH extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		Map<String, Object> map = MapHelper.parser(data);
		
		SimplePrinter.println("Your pokers type is " + map.get("playType"));
		SimplePrinter.println("Pre pokers type is " + map.get("preType"));
		SimplePrinter.println("Mismatch !!");
		
		get(ClientEventCode.CODE_GAME_POKER_PLAY).call(channel, data);
	}

}
