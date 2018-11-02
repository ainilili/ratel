package org.nico.ratel.landlords.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.nico.ratel.landlords.client.handler.TransferHandler;
import org.nico.ratel.landlords.handler.DefaultDecoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

public class SimpleClient {

	public static void main(String[] args) throws InterruptedException, IOException {

		EventLoopGroup group = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap()
					.group(group)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>(){

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							pipeline.addLast("decoder", new DefaultDecoder());
							pipeline.addLast("encoder", new ByteArrayEncoder());
							pipeline.addLast("handler", new TransferHandler());
						}

					});
			Channel channel = b.connect("127.0.0.1", 1024).sync().channel();
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				channel.writeAndFlush(reader.readLine().getBytes());
			}
		} finally {
			group.shutdownGracefully().sync();
		}


	}
}
