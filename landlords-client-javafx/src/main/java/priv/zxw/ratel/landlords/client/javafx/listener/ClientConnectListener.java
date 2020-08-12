package priv.zxw.ratel.landlords.client.javafx.listener;

import io.netty.channel.Channel;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.zxw.ratel.landlords.client.javafx.util.BeanUtil;
import priv.zxw.ratel.landlords.client.javafx.NettyClient;


public class ClientConnectListener extends AbstractClientListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientConnectListener.class);

    public ClientConnectListener() {
        super(ClientEventCode.CODE_CLIENT_CONNECT);
    }

    @Override
    public void handle(Channel channel, String json) {
        NettyClient nettyClient = BeanUtil.getBean("nettyClient");
        nettyClient.setId(Integer.parseInt(json));

        LOGGER.info("当前客户端被分配的id为 {}", nettyClient.getId());
    }
}
