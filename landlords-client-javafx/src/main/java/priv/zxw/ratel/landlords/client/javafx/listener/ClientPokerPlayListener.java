package priv.zxw.ratel.landlords.client.javafx.listener;

import io.netty.channel.Channel;
import javafx.application.Platform;
import javafx.scene.control.Label;
import org.nico.ratel.landlords.enums.ClientEventCode;
import priv.zxw.ratel.landlords.client.javafx.util.BeanUtil;
import priv.zxw.ratel.landlords.client.javafx.entity.User;
import priv.zxw.ratel.landlords.client.javafx.ui.view.util.CountDownTask;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomMethod;

public class ClientPokerPlayListener extends AbstractClientListener {

    public ClientPokerPlayListener() {
        super(ClientEventCode.CODE_GAME_POKER_PLAY);
    }

    @Override
    public void handle(Channel channel, String json) {
        User user = BeanUtil.getBean("user");

        // 隐藏出牌玩家之前出的牌
        // 展示按钮
        RoomMethod roomMethod = (RoomMethod) uiService.getMethod(RoomController.METHOD_NAME);
        Platform.runLater(() -> {
            roomMethod.hidePlayerRecentPokers(user.getNickname());
            roomMethod.showPokerPlayButtons();
        });

        // 设置倒计时定时器
        Label timer = (Label) roomMethod.getTimer(user.getNickname());

        // 在倒计时任务最终被执行后，隐藏页面元素，
        // 让服务端扫描到该客户端长时间未操作，发送踢出事件将其踢出
        CountDownTask.CountDownFuture future = BeanUtil.getBean(user.getNickname());
        // 当没有倒计时定时器或者之前的倒计时定时器已经结束，才设置定时器
        if (future == null || future.isDone()) {
            CountDownTask task = new CountDownTask(timer, 30,
                    node -> {
                        Platform.runLater(() -> roomMethod.hidePokerPlayButtons());
                    },
                    surplusTime -> Platform.runLater(() -> timer.setText(surplusTime.toString())));
            BeanUtil.addBean(user.getNickname(), task.start());
        }
    }
}
