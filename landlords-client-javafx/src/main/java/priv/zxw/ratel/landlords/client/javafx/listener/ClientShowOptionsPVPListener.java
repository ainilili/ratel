package priv.zxw.ratel.landlords.client.javafx.listener;

import io.netty.channel.Channel;
import javafx.application.Platform;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ServerEventCode;
import priv.zxw.ratel.landlords.client.javafx.ui.view.lobby.LobbyController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.lobby.LobbyMethod;


public class ClientShowOptionsPVPListener extends AbstractClientListener {

    public ClientShowOptionsPVPListener() {
        super(ClientEventCode.CODE_SHOW_OPTIONS_PVP);
    }

    @Override
    public void handle(Channel channel, String json) {
        LobbyMethod lobbyMethod = (LobbyMethod) uiService.getMethod(LobbyController.METHOD_NAME);

        Platform.runLater(lobbyMethod::toggleToPVPMenu);
        ChannelUtils.pushToServer(channel, ServerEventCode.CODE_GET_ROOMS, null);
    }
}
