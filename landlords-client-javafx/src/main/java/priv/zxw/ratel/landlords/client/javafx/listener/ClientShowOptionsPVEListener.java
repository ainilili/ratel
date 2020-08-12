package priv.zxw.ratel.landlords.client.javafx.listener;

import io.netty.channel.Channel;
import org.nico.ratel.landlords.enums.ClientEventCode;


public class ClientShowOptionsPVEListener extends AbstractClientListener {

    public ClientShowOptionsPVEListener() {
        super(ClientEventCode.CODE_SHOW_OPTIONS_PVE);
    }

    @Override
    public void handle(Channel channel, String json) {
    }
}
