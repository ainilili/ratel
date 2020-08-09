package priv.zxw.ratel.landlords.client.javafx.ui.view.room;

import javafx.scene.layout.Pane;

public class SurplusPokerPane {
    public static final double MARGIN_LEFT = 40;

    private int index;

    private Pane pane;

    public SurplusPokerPane(int index) {
        this.index = index;

        pane = new Pane();
        pane.getStyleClass().add("surplus-poker");
        pane.setLayoutX(45 + index * (MARGIN_LEFT + 110));
    }

    public Pane getPane() {
        return pane;
    }
}
