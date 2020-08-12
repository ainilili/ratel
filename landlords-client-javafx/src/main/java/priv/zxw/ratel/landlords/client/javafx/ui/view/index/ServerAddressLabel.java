package priv.zxw.ratel.landlords.client.javafx.ui.view.index;

import javafx.scene.control.Label;

public class ServerAddressLabel {

    private Label label;

    public ServerAddressLabel(String serverAddress, int index) {
        label = new Label();
        label.setText(serverAddress);
        label.setLayoutY(index * 30);
        label.getStyleClass().add("remoteServerOption");
    }

    public Label getLabel() {
        return label;
    }
}
