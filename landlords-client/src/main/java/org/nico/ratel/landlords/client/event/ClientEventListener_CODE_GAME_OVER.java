package org.nico.ratel.landlords.client.event;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.ratel.landlords.client.SimpleClient;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_GAME_OVER extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {
		Map<String, Object> map = MapHelper.parser(data);
		SimplePrinter.printNotice("\nPlayer " + map.get("winnerNickname") + "[" + map.get("winnerType") + "]" + " won the game");

		if (map.containsKey("scores")){
			List<Map<String, Object>> scores = Noson.convert(map.get("scores"), new NoType<List<Map<String, Object>>>() {});
			for (Map<String, Object> score : scores) {
				if (! Objects.equals(score.get("clientId"), SimpleClient.id)) {
					SimplePrinter.printNotice(score.get("nickName").toString() + "'s rest poker is:");
					SimplePrinter.printPokers(Noson.convert(score.get("pokers"), new NoType<List<Poker>>() {}));
				}
			}
			SimplePrinter.printNotice("\n");
			// print score
			for (Map<String, Object> score : scores) {
				String scoreInc = score.get("scoreInc").toString();
				String scoreTotal = score.get("score").toString();
				if (SimpleClient.id != (int) score.get("clientId")) {
					SimplePrinter.printNotice(score.get("nickName").toString() + "'s score is " + scoreInc + ", total score is " + scoreTotal);
				} else {
					SimplePrinter.printNotice("your score is " + scoreInc + ", total score is " + scoreTotal);
				}
			}
			ClientEventListener_CODE_GAME_READY.gameReady(channel);
		}
	}
}
