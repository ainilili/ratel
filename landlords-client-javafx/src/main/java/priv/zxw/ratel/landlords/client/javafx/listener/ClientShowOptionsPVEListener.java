package priv.zxw.ratel.landlords.client.javafx.listener;

import io.netty.channel.Channel;
import javafx.application.Platform;
import org.nico.ratel.landlords.enums.ClientEventCode;
import priv.zxw.ratel.landlords.client.javafx.ui.view.lobby.LobbyController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.lobby.LobbyMethod;


public class ClientShowOptionsPVEListener extends AbstractClientListener {

    public ClientShowOptionsPVEListener() {
        super(ClientEventCode.CODE_SHOW_OPTIONS_PVE);
    }

    @Override
    public void handle(Channel channel, String json) {
        LobbyMethod lobbyMethod = (LobbyMethod) uiService.getMethod(LobbyController.METHOD_NAME);

        Platform.runLater(lobbyMethod::toggleToPVEMenu);
    }
}
