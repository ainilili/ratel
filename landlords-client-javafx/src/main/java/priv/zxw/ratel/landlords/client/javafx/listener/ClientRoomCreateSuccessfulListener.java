package priv.zxw.ratel.landlords.client.javafx.listener;


import io.netty.channel.Channel;
import javafx.application.Platform;
import org.nico.ratel.landlords.enums.ClientEventCode;
import priv.zxw.ratel.landlords.client.javafx.ui.view.Method;
import priv.zxw.ratel.landlords.client.javafx.ui.view.lobby.LobbyController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomMethod;

public class ClientRoomCreateSuccessfulListener extends AbstractClientListener {

    public ClientRoomCreateSuccessfulListener() {
        super(ClientEventCode.CODE_ROOM_CREATE_SUCCESS);
    }

    @Override
    public void handle(Channel channel, String json) {
        Method lobbyMethod = uiService.getMethod(LobbyController.METHOD_NAME);
        RoomMethod roomMethod = (RoomMethod) uiService.getMethod(RoomController.METHOD_NAME);

        Platform.runLater(() -> {
            lobbyMethod.doClose();
            roomMethod.joinRoom();
        });
    }
}
