package org.nico.ratel.landlords.client.event;

import java.util.List;
import java.util.Map;

import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_LANDLORD_CONFIRM extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {
		Map<String, Object> map = MapHelper.parser(data);

		String landlordNickname = String.valueOf(map.get("landlordNickname"));
		int baseScore = (Integer) map.get("baseScore");
		String baseScoreString;

		if (baseScore == 1) {
			baseScoreString = "1 score";
		} else {
			baseScoreString = baseScore + " scores";
		}

		SimplePrinter.printNotice(landlordNickname + " has become the landlord with " + baseScoreString + " and gotten three extra cards");

		List<Poker> additionalPokers = Noson.convert(map.get("additionalPokers"), new NoType<List<Poker>>() {});
		SimplePrinter.printPokers(additionalPokers);

		pushToServer(channel, ServerEventCode.CODE_GAME_POKER_PLAY_REDIRECT);
	}

}
