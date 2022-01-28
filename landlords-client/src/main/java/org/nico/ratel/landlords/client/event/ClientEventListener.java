package org.nico.ratel.landlords.client.event;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ServerEventCode;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

public abstract class ClientEventListener {

	public abstract void call(Channel channel, String data);

	public final static Map<ClientEventCode, ClientEventListener> LISTENER_MAP = new HashMap<>();

	private final static String LISTENER_PREFIX = "org.nico.ratel.landlords.client.event.ClientEventListener_";

	protected static List<Poker> lastPokers = null;
	protected static String lastSellClientNickname = null;
	protected static String lastSellClientType = null;

	protected static void initLastSellInfo() {
		lastPokers = null;
		lastSellClientNickname = null;
		lastSellClientType = null;
	}

	@SuppressWarnings("unchecked")
	public static ClientEventListener get(ClientEventCode code) {
		ClientEventListener listener;
		try {
			if (ClientEventListener.LISTENER_MAP.containsKey(code)) {
				listener = ClientEventListener.LISTENER_MAP.get(code);
			} else {
				String eventListener = LISTENER_PREFIX + code.name().toUpperCase(Locale.ROOT);
				Class<ClientEventListener> listenerClass = (Class<ClientEventListener>) Class.forName(eventListener);
				listener = listenerClass.newInstance();
				ClientEventListener.LISTENER_MAP.put(code, listener);
			}
			return listener;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected ChannelFuture pushToServer(Channel channel, ServerEventCode code, String datas) {
		return ChannelUtils.pushToServer(channel, code, datas);
	}

	protected ChannelFuture pushToServer(Channel channel, ServerEventCode code) {
		return pushToServer(channel, code, null);
	}
}
