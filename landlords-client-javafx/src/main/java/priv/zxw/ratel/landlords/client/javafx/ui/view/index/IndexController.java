package priv.zxw.ratel.landlords.client.javafx.ui.view.index;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import priv.zxw.ratel.landlords.client.javafx.ui.event.IIndexEvent;
import priv.zxw.ratel.landlords.client.javafx.ui.view.UIObject;

import java.io.IOException;
import java.util.List;

public class IndexController extends UIObject implements IndexMethod {
    public static final String METHOD_NAME = "index";

    private static final String RESOURCE_NAME = "view/index.fxml";

    private IIndexEvent indexEvent;
    private IndexEventRegister eventRegister;

    public IndexController(IIndexEvent indexEvent) throws IOException {
        super();

        this.indexEvent = indexEvent;

        root = FXMLLoader.load(getClass().getClassLoader().getResource(RESOURCE_NAME));
        setScene(new Scene(root));

        registerEvent();
    }

    @Override
    public void generateRemoteServerAddressOptions(List<String> remoteServerAddressList) {
        Pane remoteServerListPane = $("remoteServerListPane", Pane.class);

        for (int i = 0, size = remoteServerAddressList.size(); i < size; i++) {
            Label serverAddressLabel = new ServerAddressLabel(remoteServerAddressList.get(i), i).getLabel();
            serverAddressLabel.setOnMouseClicked(e -> {
                Label label = (Label) e.getSource();
                String remoteServerAddress = label.getText().trim();
                String[] strs = remoteServerAddress.split(":");

                $("host", TextField.class).setText(strs[0]);
                $("port", TextField.class).setText(strs[1]);
            });

            remoteServerListPane.getChildren().add(serverAddressLabel);
        }
    }

    @Override
    public void setFetchRemoteServerAddressErrorTips() {
        $("fetchServerAddressErrorTips", TextArea.class).setVisible(true);
    }

    @Override
    public void setConnectServerErrorTips() {
        $("connectServerErrorTips", Label.class).setVisible(true);
    }

    @Override
    public String getName() {
        return METHOD_NAME;
    }

    @Override
    public void doShow() {
        super.show();
    }

    @Override
    public void doClose() {
        super.close();
    }

    @Override
    public void registerEvent() {
        eventRegister = new IndexEventRegister(this, indexEvent);
    }
}
