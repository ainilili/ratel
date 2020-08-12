package priv.zxw.ratel.landlords.client.javafx.ui.view.lobby;


import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import priv.zxw.ratel.landlords.client.javafx.entity.RoomInfo;

public class RoomPane {
    private static final int MARGIN_TOP = 25;
    private static final int MARGIN_LEFT = 40;

    private Pane pane;

    public RoomPane(RoomInfo roomInfo, int index) {
        pane = new Pane();
        pane.getStyleClass().add("roomPane");
        pane.setLayoutX(35 + (index % 3) * (150 + MARGIN_LEFT));
        pane.setLayoutY(65 + ((index / 3) * (120 + MARGIN_TOP)));

        Label idLabel = new Label();
        idLabel.setLayoutX(6);
        idLabel.setLayoutY(3);
        idLabel.getStyleClass().add("idLabel");
        idLabel.setText(roomInfo.getRoomId().toString());

        Label roomOwnerLabel = new Label();
        roomOwnerLabel.setLayoutX(0);
        roomOwnerLabel.setLayoutY(22);
        roomOwnerLabel.getStyleClass().add("roomOwnerLabel");

        Label roomOwnerNameLabel = new Label();
        roomOwnerNameLabel.setLayoutX(34);
        roomOwnerNameLabel.setLayoutY(32);
        roomOwnerNameLabel.setText(roomInfo.getRoomOwner());

        Label modalLabel = new Label();
        modalLabel.setLayoutX(54);
        modalLabel.setLayoutY(60);
        modalLabel.getStyleClass().add("modalLabel");
        modalLabel.setText(roomInfo.getRoomType());

        Label playerCountLabel = new Label();
        playerCountLabel.setLayoutX(60);
        playerCountLabel.setLayoutY(100);
        playerCountLabel.getStyleClass().add("playerCountLabel");
        playerCountLabel.setText("当前人数：" + roomInfo.getRoomClientCount() + "/3");

        ObservableList<Node> children = pane.getChildren();
        children.add(idLabel);
        children.add(roomOwnerLabel);
        children.add(roomOwnerNameLabel);
        children.add(modalLabel);
        children.add(playerCountLabel);
    }

    public Pane getPane() {
        return pane;
    }
}
