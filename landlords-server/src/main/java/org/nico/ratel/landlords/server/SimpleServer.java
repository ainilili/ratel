package org.nico.ratel.landlords.server;

import java.net.InetSocketAddress;

import org.nico.ratel.landlords.server.handler.DefaultChannelInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SimpleServer {

	public static void main(String[] args) throws InterruptedException {
		
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
			ServerBootstrap b = new ServerBootstrap()
			.group(group)
			.channel(NioServerSocketChannel.class)
			.localAddress(new InetSocketAddress(ServerContains.PORT))
			.childHandler(new DefaultChannelInitializer());
			
			ChannelFuture f = b.bind().sync();
			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}
		
		
	}
}
