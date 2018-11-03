package org.nico.ratel.landlords.client.handler;

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
				new ObjectDecoder(1024 * 1024, ClassResolvers.cacheDisabled(this
						.getClass().getClassLoader())));
		ch.pipeline().addLast(new ObjectEncoder());

		ch.pipeline().addLast("handler", new TransferHandler());
	}
	
}
