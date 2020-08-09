package priv.zxw.ratel.landlords.client.javafx.ui.view;


import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.stage.Stage;
import priv.zxw.ratel.landlords.client.javafx.BeanUtil;
import priv.zxw.ratel.landlords.client.javafx.NettyClient;

public abstract class UIObject extends Stage {

    protected Parent root;

    public UIObject() {
        setTitle("ratel javafx客户端v1.0.0");

        // 退出程序
        setOnCloseRequest(e -> {
            close();
            Platform.exit();

            NettyClient nettyClient = BeanUtil.getBean("nettyClient");
            nettyClient.destroy();
        });
    }

    public <T> T $(String id, Class<T> clazz) {
        return (T) root.lookup("#" + id);
    }

    public abstract void registerEvent();
}