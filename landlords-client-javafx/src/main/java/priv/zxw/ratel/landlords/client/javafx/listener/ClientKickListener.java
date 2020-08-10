package priv.zxw.ratel.landlords.client.javafx.listener;

import io.netty.channel.Channel;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.nico.ratel.landlords.enums.ClientEventCode;
import priv.zxw.ratel.landlords.client.javafx.ui.view.Method;
import priv.zxw.ratel.landlords.client.javafx.ui.view.lobby.LobbyController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomController;

public class ClientKickListener extends AbstractClientListener {

    public ClientKickListener() {
        super(ClientEventCode.CODE_CLIENT_KICK);
    }

    @Override
    public void handle(Channel channel, String json) {
        Method lobbyMethod = uiService.getMethod(LobbyController.METHOD_NAME);
        RoomController roomController = (RoomController) uiService.getMethod(RoomController.METHOD_NAME);

        Platform.runLater(() -> {
            Label tips = ((Label) roomController.$("playerPane", Pane.class).lookup(".primary-tips"));
            tips.setVisible(true);
            tips.setText("长时间未操作，请出房间");

            roomController.doClose();
            lobbyMethod.doShow();
        });
    }
}
