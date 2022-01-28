package org.nico.ratel.landlords.client.entity;

public class User {
    public static final User INSTANCE = new User();

    /** 是否游戏中 */
    private volatile boolean isPlaying = false;

    /** 是否观战中 */
    private volatile boolean isWatching = false;

    private String nickname = "player";

    private User() {}

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public boolean isWatching() {
        return isWatching;
    }

    public void setWatching(boolean watching) {
        isWatching = watching;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
