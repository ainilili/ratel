package priv.zxw.ratel.landlords.client.javafx.listener;


import io.netty.channel.Channel;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ServerEventCode;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomController;

public class ClientPokerLessListener extends AbstractClientListener {

    public ClientPokerLessListener() {
        super(ClientEventCode.CODE_GAME_POKER_PLAY_LESS);
    }

    @Override
    public void handle(Channel channel, String json) {
        // 出牌太少，不允许出牌，即简单的不响应用户操作即可
        RoomController roomController = (RoomController) uiService.getMethod(RoomController.METHOD_NAME);
        Platform.runLater(() -> {
            Label tips = ((Label) roomController.$("playerPane", Pane.class).lookup(".error-tips"));
            tips.setVisible(true);
            tips.setText("您的出牌应该大于上家的牌");
            roomController.delayHidden(tips, 2);
        });

        ChannelUtils.pushToServer(channel, ServerEventCode.CODE_GAME_POKER_PLAY_REDIRECT, null);
    }
}
