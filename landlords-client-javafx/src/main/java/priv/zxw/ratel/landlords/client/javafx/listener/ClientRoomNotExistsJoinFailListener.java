package priv.zxw.ratel.landlords.client.javafx.listener;

import io.netty.channel.Channel;
import javafx.application.Platform;
import org.nico.ratel.landlords.enums.ClientEventCode;
import priv.zxw.ratel.landlords.client.javafx.ui.view.lobby.LobbyController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.lobby.LobbyMethod;

public class ClientRoomNotExistsJoinFailListener extends AbstractClientListener {

    public ClientRoomNotExistsJoinFailListener() {
        super(ClientEventCode.CODE_ROOM_JOIN_FAIL_BY_INEXIST);
    }

    @Override
    public void handle(Channel channel, String json) {
        LobbyMethod lobbyMethod = (LobbyMethod) uiService.getMethod(LobbyController.METHOD_NAME);

        Platform.runLater(() -> {
            lobbyMethod.joinRoomFail("房间已经不存在", "该房间已经不存在，可能房主已经解散该房间了，请挑选其它房间进行游戏。");
        });
    }
}
