package priv.zxw.ratel.landlords.client.javafx;

import com.alibaba.fastjson.JSONArray;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.nico.ratel.landlords.utils.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.zxw.ratel.landlords.client.javafx.event.IndexEvent;
import priv.zxw.ratel.landlords.client.javafx.event.LobbyEvent;
import priv.zxw.ratel.landlords.client.javafx.event.LoginEvent;
import priv.zxw.ratel.landlords.client.javafx.event.RoomEvent;
import priv.zxw.ratel.landlords.client.javafx.ui.UIService;
import priv.zxw.ratel.landlords.client.javafx.ui.view.index.IndexController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.index.IndexMethod;
import priv.zxw.ratel.landlords.client.javafx.ui.view.lobby.LobbyController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.login.LoginController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomController;
import priv.zxw.ratel.landlords.client.javafx.util.BeanUtil;

import java.io.IOException;
import java.net.URL;
import java.util.List;


public class SimpleClient extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleClient.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        UIService uiService = new UIService();
        IndexMethod indexMethod = new IndexController(new IndexEvent());
        uiService.addMethods(indexMethod, new LoginController(new LoginEvent()),
                             new LobbyController(new LobbyEvent()), new RoomController(new RoomEvent()));
        uiService.getMethod(IndexController.METHOD_NAME).doShow();

        NettyClient nettyClient = new NettyClient(uiService);
        BeanUtil.addBean("nettyClient", nettyClient);

        try {
            List<String> remoteServerAddressList = fetchRemoteServerAddresses();
            Platform.runLater(() -> indexMethod.generateRemoteServerAddressOptions(remoteServerAddressList));
        } catch (IOException e) {
            LOGGER.error("获取远程服务器列表失败", e);
            Platform.runLater(() -> indexMethod.setFetchRemoteServerAddressErrorTips());
        }
    }

    private List<String> fetchRemoteServerAddresses() throws IOException {
        String serverInfo = StreamUtils.convertToString(
                new URL("https://raw.githubusercontent.com/ainilili/ratel/master/serverlist.json"));
        return JSONArray.parseArray(serverInfo, String.class);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
