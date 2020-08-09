package priv.zxw.ratel.landlords.client.javafx.ui.view.room;


import org.nico.ratel.landlords.entity.Poker;
import priv.zxw.ratel.landlords.client.javafx.ui.view.Method;

import java.util.List;

public interface RoomMethod extends Method {
    boolean isShow();

    void joinRoom();

    void startGame(List<Poker> pokers);

    void showRobButtons();

    void hideRobButtons();

    void showSurplusPokers(List<Poker> surplusPokers);

    void setLandLord(String landlordName);

    void showRecentPokers(List<Poker> recentPokers);

    void showPlayerMessage(String playerName, String message);

    void showTimer(String playerName, int secondTime);
}
