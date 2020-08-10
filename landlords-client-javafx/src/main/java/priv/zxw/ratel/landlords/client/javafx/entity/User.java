package priv.zxw.ratel.landlords.client.javafx.entity;

import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.enums.ClientType;

import java.util.List;
import java.util.stream.Collectors;

public class User {
    private String nickname;

    private boolean playing = false;
    private int currentRoomId = -1;
    private List<Poker> pokers;

    // null, ClientType.LANDLORD, ClientType.PEASANT
    private ClientType role;

    public User(String nickname) {
        this.nickname = nickname;
    }

    public void joinRoom(int roomId) {
        currentRoomId = roomId;
        playing = true;
    }

    public void exitRoom() {
        currentRoomId = -1;
        playing = false;
        role = null;
        pokers.clear();
        pokers = null;
    }

    public void addPokers(List<Poker> pokers) {
        if (this.pokers != null) {
            this.pokers.addAll(pokers);
        } else {
            this.pokers = pokers;
        }

        this.pokers = this.pokers.stream()
                                 .sorted((a, b) -> Integer.compare(b.getLevel().getLevel(), a.getLevel().getLevel()))
                                 .collect(Collectors.toList());
    }

    public void removePokers(List<Poker> sellPokerList) {
        for (Poker sellPoker : sellPokerList) {
            pokers.remove(sellPoker);
        }
    }

    public List<Poker> getPokers() {
        return pokers;
    }

    public ClientType getRole() {
        return role;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setRole(ClientType role) {
        this.role = role;
    }

    public String getNickname() {
        return nickname;
    }
}
