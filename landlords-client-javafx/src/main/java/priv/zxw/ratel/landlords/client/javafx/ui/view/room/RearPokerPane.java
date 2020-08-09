package priv.zxw.ratel.landlords.client.javafx.ui.view.room;

import javafx.scene.layout.Pane;

public class RearPokerPane {
    private static final double MARGIN_TOP = 20;

    // start at 0
    private int index;

    private Pane pane;

    RearPokerPane(int index) {
        this.index = index;

        pane = new Pane();
        pane.getStyleClass().add("vertical-poker");
        pane.setLayoutX(15);
        pane.setLayoutY(index * MARGIN_TOP);
    }

    public Pane getPane() {
        return pane;
    }
}
