package priv.zxw.ratel.landlords.client.javafx.ui.view.lobby;


import priv.zxw.ratel.landlords.client.javafx.entity.RoomInfo;
import priv.zxw.ratel.landlords.client.javafx.ui.view.Method;

import java.util.List;

public interface LobbyMethod extends Method {

    void toggleToPVPMenu();

    void toggleToPVEMenu();

    void showRoomList(List<RoomInfo> roomInfoList);

    void joinRoomFail(String message, String commentMessage);
}
