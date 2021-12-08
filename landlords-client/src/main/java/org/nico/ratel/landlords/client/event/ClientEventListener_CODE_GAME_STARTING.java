package org.nico.ratel.landlords.client.event;

import io.netty.channel.Channel;
import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.print.SimplePrinter;

import java.util.List;
import java.util.Map;

public class ClientEventListener_CODE_GAME_STARTING extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {

		Map<String, Object> map = MapHelper.parser(data);

		SimplePrinter.printNotice("Game starting!");

		List<Poker> pokers = Noson.convert(map.get("pokers"), new NoType<List<Poker>>() {});

		SimplePrinter.printNotice("");
		SimplePrinter.printNotice("Your cards are");
		SimplePrinter.printPokers(pokers);
		SimplePrinter.printNotice("Last cards are");
		SimplePrinter.printNotice(map.containsKey("lastPokers")?map.get("lastPokers").toString():"");

		get(ClientEventCode.CODE_GAME_LANDLORD_ELECT).call(channel, data);
	}

}
