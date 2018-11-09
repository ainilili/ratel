package org.nico.ratel.landlords.client;

import java.io.IOException;

import org.nico.ratel.landlords.client.handler.DefaultChannelInitializer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SimpleClient {

	public static int id = -1;
	
	public static void main(String[] args) throws InterruptedException, IOException {

		EventLoopGroup group = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap()
					.group(group)
					.channel(NioSocketChannel.class)
					.handler(new DefaultChannelInitializer());
			Channel channel = b.connect("127.0.0.1", 1024).sync().channel();
			channel.closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}


	}
	
}
