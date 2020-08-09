package priv.zxw.ratel.landlords.client.javafx.ui.view.lobby;


import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import priv.zxw.ratel.landlords.client.javafx.entity.RoomInfo;
import priv.zxw.ratel.landlords.client.javafx.ui.event.ILobbyEvent;

public class RoomPane {
    private Pane pane;

    private Label idLabel;
    private Label roomOwnerLabel;
    private Label modalLabel;
    private Label clientCountLabel;

    public RoomPane(RoomInfo roomInfo, ILobbyEvent lobbyEvent) {
        pane = new Pane();
        pane.setId("roomPane" + roomInfo.getRoomId());
        pane.setPrefSize(200, 150);
        pane.setStyle("-fx-border-color: black");
        pane.setCursor(Cursor.HAND);
        pane.setOnMouseClicked(e -> lobbyEvent.joinRoom(roomInfo.getRoomId()));
        ObservableList<Node> children = pane.getChildren();

        // 房间id label
        idLabel = new Label();
        idLabel.setLayoutX(14);
        idLabel.setLayoutY(14);
        idLabel.setText(roomInfo.getRoomId().toString());
        children.add(idLabel);

        // 房主label
        roomOwnerLabel = new Label();
        roomOwnerLabel.setLayoutX(14);
        roomOwnerLabel.setLayoutY(40);
        roomOwnerLabel.setText("房主:" + roomInfo.getRoomOwner());
        children.add(roomOwnerLabel);

        // 模式label
        modalLabel = new Label();
        modalLabel.setLayoutX(14);
        modalLabel.setLayoutY(112);
        modalLabel.setText(roomInfo.getRoomType() + "模式");
        children.add(modalLabel);

        // 当前人数
        clientCountLabel = new Label();
        clientCountLabel.setLayoutX(136);
        clientCountLabel.setLayoutY(112);
        clientCountLabel.setText(roomInfo.getRoomClientCount() + "/3人");
        children.add(clientCountLabel);
    }

    public Pane getPane() {
        return pane;
    }
}
