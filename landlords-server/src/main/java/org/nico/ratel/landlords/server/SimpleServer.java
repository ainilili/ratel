package org.nico.ratel.landlords.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;
import java.util.Timer;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.robot.RobotDecisionMakers;
import org.nico.ratel.landlords.server.handler.DefaultChannelInitializer;
import org.nico.ratel.landlords.server.timer.RoomClearTask;

public class SimpleServer {

	public static void main(String[] args) throws InterruptedException {
		if(args != null && args.length > 1) {
			if(args[0].equalsIgnoreCase("-p") || args[0].equalsIgnoreCase("-port")) {
				ServerContains.port = Integer.valueOf(args[1]);
			}
		}

		EventLoopGroup parentGroup = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
		EventLoopGroup childGroup = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap()
			.group(parentGroup, childGroup)
			.channel(Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
			.localAddress(new InetSocketAddress(ServerContains.port))
			.childHandler(new DefaultChannelInitializer());

			ChannelFuture f = bootstrap .bind().sync();

			SimplePrinter.serverLog("The server was successfully started on port " + ServerContains.port);
			//Init robot.
			RobotDecisionMakers.init();
			
			ServerContains.THREAD_EXCUTER.execute(() -> {
				Timer timer=new Timer();
				timer.schedule(new RoomClearTask(), 0L, 3000L);
			});
			f.channel().closeFuture().sync();
		} finally {
			parentGroup.shutdownGracefully();
			childGroup.shutdownGracefully();
		}


	}
}
