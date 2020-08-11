package priv.zxw.ratel.landlords.client.javafx.listener;

import io.netty.channel.Channel;
import javafx.application.Platform;
import javafx.scene.control.Label;
import org.nico.ratel.landlords.enums.ClientEventCode;
import priv.zxw.ratel.landlords.client.javafx.BeanUtil;
import priv.zxw.ratel.landlords.client.javafx.entity.User;
import priv.zxw.ratel.landlords.client.javafx.ui.view.CountDownTask;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomController;
import priv.zxw.ratel.landlords.client.javafx.ui.view.room.RoomMethod;

public class ClientPokerPlayListener extends AbstractClientListener {

    public ClientPokerPlayListener() {
        super(ClientEventCode.CODE_GAME_POKER_PLAY);
    }

    @Override
    public void handle(Channel channel, String json) {
        // 1，展示按钮
        RoomMethod roomMethod = (RoomMethod) uiService.getMethod(RoomController.METHOD_NAME);
        Platform.runLater(() -> roomMethod.showPokerPlayButtons());

        // 2，设置倒计时定时器
        User user = BeanUtil.getBean("user");
        Label timer = (Label) roomMethod.getTimer(user.getNickname());

        // 在倒计时任务最终被执行后，隐藏页面元素，
        // 让服务端扫描到该客户端长时间未操作，发送踢出事件将其踢出
        CountDownTask.CountDownFuture future = BeanUtil.getBean(user.getNickname());
        // 当没有倒计时定时器或者之前的倒计时定时器已经结束，才设置定时器
        if (future == null || future.isDone()) {
            CountDownTask task = new CountDownTask(timer, 30,
                    node -> {
                        // 由于服务器查找挂机客户端响应太慢，此处直接模拟服务器发送 CODE_CLIENT_KICK 事件将玩家请出房间，
                        // 然后由客户端模拟机器人代替用户进行出牌，从而规避长时间的无响应对游戏的影响。
                        // 暨此处不使用服务器的机器人功能，而由客户端自己实现机器人功能来解决玩家挂机的问题。
                        Platform.runLater(() -> roomMethod.hidePokerPlayButtons());
                        ClientListenerUtils.getListener(ClientEventCode.CODE_CLIENT_KICK).handle(channel, null);
                    },
                    surplusTime -> Platform.runLater(() -> timer.setText(surplusTime.toString())));
            BeanUtil.addBean(user.getNickname(), task.start());
        }
    }
}
