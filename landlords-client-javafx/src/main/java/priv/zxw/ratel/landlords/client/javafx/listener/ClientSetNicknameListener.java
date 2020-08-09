package priv.zxw.ratel.landlords.client.javafx.listener;

import io.netty.channel.Channel;
import javafx.application.Platform;
import org.nico.ratel.landlords.enums.ClientEventCode;
import priv.zxw.ratel.landlords.client.javafx.ui.view.Method;
import priv.zxw.ratel.landlords.client.javafx.ui.view.index.IndexController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.login.LoginController;

public class ClientSetNicknameListener extends AbstractClientListener {

    public ClientSetNicknameListener() {
        super(ClientEventCode.CODE_CLIENT_NICKNAME_SET);
    }

    @Override
    public void handle(Channel channel, String json) {
        Method indexMethod = uiService.getMethod(IndexController.METHOD_NAME);
        Method loginMethod = uiService.getMethod(LoginController.METHOD_NAME);

        Platform.runLater(() -> {
            indexMethod.doClose();
            loginMethod.doShow();
        });
    }
}
