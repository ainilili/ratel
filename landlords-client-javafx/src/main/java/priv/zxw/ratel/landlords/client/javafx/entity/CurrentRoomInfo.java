package priv.zxw.ratel.landlords.client.javafx.entity;

import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.enums.ClientType;

import java.util.List;

public class CurrentRoomInfo {
    private int roomId;
    private String roomOwner;

    private User player;
    private String prevPlayerName;
    private ClientType prevPlayerRole;
    private String nextPlayerName;
    private ClientType nextPlayerRole;

    private String recentPlayerName;
    private List<Poker> recentPokers;

    public CurrentRoomInfo(int roomId, String roomOwner) {
        this.roomId = roomId;
        this.roomOwner = roomOwner;
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
}
