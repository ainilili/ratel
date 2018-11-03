package org.nico.ratel.landlords.server.handler;

import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.entity.ClientSide;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ClientStatus;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.server.ServerContains;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class DefaultChannelInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(
				new ObjectDecoder(1024 * 1024, ClassResolvers
						.weakCachingConcurrentResolver(this.getClass()
								.getClassLoader())));
		ch.pipeline().addLast(new ObjectEncoder());
		ch.pipeline().addLast(new TransferHandler());

		ClientSide clientSide = new ClientSide(ServerContains.getClientId(), ClientStatus.TO_CHOOSE, ch);
		ServerContains.CLIENT_SIDE_MAP.put(clientSide.getId(), clientSide);
		SimplePrinter.println("A client connects to the server：" + clientSide.getId());
		ChannelUtils.pushToClient(ch, ClientEventCode.CODE_CONNECT, clientSide);
	}

}
