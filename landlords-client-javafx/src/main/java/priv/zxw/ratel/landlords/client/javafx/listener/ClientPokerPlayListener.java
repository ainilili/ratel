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
        // TODO 1，重新渲染玩家手中的牌


        // 2，展示按钮
        RoomMethod roomMethod = (RoomMethod) uiService.getMethod(RoomController.METHOD_NAME);
        Platform.runLater(() -> roomMethod.showPokerPlayButtons());

        // 3，设置倒计时定时器
        User user = BeanUtil.getBean("user");
        Label timer = (Label) roomMethod.getTimer(user.getNickname());

        // 在倒计时任务最终被执行后，隐藏页面元素，
        // 让服务端扫描到该客户端长时间未操作，发送踢出事件将其踢出
        CountDownTask task = new CountDownTask(timer, 30,
                node -> Platform.runLater(() -> roomMethod.hidePokerPlayButtons()),
                surplusTime ->  Platform.runLater(() -> timer.setText(surplusTime.toString())));
        CountDownTask.CountDownFuture future = task.start();
        BeanUtil.addBean(user.getNickname(), future);
    }
}
