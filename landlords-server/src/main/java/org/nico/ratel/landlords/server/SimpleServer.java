package org.nico.ratel.landlords.server;

import org.nico.ratel.landlords.server.proxy.ProtobufProxy;
import org.nico.ratel.landlords.server.proxy.WebsocketProxy;

public class SimpleServer {

	public static void main(String[] args) throws InterruptedException {
		if (args != null && args.length > 1) {
			if (args[0].equalsIgnoreCase("-p") || args[0].equalsIgnoreCase("-port")) {
				ServerContains.port = Integer.parseInt(args[1]);
			}
		}
		new Thread(() -> {
			try {
				new ProtobufProxy().start(ServerContains.port);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		new WebsocketProxy().start(ServerContains.port + 1);

	}
}
