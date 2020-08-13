package priv.zxw.ratel.landlords.client.javafx.ui.view.room;


import javafx.scene.Node;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.enums.ClientType;
import priv.zxw.ratel.landlords.client.javafx.ui.view.Method;

import java.util.List;

public interface RoomMethod extends Method {
    boolean isShow();

    void joinRoom();

    void startGame(List<Poker> pokers);

    void gameOver(String winnerName, ClientType winnerType);

    void refreshPlayPokers(List<Poker> pokers);

    void refreshPrevPlayerPokers(int pokerCount);

    void refreshNextPlayerPokers(int pokerCount);

    void showRobButtons();

    void hideRobButtons();

    void showSurplusPokers(List<Poker> surplusPokers);

    void setLandLord(String landlordName);

    void showRecentPokers(String recentUsername, List<Poker> recentPokers);

    void showPlayerMessage(String playerName, String message);

    Node getTimer(String playerName);

    void showPokerPlayButtons();

    void hidePokerPlayButtons();
}
