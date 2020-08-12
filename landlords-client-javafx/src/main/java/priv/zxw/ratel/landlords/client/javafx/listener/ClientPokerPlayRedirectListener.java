package priv.zxw.ratel.landlords.client.javafx.listener;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import javafx.application.Platform;
import javafx.scene.control.Label;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.zxw.ratel.landlords.client.javafx.ui.view.util.CountDownTask;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomMethod;
import priv.zxw.ratel.landlords.client.javafx.util.BeanUtil;
import priv.zxw.ratel.landlords.client.javafx.NettyClient;

public class ClientPokerPlayRedirectListener extends AbstractClientListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientPokerPlayRedirectListener.class);

    public ClientPokerPlayRedirectListener() {
        super(ClientEventCode.CODE_GAME_POKER_PLAY_REDIRECT);
    }

    @Override
    public void handle(Channel channel, String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);

        NettyClient nettyClient = BeanUtil.getBean("nettyClient");
        int sellClientId = jsonObject.getIntValue("sellClientId");
        String sellClientNickname = jsonObject.getString("sellClinetNickname");

        // 通知下一个玩家出牌
        if (sellClientId == nettyClient.getId()) {
            ClientListenerUtils.getListener(ClientEventCode.CODE_GAME_POKER_PLAY).handle(channel, json);
        }
        // 为下一个出牌的玩家设置倒计时定时器
        else {
            RoomMethod method = (RoomMethod) uiService.getMethod(RoomController.METHOD_NAME);

            Label timer = (Label) method.getTimer(sellClientNickname);
            CountDownTask task = new CountDownTask(timer, 30, n -> {}, i -> Platform.runLater(() -> timer.setText(i.toString())));
            BeanUtil.addBean(sellClientNickname, task.start());
        }
    }
}
