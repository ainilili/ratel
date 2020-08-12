package priv.zxw.ratel.landlords.client.javafx.event;

import priv.zxw.ratel.landlords.client.javafx.util.BeanUtil;
import priv.zxw.ratel.landlords.client.javafx.NettyClient;
import priv.zxw.ratel.landlords.client.javafx.ui.event.IIndexEvent;

public class IndexEvent implements IIndexEvent {

    @Override
    public void connect(String host, int port) throws Exception {
        NettyClient nettyClient = BeanUtil.getBean("nettyClient");

        nettyClient.start(host, port);
    }
}
