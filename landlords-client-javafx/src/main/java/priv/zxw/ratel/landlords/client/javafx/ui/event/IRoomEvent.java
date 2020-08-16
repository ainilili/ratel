package priv.zxw.ratel.landlords.client.javafx.ui.event;

import org.nico.ratel.landlords.entity.Poker;

import java.util.List;

public interface IRoomEvent {
    void robLandlord();

    void notRobLandlord();

    void submitPokers(List<Poker> pokerList);

    void passRound();

    void exit();
}
