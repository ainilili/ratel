package org.nico.ratel.landlords.client.event;

import java.util.List;
import java.util.Map;

import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.helper.PokerHelper;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_STARTING extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		
		Map<String, Object> map = MapHelper.parser(data);
		
		SimplePrinter.println(map.get("roomId") + " game starting !");
		
		List<Poker> pokers = Noson.convert(map.get("pokers"), new NoType<List<Poker>>() {});
		SimplePrinter.println("Your pokers is:");
		SimplePrinter.println(PokerHelper.unfoldPoker(pokers, false));
	
		get(ClientEventCode.CODE_GAME_LANDLORD_ELECT).call(channel, data);
	}

}
