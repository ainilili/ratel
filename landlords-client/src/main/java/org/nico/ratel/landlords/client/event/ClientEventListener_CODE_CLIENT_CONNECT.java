package org.nico.ratel.landlords.client.event;

import org.nico.noson.Noson;
import org.nico.ratel.landlords.client.SimpleClient;
import org.nico.ratel.landlords.enums.ServerEventCode;
import org.nico.ratel.landlords.features.Features;
import org.nico.ratel.landlords.helper.MapHelper;
import org.nico.ratel.landlords.print.SimplePrinter;

import io.netty.channel.Channel;
import org.nico.ratel.landlords.utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

public class ClientEventListener_CODE_CLIENT_CONNECT extends ClientEventListener {

	@Override
	public void call(Channel channel, String data) {
		SimplePrinter.printNotice("Connected to server. Welcome to ratel!");
		SimpleClient.id = Integer.parseInt(data);

		Map<String, Object> infos = new HashMap<>();
		infos.put("version", SimpleClient.VERSION);
		pushToServer(channel, ServerEventCode.CODE_CLIENT_INFO_SET, Noson.reversal(infos));
	}

}
