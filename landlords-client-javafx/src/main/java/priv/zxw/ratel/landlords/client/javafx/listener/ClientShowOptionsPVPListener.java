package priv.zxw.ratel.landlords.client.javafx.listener;

import io.netty.channel.Channel;
import javafx.application.Platform;
import org.nico.ratel.landlords.enums.ClientEventCode;
import priv.zxw.ratel.landlords.client.javafx.ui.view.lobby.LobbyController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.lobby.LobbyMethod;

/**
 * @ClassName ClientShowOptionsPVPListener
 * @Desc TODO
 * @Author zxw
 * @Date 2020/8/7 10:13
 * @Version 1.0
 */
public class ClientShowOptionsPVPListener extends AbstractClientListener {

    public ClientShowOptionsPVPListener() {
        super(ClientEventCode.CODE_SHOW_OPTIONS_PVP);
    }

    @Override
    public void handle(Channel channel, String json) {
        LobbyMethod lobbyMethod = (LobbyMethod) uiService.getMethod(LobbyController.METHOD_NAME);

        Platform.runLater(lobbyMethod::toggleToPVPMenu);
    }
}
