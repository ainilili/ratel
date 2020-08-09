package priv.zxw.ratel.landlords.client.javafx.ui.view.lobby;


import javafx.scene.control.Button;
import priv.zxw.ratel.landlords.client.javafx.ui.event.ILobbyEvent;
import priv.zxw.ratel.landlords.client.javafx.ui.view.EventRegister;
import priv.zxw.ratel.landlords.client.javafx.ui.view.UIObject;

public class LobbyEventRegister implements EventRegister {

    private UIObject uiObject;
    private ILobbyEvent lobbyEvent;

    public LobbyEventRegister(UIObject uiObject, ILobbyEvent lobbyEvent) {
        this.uiObject = uiObject;
        this.lobbyEvent = lobbyEvent;

        registerEvent();
    }

    @Override
    public void registerEvent() {
        selectPVPModal();
        selectPVEModal();
        createPVPRoom();
        showRooms();
    }

    private void selectPVPModal() {
        uiObject.$("pvpButton", Button.class).setOnAction(e -> lobbyEvent.selectPVPModal());
    }

    private void selectPVEModal() {
        uiObject.$("pveButton", Button.class).setOnAction(e -> lobbyEvent.selectPVEModal());
    }

    private void createPVPRoom() {
        uiObject.$("createRoomButton", Button.class).setOnAction(e -> lobbyEvent.createPVPRoom());
    }

    private void showRooms() {
        uiObject.$("listRoomsButton", Button.class).setOnAction(e -> lobbyEvent.showRooms());
    }
}
