package priv.zxw.ratel.landlords.client.javafx.ui.event;


public interface ILobbyEvent {

    void selectPVPModal();

    void selectPVEModal();

    void createPVPRoom();

    void showRooms();

    void joinRoom(int roomId);
}
