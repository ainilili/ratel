package priv.zxw.ratel.landlords.client.javafx.entity;


public class RoomInfo {
    private Integer roomId;
    private String roomOwner;
    private Integer roomClientCount;
    private String roomType;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomOwner() {
        return roomOwner;
    }

    public void setRoomOwner(String roomOwner) {
        this.roomOwner = roomOwner;
    }

    public Integer getRoomClientCount() {
        return roomClientCount;
    }

    public void setRoomClientCount(Integer roomClientCount) {
        this.roomClientCount = roomClientCount;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
