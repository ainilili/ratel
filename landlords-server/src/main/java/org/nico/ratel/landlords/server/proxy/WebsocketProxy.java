package org.nico.ratel.landlords.server.proxy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.robot.RobotDecisionMakers;
import org.nico.ratel.landlords.server.ServerContains;
import org.nico.ratel.landlords.server.handler.ProtobufTransferHandler;
import org.nico.ratel.landlords.server.handler.WebsocketTransferHandler;
import org.nico.ratel.landlords.server.timer.RoomClearTask;

import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class WebsocketProxy implements Proxy{
    @Override
    public void start(int port) throws InterruptedException {
        EventLoopGroup parentGroup = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        EventLoopGroup childGroup = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(parentGroup, childGroup)
                    .channel(Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new IdleStateHandler(60 * 30, 0, 0, TimeUnit.SECONDS))
                                    .addLast(new HttpServerCodec())
                                    .addLast(new ChunkedWriteHandler())
                                    .addLast(new HttpObjectAggregator(8192))
                                    .addLast("ws", new WebSocketServerProtocolHandler("/ratel"))
                                    .addLast(new WebsocketTransferHandler());
                        }
                    });

            ChannelFuture f = bootstrap .bind().sync();

            SimplePrinter.serverLog("The websocket server was successfully started on port " + port);
            //Init robot.
            RobotDecisionMakers.init();
            f.channel().closeFuture().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }

    }
}
