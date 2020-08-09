package priv.zxw.ratel.landlords.client.javafx.listener;

import io.netty.channel.Channel;
import javafx.application.Platform;
import org.nico.ratel.landlords.enums.ClientEventCode;
import priv.zxw.ratel.landlords.client.javafx.ui.view.lobby.LobbyController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.lobby.LobbyMethod;

public class ClientRoomFullJoinFailListener extends AbstractClientListener {

    public ClientRoomFullJoinFailListener() {
        super(ClientEventCode.CODE_ROOM_JOIN_FAIL_BY_FULL);
    }

    @Override
    public void handle(Channel channel, String json) {
        LobbyMethod lobbyMethod = (LobbyMethod) uiService.getMethod(LobbyController.METHOD_NAME);

        Platform.runLater(() -> {
            lobbyMethod.joinRoomFail("房间已经满人", "该房间人数已满，开始游戏，请挑选其它未满房间进行游戏。");
        });
    }
}
