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
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.nico.ratel.landlords.entity.ServerTransferData;
import org.nico.ratel.landlords.print.SimplePrinter;
import org.nico.ratel.landlords.robot.RobotDecisionMakers;
import org.nico.ratel.landlords.server.ServerContains;
import org.nico.ratel.landlords.server.handler.SecondProtobufCodec;
import org.nico.ratel.landlords.server.handler.ProtobufTransferHandler;
import org.nico.ratel.landlords.server.timer.RoomClearTask;

import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class ProtobufProxy implements Proxy{
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
                                .addLast(new ProtobufVarint32FrameDecoder())
                                .addLast(new ProtobufDecoder(ServerTransferData.ServerTransferDataProtoc.getDefaultInstance()))
                                .addLast(new ProtobufVarint32LengthFieldPrepender())
                                .addLast(new ProtobufEncoder())
                                .addLast(new SecondProtobufCodec())
                                .addLast(new ProtobufTransferHandler());
                        }
                    });

            ChannelFuture f = bootstrap .bind().sync();

            SimplePrinter.serverLog("The protobuf server was successfully started on port " + port);
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
