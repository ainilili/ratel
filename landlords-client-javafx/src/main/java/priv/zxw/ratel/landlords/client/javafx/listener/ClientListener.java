package priv.zxw.ratel.landlords.client.javafx.listener;

import io.netty.channel.Channel;
import org.nico.ratel.landlords.enums.ClientEventCode;
import priv.zxw.ratel.landlords.client.javafx.ui.UIService;


public interface ClientListener {
    void handle(Channel channel, String json);

    ClientEventCode getCode();

    void setUIService(UIService uiService);
}
