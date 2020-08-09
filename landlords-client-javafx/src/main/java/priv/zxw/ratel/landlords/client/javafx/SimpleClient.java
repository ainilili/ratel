package priv.zxw.ratel.landlords.client.javafx;

import javafx.application.Application;
import javafx.stage.Stage;
import priv.zxw.ratel.landlords.client.javafx.event.LobbyEvent;
import priv.zxw.ratel.landlords.client.javafx.event.LoginEvent;
import priv.zxw.ratel.landlords.client.javafx.event.RoomEvent;
import priv.zxw.ratel.landlords.client.javafx.ui.UIService;
import priv.zxw.ratel.landlords.client.javafx.ui.view.index.IndexController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.lobby.LobbyController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.login.LoginController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SimpleClient extends Application {

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void start(Stage primaryStage) throws Exception {
        UIService uiService = new UIService();
        uiService.addMethods(new IndexController(), new LoginController(new LoginEvent()),
                             new LobbyController(new LobbyEvent()), new RoomController(new RoomEvent()));
        uiService.getMethod(IndexController.METHOD_NAME).doShow();

        NettyClient nettyClient = new NettyClient(uiService);
        nettyClient.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
