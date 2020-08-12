package priv.zxw.ratel.landlords.client.javafx.ui.view.index;


import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.zxw.ratel.landlords.client.javafx.ui.event.IIndexEvent;
import priv.zxw.ratel.landlords.client.javafx.ui.view.EventRegister;
import priv.zxw.ratel.landlords.client.javafx.ui.view.UIObject;

public class IndexEventRegister implements EventRegister {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexEventRegister.class);

    private UIObject uiObject;
    private IIndexEvent indexEvent;

    public IndexEventRegister(UIObject uiObject, IIndexEvent indexEvent) {
        this.uiObject = uiObject;
        this.indexEvent = indexEvent;

        registerEvent();
    }

    @Override
    public void registerEvent() {
        connectServer();
    }

    private void connectServer() {
        uiObject.$("connectButton", Button.class).setOnAction(e -> {
            String host = uiObject.$("host", TextField.class).getText().trim();
            int port = Integer.parseInt(uiObject.$("port", TextField.class).getText().trim());

            try {
                indexEvent.connect(host, port);
            } catch (Exception ex) {
                LOGGER.error(String.format("连接netty服务端(%s:%d)失败", host, port), ex);
                Platform.runLater(() -> ((IndexMethod) uiObject).setConnectServerErrorTips());
            }
        });
    }
}
