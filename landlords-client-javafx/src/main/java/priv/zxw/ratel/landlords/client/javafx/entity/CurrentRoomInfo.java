package priv.zxw.ratel.landlords.client.javafx.entity;

import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.enums.ClientType;

import java.util.ArrayList;
import java.util.List;

public class CurrentRoomInfo {
    private int roomId;
    private String roomOwner;

    private User player;
    private String prevPlayerName;
    private ClientType prevPlayerRole;
    private int prevPlayerSurplusPokerCount;
    private String nextPlayerName;
    private ClientType nextPlayerRole;
    private int nextPlayerSurplusPokerCount;

    private String recentPlayerName;
    private List<Poker> recentPokers;

    private List<Poker> checkedPokers;

    public CurrentRoomInfo(int roomId, String roomOwner) {
        this.roomId = roomId;
        this.roomOwner = roomOwner;
        this.checkedPokers = new ArrayList<>();
    }

    public void setLandlord(String landlordName) {
        if (landlordName.equals(player.getNickname())) {
            player.setRole(ClientType.LANDLORD);
            prevPlayerRole = nextPlayerRole = ClientType.PEASANT;
        } else if (landlordName.equals(prevPlayerName)) {
            prevPlayerRole = ClientType.LANDLORD;
            nextPlayerRole = ClientType.PEASANT;
            player.setRole(ClientType.PEASANT);
        } else {
            nextPlayerRole = ClientType.LANDLORD;
            prevPlayerRole = ClientType.PEASANT;
            player.setRole(ClientType.PEASANT);
        }
    }

    public String getRecentPlayerName() {
        return recentPlayerName;
    }

    public void setRecentPlayerName(String recentPlayerName) {
        this.recentPlayerName = recentPlayerName;
    }

    public List<Poker> getRecentPokers() {
        return recentPokers;
    }

    public void setRecentPokers(List<Poker> recentPokers) {
        this.recentPokers = recentPokers;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

    public User getPlayer() {
        return player;
    }

    public void setPrevPlayerName(String prevPlayerName) {
        this.prevPlayerName = prevPlayerName;
    }

    public void setNextPlayerName(String nextPlayerName) {
        this.nextPlayerName = nextPlayerName;
    }

    public String getPrevPlayerName() {
        return prevPlayerName;
    }

    public String getNextPlayerName() {
        return nextPlayerName;
    }

    public ClientType getPrevPlayerRole() {
        return prevPlayerRole;
    }

    public ClientType getNextPlayerRole() {
        return nextPlayerRole;
    }

    public void addCheckedPoker(Poker poker) {
        checkedPokers.add(poker);
    }

    public void removeUncheckedPoker(Poker poker) {
        checkedPokers.remove(poker);
    }

    public List<Poker> pollCheckedPokers() {
        List<Poker> pokers = new ArrayList<>(checkedPokers);
        checkedPokers.clear();

        return pokers;
    }

    public int getPrevPlayerSurplusPokerCount() {
        return prevPlayerSurplusPokerCount;
    }

    public void setPrevPlayerSurplusPokerCount(int prevPlayerSurplusPokerCount) {
        this.prevPlayerSurplusPokerCount = prevPlayerSurplusPokerCount;
    }

    public int getNextPlayerSurplusPokerCount() {
        return nextPlayerSurplusPokerCount;
    }

    public void setNextPlayerSurplusPokerCount(int nextPlayerSurplusPokerCount) {
        this.nextPlayerSurplusPokerCount = nextPlayerSurplusPokerCount;
    }
}
