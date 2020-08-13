package priv.zxw.ratel.landlords.client.javafx.ui.view.room;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.enums.PokerLevel;
import org.nico.ratel.landlords.enums.PokerType;
import priv.zxw.ratel.landlords.client.javafx.util.BeanUtil;
import priv.zxw.ratel.landlords.client.javafx.entity.CurrentRoomInfo;

public class PokerPane {
    public static final int MARGIN_LEFT = 40;

    private Poker poker;
    // start at 0
    private int index;
    private int offsetX;

    private Pane pane;

    public PokerPane(int index, int offsetX, Poker poker) {
        this.poker = poker;
        this.index = index;
        this.offsetX = offsetX;

        if (PokerLevel.LEVEL_SMALL_KING.equals(poker.getLevel()) ||
                PokerLevel.LEVEL_BIG_KING.equals(poker.getLevel())) {
            createJokerPokerPane();
        } else {
            createNormalPokerPane();
        }

        pane.setOnMouseClicked(e -> {
            double y = pane.getLayoutY();
            boolean alreadyChecked = y == 0;
            CurrentRoomInfo currentRoomInfo = BeanUtil.getBean("currentRoomInfo");

            // 取消选中
            if (alreadyChecked) {
                pane.setLayoutY(y + 20);
                currentRoomInfo.removeUncheckedPoker(poker);
            }
            // 选中
            else {
                pane.setLayoutY(y - 20);
                currentRoomInfo.addCheckedPoker(poker);
            }
        });
    }

    private void createNormalPokerPane() {
        pane = new Pane();
        pane.getStyleClass().add("horizontal-poker");
        pane.setLayoutX(index * MARGIN_LEFT + offsetX);
        pane.setLayoutY(20);

        Text level = new Text();
        level.getStyleClass().add("level");
        level.setLayoutX(8);
        level.setLayoutY(34);
        level.setText(poker.getLevel().getName());

        Text typeSmall = new Text();
        typeSmall.getStyleClass().add("type-small");
        typeSmall.setLayoutX(8);
        typeSmall.setLayoutY(60);
        typeSmall.setText(poker.getType().getName());

        Text typeBig = new Text();
        typeBig.getStyleClass().add("type-big");
        typeBig.setLayoutX(50);
        typeBig.setLayoutY(120);
        typeBig.setText(poker.getType().getName());

        if (PokerType.CLUB.equals(poker.getType()) || PokerType.SPADE.equals(poker.getType())) {
            level.setFill(Paint.valueOf("black"));
            typeSmall.setFill(Paint.valueOf("black"));
            typeBig.setFill(Paint.valueOf("black"));
        } else if (PokerType.DIAMOND.equals(poker.getType()) || PokerType.HEART.equals(poker.getType())) {
            level.setFill(Paint.valueOf("#9c2023"));
            typeSmall.setFill(Paint.valueOf("#9c2023"));
            typeBig.setFill(Paint.valueOf("#9c2023"));
        }

        ObservableList<Node> children = pane.getChildren();
        children.add(level);
        children.add(typeSmall);
        children.add(typeBig);
    }

    private void createJokerPokerPane() {
        pane = new Pane();
        pane.getStyleClass().add("horizontal-poker");
        pane.setLayoutX(index * MARGIN_LEFT + offsetX);
        pane.setLayoutY(20);

        Text text1 = new Text();
        text1.getStyleClass().add("joker-level");
        text1.setLayoutX(13);
        text1.setLayoutY(26);
        text1.setText("J");

        Text text2 = new Text();
        text2.getStyleClass().add("joker-level");
        text2.setLayoutX(8);
        text2.setLayoutY(44);
        text2.setText("O");

        Text text3 = new Text();
        text3.getStyleClass().add("joker-level");
        text3.setLayoutX(10);
        text3.setLayoutY(62);
        text3.setText("K");

        Text text4 = new Text();
        text4.getStyleClass().add("joker-level");
        text4.setLayoutX(10);
        text4.setLayoutY(80);
        text4.setText("E");

        Text text5 = new Text();
        text5.getStyleClass().add("joker-level");
        text5.setLayoutX(10);
        text5.setLayoutY(98);
        text5.setText("R");

        Text logo = new Text();
        logo.setLayoutX(40);
        logo.setLayoutY(126);
        logo.setStyle("-fx-font-size: 28");
        logo.setStyle("-fx-text-fill: silver");
        logo.setText("ratel");

        if (PokerLevel.LEVEL_SMALL_KING.equals(poker.getLevel())) {
            text1.setFill(Paint.valueOf("black"));
            text2.setFill(Paint.valueOf("black"));
            text3.setFill(Paint.valueOf("black"));
            text4.setFill(Paint.valueOf("black"));
            text5.setFill(Paint.valueOf("black"));
        } else if (PokerLevel.LEVEL_BIG_KING.equals(poker.getLevel())) {
            text1.setFill(Paint.valueOf("#9c2023"));
            text2.setFill(Paint.valueOf("#9c2023"));
            text3.setFill(Paint.valueOf("#9c2023"));
            text4.setFill(Paint.valueOf("#9c2023"));
            text5.setFill(Paint.valueOf("#9c2023"));
        }

        ObservableList<Node> children = pane.getChildren();
        children.add(text1);
        children.add(text2);
        children.add(text3);
        children.add(text4);
        children.add(text5);
    }

    public Pane getPane() {
        return pane;
    }
}
