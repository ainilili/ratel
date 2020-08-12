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
    }

    private void selectPVPModal() {
        uiObject.$("pvpModalButton", Button.class).setOnAction(e -> lobbyEvent.selectPVPModal());
    }

    private void selectPVEModal() {
        uiObject.$("pveModalButton", Button.class).setOnAction(e -> lobbyEvent.selectPVEModal());
    }

    private void createPVPRoom() {
        uiObject.$("createRoomButton", Button.class).setOnAction(e -> lobbyEvent.createPVPRoom());
    }
}
