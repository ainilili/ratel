package priv.zxw.ratel.landlords.client.javafx.listener;


import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import javafx.application.Platform;
import javafx.scene.control.Label;
import org.nico.ratel.landlords.channel.ChannelUtils;
import org.nico.ratel.landlords.enums.ClientEventCode;
import org.nico.ratel.landlords.enums.ServerEventCode;
import priv.zxw.ratel.landlords.client.javafx.util.BeanUtil;
import priv.zxw.ratel.landlords.client.javafx.NettyClient;
import priv.zxw.ratel.landlords.client.javafx.entity.CurrentRoomInfo;
import priv.zxw.ratel.landlords.client.javafx.ui.view.util.CountDownTask;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomMethod;

import java.util.Collections;

public class ClientPassListener extends AbstractClientListener {

    public ClientPassListener() {
        super(ClientEventCode.CODE_GAME_POKER_PLAY_PASS);
    }

    @Override
    public void handle(Channel channel, String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);

        // 如果上一个出牌玩家是本玩家，则隐藏其出牌按钮
        RoomMethod method = (RoomMethod) uiService.getMethod(RoomController.METHOD_NAME);
        CurrentRoomInfo currentRoomInfo = BeanUtil.getBean("currentRoomInfo");
        String clientNickname = jsonObject.getString("clientNickname");
        if (clientNickname.equals(currentRoomInfo.getPlayer().getNickname())) {
            Platform.runLater(() -> method.hidePokerPlayButtons());
        }

        // 设置上一个出牌的玩家和牌
        currentRoomInfo.setRecentPlayerName(clientNickname);
        currentRoomInfo.setRecentPokers(Collections.emptyList());

        // 隐藏上一个玩家的倒计时定时器，显示其信息
        CountDownTask.CountDownFuture future = BeanUtil.getBean(clientNickname);
        if (future != null && !future.isDone()) {
            future.cancel();
        }

        Platform.runLater(() -> method.showPlayerMessage(clientNickname, "过"));

        // 显示下一个玩家的倒计时定时器
        String nextClientNickname = jsonObject.getString("nextClientNickname");

        Label timer = (Label) method.getTimer(nextClientNickname);
        CountDownTask task = new CountDownTask(timer, 30, n -> {}, i -> Platform.runLater(() -> timer.setText(i.toString())));
        BeanUtil.addBean(nextClientNickname, task.start());

        // 如果下一个出牌的是本玩家进行出牌重定向
        int turnClientId = jsonObject.getIntValue("nextClientId");
        NettyClient nettyClient = BeanUtil.getBean("nettyClient");
        if (turnClientId == nettyClient.getId()) {
            ChannelUtils.pushToServer(channel, ServerEventCode.CODE_GAME_POKER_PLAY_REDIRECT, null);
        }
    }
}
