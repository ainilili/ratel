package org.nico.ratel.landlords.client.entity;

public class User {
    public static final User INSTANCE = new User();

    /** 是否游戏中 */
    private boolean isPlaying = false;

    /** 是否观战中 */
    private boolean isWatching = false;

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
}
