package priv.zxw.ratel.landlords.client.javafx.listener;

import io.netty.channel.Channel;
import org.nico.ratel.landlords.enums.ClientEventCode;

public class ClientRestartGameListener extends AbstractClientListener {

    public ClientRestartGameListener() {
        super(ClientEventCode.CODE_GAME_LANDLORD_CYCLE);
    }

    @Override
    public void handle(Channel channel, String json) {
        System.out.println("无人抢地主，重新发牌");
    }
}
