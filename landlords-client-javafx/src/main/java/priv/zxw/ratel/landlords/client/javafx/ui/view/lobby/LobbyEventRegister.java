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
        createPVERoom();
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

    private static final int SIMPLE_MODAL = 1;
    private static final int NORMAL_MODAL = 2;
    private static final int DIFFICULT_MODAL = 3;

    private void createPVERoom() {
        uiObject.$("simpleModalButton", Button.class).setOnAction(e -> lobbyEvent.createPVERoom(SIMPLE_MODAL));
        uiObject.$("normalModalButton", Button.class).setOnAction(e -> lobbyEvent.createPVERoom(NORMAL_MODAL));
        uiObject.$("difficultModalButton", Button.class).setOnAction(e -> lobbyEvent.createPVERoom(DIFFICULT_MODAL));
    }
}
