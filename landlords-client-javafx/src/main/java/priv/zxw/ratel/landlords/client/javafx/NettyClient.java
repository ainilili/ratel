package priv.zxw.ratel.landlords.client.javafx;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import priv.zxw.ratel.landlords.client.javafx.listener.ClientListenerUtils;
import priv.zxw.ratel.landlords.client.javafx.handler.DefaultChannelInitializer;
import priv.zxw.ratel.landlords.client.javafx.ui.UIService;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class NettyClient {
    private static String host = "127.0.0.1";
    private static int port = 1024;

    private ExecutorService executorService;

    private Channel channel;
    private EventLoopGroup workerGroup;

    private int id = -1;
    private String username;

    public NettyClient(UIService uiService) {
        executorService = Executors.newSingleThreadExecutor();

        ClientListenerUtils.setUIService(uiService);
    }

    public void start() {
        // 直接启动netty会别javafx阻塞消息接受
        // 使用线程池启动则可以正常运行，why !!!!
        executorService.submit(new ClientThread());
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
            BeanUtil.addBean("channel", channel);
            BeanUtil.addBean("nettyClient", NettyClient.this);

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
