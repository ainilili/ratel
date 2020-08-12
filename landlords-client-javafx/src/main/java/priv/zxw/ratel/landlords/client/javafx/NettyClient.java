package priv.zxw.ratel.landlords.client.javafx;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.nico.noson.util.string.StringUtils;
import priv.zxw.ratel.landlords.client.javafx.listener.ClientListenerUtils;
import priv.zxw.ratel.landlords.client.javafx.handler.DefaultChannelInitializer;
import priv.zxw.ratel.landlords.client.javafx.ui.UIService;
import priv.zxw.ratel.landlords.client.javafx.util.BeanUtil;

import java.io.IOException;
import java.util.concurrent.*;


public class NettyClient {
    private String host;
    private int port;

    private ExecutorService executorService;

    private Channel channel;
    private EventLoopGroup workerGroup;

    private int id = -1;
    private String username;

    public NettyClient(UIService uiService) {
        executorService = Executors.newSingleThreadExecutor();

        ClientListenerUtils.setUIService(uiService);
    }

    public void start(String host, int port) throws Exception {
        if (StringUtils.isBlank(host)) {
            throw new IllegalArgumentException("不合法的host：" + host);
        }

        if (port < 0) {
            throw new IllegalArgumentException("不合法的端口：" + port);
        }

        this.host = host;
        this.port = port;

        try {
            // 直接启动netty会别javafx阻塞消息接受
            // 使用线程池启动则可以正常运行，why !!!!
            Future<Channel> channelFuture = executorService.submit(new ClientThread());
            Channel channel = channelFuture.get();

            if (!channel.isActive()) {
                Exception gotoCatch = new Exception();
                throw gotoCatch;
            }
        } catch (Exception e) {
            // 清理资源
            if (channel != null) {
                channel.close().syncUninterruptibly();
            }
            workerGroup.shutdownGracefully().syncUninterruptibly();

            throw new IOException(String.format("连接netty服务端(%s:%d)失败", host, port), e);
        }

        BeanUtil.addBean("channel", channel);
    }

    private class ClientThread implements Callable<Channel> {

        @Override
        public Channel call() throws Exception {
            workerGroup = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap()
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new DefaultChannelInitializer());
            channel = bootstrap.connect(host, port).syncUninterruptibly().channel();

            return channel;
        }
    }

    public void destroy() {
        if (channel == null) {
            return;
        }

        channel.close().syncUninterruptibly();
        workerGroup.shutdownGracefully().syncUninterruptibly();

        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
