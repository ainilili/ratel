package priv.zxw.ratel.landlords.client.javafx.listener;

import io.netty.channel.Channel;
import org.nico.ratel.landlords.enums.ClientEventCode;

/**
 * @ClassName ClientShowOptionsPVEListener
 * @Desc TODO
 * @Author zxw
 * @Date 2020/8/7 10:14
 * @Version 1.0
 */
public class ClientShowOptionsPVEListener extends AbstractClientListener {

    public ClientShowOptionsPVEListener() {
        super(ClientEventCode.CODE_SHOW_OPTIONS_PVE);
    }

    @Override
    public void handle(Channel channel, String json) {
    }
}
