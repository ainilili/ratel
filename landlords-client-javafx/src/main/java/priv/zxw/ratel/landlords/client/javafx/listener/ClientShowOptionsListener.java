package priv.zxw.ratel.landlords.client.javafx.listener;


import io.netty.channel.Channel;
import javafx.application.Platform;
import org.nico.ratel.landlords.enums.ClientEventCode;
import priv.zxw.ratel.landlords.client.javafx.ui.view.Method;
import priv.zxw.ratel.landlords.client.javafx.ui.view.lobby.LobbyController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.login.LoginController;

public class ClientShowOptionsListener extends AbstractClientListener {

    public ClientShowOptionsListener() {
        super(ClientEventCode.CODE_SHOW_OPTIONS);
    }

    @Override
    public void handle(Channel channel, String json) {
        Method loginMethod = uiService.getMethod(LoginController.METHOD_NAME);
        Method lobbyMethod = uiService.getMethod(LobbyController.METHOD_NAME);

        Platform.runLater(() -> {
            loginMethod.doClose();
            lobbyMethod.doShow();
        });
    }
}
