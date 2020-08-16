package priv.zxw.ratel.landlords.client.javafx.ui.view.room;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import org.nico.ratel.landlords.entity.Poker;
import org.nico.ratel.landlords.enums.PokerLevel;
import org.nico.ratel.landlords.enums.PokerType;

public class ShowPokerPane {
    public static final int MARGIN_LEFT = 30;
    public static final int MARGIN_TOP = 40;

    private Poker poker;

    private Pane pane;

    public ShowPokerPane(Poker poker) {
        this.poker = poker;

        if (PokerLevel.LEVEL_SMALL_KING.equals(poker.getLevel()) ||
                PokerLevel.LEVEL_BIG_KING.equals(poker.getLevel())) {
            createJokerPokerPane();
        } else {
            createNormalPokerPane();
        }
    }

    private void createNormalPokerPane() {
        pane = new Pane();
        pane.getStyleClass().add("showPoker");

        Label level = new Label();
        level.setLayoutX(2);
        level.setLayoutY(-2);
        level.setText(poker.getLevel().getName());
        level.getStyleClass().add("level");

        Label type = new Label();
        type.setLayoutX(2);
        type.setLayoutY(12);
        type.setText(poker.getType().getName());
        type.getStyleClass().add("type-small");

        if (PokerType.CLUB.equals(poker.getType()) || PokerType.SPADE.equals(poker.getType())) {
            level.setStyle("-fx-text-fill: black");
            type.setStyle("-fx-text-fill: black");
        } else if (PokerType.DIAMOND.equals(poker.getType()) || PokerType.HEART.equals(poker.getType())) {
            level.setStyle("-fx-text-fill: #9c2023");
            type.setStyle("-fx-text-fill: #9c2023");
        }

        pane.getChildren().add(level);
        pane.getChildren().add(type);
    }

    private void createJokerPokerPane() {
        pane = new Pane();
        pane.getStyleClass().add("showPoker");

        Label label1 = new Label();
        label1.setLayoutX(6);
        label1.setLayoutY(0);
        label1.setText("J");
        label1.getStyleClass().add("joker-level");

        Label label2 = new Label();
        label2.setLayoutX(4);
        label2.setLayoutY(13);
        label2.setText("O");
        label2.getStyleClass().add("joker-level");

        Label label3 = new Label();
        label3.setLayoutX(5);
        label3.setLayoutY(26);
        label3.setText("K");
        label3.getStyleClass().add("joker-level");

        if (PokerLevel.LEVEL_SMALL_KING.equals(poker.getLevel())) {
            label1.setStyle("-fx-text-fill: black");
            label2.setStyle("-fx-text-fill: black");
            label3.setStyle("-fx-text-fill: black");
        } else if (PokerLevel.LEVEL_BIG_KING.equals(poker.getLevel())) {
            label1.setStyle("-fx-text-fill: #9c2023");
            label2.setStyle("-fx-text-fill: #9c2023");
            label3.setStyle("-fx-text-fill: #9c2023");
        }

        pane.getChildren().add(label1);
        pane.getChildren().add(label2);
        pane.getChildren().add(label3);
    }

    public void setLayout(double x, double y) {
        pane.setLayoutX(x);
        pane.setLayoutY(y);
    }

    public Pane getPane() {
        return pane;
    }
}
