package org.nico.ratel.landlords.client.event;

import java.util.List;
import java.util.Map;

import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.ratel.landlords.client.SimpleClient;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.helper.PokerHelper;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_LANDLORD_CONFIRM extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		Map<String, Object> map = MapHelper.parser(data);
		
		String landlordNickname = (String) map.get("landlordNickname");
		
		SimplePrinter.println(landlordNickname + " grabed landlord, he got three pokers");
		
		List<Poker> additionalPokers = Noson.convert(map.get("additionalPokers"), new NoType<List<Poker>>() {});
		SimplePrinter.println(PokerHelper.unfoldPoker(additionalPokers, false));
		
		int landlordId = (int) map.get("landlordId");
		if(SimpleClient.id == landlordId) {
			get(ClientEventCode.CODE_GAME_POKER_PLAY).call(channel, data);
		}
	}

}
