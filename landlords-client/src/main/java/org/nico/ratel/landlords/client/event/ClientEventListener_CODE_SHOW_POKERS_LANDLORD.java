package org.nico.ratel.landlords.client.event;

import java.util.List;

import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.helper.PokerHelper;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;

public class ClientEventListener_CODE_SHOW_POKERS_LANDLORD extends ClientEventListener{

	@Override
	public void call(Channel channel, String data) {
		
		List<Poker> landlordPokers = Noson.convert(data, new NoType<List<Poker>>() {});
		SimplePrinter.println(PokerHelper.unfoldPoker(landlordPokers, false));
	}

}
