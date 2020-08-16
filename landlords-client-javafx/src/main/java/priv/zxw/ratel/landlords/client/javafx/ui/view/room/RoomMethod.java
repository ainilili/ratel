package priv.zxw.ratel.landlords.client.javafx.ui.view.room;


import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.enums.ClientType;
import priv.zxw.ratel.landlords.client.javafx.ui.view.Method;

import java.util.List;

public interface RoomMethod extends Method {
    boolean isShow();

    void joinRoom();

    void startGame(List<Poker> pokers);

    void gameOver(String winnerName, ClientType winnerType);

    void showPokers(String playerName, List<Poker> pokers);

    void showMessage(String playerName, String message);

    void play(String playerName);

    void refreshPlayPokers(List<Poker> pokers);

    void refreshPrevPlayerPokers(int pokerCount);

    void refreshNextPlayerPokers(int pokerCount);

    void showRobButtons();

    void hideRobButtons();

    void showSurplusPokers(List<Poker> surplusPokers);

    void setLandLord(String landlordName);

    void showPokerPlayButtons();

    void hidePokerPlayButtons();
}
