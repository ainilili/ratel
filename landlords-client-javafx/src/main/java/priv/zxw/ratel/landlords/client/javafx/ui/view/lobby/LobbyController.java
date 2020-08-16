package priv.zxw.ratel.landlords.client.javafx.ui.view.lobby;


import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import priv.zxw.ratel.landlords.client.javafx.entity.RoomInfo;
import priv.zxw.ratel.landlords.client.javafx.ui.event.ILobbyEvent;
import priv.zxw.ratel.landlords.client.javafx.ui.view.util.AlertUtils;
import priv.zxw.ratel.landlords.client.javafx.ui.view.UIObject;

import java.io.IOException;
import java.util.List;

public class LobbyController extends UIObject implements LobbyMethod {
    public static final String METHOD_NAME = "lobby";

    private static final String RESOURCE_NAME = "view/lobby.fxml";

    private ILobbyEvent lobbyEvent;
    private LobbyEventRegister lobbyEventRegister;

    public LobbyController(ILobbyEvent lobbyEvent) throws IOException {
        super();

        root = FXMLLoader.load(getClass().getClassLoader().getResource(RESOURCE_NAME));
        setScene(new Scene(root));

        this.lobbyEvent = lobbyEvent;

        registerEvent();
    }

    @Override
    public void registerEvent() {
        lobbyEventRegister = new LobbyEventRegister(this, lobbyEvent);
    }

    @Override
    public void toggleToPVPMenu() {
        Pane modalPane = $("modalPane", Pane.class);
        modalPane.setVisible(false);

        Pane pvpMenuPane = $("pvpMenuPane", Pane.class);
        pvpMenuPane.setVisible(true);
    }

    @Override
    public void toggleToPVEMenu() {
        Pane modalPane = $("modalPane", Pane.class);
        modalPane.setVisible(false);

        Pane pveMenuPane = $("pveMenuPane", Pane.class);
        pveMenuPane.setVisible(true);
    }

    @Override
    public void showRoomList(List<RoomInfo> roomInfoList) {
        Pane roomsPane = $("roomsPane", Pane.class);

        for (int i = 0, size = roomInfoList.size(); i < size; i++) {
            RoomInfo roomInfo = roomInfoList.get(i);
            Pane roomPane = new RoomPane(roomInfo, i).getPane();
            roomPane.setOnMouseClicked(e -> lobbyEvent.joinRoom(roomInfo.getRoomId()));

            roomsPane.getChildren().add(roomPane);
        }
    }

    @Override
    public void joinRoomFail(String message, String commentMessage) {
        AlertUtils.warn(message, commentMessage);
    }

    @Override
    public String getName() {
        return METHOD_NAME;
    }

    @Override
    public void doShow() {
        super.show();
    }

    @Override
    public void doClose() {
        super.close();
    }
}
